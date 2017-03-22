import {Component, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {SharedService} from "../../../services/shared.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {environment} from "../../../../environments/environment";
import {VisitService} from "../../../services/visit.service";
import {ConversationService} from "../../../services/conversation.service";
import {User} from "../../../model/user";
import {CoolLocalStorage} from "angular2-cool-storage";
import {MessengerService} from "../../../services/messenger.service";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css']
})
export class NavbarComponent implements OnInit {

  uploadImageUrl:string;
  user:User;
  isUserAdmin:boolean;

  unreadConversations:number;
  unseenVisits:number;

  // subscriptions
  conversationRefreshSubscription: any;
  visitsRefreshSubscription:any;
  userRefreshSubscription:any;
  // timers
  unreadConversationsTimer:Observable<number>;
  unseenVisitsTimer:Observable<number>;

  receiveMessageSubscription:any;
  conversationLoadedSubscription:any;


  constructor(private localStorageService: CoolLocalStorage, private authenticationService: AuthenticationService,
              private sharedService: SharedService, private conversationService: ConversationService,
              private  router: Router, private visitService: VisitService, private messengerService:MessengerService, private titleService: Title) {
    this.uploadImageUrl = environment.uploadImageUrl;
  }

  ngOnInit() {

    this.receiveMessageSubscription = this.messengerService.receiveMessageObservable.subscribe(() => this.updateUnreadConversations());
    //this.conversationLoadedSubscription = this.messengerService.conversationLoadedObservable.subscribe(() => this.updateUnreadConversations());

    this.visitsRefreshSubscription = this.sharedService.unseenNumberVisitsRefresh.subscribe(refresh => { this.getUnseenVisits(); });
    this.userRefreshSubscription = this.sharedService.userRefresh.subscribe(user => { this.user = user; });

    this.updateUnreadConversations();
    this.getUnseenVisits();

    // TODO A ACTIVER
    //this.unreadConversationsTimer = Observable.timer(0, 60000);
    //this.unreadConversationsTimer.subscribe(t => this.getUnreadConversations());

    //this.unseenVisitsTimer = Observable.timer(0, 60000);
    //this.unseenVisitsTimer.subscribe(t => this.getUnseenVisits());

    this.user = <User> this.localStorageService.getObject('user');
    this.isUserAdmin = this.sharedService.isAdmin(this.user);
  }

  updateUnreadConversations() {
    console.error("UPDATE unreadconversations");
    this.conversationService.getUnreadNumberConversations().then(unreadConversations => {
      this.unreadConversations = unreadConversations;
      console.error(this.unreadConversations);
      this.updateTitle();
    });
  }

  updateTitle() {
    let title = environment.appTitle;
    if (this.unreadConversations != 0) {
      this.titleService.setTitle(title + " (" + this.unreadConversations + ")");
    }
  }

  getUnseenVisits() {
    this.visitService.getUnseenNumber().then(unseenVisits => { this.unseenVisits = unseenVisits});
  }

  logout() {
    this.localStorageService.removeItem('token');
    this.localStorageService.removeItem('user');
    this.router.navigate(['/unauth/home']);
  }
}
