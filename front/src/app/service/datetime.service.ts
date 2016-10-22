import {Injectable} from "@angular/core";
import * as moment from 'moment/moment';
import {User} from "../model/user";
import {Visit} from "../model/visit";

/**
 * Created by pgmatz on 19/10/16.
 */

@Injectable()
export class DatetimeService {

  constructor() {}

  formatAge(user:User) {
    user.age = moment().diff(moment(user.birthDate), 'years');
  }
  formatVisit(visit:Visit) {
    visit.visitedDate = moment(visit.visitedDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
    console.log(visit.visitedDate);
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
