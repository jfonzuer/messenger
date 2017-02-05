import { Component, OnInit } from '@angular/core';
import {environment} from "../../../../environments/environment";
import {AuthenticationService} from "../../../services/authentication.service";
import {CoolLocalStorage} from "angular2-cool-storage";
var SockJS = require('sockjs-client');
var Stomp = require('stompjs');

@Component({
  selector: 'app-websocket',
  templateUrl: 'websocket.component.html',
  styleUrls: ['websocket.component.css']
})
export class WebsocketComponent implements OnInit {

  ngOnInit(): void {
  }

  stompClient: any;
  baseUrl:string;

  text: any;
  messages:String[] = [];

  constructor(private localStorageService: CoolLocalStorage) {
    this.baseUrl = environment.baseUrl;
  }

  send() {
    this.stompClient.send('/app/hello/' + 1, {}, JSON.stringify({ 'name': 'okkkk' }));
  }

  connect() {
    var that = this;
    let url:string = this.baseUrl + 'hello?token=' + this.localStorageService.getObject('token') + '&id=' + 2;
    var socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      that.stompClient.subscribe('/topic/greetings/' + 9, function (greeting) {
        console.log('greeting', greeting);
        that.messages.push(JSON.parse(greeting.body).content);
        console.log(that.messages);
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
