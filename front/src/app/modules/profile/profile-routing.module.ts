import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {UserTypesResolve} from "../../services/resolve/user-types-resolve";
import {FetishesResolve} from "../../services/resolve/fetishes-resolve.service";
import {LocalizationsResolve} from "../../services/resolve/localizations-resolve";
import {ProfileComponent} from "./profile/profile.component";
import {InformationComponent} from "./information/information.component";
import {ParametersComponent} from "./parameters/parameters.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
/**
 * Created by pgmatz on 29/10/16.
 */

@NgModule({
  imports: [RouterModule.forChild([
    {
      path: 'profile', children: [
      { path: '', redirectTo: 'profile', pathMatch: 'full'},
      { path: 'modify', component: ProfileComponent },
      { path: '', component: InformationComponent, outlet: 'information'},
      { path: '', component: ParametersComponent, outlet: 'parameters'},
      { path: '', component: ChangePasswordComponent, outlet: 'change-password'}
    ]
    },
  ])],
  exports: [RouterModule]
})
export class ProfileRoutingModule {}
