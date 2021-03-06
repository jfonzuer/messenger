import { Injectable } from '@angular/core';
import {User} from "../model/user";
import {Observable, Observer} from "rxjs";
import {Router} from "@angular/router";
import {CoolLocalStorage} from "angular2-cool-storage";
import {LoggerService} from "./logger.service";

@Injectable()
export class SharedService {

  unreadNumberConversationsRefresh: Observable<boolean>;
  unseenNumberVisitsRefresh: Observable<boolean>;
  userRefresh: Observable<User>;
  private unseenVisitsObserver: Observer<boolean>;
  private userObserver: Observer<User>;


  constructor (private localStorageService: CoolLocalStorage, private router: Router, private logger: LoggerService) {
    this.unseenNumberVisitsRefresh = new Observable<boolean>(observer => this.unseenVisitsObserver = observer).share();
    this.userRefresh = new Observable<User>(observer => this.userObserver = observer).share();
  }

  refreshUser(user:User) {
    this.localStorageService.setObject('user', user);
    this.userObserver.next(user);
  }

  refreshUnseenNumberVisits() {
    this.unseenVisitsObserver.next(true);
  }

  redirectLogin() {
    if (!this.isConnected()) {
      //this.router.navigateByUrl('unauth');
      this.router.navigate(['/unauth/home']);
    }
  }

  redirectHome() {
    if (this.isConnected()) {
      this.router.navigate(['/app/home'])
    }
  }


  isConnected() {
    return (this.localStorageService.getItem('token') != null);
  }

  isAuthenticated() {
    return (this.localStorageService.getItem('token') != null && this.localStorageService.getObject('user') != null);
  }

  isAdmin(user:User) {
    //console.debug("isAdmin ", user.authorities.find(a => a == "ROLE_ADMIN") != null)
    return user.authorities.find(a => a == "ROLE_ADMIN") != null;
  }

  isPremium() {
    let user:User = <User> this.getCurrentUser();
    this.logger.log("shared service: is Premium", user.authorities.find(a => a == "ROLE_PREMIUM") != null)
    return user.authorities.find(a => a == "ROLE_PREMIUM") != null;
  }

  isUser() {
    let user:User = <User> this.getCurrentUser();
    //console.debug("isUser ", user.authorities.find(a => a == "ROLE_USER") != null)
    return user.authorities.find(a => a == "ROLE_USER") != null;
  }

  getLocalizations() {
    return this.localStorageService.getObject('localizations');
  }

  getCurrentUser() : User {
    return <User> this.localStorageService.getObject('user');
  }

  desactivate() {
    this.localStorageService.removeItem('token');
  }

  isDomina() : boolean {
    return this.getCurrentUser().userType.name == 'Dominatrice';
  }

  isUserBlocked(user:User) : boolean {
    let currentUser = this.getCurrentUser();
    // console.log("user blocked ", currentUser.blockedUsers);

    // return true if blocked
    // console.log("ternaire", currentUser.blockedUsers != null ? currentUser.blockedUsers.find(u => u.id == user.id) != null : false);
    //console.log("currentUser.blockedUsers.find(u => u.id == user.id) ", currentUser.blockedUsers.find(u => u.id == user.id));

    return currentUser.blockedUsers != null ? currentUser.blockedUsers.find(u => u.id == user.id) != null : false;
  }

}
