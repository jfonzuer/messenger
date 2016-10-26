/**
 * Created by pgmatz on 21/10/16.
 */

import {Injectable} from "@angular/core";
import {AuthenticationService} from "./authentication.service";
import {Http} from "@angular/http";
import {Fetish} from "../model/fetish";
import {environment} from "../../environments/environment";

@Injectable()
export class FetishService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService:AuthenticationService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'fetishes', {headers:headers})
      .toPromise()
      .then(response => {
        this.handleResponse(response);

        // si reponse non vide
        if (response.text()) {
          return response.json();
        }
        return [];
      })
      .catch(this.handleError);
  }

  updateCheckedFetishes(fetish:Fetish, event:any, selectedFetishId:number[]) : number[] {
    event.target.checked ? selectedFetishId.push(fetish.id) : selectedFetishId.splice(selectedFetishId.indexOf(fetish.id), 1);
    selectedFetishId.sort();
    return selectedFetishId;
  }

  initIdList(fetishes: Fetish[]) : number[] {
    let idList: number[] = [];
    // on initialise la liste des id des pratiques
    for (let fetish of fetishes) {
      idList.push(fetish.id);
    }
    return idList;
  }

  getFetishListFromIdList(idList: number[]) : Fetish[] {
    let fetishes: Fetish[] = [];
    for (let id of idList) {
      fetishes.push(new Fetish(id, ""));
    }
    return fetishes;
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
