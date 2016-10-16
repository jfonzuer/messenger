import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-2-local-storage';
import {AuthenticationService} from "../../service/authentication.service";
import {SharedService} from "../../service/shared.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private localStorageService: LocalStorageService, private authenticationService: AuthenticationService, private sharedService: SharedService, private  router: Router) { }

  ngOnInit() {
  }

  logout() {
    this.localStorageService.remove('token');
    this.sharedService.logout();
    this.router.navigateByUrl('login');
  }
}
