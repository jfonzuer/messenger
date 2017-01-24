/**
 * Created by pgmatz on 15/11/16.
 */

import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {Localization} from "../../model/localization";
import {LocalizationService} from "../localization.service";
import {RequestService} from "../request.service";
import {CoolLocalStorage} from "angular2-cool-storage";

/**
 * Created by pgmatz on 15/11/16.
 */

@Injectable()
export class LocalizationsResolve implements Resolve<Localization[]> {

  userTypes:Localization[];

  constructor(private fetishService:LocalizationService, private rs:RequestService, private localStorageS: CoolLocalStorage)  {}

  resolve(): Promise<Localization[]>| any {
    return this.localStorageS.getObject('localizations') != null ?
      this.localStorageS.getObject('localizations') :
      this.fetishService.getAll().then(localizations => localizations).catch(error => error);
  }
}
