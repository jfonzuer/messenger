import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {AuthenticationService} from "./authentication.service";
import {environment} from "../../environments/environment";

@Injectable()
export class UploadService {

  private baseUrl:string;

  constructor (private http:Http, router: Router, private authenticationService: AuthenticationService) {
    this.baseUrl = environment.baseUrl;
  }

  uploadProfilePicture(file:File) {
    let headers = this.authenticationService.getHeaders();
    let fileForm = new FormData();
    fileForm.append("file", file);

    return this.http.post(this.baseUrl + 'medias', fileForm, {headers:headers}).map(response => response.json());;
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
