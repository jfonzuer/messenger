import {Component, OnInit, Input, EventEmitter, Output, ViewContainerRef} from '@angular/core';
import {User} from "../../../../model/user";
import {AuthenticationService} from "../../../../services/authentication.service";
import {UserService} from "../../../../services/user.service";
import {PasswordConfirmation} from "../../../../model/passwordConfirmation";
import {CoolLocalStorage} from "angular2-cool-storage";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-change-password',
  templateUrl: 'change-password.component.html',
  styleUrls: ['change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  loading:boolean = false;

  passwordConfirmation: PasswordConfirmation = new PasswordConfirmation();

  constructor(private authenticationService: AuthenticationService, private userService: UserService, private localStorageService: CoolLocalStorage, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
  }

  updatePassword() {
    if (this.passwordConfirmation.password == this.passwordConfirmation.confirmation) {
      this.loading = true;
      this.userService.updatePassword(this.passwordConfirmation).then(() => {
        this.loading = false;
        this.toastr.success("Le mot de passe a été modifié avec succés.");
        this.authenticationService.refreshToken().then(response => { this.localStorageService.setObject('token', response.token) }).catch(error => this.toastr.error(error));
      })
        .catch(error => {
          this.toastr.error(error);
          this.loading = false;
        })
    } else {
      this.toastr.error("Les mots de passe doivent être identiques");
    }
  }

}
