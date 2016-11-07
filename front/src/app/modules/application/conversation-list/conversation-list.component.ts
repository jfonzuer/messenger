import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {Observable} from "rxjs";
import {ConversationService} from "../../../services/conversation.service";
import {SharedService} from "../../../services/shared.service";
import {Conversation} from "../../../model/conversation";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-conversation-list',
  templateUrl: 'conversation-list.component.html',
  styleUrls: ['conversation-list.component.css']
})
export class ConversationListComponent implements OnInit {

  @Input() conversations: Conversation[];
  @Output() setConversation = new EventEmitter();
  selectedConversation:Conversation;
  timer:Observable<number>;

  // filter
  name:string;
  uploadUrl:string;

  constructor(private conversationService: ConversationService, private sharedService: SharedService) {
    this.uploadUrl = environment.uploadUrl;
  }

  ngOnInit() {
    this.selectedConversation = new Conversation();
    this.selectedConversation.id = 0;
  }

  onSelect(conversation : Conversation) {
    conversation.read = true;
    this.selectedConversation = conversation;
    this.setConversation.emit(conversation);
  }
}
