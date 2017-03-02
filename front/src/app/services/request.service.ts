/**
 * Created by pgmatz on 05/11/16.
 */

import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {Router} from "@angular/router";
import {SharedService} from "./shared.service";

@Injectable()
export class RequestService {

  handleError(error: any) {
    let message:string;
    if (error.status == 0) {
      message = 'Connexion au serveur impossible';
    } else if (error.status == 403) {
      message = "Vous n'avez pas les droits d'accès, ou votre abonnement premium a expiré, dans ce cas veuillez vous reconnecter.";
    }
    else if (error._body) {
      let body = JSON.parse(error._body);
      if (body.message) {
        message = body.message;
      }
    }
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
