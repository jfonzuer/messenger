import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {Conversation} from "../../model/conversation";
import {Message} from "../../model/message";
import {MessageService} from "../../services/message.service";

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.css']
})
export class MessageListComponent implements OnInit {

  @Input() selectedConversation:Conversation;
  @Input() messages: Message[] = [];
  @Output() deleteConversationEmitter = new EventEmitter();

  constructor(private messageService: MessageService) { }

  ngOnInit() {
  }

  deleteConversation() : void {
    this.deleteConversationEmitter.emit(this.selectedConversation);
  }
}
