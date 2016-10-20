/**
 * Created by pgmatz on 21/10/16.
 */

import {Injectable} from "@angular/core";
import {environment} from "../environments/environment";
import {AuthenticationService} from "./authentication.service";
import {Http} from "@angular/http";

@Injectable()
export class FetishService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService:AuthenticationService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'fetishes', {headers:headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);

        // si reponse non vide
        if (response.text()) {
          return response.json();
        }
        return [];
      })
      .catch(this.handleError);
  }

  private handleError(error: any) {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  private handleResponse(response:any) {
    if (response.status === 200) {
      return;
    }
    throw Error(response.message);
  }
}
