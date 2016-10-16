import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import { LocalStorageService } from 'angular-2-local-storage';
import {Observable, Observer, BehaviorSubject} from "rxjs";
import {Router} from "@angular/router";

/**
 * Created by pgmatz on 10/10/16.
 */


@Injectable()
export class SharedService {

  constructor (private localStorageService: LocalStorageService, private router: Router) {
  }

  getUser() {
    return this.localStorageService.get('user');
  }

  redirectLogin() {
    if (!this.isConnected()) {
      this.router.navigateByUrl('login');
    }
  }

  redirectHome() {
    if (this.isConnected()) {
      this.router.navigateByUrl('home');
    }
  }

  logout() {
    this.localStorageService.set('token', null);
  }

  isConnected() {
    return (this.localStorageService.get('token') != null);
  }
}
