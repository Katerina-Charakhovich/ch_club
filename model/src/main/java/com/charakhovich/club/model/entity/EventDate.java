package com.charakhovich.club.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class EventDate extends Entity {
    private long eventDateId;
    private long eventId;
    private LocalDateTime eventDateTime;
    private int ticketCount;
    private BigDecimal ticketCost;
    private State state;
    private int freeTicketCount;

    public enum State {
        ACTUAL(),
        BOOKED(),
    }

    public int getFreeTicketCount() {
        return freeTicketCount;
    }

    public void setFreeTicketCount(int freeTicketCount) {
        this.freeTicketCount = freeTicketCount;
    }


    public EventDate() {
    }

    public EventDate(long eventDateId, long eventId, LocalDateTime eventDateTime, int ticketCount, BigDecimal ticketCost, EventDate.State state) {
        this.eventDateId = eventDateId;
        this.eventId = eventId;
        this.eventDateTime = eventDateTime;
        this.ticketCount = ticketCount;
        this.ticketCost = ticketCost;
        this.state = state;
    }

    public EventDate(long eventId, LocalDateTime eventDateTime, BigDecimal ticketCost, int ticketCount) {
        this.eventId = eventId;
        this.eventDateTime = eventDateTime;
        this.ticketCount = ticketCount;
        this.ticketCost = ticketCost;
        this.state = EventDate.State.ACTUAL;
    }

    public long getEventDateId() {
        return eventDateId;
    }

    public void setEventDateId(long eventDateId) {
        this.eventDateId = eventDateId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public BigDecimal getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(BigDecimal ticketCost) {
        this.ticketCost = ticketCost;
    }

    public EventDate.State getState() {
        return state;
    }

    public void setState(EventDate.State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDate eventDate = (EventDate) o;
        return eventDateId == eventDate.eventDateId &&
                eventId == eventDate.eventId &&
                eventDateTime.equals(eventDate.eventDateTime);
    }

    public int hashCode() {
        final int prime = 31;
        int result = (int) (eventDateId ^ (eventDateId >>> 32));
        result = prime * result + (int) (eventId ^ (eventId >>> 32));
        result = prime * result + eventDateTime.hashCode();
        return result;
    }


    @Override
    public String toString() {
        StringBuilder strResult = new StringBuilder("EventDate {");
        strResult.append("eventDateId=").append(eventDateId)
                .append(", eventId=").append(eventId)
                .append(", eventDateTime=").append(eventDateTime)
                .append(", ticketCount=").append(ticketCount)
                .append(", ticketCost=").append(ticketCost)
                .append(", status=").append(state)
                .append('}');
        return strResult.toString();
    }
}
