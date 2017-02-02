import {SharedModule} from "../shared/shared.module";
import {MessengerComponent} from "./messenger/messenger.component";
import {ConversationListComponent} from "./conversation-list/conversation-list.component";
import {MessageListComponent} from "./message-list/message-list.component";
import {MessageSendComponent} from "./message-send/message-send.component";
import {ConversationListFilterPipe} from "../../pipes/conversation-list-filter.pipe";
import {ConversationService} from "../../services/conversation.service";
import {MessageService} from "../../services/message.service";
import {NgModule} from "@angular/core";
import {HeadingComponent} from "./heading/heading.component";
import {HomeComponent} from "./home/home.component";
import {NavbarComponent} from "./navbar/navbar.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {UserViewComponent} from "./user-view/user-view.component";
import {VisitComponent} from "./visit/visit.component";
import {VisitService} from "../../services/visit.service";
import {ApplicationRoutingModule} from "./application-routing.module";
import {FormatDateDirective} from "../../directives/FormatDateDirective";
import {UserResolve} from "../../services/resolve/user-resolve.service";
import {UploadService} from "../../services/upload.service";
import {UserTypeService} from "../../services/user-type.service";
import {FooterComponent} from "./footer/footer.component";
import {SearchComponent} from "./search/search.component";
import {ChangePasswordComponent} from "./user-profile/change-password/change-password.component";
import {InformationComponent} from "./user-profile/information/information.component";
import {UploadComponent} from "./user-profile/upload/upload.component";
import {CurrentUserResolve} from "../../services/resolve/current-user-resolve.service";
import {ProfileComponent} from "./user-profile/profile/profile.component";
import {DesactivateComponent} from "./user-profile/desactivate/desactivate.component";
import {AlertsComponent} from "./user-profile/alerts/alerts.component";
import {MessengerService} from "../../services/messenger.service";
import {BlockUserComponent} from "./user-profile/block-user/block-user.component";
import {AdminComponent} from "./admin/admin.component";
import {AdminGuardService} from "../../services/admin-guard.service";
import {AdminService} from "../../services/admin.service";
import {WebsocketComponent} from "./websocket/websocket.component";
/**
 * Created by pgmatz on 28/10/16.
 */

@NgModule({
  imports: [
    SharedModule,
    ApplicationRoutingModule,
  ],
  declarations: [
    FormatDateDirective,
    MessengerComponent,
    ConversationListComponent,
    MessageListComponent,
    MessageSendComponent,
    HeadingComponent,
    HomeComponent,
    NavbarComponent,
    UserProfileComponent,
    UserViewComponent,
    VisitComponent,
    FooterComponent,
    SearchComponent,
    ConversationListFilterPipe,
    ChangePasswordComponent,
    InformationComponent,
    UploadComponent,
    ProfileComponent,
    AlertsComponent,
    DesactivateComponent,
    BlockUserComponent,
    AdminComponent,
    WebsocketComponent
  ],
  providers: [
    ConversationService,
    MessageService,
    UploadService,
    VisitService,
    UserTypeService,
    UserResolve,
    CurrentUserResolve,
    MessengerService,
    AdminGuardService,
    AdminService
  ],
})
export class ApplicationModule { }
