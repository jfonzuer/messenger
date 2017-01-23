import {Fetish} from "./fetish";
import {Localization} from "./localization";
import {Image} from "./image";
import {UserType} from "./userType";
import {Authority} from "./authority";
/**
 * Created by pgmatz on 28/09/16.
 */

export class User {
  id: number;
  email: string;
  username: string;
  description: string;
  birthDate: string;
  fetishes: Fetish[];
  localization: Localization;
  enabled:boolean;
  blocked:boolean;
  age:number;
  reportedAsFake:number;
  images:Image[];
  userType:UserType;
  lastActivityDate: string;
  authorities:string[];
  notifyVisit:boolean;
  notifyMessage:boolean;
  blockedUsers:User[];
}
