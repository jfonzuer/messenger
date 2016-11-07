import {RouterModule} from "@angular/router";
import {LoginComponent} from "../unauth/login/login.component";
import {RegisterComponent} from "../unauth/register/register.component";
import {PasswordForgotComponent} from "../unauth/password-forgot/password-forgot.component";
import {PasswordResetComponent} from "../unauth/password-reset/password-reset.component";
import {UnauthNavbarComponent} from "../unauth/unauth-navbar/unauth-navbar.component";
import {UnauthHeadingComponent} from "../unauth/unauth-heading/unauth-heading.component";
import {NgModule} from "@angular/core";
import {VisitComponent} from "./visit/visit.component";
import {ConversationListComponent} from "./conversation-list/conversation-list.component";
import {MessengerComponent} from "./messenger/messenger.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {UserViewComponent} from "./user-view/user-view.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuardService} from "../../services/auth-guard.service";
import {UserParametersComponent} from "./user-parameters/user-parameters.component";
import {NavbarComponent} from "./navbar/navbar.component";
import {HeadingComponent} from "./heading/heading.component";
import {UserResolve} from "../../services/user-resolve.service";
/**
 * Created by pgmatz on 28/10/16.
 */

@NgModule({
    imports: [RouterModule.forChild([
        {
            path: 'app', canActivate: [AuthGuardService], children: [
            { path: '', redirectTo: 'home', pathMatch: 'full'},
            { path: 'home', component: HomeComponent },
            { path: 'profile/:id', component: UserViewComponent, resolve: { user: UserResolve } },
            { path: 'profile', component: UserProfileComponent },
            { path: 'conversation', component: MessengerComponent },
            { path: 'conversation/:id', component: MessengerComponent },
            { path: 'visits', component: VisitComponent },
            { path: 'parameters', component: UserParametersComponent },
            { path: '', component: NavbarComponent, outlet: 'header'},
            { path: '', component: HeadingComponent, outlet: 'banner'}]
        }
    ])],
    exports: [RouterModule]
})
export class ApplicationRoutingModule {}
