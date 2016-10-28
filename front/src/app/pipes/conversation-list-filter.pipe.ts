import { Pipe, PipeTransform } from '@angular/core';
import {Conversation} from "../model/conversation";

@Pipe({
  name: 'conversationListFilter'
})
export class ConversationListFilterPipe implements PipeTransform {

  transform(conversations:Conversation[], keyword:string) {
    if (keyword) {
      return conversations =  conversations.filter(c => {
        console.log("keyword " + keyword);
        console.log(c.userTwo.username);
        return c.userTwo.username.includes(keyword);
      });
    }
    return conversations;
  }
}
