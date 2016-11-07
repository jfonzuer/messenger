import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
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
import {UploadImageComponent} from "./upload-image/upload-image.component";
import {UserParametersComponent} from "./user-parameters/user-parameters.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {UserViewComponent} from "./user-view/user-view.component";
import {VisitComponent} from "./visit/visit.component";
import {VisitService} from "../../services/visit.service";
import {ApplicationRoutingModule} from "./application-routing.module";
import {FormatDateDirective} from "../../directives/FormatDateDirective";
import {UserResolve} from "../../services/user-resolve.service";
import {UploadService} from "../../services/upload.service";

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
    UploadImageComponent,
    UserParametersComponent,
    UserProfileComponent,
    UserViewComponent,
    VisitComponent,
    ConversationListFilterPipe,
  ],
  providers: [
    ConversationService,
    MessageService,
    UploadService,
    VisitService,
    UserResolve
  ]
})
export class ApplicationModule { }