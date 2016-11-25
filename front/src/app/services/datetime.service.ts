import { Injectable } from '@angular/core';
import {User} from "../model/user";
import * as moment from 'moment/moment';
import {Visit} from "../model/visit";
import {Message} from "../model/message";
import {Search} from "../model/search";

@Injectable()
export class DatetimeService {


  constructor() {}

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
