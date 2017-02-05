import {Injectable} from "@angular/core";
import {CoolLocalStorage} from "angular2-cool-storage";
import {environment} from "../../environments/environment";
import {MessengerService} from "./messenger.service";
var SockJS = require('sockjs-client');
var Stomp = require('stompjs');
/**
 * Created by pgmatz on 05/02/17.
 */

@Injectable()
export class ConversationStompService {

  stompClient: any;
  baseUrl:string;

  constructor(private localStorageService: CoolLocalStorage, private messengerService:MessengerService) {
    this.baseUrl = environment.baseUrl;
  }

  send(conversationId) {
    this.stompClient.send('/app/hello/' + conversationId, {}, JSON.stringify({ 'name': 'okkkk' }));
  }


  connect(conversationId) {
    var that = this;
    var url = this.baseUrl + 'hello?token=' + this.localStorageService.getObject('token');
    var socket = new SockJS(url);

    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      that.stompClient.subscribe('/topic/greetings', function (greeting) {
        console.log('greeting', greeting);
      });
    }, function (err) {
      console.log('err', err);
    });
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
  }
}
