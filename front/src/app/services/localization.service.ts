import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import {environment} from "../../environments/environment";
import {AuthenticationService} from "./authentication.service";
import {Localization} from "../model/localization";
import {RequestService} from "./request.service";

@Injectable()
export class LocalizationService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService:AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'localizations', {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json() as Localization[];
      })
      .catch(this.rs.handleError);
  }
}
