import { Component, OnInit } from '@angular/core';
import * as moment from 'moment/moment';
import 'moment/locale/fr';
import {Observable} from "rxjs";
import {ActivatedRoute, Params} from "@angular/router";
import {Conversation} from "../../../model/conversation";
import {User} from "../../../model/user";
import {Message} from "../../../model/message";
import {UserService} from "../../../services/user.service";
import {ConversationService} from "../../../services/conversation.service";
import {MessageService} from "../../../services/message.service";
import {SharedService} from "../../../services/shared.service";
import {Pager} from "../../../model/pager";
import {DatetimeService} from "../../../services/datetime.service";

@Component({
  selector: 'app-messenger',
  templateUrl: 'messenger.component.html',
  styleUrls: ['messenger.component.css']
})
export class MessengerComponent implements OnInit {

  loading:boolean = true;
  error:string;
  success:string;
  user:User;

  selectedConversation: Conversation;
  conversations:Conversation[] = [];
  messages: Message[] = [];
  pager:Pager;

  // timers
  messageTimer:Observable<number>;
  conversationTimer:Observable<number>;

  constructor(private route:ActivatedRoute, private userService:UserService, private datetimeService: DatetimeService,
              private conversationService: ConversationService,
              private messageService:MessageService, private  sharedService: SharedService) { }

  ngOnInit() {
    moment().locale('fr');

    // TODO : à activer
    // this.defineConversationTimer();
    this.getConversations();

    this.route.data.forEach((data:any) => {
      this.user = data.user;
    });

    this.route.params.forEach((params: Params) => {
      // si on arrive avec l'id d'un utilisateur spécifié
      if (params['id']) {
        let userId = +params['id'];// (+) converts string 'id' to a number
        this.getMessages(userId);
        // on récupère l'objet conversation et on la set en selected
        this.conversationService.getConversationBetweenSpecifiedUser(userId).then(conversation => { console.log("conversation"); console.log(conversation); this.selectedConversation = conversation; });
        // TODO : à activer
        // on définit le timer si pas encore défini
        //this.defineMessageTimerIfInexistant(userId);
      }
    });
  }

  errorListener(error:string) : void {
    this.error = error;
    setTimeout(() => this.error = "", 2000);
  }

  successListener(success:string) : void {
    this.success = success;
    setTimeout(() => this.success = "", 2000);
  }

  deleteConversationListener(conversation : Conversation) : void {
    // on supprime la conversation de la liste
    if (confirm("Êtes vous sûr de vouloir supprimer la conversation ?")) {
      this.conversations = this.conversations.filter(c => c.id != conversation.id);
      this.selectedConversation = null;
      this.messages = [];
      this.pager = null;
      this.conversationService.remove(conversation).then().catch(error => this.error = error);
    }
  }

  setConversationListener(conversation : Conversation) : void {
    this.selectedConversation = conversation;
    this.pager = null;
    this.getMessages(this.selectedConversation.userTwo.id);

    // TODO : à activer
    // on définit le timer si pas encore défini
    //this.defineMessageTimerIfInexistant(this.specifiedUser.id);
  }

  addMessageListener(message : Message) : void {
    message.sendDate = moment(message.sendDate).fromNow();
    this.getConversations();
    this.messages.push(message);
  }

  addConversationListener() : void {
    this.getMessages(this.selectedConversation.userTwo.id);
    // on récupère l'objet conversation et on la set en selected
    this.conversationService.getConversationBetweenSpecifiedUser(this.selectedConversation.userTwo.id).then(conversation => { console.log(conversation); this.selectedConversation = conversation; });
    this.getConversations();
  }

  scrollUpListener() : void {
    this.pager.page = this.pager.page + 1;
    this.getMessages(this.selectedConversation.userTwo.id);
  }

  private getMessages(userId : number) {
    this.loading = true;
    this.messageService.getMessages(userId, this.pager)
      .then( response => {
        this.formatMessages(response);
        this.loading = false;
        this.sharedService.refreshUnreadNumberConversations();
        this.pager = new Pager(response.number, response.last, response.size);
        console.log(this.pager);
      })
      .catch(e => this.error = e);
  }

  private formatMessages(response: any) {
    if (response.content) {
      let messages: Message[] = response.content;
      for (let message of messages) {
        message.sendDate = moment(message.sendDate).fromNow();
      }
      this.pager == null ? this.messages = messages : this.messages = messages.concat(this.messages);
    }
  }

  private defineMessageTimerIfInexistant(userId: number) : void {
    if (!this.messageTimer) {
      this.messageTimer = Observable.timer(0, 1000);
      this.messageTimer.subscribe(t => this.getMessages(this.selectedConversation.userTwo.id));
    }
  }

  private defineConversationTimer() : void {
    this.conversationTimer = Observable.timer(0, 60000);
    this.conversationTimer.subscribe(t => this.getConversations());
  }
  private getConversations() {
    this.conversationService.getAll().then(response => { console.log(response); this.conversations = response.content; }).catch(error => this.error = error);
  }

  private isSelectedConversationSeen() {
  }
}
