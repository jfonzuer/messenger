import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-2-local-storage';
import {Authentication} from "../../model/authentication";
import {Http} from "@angular/http";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {SharedService} from "../../services/shared.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
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

    //TODO raffraichissement du token ?
  }

  login(form: any) {
    if (form.valid) {
      this.authenticationService.post(this.authentication).then(response => {

        this.localStorageService.set("token", response.token);

        // TODO call service to get rights
        this.authenticationService.getAuthenticatedUser().then(user => {
          this.localStorageService.set('user', user);
          this.router.navigate(['/app/home']);
        });

      })
        .catch(error => {
          this.error = error;
          setTimeout(() => this.error = "", 2000);
        })
    }
  }

}
