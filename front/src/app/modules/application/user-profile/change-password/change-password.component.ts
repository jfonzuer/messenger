import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import {User} from "../../../../model/user";
import {AuthenticationService} from "../../../../services/authentication.service";
import {UserService} from "../../../../services/user.service";
import {LocalStorageService} from 'angular-2-local-storage';
import {PasswordConfirmation} from "../../../../model/passwordConfirmation";

@Component({
  selector: 'app-change-password',
  templateUrl: 'change-password.component.html',
  styleUrls: ['change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();

  passwordConfirmation: PasswordConfirmation = new PasswordConfirmation();

  constructor(private authenticationService: AuthenticationService, private userService: UserService, private localStorageService: LocalStorageService) { }

  ngOnInit() {
  }

  updatePassword(valid:boolean) {
    if (valid) {
      if (this.passwordConfirmation.password == this.passwordConfirmation.confirmation) {
        this.userService.updatePassword(this.passwordConfirmation).then(() => {
          this.successEmitter.emit("Le mot de passe a été modifié avec succés.");
          this.authenticationService.refreshToken().then(response => { this.localStorageService.set('token', response.token) }).catch(error => this.errorEmitter.emit(error));
        })
      } else {
        this.errorEmitter.emit("Les mots de passe doivent être identiques");
      }
    }
  }

}
