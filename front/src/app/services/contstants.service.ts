import {Http} from "@angular/http";
import {AuthenticationService} from "./authentication.service";
import {RequestService} from "./request.service";
import {environment} from "../../environments/environment";
import {Injectable} from "@angular/core";
/**
 * Created by pgmatz on 25/02/17.
 */

@Injectable()
export class ConstantService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService:AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'constants', {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);
        return response.json();
      })
      .catch(this.rs.handleError);
  }
}
