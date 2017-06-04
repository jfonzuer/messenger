import {Component, OnInit, Input, ViewContainerRef, OnDestroy} from '@angular/core';
import {MessageService} from '../../../services/message.service';
import {ConversationService} from '../../../services/conversation.service';
import {User} from '../../../model/user';
import {Conversation} from '../../../model/conversation';
import {Message} from '../../../model/message';
import {UserMessage} from '../../../model/userMessage';
import {MessengerService} from '../../../services/messenger.service';
import {environment} from '../../../../environments/environment';
import {SharedService} from '../../../services/shared.service';
import {ToastsManager} from 'ng2-toastr';
import {LoggerService} from "../../../services/logger.service";

@Component({
  selector: 'app-message-send',
  templateUrl: 'message-send.component.html',
  styleUrls: ['message-send.component.css']
})
export class
MessageSendComponent implements OnInit, OnDestroy {

  @Input() user: User;
  selectedConversation: Conversation;

  // subscriptions
  changeConversationSubscription: any;
  blockUserSubscription: any;
  deleteConversationSubscription: any;

  file: File;
  private sizeLimit: number;
  uploadImageUrl: string;
  sendImage = false;
  message: Message;
  enter = true;
  isUserBlocked = false;

  constructor(private messageService: MessageService, private conversationService: ConversationService, private messengerService: MessengerService,
              private sharedService: SharedService, private toastr: ToastsManager, vRef: ViewContainerRef,
              private logger: LoggerService) {

    this.toastr.setRootViewContainerRef(vRef);
    this.changeConversationSubscription = this.messengerService.changeConversationObservable.subscribe(conversation => this.changeConversation(conversation));
    this.deleteConversationSubscription = this.messengerService.deleteConversationObservable.subscribe(conversation => this.selectedConversation = null);
    this.blockUserSubscription = this.messengerService.blockUserObservable.subscribe(block => this.isUserBlocked = block);
    this.sizeLimit = environment.sizeLimit;
    this.uploadImageUrl = environment.uploadImageUrl;
  }

  ngOnInit() {
    this.message = new Message();
    this.message.source = this.user;
  }

  changeConversation(conversation: Conversation) {
    this.logger.log('changeConversation event', conversation);
    this.selectedConversation = conversation;
    this.isUserBlocked = this.sharedService.isUserBlocked(this.selectedConversation.userTwo);
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

  fileChangeEvent(event: any) {
    this.file = event.target.files[0];
    this.sendImage = true;
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
        );
      } else {
        this.toastr.error('La taille maximale de fichier est 2,048 MB');
      }
    }
  }

  private sendMessage() {
    if (this.selectedConversation) {
      this.message.conversation = this.selectedConversation;
      this.messengerService.addMessage(this.message);
      this.message.content = '';
    }
  }

  private createConversation() {
    this.logger.log('createConversation with userTwo', this.selectedConversation.userTwo);

    this.conversationService.post(new UserMessage(this.selectedConversation.userTwo, this.message)).then(response => {
      this.message.content = '';
      this.selectedConversation = response as Conversation;
    }).catch(error => this.toastr.error(error));
  }

  public ngOnDestroy(): void {
    this.changeConversationSubscription.unsubscribe();
    this.blockUserSubscription.unsubscribe();
  }
}
