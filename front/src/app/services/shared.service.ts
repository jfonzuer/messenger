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

  getLocalizations() {
    return this.localStorageService.get('localizations');
  }

  getCurrentUser() : User {
    return <User> this.localStorageService.get('user');
  }

  isDomina() : boolean {
    return this.getCurrentUser().userType.name == 'Dominatrice';
  }

}
