import {Component, Input, OnDestroy, OnInit, ViewContainerRef} from "@angular/core";
import {Message} from "../../../model/message";
import {MessageService} from "../../../services/message.service";
import {Observable} from "rxjs";
import {MessengerService} from "../../../services/messenger.service";
import {DatetimeService} from "../../../services/datetime.service";
import * as moment from "moment/moment";
import "moment/locale/fr";
import {ConversationService} from "../../../services/conversation.service";
import {environment} from "../../../../environments/environment";
import {UserService} from "../../../services/user.service";
import {SharedService} from "../../../services/shared.service";
import {User} from "../../../model/user";
import {ToastsManager} from "ng2-toastr";
import {Conversation} from "../../../model/conversation";
import {LoggerService} from "../../../services/logger.service";

@Component({
  selector: 'app-message-list',
  templateUrl: 'message-list.component.html',
  styleUrls: ['message-list.component.css']
})
export class MessageListComponent implements OnInit, OnDestroy {

  @Input() selectedConversation: Conversation;
  @Input() messages: Message[] = [];

  selectedImage: Message;


  formatMessageTimer: Observable<number>;

  // subscriptions
  changeConversationSubscription: any;
  isReadConversationSubscription: any;
  addConversationSubscription: any;
  receiveMessageSubscription: any;
  addMessageSubscription: any;

  isRead: boolean;
  isUserBlocked: boolean;
  hasPreviousMessage: boolean;
  uploadImageUrl: string;

  constructor(private messageService: MessageService, private messengerService: MessengerService, private datetimeService: DatetimeService,
              private conversationService: ConversationService, private us: UserService, private sharedService: SharedService, private toastr: ToastsManager,
              vRef: ViewContainerRef, private logger:LoggerService) {
    this.uploadImageUrl = environment.uploadImageUrl;
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    moment().locale('fr');

    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.changeConversation(conversation));
    this.isReadConversationSubscription = this.messengerService.isConversationReadObservable.subscribe(read => this.isRead = read);
    this.addConversationSubscription = this.messengerService.addConversationObservable.subscribe(conversation => this.addConversation(conversation));
    this.receiveMessageSubscription = this.messengerService.receiveMessageObservable.subscribe(message => this.addMessage(message));
    this.addMessageSubscription = this.messengerService.addMessageObservable.subscribe(message => { this.isRead = false; } );

    this.hasPreviousMessage = true;
  }

  block() {
    const currentUser: User = this.sharedService.getCurrentUser();
    this.us.blockUser(this.selectedConversation.userTwo)
      .then(users => {
        currentUser.blockedUsers = users as User[];
        this.sharedService.refreshUser(currentUser);
        this.isUserBlocked = true;
        this.toastr.success('L\'utilisateur a bien été bloqué');
        this.messengerService.blockUser(true);
      })
      .catch(error => this.toastr.error(error));
  }

  unblock() {
    const currentUser: User = this.sharedService.getCurrentUser();
    this.us.unblockUser(this.selectedConversation.userTwo)
      .then(users => {
        currentUser.blockedUsers = users as User[];
        this.sharedService.refreshUser(currentUser);
        this.isUserBlocked = false;
        this.toastr.success('L\'utilisateur a bien été débloqué');
        this.messengerService.blockUser(false);
      })
      .catch(error => this.toastr.error(error));
  }

  addConversation(conversation: Conversation) {
    this.logger.log('addConversation event', conversation);
  }

  deleteConversation() : void {
    // on supprime la conversation de la liste
    if (confirm('Êtes vous sûr de vouloir supprimer la conversation ?')) {
      this.messengerService.deleteConversation(this.selectedConversation);
      this.conversationService.remove(this.selectedConversation).then().catch(error => this.toastr.error(error));
      this.selectedConversation = null;
      this.messages = [];
    }
  }
  scrollUp() {
    this.getPreviousMessage(this.selectedConversation.userTwo.id);
  }

  private addMessage(message: Message) {
    this.logger.log('receive Message', message);

    message.sendSince = moment(message.sendDate).fromNow();
    this.messages.push(message);
  }

  private changeConversation(conversation: Conversation) {
    this.hasPreviousMessage = true;
    this.selectedConversation = conversation;
    this.defineTimers(this.selectedConversation.id);
    this.isUserBlocked = this.sharedService.isUserBlocked(conversation.userTwo);
    this.logger.log('isUserBlocked', this.isUserBlocked);
  }

  private getPreviousMessage(userId) {

    if (this.messages.length > 0) {
      this.logger.log('message list component getPreviousMessage with lastMessageId', this.messages[0].id);
      this.messageService.getPreviousMessages(userId, this.messages[0].id).then((response: any) => {
        this.logger.log('message list component getPreviousMessage', response);
        if (response.length == 0) {
          this.hasPreviousMessage = false;
        }
        const messages = response as Message[];
        this.datetimeService.formatMessages(messages);
        this.messages.length > 0 ? this.messages = messages.concat(this.messages) : this.messages = messages;
      })
        .catch(e => error => this.toastr.error(e));
    }
  }

  private defineTimers(userId: number): void {
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
