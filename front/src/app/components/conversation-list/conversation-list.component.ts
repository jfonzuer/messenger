import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {Conversation} from "../../model/conversation";
import {SharedService} from "../../services/shared.service";
import {Observable} from "rxjs";
import {ConversationService} from "../../services/conversation.service";

@Component({
  selector: 'app-conversation-list',
  templateUrl: './conversation-list.component.html',
  styleUrls: ['./conversation-list.component.css']
})
export class ConversationListComponent implements OnInit {

  @Input() conversations: Conversation;
  @Output() setConversation = new EventEmitter();
  timer:Observable<number>;

  // filter
  name:string;

  constructor(private conversationService: ConversationService, private sharedService: SharedService) { }

  ngOnInit() {
  }

  onSelect(conversation : Conversation) {
    conversation.read = true;
    this.setConversation.emit(conversation);
  }
}
