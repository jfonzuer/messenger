import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {VisitComponent} from "./visit/visit.component";
import {MessengerComponent} from "./messenger/messenger.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {UserViewComponent} from "./user-view/user-view.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuardService} from "../../services/guard/auth-guard.service";
import {NavbarComponent} from "./navbar/navbar.component";
import {HeadingComponent} from "./heading/heading.component";
import {UserResolve} from "../../services/resolve/user-resolve.service";
import {FooterComponent} from "./footer/footer.component";
import {SearchComponent} from "./search/search.component";
import {CurrentUserResolve} from "../../services/resolve/current-user-resolve.service";
import {AdminComponent} from "./admin/admin.component";
import {AdminGuardService} from "../../services/guard/admin-guard.service";
import {ConstantsResolve} from "../../services/resolve/constants-resolve.service";
import {PremiumComponent} from "./premium/premium.component";
import {PremiumGuardService} from "../../services/guard/premium-guard.service";

/**
 * Created by pgmatz on 28/10/16.
 */

@NgModule({
  imports: [RouterModule.forChild([
    {
      path: 'app', canActivate: [AuthGuardService], children: [
      { path: '', redirectTo: 'home', pathMatch: 'full'},
      { path: 'admin', component: AdminComponent, canActivate: [AdminGuardService]},
      { path: 'premium', component: PremiumComponent },
      { path: 'premium/:state', component: PremiumComponent },
      { path: 'home', component: HomeComponent },
      { path: 'profile/:id', component: UserViewComponent, resolve: { user: UserResolve } },
      { path: 'profile', component: UserProfileComponent,  resolve: { constants:ConstantsResolve, user: CurrentUserResolve} },
      { path: 'conversation', component: MessengerComponent, canActivate: [PremiumGuardService], resolve: {user:CurrentUserResolve} },
      { path: 'conversation/:id', component: MessengerComponent, canActivate: [PremiumGuardService], resolve: {user:CurrentUserResolve} },
      { path: 'visits', component: VisitComponent, canActivate: [PremiumGuardService] },
      { path: 'search', component: SearchComponent, resolve: { constants:ConstantsResolve}  },
      { path: '', component: NavbarComponent, outlet: 'header'},
      { path: '', component: HeadingComponent, outlet: 'banner'},
      { path: '', component: FooterComponent, outlet: 'footer'}]
    }
  ])],
  exports: [RouterModule]
})
export class ApplicationRoutingModule {}
