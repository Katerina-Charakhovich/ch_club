package com.charakhovich.club.model.entity;


public class Ticket extends Entity {
    private long ticketId;
    private long eventDateId;
    private long userId;
    private State state;
    private int countTicket;


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


    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getScheduleId() {
        return eventDateId;
    }

    public void setScheduleId(long scheduleId) {
        this.eventDateId = scheduleId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return userId == ticket.userId &&
                countTicket == ticket.countTicket &&
                state == ticket.state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (result * prime + userId);
        result = result * prime + countTicket;
        result = result * prime + state.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder strResult = new StringBuilder("Ticket {");
        strResult.append("{id=").append(ticketId).append(';');
        strResult.append(" eventDateId=").append(eventDateId);
        strResult.append(" countTicket=").append(countTicket);
        strResult.append(" statusTicket=").append(state.toString());
        return strResult.toString();
    }
}
