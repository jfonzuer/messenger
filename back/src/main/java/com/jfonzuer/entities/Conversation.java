package com.jfonzuer.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by pgm on 19/09/16.
 */
@Entity
public class Conversation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String preview;

    @Column(columnDefinition="tinyint(1) default 0", nullable = false)
    private Boolean isReadByUserOne;

    @Column(columnDefinition="tinyint(1) default 0", nullable = false)
    private Boolean isReadByUserTwo;

    @Column
    private LocalDateTime lastModified;

    @Column(nullable = false)
    private Long userOneCursor;

    @Column(nullable = false)
    private Long userTwoCursor;

    @Column(nullable = false)
    private Long lastMessageId;

    @ManyToOne
    private User userOne;

    @ManyToOne
    private User userTwo;


    @OneToMany(mappedBy = "conversation")
    private Collection<Message> messages;

    public Conversation() {
    }

    public Conversation(Long id, String preview, Boolean isReadByUserOne, Boolean isReadByUserTwo, LocalDateTime lastModified, Long userOneCursor, Long userTwoCursor, Long lastMessageId, User userOne, User userTwo, Collection<Message> messages) {
        this.id = id;
        this.preview = preview;
        this.isReadByUserOne = isReadByUserOne;
        this.isReadByUserTwo = isReadByUserTwo;
        this.lastModified = lastModified;
        this.userOneCursor = userOneCursor;
        this.userTwoCursor = userTwoCursor;
        this.lastMessageId = lastMessageId;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Boolean getReadByUserOne() {
        return isReadByUserOne;
    }

    public void setReadByUserOne(Boolean readByUserOne) {
        isReadByUserOne = readByUserOne;
    }

    public Boolean getReadByUserTwo() {
        return isReadByUserTwo;
    }

    public void setReadByUserTwo(Boolean readByUserTwo) {
        isReadByUserTwo = readByUserTwo;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public Long getUserOneCursor() {
        return userOneCursor;
    }

    public void setUserOneCursor(Long userOneCursor) {
        this.userOneCursor = userOneCursor;
    }

    public Long getUserTwoCursor() {
        return userTwoCursor;
    }

    public void setUserTwoCursor(Long userTwoCursor) {
        this.userTwoCursor = userTwoCursor;
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

    public Long getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(Long lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", preview='" + preview + '\'' +
                ", isReadByUserOne=" + isReadByUserOne +
                ", isReadByUserTwo=" + isReadByUserTwo +
                ", lastModified=" + lastModified +
                ", userOne=" + userOne +
                ", userTwo=" + userTwo +
                ", messages=" + messages +
                '}';
    }

    public static class ConversationBuilder {
        private Long id;
        private String preview;
        private Boolean isReadByUserOne;
        private Boolean isReadByUserTwo;
        private LocalDateTime lastModified;
        private Long userOneCursor;
        private Long userTwoCursor;
        private Long lastMessageId;
        private User userOne;
        private User userTwo;
        private Collection<Message> messages;

        public ConversationBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ConversationBuilder setPreview(String preview) {
            this.preview = preview;
            return this;
        }

        public ConversationBuilder setIsReadByUserOne(Boolean isReadByUserOne) {
            this.isReadByUserOne = isReadByUserOne;
            return this;
        }

        public ConversationBuilder setIsReadByUserTwo(Boolean isReadByUserTwo) {
            this.isReadByUserTwo = isReadByUserTwo;
            return this;
        }

        public ConversationBuilder setLastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public ConversationBuilder setUserOneCursor(Long userOneCursor) {
            this.userOneCursor = userOneCursor;
            return this;
        }

        public ConversationBuilder setUserTwoCursor(Long userTwoCursor) {
            this.userTwoCursor = userTwoCursor;
            return this;
        }

        public ConversationBuilder setLastMessageId(Long lastMessageId) {
            this.lastMessageId = lastMessageId;
            return this;
        }

        public ConversationBuilder setUserOne(User userOne) {
            this.userOne = userOne;
            return this;
        }

        public ConversationBuilder setUserTwo(User userTwo) {
            this.userTwo = userTwo;
            return this;
        }

        public ConversationBuilder setMessages(Collection<Message> messages) {
            this.messages = messages;
            return this;
        }

        public Conversation createConversation() {
            return new Conversation(id, preview, isReadByUserOne, isReadByUserTwo, lastModified, userOneCursor, userTwoCursor, lastMessageId, userOne, userTwo, messages);
        }
    }
}
