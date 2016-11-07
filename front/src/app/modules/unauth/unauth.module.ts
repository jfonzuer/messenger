import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {NgModule} from "@angular/core";
import {LoginComponent} from "./login/login.component";
import {PasswordForgotComponent} from "./password-forgot/password-forgot.component";
import {PasswordResetComponent} from "./password-reset/password-reset.component";
import {RegisterComponent} from "./register/register.component";
import {UnauthHeadingComponent} from "./unauth-heading/unauth-heading.component";
import {UnauthNavbarComponent} from "./unauth-navbar/unauth-navbar.component";
import {SharedModule} from "../shared/shared.module";
import {UnauthRoutingModule} from "./unauth-routing.module";
/**
 * Created by pgmatz on 28/10/16.
 */

@NgModule({
    imports: [
        SharedModule,
        UnauthRoutingModule,
    ],
    declarations: [
        LoginComponent,
        PasswordForgotComponent,
        PasswordResetComponent,
        RegisterComponent,
        UnauthHeadingComponent,
        UnauthNavbarComponent,
    ],
})
export class UnauthModule { }

