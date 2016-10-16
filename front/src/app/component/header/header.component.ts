import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-2-local-storage';
import {AuthenticationService} from "../../service/authentication.service";
import {SharedService} from "../../service/shared.service";
import {Router} from "@angular/router";
import {User} from "../../model/user";
import {ConversationService} from "../../service/conversation.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.css']
})
export class HeaderComponent implements OnInit {

  unreadConversations:number;
  // timers
  unreadConversationsTimer:Observable<number>;

  constructor(private localStorageService: LocalStorageService, private authenticationService: AuthenticationService, private sharedService: SharedService, private conversationService: ConversationService, private  router: Router) { }

  ngOnInit() {
    this.getUnreadConversations();

    // TODO A ACTIVER
    //this.unreadConversationsTimer = Observable.timer(0, 60000);
    //this.unreadConversationsTimer.subscribe(t => this.getUnreadConversations());
  }


  get getUsername() {
    let user = this.sharedService.getUser();
    if (user) {
      return user['username'];
    }
  }

  getUnreadConversations() {
    this.conversationService.getUnreadNumberConversations().then(unreadConversations => this.unreadConversations = unreadConversations);
  }

  logout() {
    this.localStorageService.remove('token');
    this.localStorageService.remove('user');
    this.sharedService.logout();
    this.router.navigateByUrl('login');
  }

}
