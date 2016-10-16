import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import * as moment from 'moment/moment';
import {Message} from "../../../model/message";
import {MessageService} from "../../../service/message.service";
import {Conversation} from "../../../model/conversation";

@Component({
  selector: 'app-message',
  templateUrl: 'message.component.html',
  styleUrls: ['message.component.css']
})
export class MessageComponent implements OnInit {

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
