package com.jfonzuer.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pgm on 07/11/16.
 */
@Entity
public class Image implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private User user;

    public Image() {
    }

    public Image(Integer orderNumber, String url, User user) {
        this.orderNumber = orderNumber;
        this.url = url;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", url='" + url + '\'' +
                '}';
    }


    public static final class Builder {
        private Long id;
        private Integer orderNumber;
        private String url;
        private User user;

        private Builder() {
        }

        public static Builder anImage() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withOrderNumber(Integer orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Image build() {
            Image image = new Image();
            image.setId(id);
            image.setOrderNumber(orderNumber);
            image.setUrl(url);
            image.setUser(user);
            return image;
        }
    }
}
