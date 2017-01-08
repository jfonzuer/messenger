import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {Observable} from "rxjs";
import {ConversationService} from "../../../services/conversation.service";
import {SharedService} from "../../../services/shared.service";
import {Conversation} from "../../../model/conversation";
import {environment} from "../../../../environments/environment";
import {MessengerService} from "../../../services/messenger.service";

@Component({
  selector: 'app-conversation-list',
  templateUrl: 'conversation-list.component.html',
  styleUrls: ['conversation-list.component.css']
})
export class ConversationListComponent implements OnInit {

  conversations: Conversation[] = [];
  selectedConversation:Conversation;

  // timers
  conversationTimer:Observable<number>;

  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();

  // filter
  name:string;
  uploadUrl:string;

  // subscriptions
  addMessageSubscription:any;
  deleteConversationSubscription:any;
  addConversationSubscription:any;
  changeConversationSubscription:any;

  constructor(private conversationService: ConversationService, private sharedService: SharedService, private messengerService:MessengerService) {
    this.uploadUrl = environment.uploadUrl;

    this.addMessageSubscription = this.messengerService.addMessageObservable.subscribe(message => this.getConversations());
    this.deleteConversationSubscription = this.messengerService.deleteConversationObservable.subscribe(conversation => this.conversations = this.conversations.filter(c => c.id != conversation.id));
    this.addConversationSubscription = this.messengerService.addConversationObservable.subscribe(conversation => this.addConversation(conversation));
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.selectedConversation = conversation);
  }

  ngOnInit() {
    this.selectedConversation = new Conversation();
    this.selectedConversation.id = 0;
    this.getConversations();
    this.defineConversationTimer();
  }

  onSelect(conversation : Conversation) {
    conversation.readByUserOne = true;
    this.selectedConversation = conversation;
    this.messengerService.changeConversation(conversation);
  }

  private defineConversationTimer() : void {
    this.conversationTimer = Observable.timer(0, 60000);
    //this.conversationTimer.subscribe(t => this.getConversations());
  }

  private getConversations() {
    this.conversationService.getAll().then(response => {
      // si on a un pager côté back
      this.conversations = response.content;

      // on refresh le readByUserTwo
      let selectedConversation = this.conversations.find(c => c.id == this.selectedConversation.id);
      this.messengerService.changeConversationRead(selectedConversation ? selectedConversation.readByUserTwo : this.selectedConversation.readByUserTwo);
    }).catch(error => this.errorEmitter.emit(error));
  }

  private addConversation(conversation:Conversation) {
    console.log(this.selectedConversation.userTwo);
    this.selectedConversation = conversation;
    this.getConversations();
  }
}
