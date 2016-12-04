import {RouterModule} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {PasswordForgotComponent} from "./password-forgot/password-forgot.component";
import {PasswordResetComponent} from "./password-reset/password-reset.component";
import {UnauthNavbarComponent} from "./unauth-navbar/unauth-navbar.component";
import {UnauthHeadingComponent} from "./unauth-heading/unauth-heading.component";
import {NgModule} from "@angular/core";
import {UserTypesResolve} from "../../services/resolve/user-types-resolve";
import {FetishesResolve} from "../../services/resolve/fetishes-resolve.service";
import {LocalizationsResolve} from "../../services/resolve/localizations-resolve";
import {UnauthFooterComponent} from "./unauth-footer/unauth-footer.component";
import {DesactivateComponent} from "./desactivate/desactivate.component";
/**
 * Created by pgmatz on 29/10/16.
 */


@NgModule({
  imports: [RouterModule.forChild([
    {
      path: 'unauth', children: [
      { path: '', redirectTo: 'login', pathMatch: 'full'},
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent, resolve: { fetishes:FetishesResolve, localizations: LocalizationsResolve, userTypes: UserTypesResolve} },
      { path: 'password/forgotten', component: PasswordForgotComponent },
      { path: 'password/reset/:id/:token', component: PasswordResetComponent },
      { path: 'desactivate', component: DesactivateComponent },
      { path: '', component: UnauthNavbarComponent, outlet: 'header'},
      { path: '', component: UnauthHeadingComponent, outlet: 'banner'},
      { path: '', component: UnauthFooterComponent, outlet: 'footer'}
    ]
    },
  ])],
  exports: [RouterModule]
})
export class UnauthRoutingModule {}
