import {Component, Input, OnInit} from "@angular/core";
import * as moment from "moment/moment";
import {MessengerService} from "../../../services/messenger.service";
import {Conversation} from "../../../model/conversation";

@Component({
  selector: 'app-last-activity',
  templateUrl: 'last-activity.component.html',
  styleUrls: ['last-activity.component.css']
})
export class LastActivityComponent implements OnInit {

  @Input() lastActivityDatetime:string;
  lastActivity:string;

  changeConversationSubscription: any;

  constructor(private messengerService: MessengerService) {
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.formatLastActivity(conversation.userTwo.lastActivityDatetime));
  }

  ngOnInit() {
    this.formatLastActivity(this.lastActivityDatetime);
  }

  formatLastActivity(activity: string) {
    let now = moment();
    //console.debug("lastActivityDatetime", this.lastActivityDatetime);
    let lastActivityDatetime = moment(activity);
    var duration = moment.duration(now.diff(lastActivityDatetime));
    var mins = duration.asMinutes();

    if (mins < 30 ) {
      this.lastActivity = "online"
    } else {
      this.lastActivity = moment(lastActivityDatetime).fromNow();
    }

  }

  public ngOnDestroy(): void {
    this.changeConversationSubscription.unsubscribe();
  }
}
