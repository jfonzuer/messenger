import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {UserService} from "../../service/user.service";
import {ConversationService} from "../../service/conversation.service";
import {User} from "../../model/user";
import {Message} from "../../model/message";
import {MessageService} from "../../service/message.service";
import {Conversation} from "../../model/conversation";
import * as moment from 'moment/moment';
import 'moment/locale/fr';
import {Observable} from "rxjs";
import {SharedService} from "../../service/shared.service";

@Component({
  selector: 'app-messenger',
  templateUrl: 'messenger.component.html',
  styleUrls: ['messenger.component.css']
})
export class MessengerComponent implements OnInit {

  loading:boolean = true;
  error:string;

  // TODO : utilisateur fixé manuellement
  currentUser:User = new User();

  selectedConversation: Conversation;
  conversations:Conversation[] = [];
  messages: Message[] = [];

  // timers
  messageTimer:Observable<number>;
  conversationTimer:Observable<number>;

  constructor(private route:ActivatedRoute, private userService:UserService,private conversationService: ConversationService, private messageService:MessageService, private  sharedService: SharedService) { }

  ngOnInit() {
    this.sharedService.redirectLogin();

    moment().locale('fr');

    // TODO : utilisateur fixé manuellement
    this.currentUser.id = 1;

    // TODO : à activer
    //this.defineConversationTimer();
    this.getConversations();

    this.route.params.forEach((params: Params) => {
      // si on arrive avec l'id d'un utilisateur spécifié
      if (params['id']) {

        let userId = +params['id'];// (+) converts string 'id' to a number
        this.getMessages(userId);

        // on récupère l'objet conversation et on la set en selected
        this.conversationService.getConversationBetweenSpecifiedUser(userId).then(conversation => { console.log(conversation); this.selectedConversation = conversation; });

        // TODO : à activer
        // on définit le timer si pas encore défini
        //this.defineMessageTimerIfInexistant(userId);
      }
    });
  }

  deleteConversationListener(conversation : Conversation) : void {
    // on supprime la conversation de la liste
    if (confirm("Êtes vous sûr de vouloir supprimer la conversation ?")) {
      this.conversations = this.conversations.filter(c => c.id != conversation.id);
      this.selectedConversation = null;
      this.messages = [];
      this.conversationService.remove(conversation).then().catch(error => this.error = error);
    }
  }

  setConversationListener(conversation : Conversation) : void {
    this.selectedConversation = conversation;
    console.log(this.selectedConversation);
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

  private getMessages(userId : number) {
    this.loading = true;
    console.log("get messages");
    this.messageService.getMessages(userId).then(response => { this.formatMessages(response); this.loading = false; }).catch(e => this.error = e);
  }

  private formatMessages(response: any) {
    if (response.content) {
      let messages: Message[] = response.content;
      for (let message of messages) {
        message.sendDate = moment(message.sendDate).fromNow();
      }
      this.messages = messages;
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
}
