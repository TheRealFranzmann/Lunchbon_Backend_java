package com.lunchbon.backend.model;

import jakarta.persistence.*;

@Entity
public class AppUser {

    @EmbeddedId
    private AppUserId id;

    @OneToOne
    @JoinColumn(name = "domainUserId", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "domainUserId", referencedColumnName = "id", insertable = false, updatable = false)
    private Waiter waiter;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public AppUser() {
    }

    public AppUser(AppUserId id, Employee employee, Waiter waiter, String username, String password, Role role) {
        if ((employee == null && waiter == null) || (employee != null && waiter != null)) {
            throw new IllegalArgumentException("Exactly one of 'employee' or 'waiter' must be set.");
        }

        this.id = id;
        this.employee = employee;
        this.waiter = waiter;
        this.username = username;
        this.password = password;
        this.role = role;

        this.userType = employee != null ? UserType.USERTYPE_EMPLOYEE : UserType.USERTYPE_WAITER;
    }

    public AppUserId getId() {
        return id;
    }

    public void setId(AppUserId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        if (waiter != null) {
            throw new IllegalArgumentException("AppUser can only be linked to either an employee or a waiter.");
        }
        this.employee = employee;
        this.userType = UserType.USERTYPE_EMPLOYEE;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        if (employee != null) {
            throw new IllegalArgumentException("AppUser can only be linked to either a waiter or an employee.");
        }
        this.waiter = waiter;
        this.userType = UserType.USERTYPE_WAITER;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserType getUserType() {
        return userType;
    }

    public boolean isEmployee() {
        return userType == UserType.USERTYPE_EMPLOYEE;
    }

    public boolean isWaiter() {
        return userType == UserType.USERTYPE_WAITER;
    }
}
