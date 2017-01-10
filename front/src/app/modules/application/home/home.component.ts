import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../../../model/user";
import {UserService} from "../../../services/user.service";
import {SharedService} from "../../../services/shared.service";
import {LocalStorageService} from 'angular-2-local-storage';
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css']
})
export class HomeComponent implements OnInit {

  users:User[];
  title:string;
  uploadImageUrl:string;

  constructor(private userService: UserService, private sharedService: SharedService) {
    this.uploadImageUrl = environment.uploadImageUrl;
  }

  ngOnInit() {
    this.userService.getUsers(null).then(response => {
      this.users = response.content;
    })
    let user = this.sharedService.getCurrentUser();
    user.userType.name == 'Soumis' ? this.title = 'Dernieres dominatrices inscrites' : this.title = 'Derniers soumis inscris';
  }
}
