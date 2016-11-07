import {Component, OnInit, Input} from '@angular/core';
import {LocalStorageService} from 'angular-2-local-storage';
import {Http} from "@angular/http";
import {User} from "../../../model/user";
import {UploadService} from "../../../services/upload.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {SharedService} from "../../../services/shared.service";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent implements OnInit {

  error:string;
  success:string;
  file:File;
  @Input() user:User;
  uploadUrl:string;

  constructor(private http:Http, private uploadService: UploadService, private authenticationService: AuthenticationService, private localStorageService: LocalStorageService, private sharedService: SharedService) {
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
        user => { this.user = user; this.localStorageService.set('user', this.user); this.sharedService.refreshUser(user); },
        error => this.error = error
      )
    }
  }
}
