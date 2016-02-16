package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class viewProfileActivity extends AppCompatActivity {
    Intent intent;
    ArrayList<User> valid_credentials;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        intent = this.getIntent();
        valid_credentials = intent.getParcelableArrayListExtra("CREDENTIALS");
        mDrawerList = (ListView) findViewById(R.id.navList);
        String[] optsArray = getResources().getStringArray(R.array.navigation_array);
        addDrawerItems(optsArray);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleNavClick(view);
                // Toast.makeText(HomeActivity.this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void handleNavClick(View view) {
        String label = ((TextView) view).getText().toString();
        if (label.equals("Logout")) {
            logout();
        }
        if (label.equals("Profile")) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    private void addDrawerItems(String[] optsArray) {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, optsArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putParcelableArrayListExtra("CREDENTIALS", valid_credentials);
        startActivity(intent);
        finish();
    }
}
