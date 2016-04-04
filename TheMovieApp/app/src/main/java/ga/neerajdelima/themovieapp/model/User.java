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


    /**
     * Constructor of User
     * @param username username
     * @param password password
     * @param firstName user's first name
     * @param lastName user's last name
     * @param major user's major
     * @param locked whether user is locked
     * @param banned whether user is banned
     * @param admin whether us is admin
     */
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

    /**
     * @return whether user is locked
     */
    public boolean isLocked() {
        return locked;
    }
    /**
     * @return whether user is banned
     */
    public boolean isBanned() {
        return banned;
    }
    /**
     * @return whether user is admin
     */
    public boolean isAdmin() {
        return admin;
    }
    /**
     * @param locked user's locked status to be set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    /**
     * @param banned user's banned status to be set
     */
    public void setBanned(boolean banned) {
        this.banned = banned;
    }
    /**
     * @param admin user's admin status to be set
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * @return password
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * @return user's first name
     */
    public String getFirstName() {
        return this.firstName;
    }
    /**
     * @return user's last name
     */
    public String getLastName() {
        return this.lastName;
    }
    /**
     * @return user's major
     */
    public String getMajor() {return this.major;}
    /**
     * @param username username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @param firstName user's first name to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @param lastName user's last name to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @param major user's major to be set
     */
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

        User that = (User) obj;
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