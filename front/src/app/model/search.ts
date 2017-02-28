import {UserType} from "./userType";
import {Country} from "./country";
import {Area} from "./area";
/**
 * Created by pgmatz on 17/11/16.
 */

export class Search {
  keyword:string;
  country:Country;
  area:Area;
  userType:UserType;
  birthDateOne:string;
  birthDateTwo:string;
}
