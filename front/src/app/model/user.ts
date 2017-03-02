import {Fetish} from "./fetish";
import {Image} from "./image";
import {UserType} from "./userType";
import {Country} from "./country";
import {Area} from "./area";
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
  enabled:boolean;
  blocked:boolean;
  age:number;
  country:Country;
  area:Area;
  reportedAsFake:number;
  images:Image[];
  userType:UserType;
  lastActivityDatetime: string;
  lastActivity: string;
  authorities:string[];
  notifyVisit:boolean;
  notifyMessage:boolean;
  blockedUsers:User[];
}
