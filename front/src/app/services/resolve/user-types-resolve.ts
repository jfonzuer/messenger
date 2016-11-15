/**
 * Created by pgmatz on 15/11/16.
 */

import {Injectable} from "@angular/core";
import {LocalStorageService} from 'angular-2-local-storage';
import {User} from "../../model/user";
import {Resolve} from "@angular/router";
import {UserTypeService} from "../user-type.service";
import {RequestService} from "../request.service";
import {UserType} from "../../model/userType";

@Injectable()
export class UserTypesResolve implements Resolve<UserType[]> {

  userTypes:UserType[];

  constructor(private userTypeS:UserTypeService, private rs:RequestService, private localStorageS: LocalStorageService)  {}

  resolve(): Promise<UserType[]>| any {

    return this.localStorageS.get('types') != null ?
      this.localStorageS.get('types') :
      this.userTypeS.getAll().then(userTypes => userTypes).catch(error => error);

  }
}
