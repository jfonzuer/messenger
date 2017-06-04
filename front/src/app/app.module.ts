import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {UserService} from "./services/user.service";
import {SharedService} from "./services/shared.service";
import {AuthenticationService} from "./services/authentication.service";
import {DatetimeService} from "./services/datetime.service";
import {FetishService} from "./services/fetish.service";
import {AuthGuardService} from "./services/guard/auth-guard.service";
import {UnauthModule} from "./modules/unauth/unauth.module";
import {ApplicationModule} from "./modules/application/application.module";
import {AppRoutingModule} from "./app-routing.module";
import {RequestService} from "./services/request.service";
import {UserTypesResolve} from "./services/resolve/user-types-resolve";
import {ToastModule} from "ng2-toastr";
import {CoolStorageModule} from "angular2-cool-storage";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    HttpModule,
    UnauthModule,
    ApplicationModule,
    AppRoutingModule,
    ToastModule,
    CoolStorageModule,
    BrowserAnimationsModule
  ],
  providers: [
    UserService,
    SharedService,
    AuthenticationService,
    DatetimeService,
    FetishService,
    RequestService,
    AuthGuardService,
    UserTypesResolve,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
