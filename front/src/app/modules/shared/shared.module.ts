import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ConstantService} from "../../services/contstants.service";
import {ConstantsResolve} from "../../services/resolve/constants-resolve.service";
import {ToastOptions, ToastModule, ToastsManager} from "ng2-toastr";
import {SpinnerComponent} from "./spinner/spinner.component";
import {CoolLoadingIndicatorModule} from "angular2-cool-loading-indicator";

@NgModule({
  imports: [
    CommonModule,
    ToastModule.forRoot(),
    CoolLoadingIndicatorModule
  ],
  declarations: [
    SpinnerComponent
  ],
  exports: [
    CommonModule,
    FormsModule,
    SpinnerComponent,
    CoolLoadingIndicatorModule
  ],
  providers: [
    ConstantService,
    ConstantsResolve,
    ToastsManager
  ]
})
export class SharedModule { }
