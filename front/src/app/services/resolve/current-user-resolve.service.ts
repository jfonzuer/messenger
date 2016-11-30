import {Injectable} from "@angular/core";
import {Resolve, Router, ActivatedRouteSnapshot} from "@angular/router";
import {User} from "../../model/user";
import {LocalStorageService} from 'angular-2-local-storage';
/**
 * Created by pgmatz on 30/11/16.
 */

@Injectable()
export class CurrentUserResolve implements Resolve<User> {

  constructor(private router:Router, private localStorageService:LocalStorageService) {}

  resolve(route: ActivatedRouteSnapshot): User {
    return <User> this.localStorageService.get('user');
  }
}
