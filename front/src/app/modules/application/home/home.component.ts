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
  uploadUrl:string;

  constructor(private userService: UserService, private router: Router, private sharedService: SharedService, private localStorageService:LocalStorageService) {
    this.uploadUrl = environment.uploadUrl;
  }

  ngOnInit() {
    this.sharedService.redirectLogin();
    this.userService.getLast20Users().then(users => { this.users = users; })
    let user = this.sharedService.getCurrentUser();
    user.userType.name == 'Soumis' ? this.title = 'Dernieres dominatrices inscrites' : this.title = 'Derniers soumis inscris';
  }
}
