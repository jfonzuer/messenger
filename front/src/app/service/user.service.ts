/**
 * Created by pgmatz on 28/09/16.
 */

import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {environment} from "../environments/environment";
import {User} from "../model/user";
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class UserService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService: AuthenticationService) {
    this.baseUrl = environment.baseUrl;
  }

  getLast20Users() {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'users',  {headers: headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        console.log(response);
        return response.json();
      })
      .catch(this.handleError);
  }

  getUserById(id) {

    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'users/' + id, {headers: headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return response.json() as User;
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
