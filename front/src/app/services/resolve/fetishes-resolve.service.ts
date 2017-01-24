import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {Fetish} from "../../model/fetish";
import {FetishService} from "../fetish.service";
import {RequestService} from "../request.service";
import {CoolLocalStorage} from "angular2-cool-storage";

/**
 * Created by pgmatz on 15/11/16.
 */

@Injectable()
export class FetishesResolve implements Resolve<Fetish[]> {

  userTypes:Fetish[];

  constructor(private fetishService:FetishService, private rs:RequestService, private localStorageS: CoolLocalStorage)  {}

  resolve(): Promise<Fetish[]>| any {
    return this.localStorageS.getObject('fetishes') != null ?
      this.localStorageS.getObject('fetishes') :
      this.fetishService.getAll().then(fetishes => fetishes).catch(error => error);
  }
}
