package com.jfonzuer.entities;

import com.jfonzuer.dto.ConversationDto;

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

    @Column(columnDefinition="tinyint(1) default 0", nullable = false)
    private Boolean isDeletedByUserOne;

    @Column(columnDefinition="tinyint(1) default 0", nullable = false)
    private Boolean isDeletedByUserTwo;

    @ManyToOne
    private User userOne;

    @ManyToOne
    private User userTwo;


    @OneToMany(mappedBy = "conversation")
    private Collection<Message> messages;

    public Conversation() {
    }

    public Conversation(String preview, Boolean isReadByUserOne, Boolean isReadByUserTwo, LocalDateTime lastModified, Boolean isDeletedByUserOne, Boolean isDeletedByUserTwo, User userOne, User userTwo) {
        this.preview = preview;
        this.isReadByUserOne = isReadByUserOne;
        this.isReadByUserTwo = isReadByUserTwo;
        this.lastModified = lastModified;
        this.isDeletedByUserOne = isDeletedByUserOne;
        this.isDeletedByUserTwo = isDeletedByUserTwo;
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public Conversation(Long id, String preview, Boolean isReadByUserOne, Boolean isReadByUserTwo, LocalDateTime lastModified, Boolean isDeletedByUserOne, Boolean isDeletedByUserTwo, User userOne, User userTwo, Collection<Message> messages) {
        this.id = id;
        this.preview = preview;
        this.isReadByUserOne = isReadByUserOne;
        this.isReadByUserTwo = isReadByUserTwo;
        this.lastModified = lastModified;
        this.isDeletedByUserOne = isDeletedByUserOne;
        this.isDeletedByUserTwo = isDeletedByUserTwo;
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

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", preview='" + preview + '\'' +
                ", isReadByUserOne=" + isReadByUserOne +
                ", isReadByUserTwo=" + isReadByUserTwo +
                ", lastModified=" + lastModified +
                ", isDeletedByUserOne=" + isDeletedByUserOne +
                ", isDeletedByUserTwo=" + isDeletedByUserTwo +
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
        private Boolean isDeletedByUserOne;
        private Boolean isDeletedByUserTwo;
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

        public ConversationBuilder setIsDeletedByUserOne(Boolean isDeletedByUserOne) {
            this.isDeletedByUserOne = isDeletedByUserOne;
            return this;
        }

        public ConversationBuilder setIsDeletedByUserTwo(Boolean isDeletedByUserTwo) {
            this.isDeletedByUserTwo = isDeletedByUserTwo;
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
            return new Conversation(id, preview, isReadByUserOne, isReadByUserTwo, lastModified, isDeletedByUserOne, isDeletedByUserTwo, userOne, userTwo, messages);
        }
    }
}
