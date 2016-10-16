import {Injectable} from "@angular/core";
import {environment} from "../environments/environment";
import {Router} from "@angular/router";
import {Http, Response, Headers} from "@angular/http";
import {Conversation} from "../model/conversation";
import {UserMessage} from "../model/userMessage";
import {AuthenticationService} from "./authentication.service";

/**
 * Created by pgmatz on 28/09/16.
 */


@Injectable()
export class ConversationService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService: AuthenticationService) {
    this.baseUrl = environment.baseUrl;
  }

  getConversationBetweenSpecifiedUser(id) {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'conversations/' + id, {headers: headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return this.extractData(response);
      })
      .catch(this.handleError);
  }

  getAll() {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'conversations', {headers: headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return this.extractData(response);
      })
      .catch(this.handleError);
  }

  getUnreadNumberConversations() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'conversations/unread', {headers: headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return response.json();
      })
      .catch(this.handleError);
  }

  post(userMessage:UserMessage) {

    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'conversations', userMessage, {headers: headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return response.json();
      })
      .catch(this.handleError);
  }

  remove(conversation : Conversation) {

    let headers = this.authenticationService.getHeaders();

    return this.http.delete(this.baseUrl + 'conversations/' + conversation.id, {headers: headers})
      .toPromise()
      .then(response => {
          console.log(response);
          this.handleResponse(response);
        }
      )
      .catch(this.handleError);
  }

  private handleError(error: any) {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  private handleResponse(response:any) {
    if (response.status === 200) {
      return;
    }
    throw Error(response.message);
  }

  private extractData(res: Response) {
    let body;

    // check if empty, before call json
    if (res.text()) {
      body = res.json();
    }

    return body || {};
  }
}
