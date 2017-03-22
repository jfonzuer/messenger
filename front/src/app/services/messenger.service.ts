import {Injectable} from "@angular/core";
import {Observable, Observer} from "rxjs";
import {Conversation} from "../model/conversation";
import {Message} from "../model/message";
/**
 * Created by pgmatz on 05/12/16.
 */

@Injectable()
export class MessengerService {

  changeConversationObservable: Observable<Conversation>;
  private changeConversationObserver: Observer<Conversation>;

  addConversationObservable: Observable<Conversation>;
  private addConversationObserver: Observer<Conversation>;

  addMessageObservable: Observable<Message>;
  private addMessageObserver:Observer<Message>;

  receiveMessageObservable: Observable<Message>;
  private receiveMessageObserver:Observer<Message>;

  updateConversationObservable: Observable<Conversation>;
  private updateConversationObserver:Observer<Conversation>;

  deleteConversationObservable: Observable<Conversation>;
  private deleteConversationObserver: Observer<Conversation>;

  isConversationReadObservable: Observable<boolean>;
  private isConversationReadObserver: Observer<boolean>;

  blockUserObservable: Observable<boolean>;
  private blockUserObserver: Observer<boolean>;

  conversationLoadedObservable: Observable<boolean>;
  private conversationLoadedObserver: Observer<boolean>;


  constructor() {
    this.changeConversationObservable = new Observable<Conversation>(observer => this.changeConversationObserver = observer).share();
    this.deleteConversationObservable = new Observable<Conversation>(observer => this.deleteConversationObserver = observer).share();
    this.addMessageObservable = new Observable<Message>(observer => this.addMessageObserver = observer).share();
    this.receiveMessageObservable = new Observable<Message>(observer => this.receiveMessageObserver = observer).share();
    this.updateConversationObservable = new Observable<Conversation>(observer => this.updateConversationObserver = observer).share();
    this.isConversationReadObservable = new Observable<boolean>(observer => this.isConversationReadObserver = observer).share();
    this.addConversationObservable = new Observable<Conversation>(observer => this.addConversationObserver = observer).share();
    this.blockUserObservable = new Observable<boolean>(observer => this.blockUserObserver = observer).share();
    this.conversationLoadedObservable = new Observable<boolean>(observer => this.conversationLoadedObserver = observer).share();
  }

  receiveMessage(message:Message) {
    this.receiveMessageObserver.next(message);
  }

  updateConversation(conversation:Conversation) {
    this.updateConversationObserver.next(conversation);
  }

  changeConversation(conversation:Conversation) {
    console.error("change conversation event");
    this.changeConversationObserver.next(conversation);
  }

  /**
   * Is triggered after getting all messages from conversation
   */
  conversationLoaded() {
    this.conversationLoadedObserver.next(true);
  }

  changeConversationRead(read:boolean) {
    this.isConversationReadObserver.next(read);
  }

  addMessage(message:Message) {
    this.addMessageObserver.next(message);
  }

  deleteConversation(conversation:Conversation) {
    this.deleteConversationObserver.next(conversation);
  }

  addConversation(conversation:Conversation) {
    console.log("add conversation observer");
    this.addConversationObserver.next(conversation);
  }

  blockUser(block:boolean) {
    this.blockUserObserver.next(block);
  }
}
