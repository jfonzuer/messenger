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

  // TODO : get user from localstorage
  currentUser: User = new User();
  @Input() selectedConversation: Conversation;
  @Output() addMessage = new EventEmitter();
  @Output() addConversation = new EventEmitter();
  message: Message = new Message();
  enter:boolean = true;
  error:string;

  constructor(private messageService: MessageService, private conversationService: ConversationService) { }

  ngOnInit() {
    // TODO : get user from localstorage
    this.currentUser.id = 1;
    this.currentUser.username = 'member1';
  }

  send() {
    console.log(this.selectedConversation);

    if (this.selectedConversation) {
      console.log(this.message);
      this.message.source =  this.currentUser;

      if (this.selectedConversation.id) {
        this.message.conversation = this.selectedConversation;
        console.log(this.message);
        this.messageService.post(this.message)
            .then(response => {
              this.addMessage.emit(response);
              this.message.content = ''
            })
            // si erreur on fixe le message d'erreur et on set un timeout
            .catch(error => {
              this.error = error;
              setTimeout(() => this.error = "", 2000);
            });
        // s'il s'agit d'une nouvelle conversation
      } else {
        console.log("create new conversation");
        this.conversationService.post(new UserMessage(this.selectedConversation.userTwo, this.message)).then(response => {
          console.log(response);
          this.message.content = '';
          this.addConversation.emit();
        });
      }
    }
  }

  sendEnter() {
    if (this.enter) {
      this.send();
    }
  }
}
