import {Injectable} from "@angular/core";
import {Resolve, Router, ActivatedRouteSnapshot} from "@angular/router";
import {User} from "../../model/user";
import {CoolLocalStorage} from "angular2-cool-storage";
/**
 * Created by pgmatz on 30/11/16.
 */

@Injectable()
export class CurrentUserResolve implements Resolve<User> {

  constructor(private router:Router, private localStorageService:CoolLocalStorage) {}

  resolve(route: ActivatedRouteSnapshot): User {
    return <User> this.localStorageService.getObject('user');
  }
}
