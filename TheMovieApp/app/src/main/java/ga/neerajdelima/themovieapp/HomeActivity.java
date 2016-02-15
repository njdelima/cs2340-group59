package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import org.w3c.dom.Text;


public class HomeActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    ArrayList<User> valid_credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        valid_credentials = this.getIntent().getParcelableArrayListExtra("CREDENTIALS");

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

        Intent intent = getIntent();
        String message = intent.getStringExtra("USERNAME");

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.home_main_layout);
        relativeLayout.addView(textView);
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
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optsArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putParcelableArrayListExtra("CREDENTIALS", valid_credentials);
        startActivity(intent);
        finish();
    }

}
