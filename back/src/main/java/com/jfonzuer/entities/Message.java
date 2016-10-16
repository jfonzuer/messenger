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

    public Message() {
    }

    public Message(Long id, User source, Conversation conversation, String content, LocalDateTime sentDateTime) {
        this.id = id;
        this.source = source;
        this.conversation = conversation;
        this.content = content;
        this.sentDateTime = sentDateTime;
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

        public Message createMessage() {
            return new Message(id, source, conversation, content, sentDateTime);
        }
    }
}
