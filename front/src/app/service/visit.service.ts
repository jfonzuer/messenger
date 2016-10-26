/**
 * Created by pgmatz on 18/10/16.
 */

import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {AuthenticationService} from "./authentication.service";
import {Visit} from "../model/visit";
import {DatetimeService} from "./datetime.service";
import {environment} from "../../environments/environment";

@Injectable()
export class VisitService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService: AuthenticationService, private datetimeService:DatetimeService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'visits', {headers:headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);

        // si reponse non vide
        if (response.text()) {
          let visits:Visit[] = response.json().content;
          for(let visit of visits) {
            this.datetimeService.formatVisit(visit);
            this.datetimeService.formatAge(visit.visitor);
          }
          return visits;
        }
        return [];
      })
      .catch(this.handleError);
  }

  getUnseenNumber() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'visits/number', {headers:headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return response.json() as number;
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
