package ga.neerajdelima.themovieapp;

import android.content.Context;
import android.util.Log;
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
    ArrayList<User> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        userList = new ArrayList<>();
        userModel = new UserModel();
        userModel.getUserList(AdminHomeActivity.this);
        user = new ArrayList<>();
        uCustomAdapter = new MyCustomAdapter(userList, this);
        uListView = (ListView) findViewById(R.id.userListView);
        uListView.setAdapter(uCustomAdapter);
//        uListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {
//
//            }
//        });
    }

    @Override
    public void onFetchUserListComplete(ArrayList<User> users) {
         for (User u : users) {
             userList.add(u.getUsername() + "\n"
                     + u.getFirstName() + " " + u.getLastName() + "\n"
                     + u.getMajor());
             user.add(u);
         }
//        for (User user : users)
//            Log.d("current user", user.toString());
    }

    /**
     * Customized Adapter for buttons in the listview
     */
    private class MyCustomAdapter extends BaseAdapter implements ListAdapter {
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
            final Button lockBtn = (Button)view.findViewById(R.id.lock_btn);
            final Button admBtn = (Button)view.findViewById(R.id.admin_btn);
            //Need to make the buttons check whether or not they are banned, locked or an admin
//            if (user.get(position).isBanned()) {
//                banBtn.setText("Unban");
//            }
//            if (user.get(position).isLocked()) {
//                lockBtn.setText("Unlock");
//            }
//            if (user.get(position).isAdmin()) {
//                admBtn.setText("Demote");
//            }
            banBtn.setTag(position);
            banBtn.setTag(position);
            admBtn.setTag(position);
            lockBtn.setTag(position);
            banBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (Integer) v.getTag();
                    if (user.get(index).isBanned()) {
                        banBtn.setText("Unban");
                        Toast.makeText(AdminHomeActivity.this, "Banned " + user.get(index).getUsername(), Toast.LENGTH_SHORT).show();
                        user.get(index).setBanned(true);
                        userModel.banUser(user.get(index).getUsername());
                        Log.d("user info", user.get(index).toString());
                    }
                    notifyDataSetChanged();
                }
            });
            admBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (Integer) v.getTag();
                    if (user.get(index).isAdmin()) {
                        admBtn.setText("Make Admin");
                        Toast.makeText(AdminHomeActivity.this, "Demoted " + user.get(index).getUsername(), Toast.LENGTH_SHORT).show();
                        user.get(index).setAdmin(false);
                        userModel.removeAdmin(user.get(index).getUsername());
                        Log.d("user info", user.get(index).toString());
                    } else {
                        admBtn.setText("Demote");
                        Toast.makeText(AdminHomeActivity.this, "Made " + user.get(index).getUsername() + " to Admin", Toast.LENGTH_SHORT).show();
                        user.get(index).setAdmin(true);
                        userModel.makeAdmin(user.get(index).getUsername());
                        Log.d("user info", user.get(index).toString());
                    }
                    notifyDataSetChanged();
                }
            });
            lockBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (Integer) v.getTag();
                    if (user.get(index).isLocked()) {
                        lockBtn.setText("Lock");
                        Toast.makeText(AdminHomeActivity.this, "Unlocked " + user.get(index).getUsername(), Toast.LENGTH_SHORT).show();
                        user.get(index).setLocked(false);
                        userModel.unlockUser(user.get(index).getUsername());
                        Log.d("user info", user.get(index).toString());
                    } else {
                        lockBtn.setText("Unlock");
                        Toast.makeText(AdminHomeActivity.this, "Locked " + user.get(index).getUsername(), Toast.LENGTH_SHORT).show();
                        user.get(index).setLocked(true);
                        userModel.lockUser(user.get(index).getUsername());
                        Log.d("user info", user.get(index).toString());
                    }
                }
            });
            return view;
        }
    }
}
