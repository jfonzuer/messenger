/**
 * Created by pgmatz on 05/11/16.
 */

import {Injectable} from "@angular/core";

@Injectable()
export class RequestService {



  handleError(error: any) {
    console.log("ok");
    error.json();
    console.log(error);
    console.log(error._body);
    let body = JSON.parse(error._body);
    console.log(body);

    console.log(error.b);
    console.error(error);
    let message = "";
    error.status === 0 ? message = 'Server unreachable' : message = error.body.message;
    let e =  error.json();
    console.log(e);
    console.log("error");
    return Promise.reject(message);
  }

  /*
  handleError(error: any) {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
  */

  handleResponse(response:any) {
    console.log(response);
    if (response.status === 200) {
      return;
    }
    throw Error(response.message);
  }

}
