/**
 * Created by pgmatz on 14/11/16.
 */

import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {AuthenticationService} from "./authentication.service";
import {RequestService} from "./request.service";
import {environment} from "../../environments/environment";

@Injectable()
export class UserTypeService {

  private baseUrl:string;

  constructor (private http:Http, private authS:AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authS.getHeaders();
    return this.http.get(this.baseUrl + 'user/types',  {headers: headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);

    //return this.http.get(this.baseUrl + 'user/types', {headers:headers}).map(response => { this.rs.handleResponse(response); response.json(); }).catch(this.rs.handleError);
  }
}
