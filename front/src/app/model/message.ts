import {User} from "./user";
import {Conversation} from "./conversation";
/**
 * Created by pgmatz on 28/09/16.
 */

export class Message {
  id: number;
  source: User;
  type: String;
  url: String;
  content: string;
  sendDate: string;
  sendSince:string;
  conversation: Conversation;
}
