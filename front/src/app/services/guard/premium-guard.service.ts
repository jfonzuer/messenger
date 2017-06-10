import {Router, CanActivate} from "@angular/router";
import {SharedService} from "../shared.service";
import {Injectable} from "@angular/core";
import {LoggerService} from "../logger.service";
/**
 * Created by pgmatz on 26/02/17.
 */

@Injectable()
export class PremiumGuardService implements CanActivate {

  constructor (private sharedService: SharedService, private router:Router, private logger: LoggerService) { }

  canActivate() {
    let isPremium = this.sharedService.isPremium();
    this.logger.log("premium guard: is user premium", isPremium);
    if (!isPremium) {
      this.router.navigate(['/app/premium']);
    }
    return isPremium;
  }
}

