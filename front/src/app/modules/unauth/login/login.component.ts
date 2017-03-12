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

  private authentication: Authentication;

  constructor(private http:Http, private localStorageService: CoolLocalStorage, private authenticationService: AuthenticationService, private router: Router, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
    this.authentication = new Authentication();
  }

  ngOnInit() {
    //this.sharedService.redirectHome();
    this.authentication.email = "u3@gmail.com";
    this.authentication.password = "test";

    //TODO raffraichissement du token ?
  }

  send() {
    if (this.authentication.email != "" && this.authentication.password != "") {
      this.authenticationService.post(this.authentication).then(response => {
        this.localStorageService.setObject("token", response.token);
        this.localStorageService.setObject("user", response.user);
        this.router.navigate(['/app/home']);

      })
        .catch(error => {
          this.toastr.error(error.json().message);
          if (error.status === 423) {
            // this.toastr.warning("Vous allez être redirigé vers la page d'activation");
            //this.router.navigate(['/unauth/activate']);
            setTimeout(() => this.router.navigate(['/unauth/activate']), 1000);
            //setTimeout(this.router.navigate(['/unauth/activate']), 500);
            }
        })
    }
  }
}
