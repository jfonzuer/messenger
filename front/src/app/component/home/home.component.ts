import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../model/user";
import {Router} from "@angular/router";
import {SharedService} from "../../service/shared.service";

@Component({
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css']
})
export class HomeComponent implements OnInit {

  users:User[];

  constructor(private userService: UserService, private router: Router, private sharedService: SharedService) { }

  ngOnInit() {
    this.sharedService.redirectLogin();
    this.userService.getLast20Users().then(users => { this.users = users; })
  }
}
