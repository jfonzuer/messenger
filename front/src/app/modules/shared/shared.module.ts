import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FlashMessageErrorComponent} from "./flash-message-error/flash-message-error.component";
import {FlashMessageSuccessComponent} from "./flash-message-success/flash-message-success.component";
import {FormsModule} from "@angular/forms";
import {FlashMessageComponent} from "./flash-message/flash-message.component";

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    FlashMessageComponent,
    FlashMessageErrorComponent,
    FlashMessageSuccessComponent,
  ],
  exports: [
    FlashMessageComponent,
    FlashMessageErrorComponent,
    FlashMessageSuccessComponent,
    CommonModule,
    FormsModule,
  ]
})
export class SharedModule { }
