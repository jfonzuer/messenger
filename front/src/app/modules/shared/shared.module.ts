import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ConstantService} from "../../services/contstants.service";
import {ConstantsResolve} from "../../services/resolve/constants-resolve.service";
import {ToastModule, ToastsManager} from "ng2-toastr";
import {SpinnerComponent} from "./spinner/spinner.component";

@NgModule({
  imports: [
    CommonModule,
    ToastModule.forRoot(),
  ],
  declarations: [
    SpinnerComponent
  ],
  exports: [
    CommonModule,
    FormsModule,
    SpinnerComponent,
  ],
  providers: [
    ConstantService,
    ConstantsResolve,
    ToastsManager
  ]
})
export class SharedModule { }
