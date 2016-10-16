import { Component, OnInit } from '@angular/core';
import {Http, Headers} from "@angular/http";
import {environment} from "../../environments/environment";
import {Authentication} from "../../model/authentication";
import { LocalStorageService } from 'angular-2-local-storage';
import {AuthenticationService} from "../../service/authentication.service";
import {Router} from "@angular/router";
import {SharedService} from "../../service/shared.service";

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
    this.authentication.email = "member1@gmail.com";
    this.authentication.password = "password1";

    //TODO raffraichissement du token ?
  }

  login(form: any) {
    if (form.valid) {
      this.authenticationService.post(this.authentication).then(response => {
        console.log(response);
        console.log(response.token);

        this.localStorageService.set("token", response.token);
        console.log(response.token);

        this.router.navigateByUrl('home');

        // TODO call service to get rights

        this.authenticationService.getAuthenticatedUser().then(user => { this.localStorageService.set('user', user);  console.log(user); });

      })
        .catch(error => {
          this.error = error;
          setTimeout(() => this.error = "", 2000);
        })
    }
  }

}
