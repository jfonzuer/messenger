import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {Message} from "../../../model/message";
import {ConversationService} from "../../../service/conversation.service";
import {User} from "../../../model/user";
import {Conversation} from "../../../model/conversation";
import {UserMessage} from "../../../model/userMessage";
import {MessageService} from "../../../service/message.service";

@Component({
  selector: 'app-send',
  templateUrl: 'send.component.html',
  styleUrls: ['send.component.css']
})
export class SendComponent implements OnInit {

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

      // s'il s'agit d'une nouvelle conversation
      if (!this.selectedConversation.id) {

        console.log("create new conversation");

        this.conversationService.post(new UserMessage(this.selectedConversation.userTwo, this.message )).then(response => {
          console.log(response);
          this.message.content = '';
          this.addConversation.emit();
        });
      }

      // sinon envoi simple d'un message une conversation est sélectionnée
      else {
        this.message.conversation = this.selectedConversation;
        console.log(this.message);
        this.messageService.post(this.message)
          .then(response => { this.addMessage.emit(response); this.message.content = '' })
          // si erreur on fixe le message d'erreur et on set un timeout
          .catch(error => { this.error = error; setTimeout(() => this.error = "", 2000); });
      }
    }
  }

  sendEnter() {
    if (this.enter) {
      this.send();
    }
  }

}
