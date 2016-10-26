import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-2-local-storage';
import {AuthenticationService} from "../../service/authentication.service";
import {SharedService} from "../../service/shared.service";
import {Router} from "@angular/router";
import {User} from "../../model/user";
import {ConversationService} from "../../service/conversation.service";
import {Observable} from "rxjs";
import {VisitService} from "../../service/visit.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.css']
})
export class HeaderComponent implements OnInit {

  uploadUrl:string;
  user:User;

  unreadConversations:number;
  unseenVisits:number;

  // subscriptions
  conversationRefreshSubscription: any;
  visitsRefreshSubscription:any;
  // timers
  unreadConversationsTimer:Observable<number>;
  unseenVisitsTimer:Observable<number>;

  constructor(private localStorageService: LocalStorageService, private authenticationService: AuthenticationService,
              private sharedService: SharedService, private conversationService: ConversationService,
              private  router: Router, private visitService: VisitService) {
    this.uploadUrl = environment.uploadUrl;
  }

  ngOnInit() {

    this.conversationRefreshSubscription = this.sharedService.unreadNumberConversationsRefresh.subscribe(refresh => { this.getUnreadConversations(); });
    this.visitsRefreshSubscription = this.sharedService.unseenNumberVisitsRefresh.subscribe(refresh => { this.getUnseenVisits(); });

    this.getUnreadConversations();
    this.getUnseenVisits();

    // TODO A ACTIVER
    //this.unreadConversationsTimer = Observable.timer(0, 60000);
    //this.unreadConversationsTimer.subscribe(t => this.getUnreadConversations());

    //this.unseenVisitsTimer = Observable.timer(0, 60000);
    //this.unseenVisitsTimer.subscribe(t => this.getUnseenVisits());

  }

  get getUsername() {
    let user = this.sharedService.getUser();
    if (user) {
      //console.log(user);
      return user['username'];
    }
  }

  getProfilePicture() {
    let user = this.sharedService.getUser();
    if (user) {
      //console.log(user);
      return user['profilePicture'];
    }
  }

  getUnreadConversations() {
    this.conversationService.getUnreadNumberConversations().then(unreadConversations => { console.log(unreadConversations); this.unreadConversations = unreadConversations });
  }

  getUnseenVisits() {
    this.visitService.getUnseenNumber().then(unseenVisits => { console.log(unseenVisits); this.unseenVisits = unseenVisits});
  }

  logout() {
    this.localStorageService.remove('token');
    this.localStorageService.remove('user');
    this.sharedService.logout();
    this.router.navigateByUrl('login');
  }

}
