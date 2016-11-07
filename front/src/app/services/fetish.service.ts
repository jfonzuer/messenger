import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import {AuthenticationService} from "./authentication.service";
import {environment} from "../../environments/environment";
import {Fetish} from "../model/fetish";
import {RequestService} from "./request.service";

@Injectable()
export class FetishService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService:AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  getAll() {
    let headers = this.authenticationService.getHeaders();

    return this.http.get(this.baseUrl + 'fetishes', {headers:headers})
      .toPromise()
      .then(response => {
        this.rs.handleResponse(response);

        // si reponse non vide
        if (response.text()) {
          return response.json();
        }
        return [];
      })
      .catch(this.rs.handleError);
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
}
