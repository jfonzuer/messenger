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

  deleteConversationObservable: Observable<Conversation>;
  private deleteConversationObserver: Observer<Conversation>;

  isConversationReadObservable: Observable<boolean>;
  private isConversationReadObserver: Observer<boolean>;


  constructor() {
    this.changeConversationObservable = new Observable<Conversation>(observer => this.changeConversationObserver = observer).share();
    this.deleteConversationObservable = new Observable<Conversation>(observer => this.deleteConversationObserver = observer).share();
    this.addMessageObservable = new Observable<Message>(observer => this.addMessageObserver = observer).share();
    this.isConversationReadObservable = new Observable<boolean>(observer => this.isConversationReadObserver = observer).share();
    this.addConversationObservable = new Observable<Conversation>(observer => this.addConversationObserver = observer).share();
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
}
