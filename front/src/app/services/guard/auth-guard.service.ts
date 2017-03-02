import { Injectable } from '@angular/core';
import {SharedService} from "../shared.service";
import {Router, CanActivate} from "@angular/router";

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor (private sharedService: SharedService, private router:Router) { }

  canActivate() {
    let isAuth = this.sharedService.isAuthenticated();
    if (!isAuth) {
      this.router.navigate(['/unauth/home']);
    }
    return isAuth;
  }
}
