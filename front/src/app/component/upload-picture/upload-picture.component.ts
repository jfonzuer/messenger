import {Component, OnInit, Input} from '@angular/core';
import {User} from "../../model/user";
import {UploadService} from "../../service/upload.service";
import {Http} from "@angular/http";
import {AuthenticationService} from "../../service/authentication.service";
import {environment} from "../../../environments/environment";
import { LocalStorageService } from 'angular-2-local-storage';

@Component({
  selector: 'app-upload-picture',
  templateUrl: 'upload-picture.component.html',
  styleUrls: ['upload-picture.component.css']
})
export class UploadPictureComponent implements OnInit {

  error:string;
  success:string;
  file:File;
  @Input() user:User;
  uploadUrl:string;

  constructor(private http:Http, private uploadService: UploadService, private authenticationService: AuthenticationService, private localStorageService: LocalStorageService) {
    this.uploadUrl = environment.uploadUrl;
    console.log(this.uploadUrl);
  }

  ngOnInit() {
  }

  fileChangeEvent(event:any) {
    this.file = event.target.files[0];
  }

  send(valid:boolean) {
    if (valid) {
      this.uploadService.uploadProfilePicture(this.file).subscribe(
        user => { this.user = user; this.localStorageService.set('user', this.user); },
        error => this.error = error
      )
    }
  }
}
