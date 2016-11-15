/**
 * Created by pgmatz on 15/11/16.
 */

import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {LocalStorageService} from 'angular-2-local-storage';
import {Localization} from "../../model/localization";
import {LocalizationService} from "../localization.service";
import {RequestService} from "../request.service";

/**
 * Created by pgmatz on 15/11/16.
 */

@Injectable()
export class LocalizationsResolve implements Resolve<Localization[]> {

  userTypes:Localization[];

  constructor(private fetishService:LocalizationService, private rs:RequestService, private localStorageS: LocalStorageService)  {}

  resolve(): Promise<Localization[]>| any {
    return this.localStorageS.get('localizations') != null ?
      this.localStorageS.get('localizations') :
      this.fetishService.getAll().then(localizations => localizations).catch(error => error);
  }
}
