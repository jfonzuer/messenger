import {Component, OnInit, ViewContainerRef, OnDestroy} from "@angular/core";
import "moment/locale/fr";
import {ActivatedRoute, Params} from "@angular/router";
import {Conversation} from "../../../model/conversation";
import {User} from "../../../model/user";
import {Message} from "../../../model/message";
import {ConversationService} from "../../../services/conversation.service";
import {MessengerService} from "../../../services/messenger.service";
import {environment} from "../../../../environments/environment";
import {CoolLocalStorage} from "angular2-cool-storage";
import {ToastsManager} from "ng2-toastr";
import {LoggerService} from "../../../services/logger.service";
var SockJS = require('sockjs-client');
var Stomp = require('stompjs');

@Component({
  selector: 'app-messenger',
  templateUrl: 'messenger.component.html',
  styleUrls: ['messenger.component.css']
})
export class MessengerComponent implements OnInit, OnDestroy {
  user:User;
  baseUrl:string;
  stompClient:any;

  // subscriptions
  addMessageSubscription:any;
  changeConversationSubscription: any;
  addConversationSubscription:any;
  conversationsSubscription:any;

  // ws subscriptions
  conversationSubscription:any;
  errorSubscription:any;

  selectedConversation:Conversation;

  constructor(private route:ActivatedRoute, private toastr: ToastsManager, vRef: ViewContainerRef, private conversationService: ConversationService,
              private messengerService:MessengerService, private localStorageService:CoolLocalStorage, private logger: LoggerService) {
    this.baseUrl = environment.baseUrl;
    this.toastr.setRootViewContainerRef(vRef);
    // si on ajoute un message et que la conversation existe, on send le message via websocket sinon on l'envoit via une requête.
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
          this.messengerService.changeConversation(conversation)
        });
        // sinon
      } else {
        this.connect(null);
      }
    });
  }

  send(message: Message) {
    if (message.content != '') {
      this.stompClient.send('/app/ws-conversation-endpoint/' + this.selectedConversation.id, {}, JSON.stringify(message));
    }
  }

  connect(conversationId) {
    var that = this;
    let url:string = this.baseUrl + 'ws-conversation-endpoint?token=' + this.localStorageService.getObject('token');
    var socket = new SockJS(url);

    this.stompClient = Stomp.over(socket);
    this.stompClient.debug = null;

    if (that.conversationSubscription) {
      that.conversationSubscription.unsubscribe();
    }
    if (that.errorSubscription) {
      that.errorSubscription.unsubscribe();
    }
    if (that.conversationsSubscription) {
      that.conversationsSubscription.unsubscribe();
    }

    this.stompClient.connect({}, function (frame) {
      //console.log('Connected: ' + frame);
      if (conversationId) {

        that.conversationSubscription = that.stompClient.subscribe('/ws-conversation-broker/conversation/' + conversationId, function (response) {
          that.logger.log('WS client, receive message', response);
          that.messengerService.receiveMessage(JSON.parse(response.body));
        });
      }

      that.errorSubscription = that.stompClient.subscribe('/queue/errors/' + that.user.id, function (response) {
        that.toastr.error(response.body);
      });

      // endpoint de reception des messages d'autres conversations
      that.conversationsSubscription = that.stompClient.subscribe('/ws-user-broker/conversations/' + that.user.id, function (response) {
        that.logger.log("WS client, conversation subscription", response);
        that.messengerService.updateConversation(JSON.parse(response.body));
      });

    }, function (err) {
      that.toastr.error("Erreur lors de la connexion", null, {toastLife: 5000});
    });
  }

  public ngOnDestroy(): void {
    this.changeConversationSubscription.unsubscribe();
    this.addMessageSubscription.unsubscribe();
    this.addConversationSubscription.unsubscribe();

    if (this.conversationSubscription) {
      this.conversationSubscription.unsubscribe();
    }
    if (this.errorSubscription) {
      this.errorSubscription.unsubscribe();
    }
    if (this.conversationsSubscription) {
      this.conversationsSubscription.unsubscribe();
    }
  }
}
