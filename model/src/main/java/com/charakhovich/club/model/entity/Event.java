package com.charakhovich.club.model.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Event extends Entity {
    private long eventId;
    private Type eventType;
    private String name;
    private String description;
    private String shortDescription;
    private State state;
    private LocalDateTime modifyDate;
    private Picture mainPicture;
    private List<Picture> listAdditionalPicture;
    private double duration;


    public Event() {
        this.state = State.ACTUAL;
    }

    public Event(int eventId, Type eventType, String name, String description,
                 Event.State state, String shortDescription,double duration) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.name = name;
        this.description = description;
        this.state = state;
        this.shortDescription = shortDescription;
        this.duration = duration;
    }

    public Event(Type eventType, String name, String description, String shortDescription,double duration) {
        this.eventType = eventType;
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.state = Event.State.ACTUAL;
        this.duration = duration;
    }

    public enum Type {
        THEATRE(),
        QUEST(),
        ANIMATOR()
    }

    public enum State {
        ACTUAL(),
        CANCELLED(),
    }


    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Type getEventType() {
        return eventType;
    }

    public void setEventType(Type eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Picture getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(Picture mainPicture) {
        this.mainPicture = mainPicture;
    }

    public List<Picture> getListAdditionalPicture() {
        return listAdditionalPicture;
    }

    public void setListAdditionalPicture(List<Picture> listAdditionalPicture) {
        this.listAdditionalPicture = listAdditionalPicture;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getEventType() == event.getEventType() &&
                getName().equals(event.getName()) &&
                getDescription().equals(event.getDescription()) &&
                getShortDescription().equals(event.getShortDescription());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = (int) (eventId ^ (eventId >>> 32));
        result = prime * result + eventType.hashCode();
        result = prime * result + name.hashCode();
        result = prime * result + description.hashCode();
        result = prime * result + shortDescription.hashCode();

        return result;
    }

    @Override
    public String toString() {
        StringBuilder strResult = new StringBuilder("Event:");
        strResult.append("eventId=").append(eventId)
                .append(",eventType=").append(eventType)
                .append(", name ='").append(name)
                .append(",description=").append(description)
                .append(",shortDescription =").append(shortDescription)
                .append('}');
        return strResult.toString();
    }
}
