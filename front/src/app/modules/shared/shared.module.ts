import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {FlashMessageComponent} from "./flash-message/flash-message.component";

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    FlashMessageComponent,
  ],
  exports: [
    FlashMessageComponent,
    CommonModule,
    FormsModule,
  ]
})
export class SharedModule { }
