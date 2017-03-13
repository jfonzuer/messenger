import {Component, OnInit, Input} from '@angular/core';
import {User} from "../../../../model/user";

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent implements OnInit {

  @Input() user:User;
  @Input() show:boolean;

  constructor() { }

  ngOnInit() {
  }
}
