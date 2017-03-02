import { Injectable } from '@angular/core';
import {User} from "../model/user";
import * as moment from 'moment/moment';
import {Visit} from "../model/visit";
import {Message} from "../model/message";
import {Search} from "../model/search";

@Injectable()
export class DatetimeService {


  constructor() {}

  formatLastActivty(user:User) {
    let now = moment();
    console.log(user);
    console.log(user.lastActivityDatetime);
    let lastActivityDatetime = moment(user.lastActivityDatetime);
    var duration = moment.duration(now.diff(lastActivityDatetime));
    var days = duration.asDays();
    var hours = duration.asHours();
    var mins = duration.asMinutes();
    console.log("days", days);
    console.log("hours", hours);
    console.log("mins", mins);

    if (mins < 30 ) {
      user.lastActivity = "online"
    } else {
      user.lastActivity = moment(user.lastActivityDatetime).fromNow();
    }
    //user.lastActivityDate = moment(user.lastActivityDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
  }

  formatAge(user:User) {
    user.age = moment().diff(moment(user.birthDate), 'years');
  }

  formatUsersAge(users:User[]) {
    for (let user of users) {
      user.age = moment().diff(moment(user.birthDate), 'years');
    }
  }

  formatSearch(search:Search, ageOne:number, ageTwo:number) {
    console.log("search by age");
    console.log(search);
    search.birthDateOne = moment().subtract(ageTwo, 'years').format('YYYY-MM-DD');
    search.birthDateTwo = moment().subtract(ageOne, 'years').format('YYYY-MM-DD');
  }

  formatVisit(visit:Visit) {
    visit.visitedDate = moment(visit.visitedDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
    console.log(visit.visitedDate);
  }

  formatVisits(visits:Visit[]) {
    for (let visit of visits) {
      this.formatVisit(visit);
    }
  }

  formatMessages(messages:Message[]) {
    for (let message of messages) {
      message.sendSince = moment(message.sendDate).fromNow();
    }
  }

  formatMessage(message:Message) {
    message.sendSince = moment(message.sendDate).fromNow();
  }

  formatBirthDate(user:User) {
    user.birthDate = moment(user.birthDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
  }

  toStandardFormat(date:string) : string {
    return moment(date, 'DD/MM/YYYY').format('YYYY-MM-DD');
  }

  toFrFormat(date:string) : string {
    return moment(date, 'YYYY-MM-DD').format('DD/MM/YYYY');
  }

}
