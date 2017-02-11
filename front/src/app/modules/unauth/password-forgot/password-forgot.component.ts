import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-password-forgot',
  templateUrl: 'password-forgot.component.html',
  styleUrls: ['password-forgot.component.css']
})
export class PasswordForgotComponent implements OnInit {

  email:string;
  constructor(private authenticationService:AuthenticationService, private router:Router, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.email = 'pgiraultmatz@gmail.com';
  }

  send(valid:boolean) {
    if (valid) {
      this.toastr.success("Envoi du mail ...")
      this.authenticationService.resetPasswordByEmail(this.email).then(() => {
        this.toastr.success("Un email vous a été envoyé pour réinitialiser le mot de passe, vous allez être redirigé vers le login")
        setTimeout(this.router.navigate(['/unauth/login']), 2000);
      })
        .catch(error => this.toastr.error(error));
    }
  }


}
