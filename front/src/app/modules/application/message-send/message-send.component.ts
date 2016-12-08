import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {MessageService} from "../../../services/message.service";
import {ConversationService} from "../../../services/conversation.service";
import {User} from "../../../model/user";
import {Conversation} from "../../../model/conversation";
import {Message} from "../../../model/message";
import {UserMessage} from "../../../model/userMessage";
import {MessengerService} from "../../../services/messenger.service";

@Component({
  selector: 'app-message-send',
  templateUrl: 'message-send.component.html',
  styleUrls: ['message-send.component.css']
})
export class MessageSendComponent implements OnInit {

  @Input() user: User;
  selectedConversation: Conversation;
  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();
  changeConversationSubscription: any;

  message: Message;
  enter:boolean = true;

  constructor(private messageService: MessageService, private conversationService: ConversationService, private messengerService:MessengerService) {
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.selectedConversation = conversation);
  }

  ngOnInit() {
    this.message = new Message();
    this.message.source = this.user;
  }

  send() {
    if (this.selectedConversation) {
      // si l'id n'existe pas on crée une conversation
      this.selectedConversation.id ? this.sendMessage() : this.createConversation();
    }
  }

  sendEnter() {
    if (this.enter) {
      this.send();
    }
  }

  private sendMessage() {
    this.message.conversation = this.selectedConversation;
    this.messageService.post(this.message).then(response => { this.messengerService.addMessage(response); this.message.content = ''; }).catch(error => this.errorEmitter.emit(error) );
  }

  private createConversation() {
    console.log("create conversation");
    this.conversationService.post(new UserMessage(this.selectedConversation.userTwo, this.message)).then(response => {
      this.message.content = '';
      console.log("conversation response");
      console.log(response);
      this.selectedConversation = response;
      this.messengerService.addConversation(this.selectedConversation);
    }).catch(error => this.errorEmitter.emit(error));
  }
}
