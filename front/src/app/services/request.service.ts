/**
 * Created by pgmatz on 05/11/16.
 */

import {Injectable} from "@angular/core";
import {Response} from "@angular/http";

@Injectable()
export class RequestService {

  handleError(error: any) {
    let message:string;
    error.status == 0 ? message = 'Server unreachable' : error._body.message ? message = JSON.parse(error._body).message : message = error._body;
    return Promise.reject(message);
  }

  handleResponse(response:any) {
    if (response.status === 200) {
      return;
    }
    throw Error(response.message);
  }

  extractData(res: Response) {
    let body;

    // check if empty, before call json
    if (res.text()) {
      body = res.json();
    }

    return body || {};
  }
}
