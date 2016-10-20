import {User} from "./user";
/**
 * Created by pgmatz on 17/10/16.
 */

export class Visit {
  id:number;
  visitor:User;
  visited:User;
  visitedDate: string;
  isSeenByVisited: boolean;
}
