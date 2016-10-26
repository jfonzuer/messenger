import {Fetish} from "./fetish";
import {Localization} from "./localization";
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
  enable:boolean;
  age:number;
  profilePicture:string;
}
