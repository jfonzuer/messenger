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
import {MessengerService} from "../../../services/messenger.service";

@Component({
  selector: 'app-messenger',
  templateUrl: 'messenger.component.html',
  styleUrls: ['messenger.component.css']
})
export class MessengerComponent implements OnInit {

  error:string;
  success:string;
  user:User;

  constructor(private route:ActivatedRoute,
              private conversationService: ConversationService,
              private messengerService:MessengerService) { }

  ngOnInit() {
    this.route.data.forEach((data:any) => {
      this.user = data.user;
    });

    this.route.params.forEach((params: Params) => {
      // si on arrive avec l'id d'un utilisateur spécifié
      if (params['id']) {
        let userId = +params['id'];// (+) converts string 'id' to a number
        this.conversationService.getConversationBetweenSpecifiedUser(userId).then(conversation => this.messengerService.changeConversation(conversation));
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
  /*
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




  private defineConversationTimer() : void {
    this.conversationTimer = Observable.timer(0, 60000);
    this.conversationTimer.subscribe(t => this.getConversations());
  }
  private getConversations() {
    this.conversationService.getAll().then(response => { console.log(response); this.conversations = response.content; }).catch(error => this.error = error);
  }
  */
}
