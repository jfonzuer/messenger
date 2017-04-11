import {Component, OnInit, EventEmitter, Output, Input, ViewContainerRef, OnDestroy} from '@angular/core';
import {Observable} from "rxjs";
import {ConversationService} from "../../../services/conversation.service";
import {SharedService} from "../../../services/shared.service";
import {Conversation} from "../../../model/conversation";
import {environment} from "../../../../environments/environment";
import {MessengerService} from "../../../services/messenger.service";
import {ToastsManager} from "ng2-toastr";
import {Message} from "../../../model/message";

@Component({
  selector: 'app-conversation-list',
  templateUrl: 'conversation-list.component.html',
  styleUrls: ['conversation-list.component.css']
})
export class ConversationListComponent implements OnInit, OnDestroy {

  conversations: Conversation[] = [];
  selectedConversation:Conversation;

  // filter
  name:string;
  uploadImageUrl:string;

  // subscriptions
  deleteConversationSubscription:any;
  changeConversationSubscription:any;
  updateConversationSubscription:any;
  addConversationSubscription:any;
  receiveMessageSubscription:any;

  constructor(private conversationService: ConversationService, private sharedService: SharedService, private messengerService:MessengerService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.uploadImageUrl = environment.uploadImageUrl;
    this.toastr.setRootViewContainerRef(vRef);

    this.deleteConversationSubscription = this.messengerService.deleteConversationObservable.subscribe(conversation => this.conversations = this.conversations.filter(c => c.id != conversation.id));
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.selectedConversation = conversation);
    this.updateConversationSubscription = this.messengerService.updateConversationObservable.subscribe(conversation => this.updateConversation(conversation));
    this.addConversationSubscription = this.messengerService.addConversationObservable.subscribe(conversation => this.addConversation(conversation));
    this.receiveMessageSubscription = this.messengerService.receiveMessageObservable.subscribe(message => this.checkIfConversationExists(message));
  }

  ngOnInit() {
    this.selectedConversation = new Conversation();
    this.selectedConversation.id = 0;
    this.getConversations();
  }

  onSelect(conversation : Conversation) {
    if (conversation.id != this.selectedConversation.id) {
      conversation.readByUserOne = true;
      this.selectedConversation = conversation;
      this.messengerService.changeConversation(conversation);
    }
  }

  private updateConversation(conversation:Conversation) {
    let index:number = this.conversations.findIndex(c => c.id == conversation.id);
    // si la conversation existe on la met à jour, sinon on la concatene à la liste existante en la plaçant en première place
    index >= 0 ? this.conversations[index] = conversation : [conversation].concat(this.conversations);
  }

  // utilisé lorsque l'utilisateur initie une conversation
  private addConversation(conversation:Conversation) {
    console.log("conversation", conversation);
    this.selectedConversation = conversation;
    this.conversations = [conversation].concat(this.conversations);
  }

  private getConversations() {
    this.conversationService.getAll().then(response => {
      this.conversations = response.content;

      // on refresh le readByUserTwo
      let selectedConversation = this.conversations.find(c => c.id == this.selectedConversation.id);
      this.messengerService.changeConversationRead(selectedConversation ? selectedConversation.readByUserTwo : this.selectedConversation.readByUserTwo);
    }).catch(error => this.toastr.error(error));
  }

  // méthode permettant d'ajouter la conversation à la liste dans le cas de conversation supprimée puis recommencée
  private checkIfConversationExists(message:Message) {
    let index:number = this.conversations.findIndex(c => c.id == message.conversation.id);
    // si elle n'existe pas, on la place en premier
    if (index < 0) {
      this.getConversations();
    }
  }

  public ngOnDestroy(): void {
    this.changeConversationSubscription.unsubscribe();
    this.deleteConversationSubscription.unsubscribe();
    this.updateConversationSubscription.unsubscribe();
    this.receiveMessageSubscription.unsubscribe();
    this.deleteConversationSubscription.unsubscribe();
  }
}
