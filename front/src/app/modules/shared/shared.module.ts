import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ConstantService} from "../../services/contstants.service";
import {ConstantsResolve} from "../../services/resolve/constants-resolve.service";
import {ToastOptions, ToastModule, ToastsManager} from "ng2-toastr";

@NgModule({
  imports: [
    CommonModule,
    ToastModule.forRoot(),
  ],
  declarations: [
  ],
  exports: [
    CommonModule,
    FormsModule,
  ],
  providers: [
    ConstantService,
    ConstantsResolve,
    ToastsManager
  ]
})
export class SharedModule { }
