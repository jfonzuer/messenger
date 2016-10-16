import {Component, OnInit} from '@angular/core';
import {UserService} from "./service/user.service";
import './rxjs-operators';
import { LocalStorageService } from 'angular-2-local-storage';
import {SharedService} from "./service/shared.service";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']
})
export class AppComponent implements OnInit {

  authenticated: boolean;
  subscription: any;

  constructor(private _sharedService: SharedService, private localStorageService: LocalStorageService, private router: Router, private sharedService: SharedService) {
  }

  ngOnInit(): void {

    /*
    if (this.localStorageService.get('token')) {
      this.sharedService.authenticated();
    }
    else {
      console.log("no token");
      //this.sharedService.logout();
    }

    //this.router.navigateByUrl('efze');
    //this.authenticated = this._sharedService.authenticated();
    this.subscription = this._sharedService.authenticatedChange$.subscribe(
      authenticated => this.selectedAuthenticated(authenticated));
      */
  }
  /*
  selectedAuthenticated(authenticated: boolean) {
    this.authenticated = authenticated;
  }
  */
  /*
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
  */

  get connected () {
    return this.sharedService.isConnected() && (this.router.url !== '/login');
  }

}
