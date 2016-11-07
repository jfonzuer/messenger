import {RouterModule} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {PasswordForgotComponent} from "./password-forgot/password-forgot.component";
import {PasswordResetComponent} from "./password-reset/password-reset.component";
import {UnauthNavbarComponent} from "./unauth-navbar/unauth-navbar.component";
import {UnauthHeadingComponent} from "./unauth-heading/unauth-heading.component";
import {NgModule} from "@angular/core";
/**
 * Created by pgmatz on 29/10/16.
 */


@NgModule({
    imports: [RouterModule.forChild([
        {
            path: 'unauth', children: [
            { path: '', redirectTo: 'login', pathMatch: 'full'},
            { path: 'login', component: LoginComponent },
            { path: 'register', component: RegisterComponent },
            { path: 'password/forgotten', component: PasswordForgotComponent },
            { path: 'password/reset/:id/:token', component: PasswordResetComponent },
            { path: '', component: UnauthNavbarComponent, outlet: 'header'},
            { path: '', component: UnauthHeadingComponent, outlet: 'banner'}
        ]
        },
    ])],
    exports: [RouterModule]
})
export class UnauthRoutingModule {}
