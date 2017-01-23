import { Injectable } from '@angular/core';
import {AuthenticationService} from "./authentication.service";
import {Http} from "@angular/http";
import {environment} from "../../environments/environment";
import {RequestService} from "./request.service";
import {User} from "../model/user";

@Injectable()
export class AdminService {

  private baseUrl:string;

  constructor(private http:Http, private authenticationService: AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getReportedUser() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'admin/reported', {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json() as User[];
      })
      .catch(this.rs.handleError);
  }

  block(id:number) {
    let headers = this.authenticationService.getHeaders();

    return this.http.put(this.baseUrl + 'admin/unblock/' + id, {}, {headers: headers})
      .toPromise()
      .then(() => {
      })
      .catch(this.rs.handleError);
  }

  unblock(id:number) {
    let headers = this.authenticationService.getHeaders();

    return this.http.put(this.baseUrl + 'admin/block/' + id, {}, {headers: headers})
      .toPromise()
      .then(() => {})
      .catch(this.rs.handleError);
  }
}
