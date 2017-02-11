import {Component, OnInit, Input, Output, EventEmitter, ViewContainerRef} from '@angular/core';
import {MessageService} from "../../../services/message.service";
import {ConversationService} from "../../../services/conversation.service";
import {User} from "../../../model/user";
import {Conversation} from "../../../model/conversation";
import {Message} from "../../../model/message";
import {UserMessage} from "../../../model/userMessage";
import {MessengerService} from "../../../services/messenger.service";
import {environment} from "../../../../environments/environment";
import {SharedService} from "../../../services/shared.service";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'app-message-send',
  templateUrl: 'message-send.component.html',
  styleUrls: ['message-send.component.css']
})
export class MessageSendComponent implements OnInit {

  @Input() user: User;
  selectedConversation: Conversation;
  changeConversationSubscription: any;
  blockUserSubscription:any;

  file:File;
  private sizeLimit:number;
  uploadImageUrl:string;
  sendImage:boolean = false;
  message: Message;
  enter:boolean = true;
  isUserBlocked = false;

  constructor(private messageService: MessageService, private conversationService: ConversationService, private messengerService:MessengerService, private sharedService: SharedService, private toastr: ToastsManager, vRef: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vRef);
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation =>  {
      this.selectedConversation = conversation;
      this.isUserBlocked = this.sharedService.isUserBlocked(this.selectedConversation.userTwo);
      console.log("isUserBlocked", this.isUserBlocked);
    });
    this.blockUserSubscription = this.messengerService.blockUserObservable.subscribe(block => this.isUserBlocked = block);
    this.sizeLimit = environment.sizeLimit;
    this.uploadImageUrl = environment.uploadImageUrl;
  }

  ngOnInit() {
    this.message = new Message();
    this.message.source = this.user;
  }

  send() {
    if (this.selectedConversation) {
      // si l'id n'existe pas on crée une conversation
      this.selectedConversation.id ? this.sendMessage() : this.createConversation();
    }
  }

  sendEnter() {
    if (this.enter) {
      this.send();
    }
  }

  fileChangeEvent(event:any) {
    this.file = event.target.files[0];
    this.sendImage = true;
    console.log("file : ", this.file);
  }

  cancelSendImage() {
    this.file = null;
    this.sendImage = false;
  }

  uploadImage() {
    if (this.file != null && this.selectedConversation && this.selectedConversation.id) {
      if (this.file.size < this.sizeLimit) {
        this.message.conversation = this.selectedConversation;
        this.messageService.uploadImage(this.file, this.selectedConversation.id).subscribe(
          response => {
            this.messengerService.receiveMessage(response);
            this.file = null;
            this.sendImage = false;
          },
          error => this.toastr.error(error)
        )
      }
      else {
        this.toastr.error("La taille maximale de fichier est 2,048 MB");
      }
    }
  }

  private sendMessage() {
    this.message.conversation = this.selectedConversation;
    this.messengerService.addMessage(this.message);
    this.message.content = '';
    /*
    this.messageService.post(this.message).then(response => {
      this.messengerService.addMessage(response); this.message.content = '';
    }).catch(error => {
      this.errorEmitter.emit(error); }
    );
    */
  }

  private createConversation() {
    console.log("create conversation");
    this.conversationService.post(new UserMessage(this.selectedConversation.userTwo, this.message)).then(response => {
      this.message.content = '';
      this.selectedConversation = response;
      this.messengerService.addConversation(this.selectedConversation);
    }).catch(error => this.toastr.error(error));
  }


}
