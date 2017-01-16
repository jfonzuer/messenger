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
import {Pager} from "../model/pager";
import {Search} from "../model/search";
import {Alerts} from "../model/alerts";
import {Desactivate} from "../model/desactivate";

@Injectable()
export class UserService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService: AuthenticationService, private datetimeService:DatetimeService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getUsers(pager:Pager) : any {

    let headers = this.authenticationService.getHeaders();
    let queryParams = '?l=20';
    if (pager) {
      queryParams = queryParams.concat('&p=' + pager.page);
    }

    return this.http.get(this.baseUrl + 'users/' + queryParams,  {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);

        // if response not empty
        //if (response.text()) {
          let body = response.json();
          this.datetimeService.formatUsersAge(<User[]> body.content);
          return body;
        //}
        //return [];
      })
      .catch(this.rs.handleError);
  }

  searchUsers(search:Search, pager:Pager) {
    let headers = this.authenticationService.getHeaders();
    let queryParams = '?l=1';
    if (pager) {
      queryParams = queryParams.concat('&p=' + pager.page);
    }

    return this.http.post(this.baseUrl + 'users/' + queryParams, search, {headers:headers})
      .toPromise()
      .then(response => {
        let body = response.json();
        this.datetimeService.formatUsersAge(<User[]> body.content);
        return body;
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

        // format different dates
        this.datetimeService.formatAge(user);
        this.datetimeService.formatBirthDate(user);
        this.datetimeService.formatLastActivtyDate(user);

        return user;
      })
      .catch(this.rs.handleError);
  }

  report(id:number) {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'users/report/' + id, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
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

  updateAlerts(alerts: Alerts) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.put(this.baseUrl + 'users/alerts', alerts, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);
  }

  desactivate(desactivate:Desactivate) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.put(this.baseUrl + 'users/desactivate', desactivate, {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
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

  blockUser(userToBlock:User) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'users/block', userToBlock, {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError)
  }
  unblockUser(userToUnblock:User) {
    let headers = this.authenticationService.getHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'users/unblock', userToUnblock, {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError)
  }

}
