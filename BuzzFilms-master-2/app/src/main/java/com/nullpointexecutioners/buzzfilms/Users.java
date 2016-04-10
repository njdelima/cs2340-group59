package com.nullpointexecutioners.buzzfilms;

public class Users {

    private String username;
    private String name;
    private String email;
    private String major;
    private String status;

    /**
     * Constructor for Users
     * @param username of user
     * @param name of user
     * @param email of user
     * @param major of user
     * @param status of user's account
     */
    public Users(String username, String name, String email, String major, String status) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.major = major;
        this.status = status;
    }

    /**
     * Gets this user's username
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets this user's name
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets this user's email
     * @return email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets this user's major
     * @return major of user
     */
    public String getMajor() {
        return major;
    }

    /**
     * Gets the account status of this user
     * @return account status of user
     */
    public String getStatus() {
        return status;
    }
}
