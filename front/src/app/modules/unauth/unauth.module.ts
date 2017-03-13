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
import {UnauthFooterComponent} from "./unauth-footer/unauth-footer.component";
import {DesactivateComponent} from "./desactivate/desactivate.component";
import {BootstrapModalModule} from "angular2-modal/plugins/bootstrap";
import {ModalModule} from "angular2-modal";
import {TermsComponent} from "./terms/terms.component";
import {UnauthHomeComponent} from "./unauth-home/unauth-home.component";
import {UnauthUserService} from "../../services/unauth-user.service";
import {SendActivationMailComponent} from "./send-activation-mail/send-activation-mail.component";
import {ReCaptchaModule} from "angular2-recaptcha";

/**
 * Created by pgmatz on 28/10/16.
 */

@NgModule({
  imports: [
    SharedModule,
    UnauthRoutingModule,
    ModalModule.forRoot(),
    BootstrapModalModule,
    ReCaptchaModule
  ],
  declarations: [
    LoginComponent,
    PasswordForgotComponent,
    PasswordResetComponent,
    RegisterComponent,
    UnauthHeadingComponent,
    UnauthNavbarComponent,
    UnauthFooterComponent,
    DesactivateComponent,
    TermsComponent,
    UnauthHomeComponent,
    SendActivationMailComponent
  ],
  providers: [
    UnauthUserService
  ]
})
export class UnauthModule { }

