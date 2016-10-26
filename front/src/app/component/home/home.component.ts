import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../model/user";
import {Router} from "@angular/router";
import {SharedService} from "../../service/shared.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css']
})
export class HomeComponent implements OnInit {

  users:User[];
  uploadUrl:string;

  constructor(private userService: UserService, private router: Router, private sharedService: SharedService) {
    this.uploadUrl = environment.uploadUrl;
  }

  ngOnInit() {
    this.sharedService.redirectLogin();
    this.userService.getLast20Users().then(users => { this.users = users; })
  }
}
