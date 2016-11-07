/**
 * Created by pgmatz on 02/11/16.
 */

import {Injectable} from "@angular/core";
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from "@angular/router";
import {User} from "../model/user";
import {Observable} from "rxjs";
import {UserService} from "./user.service";

@Injectable()
export class UserResolve implements Resolve<User> {

  constructor(private us: UserService, private router:Router) {}

  resolve(route: ActivatedRouteSnapshot): Promise<User>|boolean {

    let id = +route.params['id'];

    return this.us.getUserById(id).then(user => {
      if (user) {
        return user;
      }
      else {
        // id not found
        this.router.navigate(['/app/home']);
        return false;
      }
    })
  }

}