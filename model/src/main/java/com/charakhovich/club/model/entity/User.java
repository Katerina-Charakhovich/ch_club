package com.charakhovich.club.model.entity;
import java.math.BigDecimal;
import java.util.List;

public class User extends Entity {
    private long userId;
    private String firstName;
    private String lastName;
    private String phone;
    private String login;
    private Role role;
    private State state;
    private String photo;
    private BigDecimal balance;
    private List<Ticket> listTicket;

    public enum Role {
        ADMIN,
        USER,
        GUEST;
    }

    public enum State {
        ACTUAL,
        BLOCKED,
        UNCONFIRMED,
        DELETED;
    }

    public User() {
        this.state = State.ACTUAL;
    }

    public User(long userId, String firstName, String lastName, String phone, String login,
                Role role, State state, BigDecimal balance) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.login = login;
        this.role = role;
        this.state = state;
        this.balance = balance;
    }

    public User(String firstName, String lastName, String phone, String login, Role role,User.State state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.login = login;
        this.role = role;
        this.state = state;
        this.balance = BigDecimal.valueOf(0.00);
    }


    public String fullName() {
        StringBuilder strResult = new StringBuilder(this.firstName);
        strResult.append(" ").append(this.lastName);
        return strResult.toString();
    }

    public List<Ticket> getListTicket() {
        return listTicket;
    }

    public void setListTicket(List<Ticket> listTicket) {
        this.listTicket = listTicket;
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

    public void setState(State status) {
        this.state = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                login.equals(user.login) &&
                role == user.role ;

    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + firstName.hashCode();
        result = result * prime + lastName.hashCode();
        result = result * prime + login.hashCode();
        result = result * prime + role.hashCode();
        return result;
    }

    public String toString() {
        StringBuilder strResult = new StringBuilder("User:");
        strResult.append("{userId=").append(userId).append(';');
        strResult.append(" lastName=").append(lastName).append(';');
        strResult.append(" firstName=").append(firstName).append(';');
        strResult.append(" login=").append(login).append(';');
        strResult.append(" role=").append(role).append('.');
        return strResult.toString();
    }

}

