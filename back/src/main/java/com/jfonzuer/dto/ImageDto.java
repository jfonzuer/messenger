package com.jfonzuer.dto;

/**
 * Created by pgm on 07/11/16.
 */
public class ImageDto {
    private Long id;
    private Integer orderNumber;
    private String url;

    public ImageDto() {
    }

    public ImageDto(Long id, Integer order, String url) {
        this.id = id;
        this.orderNumber = order;
        this.url = url;
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


    public static final class Builder {
        private Long id;
        private Integer orderNumber;
        private String url;

        private Builder() {
        }

        public static Builder anImageDto() {
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

        public ImageDto build() {
            ImageDto imageDto = new ImageDto();
            imageDto.setId(id);
            imageDto.setOrderNumber(orderNumber);
            imageDto.setUrl(url);
            return imageDto;
        }
    }
}
