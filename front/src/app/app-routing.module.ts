import {NgModule} from "@angular/core";
import {Routes, RouterModule, PreloadAllModules} from "@angular/router";
/**
 * Created by pgmatz on 28/10/16.
 */

export const routes: Routes = [
    { path: '', redirectTo: 'unauth', pathMatch: 'full'},
    { path: 'app', loadChildren: 'app/modules/application/application.module#ApplicationModule' },
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
