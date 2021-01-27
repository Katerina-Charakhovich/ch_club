package com.charakhovich.club.model.entity;

import java.time.LocalDateTime;

public class Picture extends Entity {
    private long pictureId;
    private String name;
    private Type type;
    private State state;
    private LocalDateTime modifyDate;

    public Picture() {
        this.state = State.ACTUAL;
    }


    public Picture(long pictureId, Type type, String name, State state, LocalDateTime modifyDate) {
        this.pictureId = pictureId;
        this.type = type;
        this.name = name;
        this.state = state;
        this.modifyDate = modifyDate;
    }

    public enum Type {
        MAIN,
        ADDITIONAL;
    }

    public enum State {
        ACTUAL,
        DELETED;
    }

    public long getPictureId() {
        return pictureId;
    }

    public void setPictureId(long pictureId) {
        this.pictureId = pictureId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture.State getState() {
        return state;
    }

    public void setStatus(Picture.State state) {
        this.state = state;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return getType() == picture.getType() &&
                getName().equals(picture.getName());
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + type.hashCode();
        result = result * prime + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder strResult = new StringBuilder("Picture:");
        strResult.append("pictureId=").append(pictureId)
                .append(", type=").append(type)
                .append(", name ='").append(name)
                .append(", modifyDate=").append(modifyDate)
                .append('}');
        return strResult.toString();
    }
}
