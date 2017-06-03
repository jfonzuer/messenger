/**
 * Created by pgmatz on 03/06/17.
 */

import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";

@Injectable()
export class LoggerService {

  private prod: boolean;

  constructor() {
    this.prod = environment.production;
  }

  log(message: string, object: any) {
    if (!this.prod) {
      console.log(message, object);
    }
  }

  error(message: string, object: any) {
    if (!this.prod) {
      console.error(message, object);
    }
  }
}
