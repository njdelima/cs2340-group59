package ga.neerajdelima.themovieapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import java.util.ArrayList;

import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchUserListResponse;

/**
 * Class that handles AdminActivity.
 * @author Min Ho Lee
 * @version 1.0
 */
public class AdminHomeActivity extends AppCompatActivity implements FetchUserListResponse {

    UserModel userModel;
    ListView uListView;
    MyCustomAdapter uCustomAdapter;
    ArrayList<String> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        //userModel = new UserModel();
        //userModel.getUserList(AdminHomeActivity.this);
        //onFetchUserListComplete();
        userList = new ArrayList<>();
        userList.add("Heelo");
        userList.add("fefadf");
        userList.add("asfewf");
        userList.add("asfwef");
        userList.add("dabva");
        userList.add("asdfwef");
        uCustomAdapter = new MyCustomAdapter(userList, this);
        uListView = (ListView) findViewById(R.id.userListView);
        uListView.setAdapter(uCustomAdapter);
    }

    @Override
    public void onFetchUserListComplete(ArrayList<User> users) {
        // for (User u : users) {
        //     userList.add(u.getUsername() + "\n"
        //             + u.getFirstName() + " " + u.getLastName() + "\n"
        //             + u.getMajor());
        // }
        for (User user : users) {
            Log.d("current user", user.toString());
    }

    /**
     * Customized Adapter for buttons in the listview
     */
    public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<String> list = new ArrayList<String>();
        private Context context;

        public MyCustomAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;
        }
        @Override
        public long getItemId(int pos) {
            return 0;
            //return list.get(pos).getId();
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_admin_home_list, null);
            }

            TextView listItemText = (TextView)view.findViewById(R.id.user_list);
            listItemText.setText(list.get(position));

            final Button banBtn = (Button)view.findViewById(R.id.ban_btn);
            final Button admBtn = (Button)view.findViewById(R.id.admin_btn);
            final Button lockBtn = (Button)view.findViewById(R.id.lock_btn);
            banBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(AdminHomeActivity.this, "Banned", Toast.LENGTH_SHORT).show();
                    banBtn.setText("Unban");
                    notifyDataSetChanged();
                }
            });
            admBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AdminHomeActivity.this, "Made Admin", Toast.LENGTH_SHORT).show();
                    admBtn.setText("Admin");
                    notifyDataSetChanged();
                }
            });
            lockBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(AdminHomeActivity.this, "Locked", Toast.LENGTH_SHORT).show();
                    lockBtn.setText("Unlock");
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
