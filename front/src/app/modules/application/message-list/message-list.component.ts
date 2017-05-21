import {Component, OnInit, Input, Output, EventEmitter, ViewContainerRef, OnDestroy} from '@angular/core';
import {Conversation} from "../../../model/conversation";
import {Message} from "../../../model/message";
import {MessageService} from "../../../services/message.service";
import {Pager} from "../../../model/pager";
import {Observable} from "rxjs";
import {MessengerService} from "../../../services/messenger.service";
import {DatetimeService} from "../../../services/datetime.service";
import * as moment from 'moment/moment';
import 'moment/locale/fr';
import {ConversationService} from "../../../services/conversation.service";
import {environment} from "../../../../environments/environment";
import {UserService} from "../../../services/user.service";
import {SharedService} from "../../../services/shared.service";
import {User} from "../../../model/user";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-message-list',
  templateUrl: 'message-list.component.html',
  styleUrls: ['message-list.component.css']
})
export class MessageListComponent implements OnInit, OnDestroy {

  selectedConversation:Conversation;
  selectedImage:Message;
  messages: Message[] = [];
  pager:Pager;

  formatMessageTimer:Observable<number>;

  // subscriptions
  changeConversationSubscription: any;
  isReadConversationSubscription: any;
  addConversationSubscription:any;
  receiveMessageSubscription:any;
  addMessageSubscription:any;

  isRead:boolean;
  isUserBlocked:boolean;
  uploadImageUrl:string;

  constructor(private messageService: MessageService, private messengerService:MessengerService, private datetimeService:DatetimeService, private conversationService:ConversationService, private us: UserService, private sharedService: SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.uploadImageUrl = environment.uploadImageUrl;
    this.toastr.setRootViewContainerRef(vRef);

  }

  ngOnInit() {
    moment().locale('fr');

    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.changeConversation(conversation));
    this.isReadConversationSubscription = this.messengerService.isConversationReadObservable.subscribe(read => this.isRead = read);
    this.addConversationSubscription = this.messengerService.addConversationObservable.subscribe(conversation => { this.pager = null; this.selectedConversation = conversation; this.getMessages(this.selectedConversation.userTwo.id)});
    this.receiveMessageSubscription = this.messengerService.receiveMessageObservable.subscribe(message => this.addMessage(message));
    this.addMessageSubscription = this.messengerService.addMessageObservable.subscribe(message => { this.isRead = false; } );
  }

  block() {
    let currentUser:User = this.sharedService.getCurrentUser();
    this.us.blockUser(this.selectedConversation.userTwo)
      .then(users => {
        currentUser.blockedUsers = users;
        this.sharedService.refreshUser(currentUser);
        this.isUserBlocked = true;
        this.toastr.success("L'utilisateur a bien été bloqué")
        this.messengerService.blockUser(true);
      })
      .catch(error => this.toastr.error(error));
  }

  unblock() {
    let currentUser:User = this.sharedService.getCurrentUser();
    this.us.unblockUser(this.selectedConversation.userTwo)
      .then(users => {
        currentUser.blockedUsers = users;
        this.sharedService.refreshUser(currentUser);
        this.isUserBlocked = false;
        this.toastr.success("L'utilisateur a bien été débloqué")
        this.messengerService.blockUser(false);
      })
      .catch(error => this.toastr.error(error));
  }

  deleteConversation() : void {
    // on supprime la conversation de la liste
    if (confirm("Êtes vous sûr de vouloir supprimer la conversation ?")) {
      this.messengerService.deleteConversation(this.selectedConversation);
      this.conversationService.remove(this.selectedConversation).then().catch(error => this.toastr.error(error));
      this.selectedConversation = null;
      this.messages = [];
      this.pager = null;
    }
  }
  scrollUp() {
    this.pager.page = this.pager.page + 1;
    this.getMessages(this.selectedConversation.userTwo.id);
  }

  private addMessage(message:Message) {
    message.sendSince = moment(message.sendDate).fromNow();
    this.messages.push(message);
  }

  private changeConversation(conversation:Conversation) {
    this.selectedConversation = conversation;
    this.pager = null;
    this.getMessages(this.selectedConversation.userTwo.id);
    this.isRead = this.selectedConversation.readByUserTwo;
    this.defineTimers(this.selectedConversation.id);

    this.isUserBlocked = this.sharedService.isUserBlocked(conversation.userTwo);

    //console.debug("is user blocked", this.isUserBlocked);
  }

  private getMessages(userId : number) {
    this.messageService.getMessages(userId, this.pager).then(response => {
      //console.debug("messages", response);
      this.concatMessage(response);
      this.pager = new Pager(response.number, response.last, response.size, 10);

      // trigger conversation loaded event
      this.messengerService.conversationLoaded();
    })
      .catch(e => error => this.toastr.error(e));
  }

  private concatMessage(response: any) {
    if (response.content) {
      let messages: Message[] = response.content;
      this.datetimeService.formatMessages(messages);
      this.pager == null ? this.messages = messages : this.messages = messages.concat(this.messages);
    }
  }

  private defineTimers(userId: number) : void {
    this.formatMessageTimer = Observable.timer(0, 60000);
    this.formatMessageTimer.subscribe(t => { this.datetimeService.formatMessages(this.messages); });
  }


  public ngOnDestroy(): void {
    this.changeConversationSubscription.unsubscribe();
    this.isReadConversationSubscription.unsubscribe();
    this.addConversationSubscription.unsubscribe();
    this.receiveMessageSubscription.unsubscribe();
    this.addMessageSubscription.unsubscribe();
  }
}
