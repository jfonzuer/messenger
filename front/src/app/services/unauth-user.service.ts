import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {User} from "../model/user";
import {environment} from "../../environments/environment";
import {DatetimeService} from "./datetime.service";
import {RequestService} from "./request.service";
/**
 * Created by pgmatz on 04/03/17.
 */

@Injectable()
export class UnauthUserService {

  private baseUrl:string;

  constructor (private http:Http, private datetimeService:DatetimeService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getLastRegisteredDominas() : any {

    let headers = new Headers();

    return this.http.get(this.baseUrl + 'unauth/dominas', {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        // if response not empty
        if (response.text()) {
          let body = response.json();
          this.datetimeService.formatUsersAge(<User[]> body);
          return body;
        }
        return [];
      })
      .catch(this.rs.handleError);
  }
}
