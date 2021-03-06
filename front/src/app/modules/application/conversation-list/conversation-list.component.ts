import {Component, Input, OnDestroy, OnInit, ViewContainerRef} from "@angular/core";
import {ConversationService} from "../../../services/conversation.service";
import {SharedService} from "../../../services/shared.service";
import {Conversation} from "../../../model/conversation";
import {environment} from "../../../../environments/environment";
import {MessengerService} from "../../../services/messenger.service";
import {ToastsManager} from "ng2-toastr";
import {Message} from "../../../model/message";
import {Pager} from "../../../model/pager";
import {LoggerService} from "../../../services/logger.service";

@Component({
  selector: 'app-conversation-list',
  templateUrl: 'conversation-list.component.html',
  styleUrls: ['conversation-list.component.css']
})
export class ConversationListComponent implements OnInit, OnDestroy {

  conversations: Conversation[] = [];
  @Input() selectedConversation:Conversation;

  // filter
  name:string;

  uploadImageUrl:string = environment.uploadImageUrl;
  pager:Pager;

  // subscriptions
  deleteConversationSubscription:any;
  changeConversationSubscription:any;
  updateConversationSubscription:any;
  addConversationSubscription:any;
  receiveMessageSubscription:any;

  constructor(private conversationService: ConversationService, private sharedService: SharedService, private messengerService:MessengerService,
              private toastr: ToastsManager, vRef: ViewContainerRef, private logger: LoggerService) {

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

  setSelectedConversation(conversation : Conversation) {
    if (conversation.id != this.selectedConversation.id) {
      this.messengerService.changeConversation(conversation);
    }
  }

  private updateConversation(conversation:Conversation) {
    let index:number = this.conversations.findIndex(c => c.id == conversation.id);
    let conversationIsInList = index >= 0;

    // si la conversation existe on la met à jour, sinon on la concatene à la liste existante en la plaçant en première place
    conversationIsInList ? this.conversations[index] = conversation : this.conversations = [conversation].concat(this.conversations);

    // // si la conversation n'est pas dans la liste et que le sender est l'utilisateur actuel, on switch pour cette conversation
    // if (!conversationIsInList && conversation.userOne.id == this.sharedService.getCurrentUser().id) {
    //   this.logger.log('conversation sender is authenticated user, switching to this conversation', '');
    //   this.messengerService.changeConversation(conversation);
    // }
  }

  // utilisé lorsque l'utilisateur initie une conversation
  private addConversation(conversation:Conversation) {
    this.conversations = [conversation].concat(this.conversations);
  }

  private getConversations() {

    this.conversationService.getConversations(this.pager).then(response => {
      this.logger.log('getConversations', response);
      this.pager == null ? this.conversations = response.content : this.conversations = this.conversations.concat(response.content);
      this.pager = new Pager(response.number, response.last, response.size, environment.pagerSize);

      this.logger.log('update conversation pager', this.pager);

    }).catch(error => this.toastr.error(error));
  }

  scrollDown() {
    this.pager.page = this.pager.page + 1;
    if (this.pager != null) {
      // length = 10
      // update page
      this.pager.page = Math.floor(this.conversations.length / environment.pagerSize);
      let i = this.conversations.length % environment.pagerSize;
      this.pager.length = environment.pagerSize - i;
    }
    this.getConversations();
  }

  // méthode permettant d'ajouter la conversation à la liste dans le cas de conversation supprimée puis recommencée
  private checkIfConversationExists(message:Message) {
    let index:number = this.conversations.findIndex(c => c.id == message.conversation.id);
    // si elle n'existe pas, on la place en premier
    if (index < 0) {
      this.pager = null;
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
