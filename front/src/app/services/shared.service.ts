import { Injectable } from '@angular/core';
import {User} from "../model/user";
import {Observable, Observer} from "rxjs";
import { LocalStorageService } from 'angular-2-local-storage';
import {Router} from "@angular/router";

@Injectable()
export class SharedService {

  unreadNumberConversationsRefresh: Observable<boolean>;
  unseenNumberVisitsRefresh: Observable<boolean>;
  userRefresh: Observable<User>;
  private unreadNumberConversationsObserver: Observer<boolean>;
  private unseenVisitsObserver: Observer<boolean>;
  private userObserver: Observer<User>;


  constructor (private localStorageService: LocalStorageService, private router: Router) {
    this.unreadNumberConversationsRefresh = new Observable<boolean>(observer => this.unreadNumberConversationsObserver = observer).share();
    this.unseenNumberVisitsRefresh = new Observable<boolean>(observer => this.unseenVisitsObserver = observer).share();
    this.userRefresh = new Observable<User>(observer => this.userObserver = observer).share();
  }

  refreshUnreadNumberConversations() {
    this.unreadNumberConversationsObserver.next(true);
  }

  refreshUser(user:User) {
    this.localStorageService.set('user', user);
    this.userObserver.next(user);
  }

  refreshUnseenNumberVisits() {
    this.unseenVisitsObserver.next(true);
  }

  redirectLogin() {
    if (!this.isConnected()) {
      //this.router.navigateByUrl('unauth');
      this.router.navigate(['/unauth/login']);
    }
  }

  redirectHome() {
    if (this.isConnected()) {
      this.router.navigate(['/app/home'])
    }
  }


  isConnected() {
    return (this.localStorageService.get('token') != null);
  }

  isAuthenticated() {
    return (this.localStorageService.get('token') != null && this.localStorageService.get('user') != null);
  }

  /*
  isAdmin(user:User) {
    return user.authorities.find(a => a.authority == "ROLE_ADMIN") != null;
  }
  */

  getLocalizations() {
    return this.localStorageService.get('localizations');
  }

  getCurrentUser() : User {
    return <User> this.localStorageService.get('user');
  }

  desactivate() {
    this.localStorageService.remove('token');
  }

  isDomina() : boolean {
    return this.getCurrentUser().userType.name == 'Dominatrice';
  }

  isUserBlocked(user:User) : boolean {
    let currentUser = this.getCurrentUser();
    console.log("user blocked ", currentUser.blockedUsers);
    // return true if blocked
    console.log("ternaire", currentUser.blockedUsers != null ? currentUser.blockedUsers.find(u => u.id == user.id) != null : false);
    console.log("currentUser.blockedUsers.find(u => u.id == user.id) ", currentUser.blockedUsers.find(u => u.id == user.id));

    return currentUser.blockedUsers != null ? currentUser.blockedUsers.find(u => u.id == user.id) != null : false;
  }

}
