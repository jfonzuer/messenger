import {Message} from "./message";
import {User} from "./user";
/**
 * Created by pgmatz on 02/10/16.
 */

export class UserMessage {
  constructor(public user:User, public message:Message) {}
}

