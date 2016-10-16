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
import {ConversationService} from "./service/conversation.service";
import {MessengerComponent} from "./component/messenger/messenger.component";
import {MessageService} from "./service/message.service";
import {ConversationComponent} from "./component/conversation/conversation.component";
import {ErrorComponent} from "./component/error/error.component";
import {MessageComponent} from "./component/messenger/message/message.component";
import {SendComponent} from "./component/messenger/send/send.component";
import {ConversationUserFilter} from "./pipes/conversation-user-filter.pipe";
import { LocalStorageService, LOCAL_STORAGE_SERVICE_CONFIG } from 'angular-2-local-storage';
import {AuthenticationService} from "./service/authentication.service";
import {SharedService} from "./service/shared.service";
import {UserComponent} from "./component/user/user.component";
import {ParametersComponent} from "./component/parameters/parameters.component";

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
    ProfileComponent,
    MessengerComponent,
    ConversationComponent,
    MessageComponent,
    SendComponent,
    ErrorComponent,
    ConversationUserFilter,
    UserComponent,
    ParametersComponent,
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
