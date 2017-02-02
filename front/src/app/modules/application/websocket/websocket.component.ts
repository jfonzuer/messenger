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
    this.connect();
  }

  stompClient: any;
  baseUrl:string;

  activityId: any;
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
    var socket = new SockJS(this.baseUrl + '/hello/');
    this.stompClient = Stomp.over(socket);

    var headers = {};
    headers['Authorization'] = '' + this.localStorageService.getObject('token');
    this.stompClient.connect(headers, function (frame) {
      console.log('Connected: ' + frame);
      that.stompClient.subscribe('/topic/greetings/' + 1, function (greeting) {
        console.log('greeting', greeting);
        that.messages.push(JSON.parse(greeting.body).content);
        console.log(that.messages);
      });
    }, function (err) {
      console.log('err', err);
    });
  }

}
