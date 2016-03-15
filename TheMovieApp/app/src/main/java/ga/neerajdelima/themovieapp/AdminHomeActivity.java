package ga.neerajdelima.themovieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchUserListResponse;

public class AdminHomeActivity extends AppCompatActivity implements FetchUserListResponse {

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        userModel = new UserModel();
        userModel.getUserList(AdminHomeActivity.this);
    }

    @Override
    public void onFetchUserListComplete(ArrayList<User> users) {

    }
}
