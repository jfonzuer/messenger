import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";
import {SharedService} from "../../../services/shared.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {Authentication} from "../../../model/authentication";
import {CoolLocalStorage} from "angular2-cool-storage";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css']
})
export class LoginComponent implements OnInit {

  private baseUrl:string;
  private authentication: Authentication = new Authentication();
  error:string;

  constructor(private http:Http, private localStorageService: CoolLocalStorage, private authenticationService: AuthenticationService, private router: Router, private sharedService: SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.baseUrl = environment.baseUrl;
    this.toastr.setRootViewContainerRef(vRef)
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

        this.localStorageService.setObject("token", response.token);
        this.localStorageService.setObject("user", response.user);
        console.log(response.user);
        this.router.navigate(['/app/home']);

      })
        .catch(error => {
          this.toastr.error("ok");
          this.error = error;
          setTimeout(() => this.error = "", 2000);
        })
    }
  }
}
