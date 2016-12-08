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

@Component({
  selector: 'app-message-list',
  templateUrl: 'message-list.component.html',
  styleUrls: ['message-list.component.css']
})
export class MessageListComponent implements OnInit {

  selectedConversation:Conversation;
  messages: Message[] = [];
  pager:Pager;

  getMessageTimer:Observable<number>;
  formatMessageTime:Observable<number>;

  // subscriptions
  changeConversationSubscription: any;
  isReadConversationSubscription: any;
  addMessageSubscription:any;
  addConversationSubscription:any;
  refreshMessagesSubscription:any;

  isRead:boolean;


  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();


  constructor(private messageService: MessageService, private messengerService:MessengerService, private datetimeService:DatetimeService, private conversationService:ConversationService) {

    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.changeConversation(conversation));
    this.isReadConversationSubscription = this.messengerService.isConversationReadObservable.subscribe(read => this.isRead = read);
    this.addMessageSubscription = this.messengerService.addMessageObservable.subscribe(message => { message.sendDate = moment().toISOString(); this.messages.push(message); });
    this.addConversationSubscription = this.messengerService.addConversationObservable.subscribe(conversation => { this.pager = null; this.selectedConversation = conversation; this.getMessages(this.selectedConversation.userTwo.id)});
  }

  ngOnInit() {
    moment().locale('fr');
  }

  deleteConversation() : void {
    //this.deleteConversationEmitter.emit(this.selectedConversation);


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

  private changeConversation(conversation:Conversation) {
    this.selectedConversation = conversation;
    this.pager = null;
    this.getMessages(this.selectedConversation.userTwo.id);
    this.isRead = this.selectedConversation.readByUserTwo;
    this.defineTimers(this.selectedConversation.id);
  }

  private getMessages(userId : number) {
    this.messageService.getMessages(userId, this.pager).then(response => {
        this.concatMessage(response);
        this.pager = new Pager(response.number, response.last, response.size);
      })
      .catch(e => this.errorEmitter.emit(e));
  }

  private getNewerMessages() {

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
      // coder endpoint de l'api pr chercher les messages de la conversation avec un id supérieur à celui du dernier message
      //this.messageTimer.subscribe(t => this.getMessages(this.selectedConversation.userTwo.id));
      //this.getMessageTimer.subscribe(t => { console.log(this.messages); this.datetimeService.formatMessages(this.messages); });
      this.getMessageTimer = Observable.timer(0, 60000);
      this.getMessageTimer.subscribe(t => { this.datetimeService.formatMessages(this.messages); });
    }
  }
}
