import {Component, OnInit, ViewContainerRef} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {Authentication} from "../../../model/authentication";
import {CoolLocalStorage} from "angular2-cool-storage";
import {ToastsManager} from "ng2-toastr";
import {environment} from "../../../../environments/environment";
import {ConstantService} from "../../../services/contstants.service";

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css']
})
export class LoginComponent implements OnInit {

  private authentication: Authentication;
  loading:boolean = false;

  constructor(private localStorageService: CoolLocalStorage, private authenticationService: AuthenticationService, private router: Router,
              private toastr: ToastsManager, vRef: ViewContainerRef, private constantService:ConstantService) {
    this.toastr.setRootViewContainerRef(vRef);
    this.authentication = new Authentication();
  }

  ngOnInit() {
    //this.sharedService.redirectHome();
    if (!environment.production) {
      this.authentication.email = "contact@dominapp.com";
      this.authentication.password = 'test';
    }
    // refresh contstants on logine
    this.constantService.getAll().then(constants => { this.localStorageService.setObject("constants", constants); return constants; }).catch(error => error);
  }

  send() {
    if (this.authentication.email != "" && this.authentication.password != "") {

      this.loading = true;

      this.authenticationService.post(this.authentication).then(response => {
        this.localStorageService.setObject("token", response.token);
        this.localStorageService.setObject("user", response.user);
        this.router.navigate(['/app/home']);

      })
        .catch(error => {
          this.loading = false;
          let message = error.json().message;
          this.toastr.error(message ? message : "Connexion au serveur impossible");
          if (error.status === 423) {
            // this.toastr.warning("Vous allez être redirigé vers la page d'activation");
            //this.router.navigate(['/unauth/activate']);
            setTimeout(() => this.router.navigate(['/unauth/activate']), 1000);
          }
        })
    }
  }
}
