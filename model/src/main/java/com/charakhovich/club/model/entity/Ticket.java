package com.charakhovich.club.model.entity;


import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket extends Entity {
    private long ticketId;
    private long eventDateId;
    private long userId;
    private State state;
    private int countTicket;
    private String eventName;
    private String userName;
    private LocalDateTime dateTime;

    public Ticket() {
    }

    public Ticket(long eventDateId, long userId, State state, int countTicket) {
        this.eventDateId = eventDateId;
        this.userId = userId;
        this.state = state;
        this.countTicket = countTicket;
    }

    public Ticket(long ticketId, long eventDateId, long userId, State state, int countTicket) {
        this.ticketId = ticketId;
        this.eventDateId = eventDateId;
        this.userId = userId;
        this.state = state;
        this.countTicket = countTicket;
    }

    public enum State {
        PAID(),
        BOOKED(),
        CANCEL();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public long getEventDateId() {
        return eventDateId;
    }

    public void setEventDateId(long eventDateId) {
        this.eventDateId = eventDateId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String description) {
        this.eventName = description;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

        public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCountTicket() {
        return countTicket;
    }

    public void setCountTicket(int countTicket) {
        this.countTicket = countTicket;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketId == ticket.ticketId &&
                eventDateId == ticket.eventDateId &&
                userId == ticket.userId &&
                countTicket == ticket.countTicket;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = (int) (ticketId ^ (ticketId >>> 32));
        result = prime * result + (int) (eventDateId ^ (eventDateId >>> 32));
        result = prime * result + (int) (userId ^ (userId >>> 32));
        result = prime * result + countTicket;
        return result;
    }


    @Override
    public String toString() {
        StringBuilder strResult = new StringBuilder("Ticket {");
        strResult.append("{ticketId=").append(ticketId).append(';');
        strResult.append(" userId").append(userId);
        strResult.append(" eventDateId=").append(eventDateId);
        strResult.append(" countTicket=").append(countTicket);
        strResult.append(" statusTicket=").append(state.toString());
        return strResult.toString();
    }
}
