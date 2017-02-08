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
import {environment} from "../../../../environments/environment";
import {CoolLocalStorage} from "angular2-cool-storage";
var SockJS = require('sockjs-client');
var Stomp = require('stompjs');

@Component({
  selector: 'app-messenger',
  templateUrl: 'messenger.component.html',
  styleUrls: ['messenger.component.css']
})
export class MessengerComponent implements OnInit {

  error:string;
  success:string;
  user:User;
  baseUrl:string;
  stompClient:any;

  addMessageSubscription:any;
  changeConversationSubscription: any;
  addConversationSubscription:any;
  selectedConversation:Conversation;

  constructor(private route:ActivatedRoute, private conversationService: ConversationService, private messengerService:MessengerService, private localStorageService:CoolLocalStorage) {
    this.baseUrl = environment.baseUrl;

    this.addMessageSubscription = this.messengerService.addMessageObservable.subscribe(message => this.selectedConversation.id ? this.send(message) : null );
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => { this.connect(conversation.id ? conversation.id : null); this.selectedConversation = conversation ;});
    this.addConversationSubscription = this.messengerService.addConversationObservable.subscribe(conversation => { this.connect(conversation.id); this.selectedConversation = conversation});
  }

  ngOnInit() {
    this.route.data.forEach((data:any) => {
      this.user = data.user;
    });

    this.route.params.forEach((params: Params) => {
      // si on arrive avec l'id d'un utilisateur spécifié
      if (params['id']) {
        let userId = +params['id'];// (+) converts string 'id' to a number
        this.conversationService.getConversationBetweenSpecifiedUser(userId).then(conversation => {
          console.log("conversation : ", conversation);
          this.messengerService.changeConversation(conversation)
        });
      }
    });
  }

  send(message: Message) {
    this.stompClient.send('/app/ws-conversation-endpoint/' + this.selectedConversation.id, {}, JSON.stringify(message));
  }

  connect(conversationId) {
    var that = this;
    let url:string = this.baseUrl + 'ws-conversation-endpoint?token=' + this.localStorageService.getObject('token');
    var socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      if (conversationId) {
        that.stompClient.subscribe('/ws-conversation-broker/conversation/' + conversationId, function (response) {
          console.log(response);
          that.messengerService.receiveMessage(JSON.parse(response.body));
        });
      }
    }, function (err) {
      console.log('err', err);
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
}
