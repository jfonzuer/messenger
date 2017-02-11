import {Component, OnInit, EventEmitter, Output, Input, ViewContainerRef} from '@angular/core';
import {Observable} from "rxjs";
import {ConversationService} from "../../../services/conversation.service";
import {SharedService} from "../../../services/shared.service";
import {Conversation} from "../../../model/conversation";
import {environment} from "../../../../environments/environment";
import {MessengerService} from "../../../services/messenger.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-conversation-list',
  templateUrl: 'conversation-list.component.html',
  styleUrls: ['conversation-list.component.css']
})
export class ConversationListComponent implements OnInit {

  conversations: Conversation[] = [];
  selectedConversation:Conversation;

  // filter
  name:string;
  uploadImageUrl:string;

  // subscriptions
  addMessageSubscription:any;
  deleteConversationSubscription:any;
  addConversationSubscription:any;
  changeConversationSubscription:any;
  updateConversationSubscription:any;

  constructor(private conversationService: ConversationService, private sharedService: SharedService, private messengerService:MessengerService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.uploadImageUrl = environment.uploadImageUrl;
    this.toastr.setRootViewContainerRef(vRef);

    this.addMessageSubscription = this.messengerService.addMessageObservable.subscribe(message => this.getConversations());
    this.deleteConversationSubscription = this.messengerService.deleteConversationObservable.subscribe(conversation => this.conversations = this.conversations.filter(c => c.id != conversation.id));
    //this.addConversationSubscription = this.messengerService.addConversationObservable.subscribe(conversation => this.addConversation(conversation));
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.selectedConversation = conversation);
    this.updateConversationSubscription = this.messengerService.updateConversationObservable.subscribe(conversation => this.updateConversation(conversation));
  }

  ngOnInit() {
    this.selectedConversation = new Conversation();
    this.selectedConversation.id = 0;
    this.getConversations();
  }

  onSelect(conversation : Conversation) {
    conversation.readByUserOne = true;
    this.selectedConversation = conversation;
    this.messengerService.changeConversation(conversation);
  }

  private updateConversation(conversation:Conversation) {
    let c = this.conversations.find(c => c.id === conversation.id);
    // si la conversation existe, on la met à jour, sinon, il s'agit d'une nouvelle et dans ce cas on la concatène en premier.
    c ? conversation = c : [c].concat(this.conversations);
  }

  private getConversations() {
    this.conversationService.getAll().then(response => {
      this.conversations = response.content;

      // on refresh le readByUserTwo
      let selectedConversation = this.conversations.find(c => c.id == this.selectedConversation.id);
      this.messengerService.changeConversationRead(selectedConversation ? selectedConversation.readByUserTwo : this.selectedConversation.readByUserTwo);
    }).catch(error => this.toastr.error(error));
  }

  /*
  private addConversation(conversation:Conversation) {
    console.log(this.selectedConversation.userTwo);
    this.selectedConversation = conversation;
    this.getConversations();
  }
  */
}
