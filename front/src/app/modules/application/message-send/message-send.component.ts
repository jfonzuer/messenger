import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {MessageService} from "../../../services/message.service";
import {ConversationService} from "../../../services/conversation.service";
import {User} from "../../../model/user";
import {Conversation} from "../../../model/conversation";
import {Message} from "../../../model/message";
import {UserMessage} from "../../../model/userMessage";

@Component({
  selector: 'app-message-send',
  templateUrl: 'message-send.component.html',
  styleUrls: ['message-send.component.css']
})
export class MessageSendComponent implements OnInit {

  @Input() user: User;
  @Input() selectedConversation: Conversation;
  @Output() addMessage = new EventEmitter();
  @Output() addConversation = new EventEmitter();
  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();

  message: Message;
  enter:boolean = true;
  error:string;

  constructor(private messageService: MessageService, private conversationService: ConversationService) { }

  ngOnInit() {
    this.message = new Message();
    this.message.source = this.user;
  }

  send() {
    if (this.selectedConversation) {
      if (this.selectedConversation.id) {
        this.message.conversation = this.selectedConversation;

        this.messageService.post(this.message)
            .then(response => {
              this.addMessage.emit(response);
              this.message.content = '';
            })
            .catch(error => this.errorEmitter.emit(error) );
        // s'il s'agit d'une nouvelle conversation
      } else {
        console.log("create new conversation");
        this.conversationService.post(new UserMessage(this.selectedConversation.userTwo, this.message)).then(response => {
          this.message.content = '';
          this.addConversation.emit();
        }).catch(error => this.errorEmitter.emit(error));
      }
    }
  }

  sendEnter() {
    if (this.enter) {
      this.send();
    }
  }
}
