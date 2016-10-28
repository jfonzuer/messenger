import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {LocalStorageService} from 'angular-2-local-storage';
import {AuthenticationService} from "../../services/authentication.service";
import {SharedService} from "../../services/shared.service";
import {ConversationService} from "../../services/conversation.service";
import {Router} from "@angular/router";
import {VisitService} from "../../services/visit.service";
import {environment} from "../../../environments/environment";
import {User} from "../../model/user";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  uploadUrl:string;
  user:User;

  unreadConversations:number;
  unseenVisits:number;

  // subscriptions
  conversationRefreshSubscription: any;
  visitsRefreshSubscription:any;
  userRefreshSubscription:any;
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
    this.userRefreshSubscription = this.sharedService.userRefresh.subscribe(user => { this.user = user; });

    this.getUnreadConversations();
    this.getUnseenVisits();

    // TODO A ACTIVER
    //this.unreadConversationsTimer = Observable.timer(0, 60000);
    //this.unreadConversationsTimer.subscribe(t => this.getUnreadConversations());

    //this.unseenVisitsTimer = Observable.timer(0, 60000);
    //this.unseenVisitsTimer.subscribe(t => this.getUnseenVisits());

    this.user = <User> this.localStorageService.get('user');
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
    this.router.navigate(['/unauth/login']);
  }
}
