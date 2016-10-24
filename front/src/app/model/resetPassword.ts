import {PasswordConfirmation} from "./passwordConfirmation";
/**
 * Created by pgmatz on 24/10/16.
 */

export class ResetPassword {
  constructor(public passwordConfirmation:PasswordConfirmation, public token:string, public  userId:number) {}
}
