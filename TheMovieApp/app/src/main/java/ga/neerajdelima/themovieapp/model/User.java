package ga.neerajdelima.themovieapp.model;

/**
 * Created by Neeraj on 2/15/16.
 */

public class User {
    private String username;
    private String password;
    private String major;
    private String firstName;
    private String lastName;

    private boolean locked;
    private boolean banned;
    private boolean admin;


    /*public User(String username, String password) {
        this.username = username;
        this.password = password;
    }*/
    public User(String username, String password, String firstName, String lastName, String major,
                boolean locked, boolean banned, boolean admin) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.locked = locked;
        this.banned = banned;
        this.admin = admin;
    }
    public boolean isLocked() {
        return locked;
    }

    public boolean isBanned() {
        return banned;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    /*public User(String username, String password) {
        this.username = username;
        this.password = password;
    }*/
//    public User(String username, String password, String firstName, String lastName, String major,
//                boolean locked, boolean banned, boolean admin) {
//        this.username = username;
//        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.major = major;
//        this.locked = locked;
//        this.banned = banned;
//        this.admin = admin;
//    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getMajor() {return this.major;}
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    @Override

    public int hashCode() {
        return username.hashCode() + password.hashCode() + major.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        final User that = (User) obj;
        return ( (this.getUsername().equals(that.getUsername()) ) &&
                ( this.getPassword().equals(that.getPassword()) ) );
    }
    @Override
    public String toString() {
        String s = "";
        s = s + "Username: " + this.username + ", ";
        s = s + "Password: " + this.password + ", ";
        s = s + "First Name: " + this.firstName + ", ";
        s = s + "Last Name: " + this.lastName + ", ";
        s = s + "Major: " + this.major + ", ";
        s = s + "Banned: " + Boolean.toString(this.banned) + ", ";
        s = s + "Locked: " + Boolean.toString(this.locked) + ", ";
        s = s + "Admin?: " + Boolean.toString(this.admin);
        return s;
    }
}