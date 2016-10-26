import {Routes, RouterModule} from "@angular/router";
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {HomeComponent} from "./component/home/home.component";
import {MessengerComponent} from "./component/messenger/messenger.component";
import {VisitComponent} from "./component/visit/visit.component";
import {ForgotPasswordComponent} from "./component/password/forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./component/password/reset-password/reset-password.component";
import {UserParametersComponent} from "./component/user/user-parameters/user-parameters.component";
import {UserProfileComponent} from "./component/user/user-profile/user-profile.component";
import {UserInformationsComponent} from "./component/user/user-informations/user-informations.component";

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
    component: UserProfileComponent
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
    component: UserInformationsComponent
  },
  {
    path : 'parameters',
    component: UserParametersComponent
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
