import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {AuthenticationService} from "./authentication.service";
import {Router} from "@angular/router";
import {Http, Response} from "@angular/http";
import {UserMessage} from "../model/userMessage";
import {Conversation} from "../model/conversation";
import {RequestService} from "./request.service";

@Injectable()
export class ConversationService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService: AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getConversationBetweenSpecifiedUser(id) {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'conversations/' + id, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return this.rs.extractData(response);
      })
      .catch(this.rs.handleError);
  }

  getAll() {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'conversations', {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return this.rs.extractData(response);
      })
      .catch(this.rs.handleError);
  }

  getUnreadNumberConversations() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'conversations/unread', {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);
  }

  post(userMessage:UserMessage) {

    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'conversations', userMessage, {headers: headers})
      .toPromise()
      .then(response => {
        console.log(response);
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);
  }

  remove(conversation : Conversation) {

    let headers = this.authenticationService.getHeaders();

    return this.http.delete(this.baseUrl + 'conversations/' + conversation.id, {headers: headers})
      .toPromise()
      .then(response => {
          console.log(response);
          this.rs.handleResponse(response);
        }
      )
      .catch(this.rs.handleError);
  }
}
