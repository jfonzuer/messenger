import { Injectable } from '@angular/core';
import {Http, Response} from "@angular/http";
import {Router} from "@angular/router";
import {AuthenticationService} from "./authentication.service";
import {environment} from "../../environments/environment";
import {RequestService} from "./request.service";
import {Observable} from "rxjs";
import {OrderNumber} from "../model/orderNumber";

@Injectable()
export class UploadService {

  private baseUrl:string;

  constructor (private http:Http, private authenticationService: AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  deleteImage(id:number) {
    let headers = this.authenticationService.getHeaders();
    return this.http.delete(this.baseUrl + 'medias/' + id, {headers:headers}).map(response => response.json()).catch(this.rs.handleError);
  }

  uploadImage(file:File, order:number) {
      let headers = this.authenticationService.getHeaders();
      let fileForm = new FormData();
      fileForm.append("file", file);
      fileForm.append("order", order);
      return this.http.post(this.baseUrl + 'medias', fileForm, {headers:headers}).map(response => response.json()).catch(this.rs.handleError);
  }

  setAsProfile(order:number) {
    let headers = this.authenticationService.getHeaders();
    return this.http.put(this.baseUrl + 'medias/', new OrderNumber(order), {headers:headers}).map(response => response.json()).catch(this.rs.handleError);
  }
}
