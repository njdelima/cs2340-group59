package ga.neerajdelima.themovieapp;

import android.os.Parcelable;
import android.os.Parcel;

/**
 * Created by Neeraj on 2/15/16.
 */
public class User implements Parcelable {
    private String username;
    private String password;
    private String major;
    private String firstName;
    private String lastName;

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel source)
        {
            return new User(source);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
    public String getMajor() {
        return this.major;
    }
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
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof User)) return false;
        if (obj == this) return true;

        User that = (User) obj;
        if ( (this.getUsername().equals(that.getUsername()) ) &&
                ( this.getPassword().equals(that.getPassword()) ) ) {
            return true;
        } else {
            return false;
        }
    }

    public User(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
    }
}