package com.charakhovich.club.model.entity;
import java.time.LocalDateTime;

public class MessageEvent extends Entity {
    private long messageId;
    private Event event;
    private User user;
    private String text;
    private State state;
    private LocalDateTime modifyDate;

    public enum State {
        NEW(),
        ACTUAL(),
        CANCEL();
    }
    public MessageEvent() {
        this.state=State.ACTUAL;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEvent that = (MessageEvent) o;
        return event.equals(that.event) &&
                user == that.user &&
                text.equals(that.text);
    }

    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = result * prime + event.hashCode();
            result = result * prime + user.hashCode();
            result = result * prime + text.hashCode();
            return result;
        }

    @Override
    public String toString() {
        StringBuilder strResult = new StringBuilder("Message {");
        strResult.append( "messageId=").append(messageId)
                .append(", eventId=" ).append(event.getEventId())
                .append( ", user=").append(user.toString())
                .append(", text='").append(text)
                .append(", modifyDate=").append(modifyDate)
                .append( '}');
        return strResult.toString();
    }
}

