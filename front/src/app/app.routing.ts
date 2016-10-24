import {Routes, RouterModule} from "@angular/router";
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {HomeComponent} from "./component/home/home.component";
import {ProfileComponent} from "./component/profile/profile.component";
import {MessengerComponent} from "./component/messenger/messenger.component";
import {UserComponent} from "./component/user/user.component";
import {ParametersComponent} from "./component/parameters/parameters.component";
import {VisitComponent} from "./component/visit/visit.component";
import {ForgotPasswordComponent} from "./component/password/forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./component/password/reset-password/reset-password.component";

/**
 * Created by pgmatz on 28/09/16.
 */

const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'profile/:id',
    component: ProfileComponent
  },
  {
    path: 'conversation',
    component: MessengerComponent
  },
  {
    path: 'conversation/:id',
    component: MessengerComponent
  },
  {
    path : 'profile',
    component: UserComponent
  },
  {
    path : 'parameters',
    component: ParametersComponent
  },
  {
    path: 'visits',
    component: VisitComponent
  },
  {
    path: 'password/forgotten',
    component: ForgotPasswordComponent
  },
  {
    path: 'password/reset/:id/:token',
    component: ResetPasswordComponent
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
];

export const routing = RouterModule.forRoot(appRoutes);
