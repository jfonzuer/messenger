import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { LocalStorageService, LOCAL_STORAGE_SERVICE_CONFIG } from 'angular-2-local-storage';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { MessengerComponent } from './components/messenger/messenger.component';
import { ConversationListComponent } from './components/conversation-list/conversation-list.component';
import { MessageListComponent } from './components/message-list/message-list.component';
import { MessageSendComponent } from './components/message-send/message-send.component';
import { UploadImageComponent } from './components/upload-image/upload-image.component';
import { UserViewComponent } from './components/user-view/user-view.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { UserParametersComponent } from './components/user-parameters/user-parameters.component';
import { UserVisitsComponent } from './components/user-visits/user-visits.component';
import { FlashMessageSuccessComponent } from './components/flash-message-success/flash-message-success.component';
import { FlashMessageErrorComponent } from './components/flash-message-error/flash-message-error.component';
import { HeadingComponent } from './components/heading/heading.component';
import { PasswordForgotComponent } from './components/password-forgot/password-forgot.component';
import { PasswordResetComponent } from './components/password-reset/password-reset.component';
import { RegisterComponent } from './components/register/register.component';
import { UnauthHeadingComponent } from './components/unauth-heading/unauth-heading.component';
import { ConversationListFilterPipe } from './pipes/conversation-list-filter.pipe';
import { LoginComponent } from './components/login/login.component';
import {UserService} from "./services/user.service";
import {ConversationService} from "./services/conversation.service";
import {MessageService} from "./services/message.service";
import {SharedService} from "./services/shared.service";
import {AuthenticationService} from "./services/authentication.service";
import {VisitService} from "./services/visit.service";
import {DatetimeService} from "./services/datetime.service";
import {LocalizationService} from "./services/localization.service";
import {FetishService} from "./services/fetish.service";
import {UploadService} from "./services/upload.service";
import {AuthGuardService} from "./services/auth-guard.service";
import {RouterModule, Routes} from "@angular/router";
import { UnauthNavbarComponent } from './components/unauth-navbar/unauth-navbar.component';

// Create config options (see ILocalStorageServiceConfigOptions) for deets:
let localStorageServiceConfig = {
  prefix: 'app-root',
  storageType: 'sessionStorage'
};

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/app/home',
    pathMatch: 'full'
  },
  {
    path: 'app', canActivate: [AuthGuardService], children: [
    { path: 'home', component: HomeComponent },
    { path: 'profile/:id', component: UserViewComponent },
    { path: 'profile', component: UserProfileComponent },
    { path: 'conversation', component: MessengerComponent },
    { path: 'conversion/:id', component: ConversationListComponent },
    { path: 'visits', component: UserVisitsComponent },
    { path: 'parameters', component: UserParametersComponent },
    { path: '', component: NavbarComponent, outlet: 'header'},
    { path: '', component: HeadingComponent, outlet: 'banner'}
  ]
  },
  {
    path: 'unauth', children: [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'password/forgotten', component: PasswordForgotComponent },
    { path: 'password/reset/:id/:token', component: PasswordResetComponent },
    { path: '', component: UnauthNavbarComponent, outlet: 'header'},
    { path: '', component: UnauthHeadingComponent, outlet: 'banner'},
  ]
  },
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    MessengerComponent,
    ConversationListComponent,
    MessageListComponent,
    MessageSendComponent,
    UploadImageComponent,
    UserViewComponent,
    UserProfileComponent,
    UserParametersComponent,
    UserVisitsComponent,
    FlashMessageSuccessComponent,
    FlashMessageErrorComponent,
    HeadingComponent,
    PasswordForgotComponent,
    PasswordResetComponent,
    RegisterComponent,
    UnauthHeadingComponent,
    ConversationListFilterPipe,
    LoginComponent,
    UnauthNavbarComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes),
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
    UploadService,
    AuthGuardService,
    LocalStorageService,
    {
      provide: LOCAL_STORAGE_SERVICE_CONFIG, useValue: localStorageServiceConfig
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
