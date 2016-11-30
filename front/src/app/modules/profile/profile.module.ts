import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {NgModule} from "@angular/core";
import {ApplicationModule} from "../application/application.module";
import {SharedModule} from "../shared/shared.module";
import {ProfileRoutingModule} from "./profile-routing.module";
import {ProfileComponent} from "./profile/profile.component";
import {ParametersComponent} from "./parameters/parameters.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {InformationComponent} from "./information/information.component";
/**
 * Created by pgmatz on 28/10/16.
 */

@NgModule({
  imports: [
    SharedModule,
    ApplicationModule,
    ProfileRoutingModule,
  ],
  declarations: [
    ProfileComponent,
    ParametersComponent,
    ChangePasswordComponent,
    InformationComponent
  ],
})
export class ProfileModule { }

