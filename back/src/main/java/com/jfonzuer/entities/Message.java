package com.jfonzuer.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by pgm on 19/09/16.
 */

@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User source;

    @ManyToOne
    private Conversation conversation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime sentDateTime;

    @Column(columnDefinition="tinyint(1) default 0", nullable = false)
    private Boolean isDeletedByUserOne;

    @Column(columnDefinition="tinyint(1) default 0", nullable = false)
    private Boolean isDeletedByUserTwo;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(nullable = true)
    private String url;

    @ManyToOne
    private User userOne;

    @ManyToOne
    private User userTwo;

    public Message() {
    }

    public Message(Long id, User source, Conversation conversation, String content, LocalDateTime sentDateTime, Boolean isDeletedByUserOne, Boolean isDeletedByUserTwo, MessageType type, String url, User userOne, User userTwo) {
        this.id = id;
        this.source = source;
        this.conversation = conversation;
        this.content = content;
        this.sentDateTime = sentDateTime;
        this.isDeletedByUserOne = isDeletedByUserOne;
        this.isDeletedByUserTwo = isDeletedByUserTwo;
        this.type = type;
        this.url = url;
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSource() {
        return source;
    }

    public void setSource(User source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(LocalDateTime sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Boolean getDeletedByUserOne() {
        return isDeletedByUserOne;
    }

    public void setDeletedByUserOne(Boolean deletedByUserOne) {
        isDeletedByUserOne = deletedByUserOne;
    }

    public Boolean getDeletedByUserTwo() {
        return isDeletedByUserTwo;
    }

    public void setDeletedByUserTwo(Boolean deletedByUserTwo) {
        isDeletedByUserTwo = deletedByUserTwo;
    }

    public User getUserOne() {
        return userOne;
    }

    public void setUserOne(User userOne) {
        this.userOne = userOne;
    }

    public User getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(User userTwo) {
        this.userTwo = userTwo;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", source=" + source +
                ", content='" + content + '\'' +
                ", sentDateTime=" + sentDateTime +
                '}';
    }


    public static class MessageBuilder {
        private Long id;
        private User source;
        private Conversation conversation;
        private String content;
        private LocalDateTime sentDateTime;
        private Boolean isDeletedByUserOne;
        private Boolean isDeletedByUserTwo;
        private MessageType type;
        private String url;
        private User userOne;
        private User userTwo;

        public MessageBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public MessageBuilder setSource(User source) {
            this.source = source;
            return this;
        }

        public MessageBuilder setConversation(Conversation conversation) {
            this.conversation = conversation;
            return this;
        }

        public MessageBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public MessageBuilder setSentDateTime(LocalDateTime sentDateTime) {
            this.sentDateTime = sentDateTime;
            return this;
        }

        public MessageBuilder setIsDeletedByUserOne(Boolean isDeletedByUserOne) {
            this.isDeletedByUserOne = isDeletedByUserOne;
            return this;
        }

        public MessageBuilder setIsDeletedByUserTwo(Boolean isDeletedByUserTwo) {
            this.isDeletedByUserTwo = isDeletedByUserTwo;
            return this;
        }

        public MessageBuilder setType(MessageType type) {
            this.type = type;
            return this;
        }

        public MessageBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public MessageBuilder setUserOne(User userOne) {
            this.userOne = userOne;
            return this;
        }

        public MessageBuilder setUserTwo(User userTwo) {
            this.userTwo = userTwo;
            return this;
        }

        public Message createMessage() {
            return new Message(id, source, conversation, content, sentDateTime, isDeletedByUserOne, isDeletedByUserTwo, type, url, userOne, userTwo);
        }
    }
}
