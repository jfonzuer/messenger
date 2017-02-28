import {Router, CanActivate} from "@angular/router";
import {SharedService} from "../shared.service";
import {Injectable} from "@angular/core";
/**
 * Created by pgmatz on 26/02/17.
 */

@Injectable()
export class PremiumGuardService implements CanActivate {

  constructor (private sharedService: SharedService, private router:Router) { }

  canActivate() {
    let isPremium = this.sharedService.isPremium();
    if (!isPremium) {
      this.router.navigate(['/app/premium']);
    }
    return isPremium;
  }
}

