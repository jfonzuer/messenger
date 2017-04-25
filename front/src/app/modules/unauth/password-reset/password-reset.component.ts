import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {ActivatedRoute, Router, Params} from "@angular/router";
import {PasswordConfirmation} from "../../../model/passwordConfirmation";
import {UserService} from "../../../services/user.service";
import {ResetPassword} from "../../../model/resetPassword";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-password-reset',
  templateUrl: 'password-reset.component.html',
  styleUrls: ['password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  userId:number;
  token:string;
  passwordConfirmation:PasswordConfirmation = new PasswordConfirmation();
  loading:boolean = false;

  constructor(private route:ActivatedRoute, private router:Router, private userService: UserService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      // si on arrive avec l'id d'un utilisateur spécifié
      if (params['id'] && params['token']) {
        this.userId = +params['id'];// (+) converts string 'id' to a number
        this.token = params['token'];
      }
      else {
        this.router.navigateByUrl('home');
      }
    });
  }

  send() {
    if (this.passwordConfirmation.password == this.passwordConfirmation.confirmation) {
      this.loading = true;
      //this.userService.resetPassword(this.passwordConfirmation)
      this.userService.resetPassword(new ResetPassword(this.passwordConfirmation, this.token, this.userId))
        .then(() => {
          this.toastr.success("Le mot de passe a été modifié, vous allez être redirigé vers la page de login"); setTimeout(this.router.navigate(['/unauth/home']), 2000) })
        .catch(error => {
            this.loading = false;
            this.toastr.error(error)
          }
        );
    }
    else {
      this.toastr.error("Les mots de passe doivent être identiques");
    }
  }
}
