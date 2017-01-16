import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
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

@Component({
  selector: 'app-message-list',
  templateUrl: 'message-list.component.html',
  styleUrls: ['message-list.component.css']
})
export class MessageListComponent implements OnInit {

  selectedConversation:Conversation;
  selectedImage:Message;
  messages: Message[] = [];
  pager:Pager;

  getMessageTimer:Observable<number>;
  formatMessageTimer:Observable<number>;

  // subscriptions
  changeConversationSubscription: any;
  isReadConversationSubscription: any;
  addMessageSubscription:any;
  addConversationSubscription:any;

  isRead:boolean;
  isUserBlocked:boolean;
  uploadUrl:string;


  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();

  constructor(private messageService: MessageService, private messengerService:MessengerService, private datetimeService:DatetimeService, private conversationService:ConversationService, private us: UserService, private sharedService: SharedService) {
    this.uploadUrl = environment.uploadUrl;
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.changeConversation(conversation));
    this.isReadConversationSubscription = this.messengerService.isConversationReadObservable.subscribe(read => this.isRead = read);
    this.addMessageSubscription = this.messengerService.addMessageObservable.subscribe(message => this.addMessage(message));
    this.addConversationSubscription = this.messengerService.addConversationObservable.subscribe(conversation => { this.pager = null; this.selectedConversation = conversation; this.getMessages(this.selectedConversation.userTwo.id)});
  }

  ngOnInit() {
    moment().locale('fr');
  }

  block() {
    if (confirm("Êtes vous sûr de vouloir bloquer cet utilisateur ?")) {
      let currentUser:User = this.sharedService.getCurrentUser();
      this.us.blockUser(this.selectedConversation.userTwo)
        .then(users => {
          currentUser.blockedUsers = users;
          this.sharedService.refreshUser(currentUser);
          this.isUserBlocked = true;
          this.messengerService.blockUser(true);
        })
        .catch(error => this.errorEmitter.emit(error));
    }
  }

  unblock() {
    if (confirm("Êtes vous sûr de vouloir débloquer cet utilisateur ?")) {
      let currentUser:User = this.sharedService.getCurrentUser();
      this.us.unblockUser(this.selectedConversation.userTwo)
        .then(users => {
          currentUser.blockedUsers = users;
          this.sharedService.refreshUser(currentUser);
          this.isUserBlocked = false;
          this.messengerService.blockUser(false);
        })
        .catch(error => this.errorEmitter.emit(error));
    }
  }

  deleteConversation() : void {
    // on supprime la conversation de la liste
    if (confirm("Êtes vous sûr de vouloir supprimer la conversation ?")) {
      this.messengerService.deleteConversation(this.selectedConversation);
      this.conversationService.remove(this.selectedConversation).then().catch(error => this.errorEmitter.emit(error));
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
    console.log("add message event : ", message);
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
    console.debug("is user blocked", this.isUserBlocked);
  }

  private getMessages(userId : number) {
    this.messageService.getMessages(userId, this.pager).then(response => {
      console.debug("messages", response);
      this.concatMessage(response);
      this.pager = new Pager(response.number, response.last, response.size);
    })
      .catch(e => this.errorEmitter.emit(e));
  }

  private getNewerMessages() {
    if (this.messages.length > 0) {
      this.messageService.getNewerMessages(this.selectedConversation.userTwo.id, this.messages[this.messages.length - 1].id).then(messages => {
        console.log(messages);
        this.datetimeService.formatMessages(messages);
        this.messages = this.messages.concat(messages);
      })
    }
  }

  private concatMessage(response: any) {
    if (response.content) {
      let messages: Message[] = response.content;
      this.datetimeService.formatMessages(messages);
      this.pager == null ? this.messages = messages : this.messages = messages.concat(this.messages);
    }
  }

  private defineTimers(userId: number) : void {
    if (!this.getMessageTimer) {
      this.getMessageTimer = Observable.timer(0, 2000);
      //this.getMessageTimer.subscribe(t => { this.getNewerMessages(); });
      this.formatMessageTimer = Observable.timer(0, 60000);
      this.formatMessageTimer.subscribe(t => { this.datetimeService.formatMessages(this.messages); });
    }
  }
}
