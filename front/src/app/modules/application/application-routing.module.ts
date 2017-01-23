import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {VisitComponent} from "./visit/visit.component";
import {MessengerComponent} from "./messenger/messenger.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {UserViewComponent} from "./user-view/user-view.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuardService} from "../../services/auth-guard.service";
import {NavbarComponent} from "./navbar/navbar.component";
import {HeadingComponent} from "./heading/heading.component";
import {UserResolve} from "../../services/resolve/user-resolve.service";
import {FetishesResolve} from "../../services/resolve/fetishes-resolve.service";
import {LocalizationsResolve} from "../../services/resolve/localizations-resolve";
import {FooterComponent} from "./footer/footer.component";
import {SearchComponent} from "./search/search.component";
import {UserTypesResolve} from "../../services/resolve/user-types-resolve";
import {CurrentUserResolve} from "../../services/resolve/current-user-resolve.service";
import {AdminComponent} from "./admin/admin.component";
import {AdminGuardService} from "../../services/admin-guard.service";

/**
 * Created by pgmatz on 28/10/16.
 */

@NgModule({
  imports: [RouterModule.forChild([
    {
      path: 'app', canActivate: [AuthGuardService], children: [
      { path: '', redirectTo: 'home', pathMatch: 'full'},
      { path: 'admin', component: AdminComponent, canActivate: [AdminGuardService]},
      { path: 'home', component: HomeComponent },
      { path: 'profile/:id', component: UserViewComponent, resolve: { user: UserResolve } },
      { path: 'profile', component: UserProfileComponent,  resolve: { fetishes:FetishesResolve, localizations: LocalizationsResolve, user: CurrentUserResolve} },
      { path: 'conversation', component: MessengerComponent, resolve: {user:CurrentUserResolve} },
      { path: 'conversation/:id', component: MessengerComponent, resolve: {user:CurrentUserResolve} },
      { path: 'visits', component: VisitComponent },
      { path: 'search', component: SearchComponent, resolve: { types:UserTypesResolve, localizations: LocalizationsResolve}  },
      { path: '', component: NavbarComponent, outlet: 'header'},
      { path: '', component: HeadingComponent, outlet: 'banner'},
      { path: '', component: FooterComponent, outlet: 'footer'}]
    }
  ])],
  exports: [RouterModule]
})
export class ApplicationRoutingModule {}
