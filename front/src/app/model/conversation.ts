import {User} from "./user";
/**
 * Created by pgmatz on 28/09/16.
 */

export class Conversation {
  id: number;
  preview: string;
  read:boolean;
  userOne: User;
  userTwo: User;
}
