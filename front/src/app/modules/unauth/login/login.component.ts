import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-2-local-storage';
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";
import {SharedService} from "../../../services/shared.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {Authentication} from "../../../model/authentication";

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css']
})
export class LoginComponent implements OnInit {

  private baseUrl:string;
  private authentication: Authentication = new Authentication();
  error:string;

  constructor(private http:Http, private localStorageService: LocalStorageService, private authenticationService: AuthenticationService, private router: Router, private sharedService: SharedService) {
    this.baseUrl = environment.baseUrl;
  }

  ngOnInit() {
    //this.sharedService.redirectHome();
    this.authentication.email = "pgiraultmatz@gmail.com";
    this.authentication.password = "test";

    //this.authentication.email = "member4@gmail.com";
    //this.authentication.password = "password4";

    //TODO raffraichissement du token ?
  }

  login(form: any) {
    if (form.valid) {
      this.authenticationService.post(this.authentication).then(response => {

        this.localStorageService.set("token", response.token);
        this.localStorageService.set("user", response.user);
        console.log(response.user);
        this.router.navigate(['/app/home']);

      })
        .catch(error => {
          this.error = error;
          setTimeout(() => this.error = "", 2000);
        })
    }
  }
}
