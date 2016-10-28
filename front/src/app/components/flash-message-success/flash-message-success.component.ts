import {Component, OnInit, Input} from '@angular/core';

@Component({
  selector: 'app-flash-message-success',
  templateUrl: './flash-message-success.component.html',
  styleUrls: ['./flash-message-success.component.css']
})
export class FlashMessageSuccessComponent implements OnInit {

  @Input() success:string;

  constructor() { }

  ngOnInit() {
  }


}
