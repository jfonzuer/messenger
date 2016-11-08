import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {DatetimeService} from "./datetime.service";
import {AuthenticationService} from "./authentication.service";
import {Router} from "@angular/router";
import {Http, Headers} from "@angular/http";
import {User} from "../model/user";
import {Register} from "../model/register";
import {ResetPassword} from "../model/resetPassword";
import {PasswordConfirmation} from "../model/passwordConfirmation";
import {RequestService} from "./request.service";

@Injectable()
export class UserService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService: AuthenticationService, private datetimeService:DatetimeService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getLast20Users() {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'users',  {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        console.log(response);
        let users:User[] = response.json().content;
        console.log(response);
        for (let user of users) {
          this.datetimeService.formatAge(user);
        }
        return users;
      })
      .catch(this.rs.handleError);
  }

  getUserById(id) {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'users/' + id, {headers: headers})
      .toPromise()
      .then(response => {
        console.log(response);
        this.rs.handleResponse(response);
        let user:User = response.json();
        this.datetimeService.formatAge(user);
        this.datetimeService.formatBirthDate(user);
        return user;
      })
      .catch(this.rs.handleError);
  }

  post(register:Register) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'register', register, {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
      })
      .catch(this.rs.handleError)
  }

  updateProfile(user : User) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.put(this.baseUrl + 'users/profile', user, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);
  }

  updateInformations(user : User) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.put(this.baseUrl + 'users/informations', user, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);
  }

  updatePassword(passwordConfirmation : PasswordConfirmation) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.put(this.baseUrl + 'users/password/reset', passwordConfirmation, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
      })
      .catch(this.rs.handleError);
  }

  resetPassword(resetPassword:ResetPassword) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'password/reset', resetPassword, {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
      })
      .catch(this.rs.handleError)
  }
}
