import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import {environment} from "../../environments/environment";
import {AuthenticationService} from "./authentication.service";
import {Localization} from "../model/localization";

@Injectable()
export class LocalizationService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService:AuthenticationService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'localizations', {headers:headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);
        return response.json() as Localization[];
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
