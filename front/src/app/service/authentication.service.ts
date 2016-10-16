import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {environment} from "../environments/environment";
import {Authentication} from "../model/authentication";
import { LocalStorageService } from 'angular-2-local-storage';

/**
 * Created by pgmatz on 10/10/16.
 */



@Injectable()
export class AuthenticationService {

  private baseUrl:string;

  constructor (private http:Http, private router: Router, private localStorageService: LocalStorageService) {
    this.baseUrl = environment.baseUrl;
  }

  post(authentication:Authentication) {

    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.baseUrl + 'login', authentication, headers)
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return response.json();
      })
      .catch(this.handleError);
  }

  getAuthenticatedUser() {
    let headers = this.getHeaders();
    return this.http.get(this.baseUrl + 'user', { headers:headers })
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return response.json();
      })
      .catch(this.handleError);
  }

  getHeaders() : Headers {
    let headers = new Headers();
    let token = this.localStorageService.get("token");
    headers.append('Authorization', '' + token);
    console.log(token);
    console.log(headers);
    return headers;
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
