import { Pipe, PipeTransform } from '@angular/core';
import {Conversation} from "../model/conversation";

@Pipe({
  name: 'conversationListFilter'
})
export class ConversationListFilterPipe implements PipeTransform {

  transform(conversations:Conversation[], keyword:string) {
    if (keyword) {
      return conversations =  conversations.filter(c => {
        return c.userTwo.username.includes(keyword);
      });
    }
    return conversations;
  }
}
