package ga.neerajdelima.themovieapp;

import ga.neerajdelima.themovieapp.model.UserModel;

/**
 * Created by minholee on 4/5/16.
 */
public class longinTest {
    UserModel user = new UserModel();
    public void test() {
        user.setLoggedInUser("jae");
        user.getLoggedInUser();

    }
}
