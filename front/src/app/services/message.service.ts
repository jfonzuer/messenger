import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {AuthenticationService} from "./authentication.service";
import {environment} from "../../environments/environment";
import {Message} from "../model/message";
import {Pager} from "../model/pager";
import {RequestService} from "./request.service";

@Injectable()
export class MessageService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService : AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getMessages(userId:number, pager:Pager) {

    let headers = this.authenticationService.getHeaders();
    let queryParams = '?l=1';
    if (pager) {
      queryParams = queryParams.concat('&p=' + pager.page);
    }

    return this.http.get(this.baseUrl + 'messages/' + userId + queryParams, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        if (response.text()) {
          return response.json();
        }
        return [];
      })
      .catch(this.rs.handleError);
  }

  getNewerMessages(userId:number, messageId) {
    let headers = this.authenticationService.getHeaders();
    return this.http.get(this.baseUrl + 'messages/newer/' + userId + '/' + messageId, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        if (response.text()) {
          return response.json();
        }
        return [];
      })
      .catch(this.rs.handleError);
  }

  post(message:Message) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'messages', message, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);
  }

  uploadImage(file:File, id:number) {
    let headers = this.authenticationService.getHeaders();
    let fileForm = new FormData();
    fileForm.append("file", file);
    fileForm.append("id", id);
    return this.http.post(this.baseUrl + 'messages/image', fileForm, {headers:headers}).map(response => response.json()).catch(this.rs.handleError);
  }
}
