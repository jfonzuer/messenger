import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {Constant} from "../../model/response/constants";
import {ConstantService} from "../contstants.service";
import {RequestService} from "../request.service";
import {CoolLocalStorage} from "angular2-cool-storage";
/**
 * Created by pgmatz on 25/02/17.
 */

@Injectable()
export class ConstantsResolve implements Resolve<Constant> {


  constructor(private constantService:ConstantService, private rs:RequestService, private localStorageS: CoolLocalStorage)  {}

  resolve(): Promise<Constant> {

    return this.localStorageS.getObject('constants') != null ?
      this.localStorageS.getObject('constants') :
      this.constantService.getAll().then(constants => { this.localStorageS.setObject("constants", constants); return constants; }).catch(error => error);
  }
}

