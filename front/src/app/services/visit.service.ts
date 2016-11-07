import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {DatetimeService} from "./datetime.service";
import {AuthenticationService} from "./authentication.service";
import {Http} from "@angular/http";
import {Visit} from "../model/visit";
import {RequestService} from "./request.service";

@Injectable()
export class VisitService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService: AuthenticationService, private datetimeService:DatetimeService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'visits', {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);

        // si reponse non vide
        if (response.text()) {
          let visits:Visit[] = response.json().content;
          for(let visit of visits) {
            //this.datetimeService.formatVisit( visit);
            this.datetimeService.formatAge(visit.visitor);
          }
          return visits;
        }
        return [];
      })
      .catch(this.rs.handleError);
  }

  getUnseenNumber() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'visits/number', {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json() as number;
      })
      .catch(this.rs.handleError);
  }
}
