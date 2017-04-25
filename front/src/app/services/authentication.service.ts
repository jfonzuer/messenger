import { Injectable } from '@angular/core';
import {Http, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {DatetimeService} from "./datetime.service";
import {environment} from "../../environments/environment";
import {Authentication} from "../model/authentication";
import {User} from "../model/user";
import {RequestService} from "./request.service";
import {CoolLocalStorage} from "angular2-cool-storage";

@Injectable()
export class AuthenticationService {

  private baseUrl:string;

  constructor (private http:Http, private router: Router, private localStorageService: CoolLocalStorage, private datetimeService: DatetimeService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  post(authentication:Authentication) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.baseUrl + 'login', authentication, headers)
      .toPromise()
      .then(response => {
        return response.json();
      })
  }

  sendActivationEmail(email:string) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'resend/activation/email', email, headers)
      .toPromise()
      .then(response => {
        return response;
      })
  }

  sendActivationToken(token:string) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'validate/account', token, headers)
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response;
      })
  }

  getAuthenticatedUser() {
    let headers = this.getHeaders();
    return this.http.get(this.baseUrl + 'user', { headers:headers })
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        let user:User = response.json();
        //this.datetimeService.formatAge(user);
        //this.datetimeService.formatBirthDate(user);
        return user;
      })
      .catch(this.rs.handleError);
  }

  refreshToken() {
    let headers = this.getHeaders();
    return this.http.get(this.baseUrl + 'refresh', { headers:headers })
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);
  }

  resetPasswordByEmail(email:string) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'password/reset/mail', email, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
      })
      .catch(this.rs.handleError);
  }

  getHeaders() : Headers {
    let headers = new Headers();
    let token = this.localStorageService.getObject("token");
    headers.append('Authorization', '' + token);
    return headers;
  }
}
