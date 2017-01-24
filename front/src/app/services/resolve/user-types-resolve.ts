/**
 * Created by pgmatz on 15/11/16.
 */

import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {UserTypeService} from "../user-type.service";
import {RequestService} from "../request.service";
import {UserType} from "../../model/userType";
import {CoolLocalStorage} from "angular2-cool-storage";

@Injectable()
export class UserTypesResolve implements Resolve<UserType[]> {

  userTypes:UserType[];

  constructor(private userTypeS:UserTypeService, private rs:RequestService, private localStorageS: CoolLocalStorage)  {}

  resolve(): Promise<UserType[]>| any {

    return this.localStorageS.getObject('types') != null ?
      this.localStorageS.getObject('types') :
      this.userTypeS.getAll().then(userTypes => userTypes).catch(error => error);

  }
}
