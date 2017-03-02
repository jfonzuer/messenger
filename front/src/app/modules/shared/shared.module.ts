import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {ConstantService} from "../../services/contstants.service";
import {ConstantsResolve} from "../../services/resolve/constants-resolve.service";
import {RequestService} from "../../services/request.service";

@NgModule({
  imports: [
    CommonModule,
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
  ]
})
export class SharedModule { }
