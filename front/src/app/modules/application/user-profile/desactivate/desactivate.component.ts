import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {User} from "../../../../model/user";

@Component({
  selector: 'app-desactivate',
  templateUrl: 'desactivate.component.html',
  styleUrls: ['desactivate.component.css']
})

export class DesactivateComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;
  @Output() successEmitter = new EventEmitter();
  @Output() errorEmitter = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

}
