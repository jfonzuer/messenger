import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {Fetish} from "../../model/fetish";
import {FetishService} from "../fetish.service";
import {RequestService} from "../request.service";
import {LocalStorageService} from 'angular-2-local-storage';

/**
 * Created by pgmatz on 15/11/16.
 */

@Injectable()
export class FetishesResolve implements Resolve<Fetish[]> {

  userTypes:Fetish[];

  constructor(private fetishService:FetishService, private rs:RequestService, private localStorageS: LocalStorageService)  {}

  resolve(): Promise<Fetish[]>| any {
    return this.localStorageS.get('fetishes') != null ?
      this.localStorageS.get('fetishes') :
      this.fetishService.getAll().then(fetishes => fetishes).catch(error => error);
  }
}
