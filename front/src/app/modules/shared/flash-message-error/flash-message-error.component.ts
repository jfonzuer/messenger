import {Component, OnInit, Input} from '@angular/core';

@Component({
  selector: 'app-flash-message-error',
  templateUrl: 'flash-message-error.component.html',
  styleUrls: ['flash-message-error.component.css']
})
export class FlashMessageErrorComponent implements OnInit {

  @Input() error:string;

  constructor() { }

  ngOnInit() {
  }

}
