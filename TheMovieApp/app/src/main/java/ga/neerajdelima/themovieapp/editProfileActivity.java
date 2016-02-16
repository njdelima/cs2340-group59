package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ga.neerajdelima.themovieapp.model.UserModel;

public class editProfileActivity extends AppCompatActivity {
    Intent intent;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = this.getIntent();
        userModel = new UserModel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }
    public void saveChanges(View view) {
        EditText firstNameText = (EditText) findViewById(R.id.edit_firstName);
        String firstName = firstNameText.getText().toString();
        if (firstName != null) {
            userModel.getLoggedInUser().setFirstName(firstName);
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

    }
}
