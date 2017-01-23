import { Injectable } from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {SharedService} from "./shared.service";
import {User} from "../model/user";

@Injectable()
export class AdminGuardService implements CanActivate {

  constructor (private sharedService: SharedService, private router:Router) { }

  canActivate() {
    let user:User = this.sharedService.getCurrentUser();
    let isAdmin = this.sharedService.isAdmin(user);
    if (!isAdmin) {
      this.router.navigate(['/app/home']);
    }
    return isAdmin;
  }
}
