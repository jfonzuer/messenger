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
  addImage:boolean = false;
  orderNumber:number;
  uploadUrl:string;
  private sizeLimit:number;

  constructor(private http:Http, private uploadService: UploadService, private authenticationService: AuthenticationService, private localStorageService: LocalStorageService, private sharedService: SharedService) {
    this.sizeLimit = environment.sizeLimit;
    this.uploadUrl = environment.uploadUrl;
  }

  // methode qui check si on peut ajouter une image, qui recalcul l'ordre, qui remet file à null
  private checkAll() {
    this.checkAddImage();
    this.checkOrder();
    this.file = null;
  }

  ngOnInit() {
    this.checkAll();
  }

  fileChangeEvent(event:any) {
    this.file = event.target.files[0];
  }

  send(valid:boolean) {
    if (valid && this.file != null) {
      if (this.file.size < this.sizeLimit) {
        console.log(this.orderNumber);
        console.log("SEND POST IMAGE REQUEST");
        this.uploadService.uploadImage(this.file, this.orderNumber).subscribe(
          image => {
            this.user.images[this.orderNumber - 1] = image;
            this.localStorageService.set('user', this.user);
            this.sharedService.refreshUser(this.user);

            this.checkAll();
          },
          error => this.error = error
        )
      }
      else {
        this.error = "La taille maximale de fichier est 2,048 MB";
        setTimeout(() => this.error = "", 2000);
      }
    }
  }

  deleteImage(orderNumber:number) {
    console.log(orderNumber);
    this.uploadService.deleteImage(orderNumber).subscribe(
      images => {
        console.log("success");
        console.log(images);
        this.user.images = images;

        this.checkAll();
      },
      error => this.error = error
    );
  }

  setAsProfile(orderNumber:number) {
    console.log("set as profile " + orderNumber);
    this.uploadService.setAsProfile(orderNumber).subscribe(
      images => {
        console.log(images);
        this.user.images = images;
        this.localStorageService.set('user', this.user);
      },
      error => this.error = error
    )
  }

  checkAddImage() {
    this.user.images.length < 3 ? this.addImage = true : this.addImage = false;
  }
  checkOrder() {
    this.user.images[0].url == 'profile.png' ? this.orderNumber = 1 : this.orderNumber = this.user.images.length + 1;
  }
}
