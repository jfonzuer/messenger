import { Injectable } from '@angular/core';
import {Http, Response} from "@angular/http";
import {Router} from "@angular/router";
import {AuthenticationService} from "./authentication.service";
import {environment} from "../../environments/environment";
import {Message} from "../model/message";

@Injectable()
export class MessageService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService : AuthenticationService) {
    this.baseUrl = environment.baseUrl;
  }

  getMessages(userId) {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'messages/' + userId, {headers: headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        if (response.text()) {
          return response.json();
        }
        return [];
      })
      .catch(this.handleError);
  }

  post(message:Message) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'messages', message, {headers: headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return response.json();
      })
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
