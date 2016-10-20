import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {Observable} from "rxjs";
import {Input} from "@angular/core/src/metadata/directives";
import {Conversation} from "../../../model/conversation";
import {ConversationService} from "../../../service/conversation.service";
import {SharedService} from "../../../service/shared.service";

@Component({
  selector: 'app-conversation',
  templateUrl: 'conversation.component.html',
  styleUrls: ['conversation.component.css'],
})
export class ConversationComponent implements OnInit {

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
