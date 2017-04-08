import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {DatetimeService} from "./datetime.service";
import {AuthenticationService} from "./authentication.service";
import {Http} from "@angular/http";
import {Visit} from "../model/visit";
import {RequestService} from "./request.service";
import {Pager} from "../model/pager";

@Injectable()
export class VisitService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService: AuthenticationService, private datetimeService:DatetimeService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getVisits(pager:Pager) : any {
    let headers = this.authenticationService.getHeaders();

    let queryParams = '?l=1';
    if (pager) {
      queryParams = queryParams.concat('&p=' + pager.page);
    }

    return this.http.get(this.baseUrl + 'visits/' + queryParams, {headers:headers})
      .toPromise()
      .then(response => {
        console.log(response);
        this.rs.handleResponse(response);

        let body = response.json();
        let visits:Visit[] = body.content;
        for(let visit of visits) {
          this.datetimeService.formatVisit( visit);
          this.datetimeService.formatAge(visit.visitor);
        }
        body.content = visits;
        return body;
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
