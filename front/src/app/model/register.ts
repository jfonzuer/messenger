import {User} from "./user";
import {PasswordConfirmation} from "./passwordConfirmation";
/**
 * Created by pgmatz on 22/10/16.
 */

export class Register {
  constructor(public user:User, public password:string) {}
}
