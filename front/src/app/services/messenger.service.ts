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

  deleteConversationObservable: Observable<Conversation>;
  private deleteConversationObserver: Observer<Conversation>;

  isConversationReadObservable: Observable<boolean>;
  private isConversationReadObserver: Observer<boolean>;

  blockUserObservable: Observable<boolean>;
  private blockUserObserver: Observer<boolean>;


  constructor() {
    this.changeConversationObservable = new Observable<Conversation>(observer => this.changeConversationObserver = observer).share();
    this.deleteConversationObservable = new Observable<Conversation>(observer => this.deleteConversationObserver = observer).share();
    this.addMessageObservable = new Observable<Message>(observer => this.addMessageObserver = observer).share();
    this.receiveMessageObservable = new Observable<Message>(observer => this.receiveMessageObserver = observer).share();
    this.isConversationReadObservable = new Observable<boolean>(observer => this.isConversationReadObserver = observer).share();
    this.addConversationObservable = new Observable<Conversation>(observer => this.addConversationObserver = observer).share();
    this.blockUserObservable = new Observable<boolean>(observer => this.blockUserObserver = observer).share();
  }

  receiveMessage(message:Message) {
    this.receiveMessageObserver.next(message);
  }

  changeConversation(conversation:Conversation) {
    this.changeConversationObserver.next(conversation);
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
