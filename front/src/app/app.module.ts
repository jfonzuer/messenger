import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ApplicationRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import {HeaderComponent} from "./component/header/header.component";
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {routing}     from './app.routing';
import {UserService} from "./service/user.service";
import {HttpModule} from "@angular/http";
import {HomeComponent} from "./component/home/home.component";
import {ProfileComponent} from "./component/profile/profile.component";
import {MessengerComponent} from "./component/messenger/messenger.component";
import {MessageService} from "./service/message.service";
import {SendComponent} from "./component/messenger/send/send.component";
import {ConversationComponent} from "./component/messenger/conversation/conversation.component";
import {ConversationUserFilter} from "./pipes/conversation-user-filter.pipe";
import { LocalStorageService, LOCAL_STORAGE_SERVICE_CONFIG } from 'angular-2-local-storage';
import {AuthenticationService} from "./service/authentication.service";
import {SharedService} from "./service/shared.service";
import {VisitService} from "./service/visit.service";
import {ConversationService} from "./service/conversation.service";
import {VisitComponent} from "./component/visit/visit.component";
import {DatetimeService} from "./service/datetime.service";
import {LocalizationService} from "./service/localization.service";
import {FetishService} from "./service/fetish.service";
import {ForgotPasswordComponent} from "./component/password/forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./component/password/reset-password/reset-password.component";
import {MessageComponent} from "./component/messenger/message/message.component";
import {ErrorComponent} from "./component/messages/error/error.component";
import {SuccessComponent} from "./component/messages/success/success.component";
import {UserParametersComponent} from "./component/user/user-parameters/user-parameters.component";
import {UserInformationsComponent} from "./component/user/user-informations/user-informations.component";
import {UserProfileComponent} from "./component/user/user-profile/user-profile.component";

// Create config options (see ILocalStorageServiceConfigOptions) for deets:
let localStorageServiceConfig = {
  prefix: 'app-root',
  storageType: 'sessionStorage'
};

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    MessengerComponent,
    ConversationComponent,
    MessageComponent,
    SendComponent,
    ErrorComponent,
    ConversationUserFilter,
    VisitComponent,
    SuccessComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    UserParametersComponent,
    UserInformationsComponent,
    UserProfileComponent,
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    routing,
    HttpModule
  ],
  providers: [
    UserService,
    ConversationService,
    MessageService,
    SharedService,
    AuthenticationService,
    VisitService,
    DatetimeService,
    LocalizationService,
    FetishService,
    LocalStorageService,
    {
      provide: LOCAL_STORAGE_SERVICE_CONFIG, useValue: localStorageServiceConfig
    }
  ],

  entryComponents: [AppComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
