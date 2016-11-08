import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {AuthenticationService} from "./authentication.service";
import {environment} from "../../environments/environment";
import {RequestService} from "./request.service";

@Injectable()
export class UploadService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService: AuthenticationService, private rs:RequestService) {
    this.baseUrl = environment.baseUrl;
  }

  uploadProfilePicture(file:File, order:number) {
    let headers = this.authenticationService.getHeaders();
    let fileForm = new FormData();
    fileForm.append("file", file);
    fileForm.append("order", order);

    return this.http.post(this.baseUrl + 'medias', fileForm, {headers:headers}).map(response => response.json());;
  }
}
