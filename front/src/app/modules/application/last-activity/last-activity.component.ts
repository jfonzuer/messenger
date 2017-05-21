import {Component, OnInit, Input} from '@angular/core';
import * as moment from 'moment/moment';

@Component({
  selector: 'app-last-activity',
  templateUrl: 'last-activity.component.html',
  styleUrls: ['last-activity.component.css']
})
export class LastActivityComponent implements OnInit {

  @Input() lastActivityDatetime:string;
  lastActivity:string;


  constructor() { }

  ngOnInit() {
    let now = moment();
    //console.debug("lastActivityDatetime", this.lastActivityDatetime);
    let lastActivityDatetime = moment(this.lastActivityDatetime);
    var duration = moment.duration(now.diff(lastActivityDatetime));
    var mins = duration.asMinutes();

    if (mins < 30 ) {
      this.lastActivity = "online"
    } else {
      this.lastActivity = moment(lastActivityDatetime).fromNow();
    }
  }
}
