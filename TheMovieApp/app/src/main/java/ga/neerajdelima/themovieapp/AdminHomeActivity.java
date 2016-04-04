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

import java.util.List;
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
    private UserModel userModel;
    private List<String> userList;
    private List<User> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        userList = new ArrayList<>();
        userModel = new UserModel();
        userModel.getUserList(AdminHomeActivity.this);
        user = new ArrayList<>();
        final MyCustomAdapter uCustomAdapter = new MyCustomAdapter(userList, this);
        final ListView uListView = (ListView) findViewById(R.id.userListView);
        uListView.setAdapter(uCustomAdapter);
        uListView.invalidateViews();
    }

    @Override
    public void onFetchUserListComplete(List<User> users) {
        for (User u : users) {
            userList.add(u.getUsername() + "\n"
                    + u.getFirstName() + " " + u.getLastName() + "\n"
                    + u.getMajor());
            user.add(u);
            Log.d(String.valueOf(u.getUsername()), String.valueOf(u.isLocked()));
        }
//        for (User user : users)
//            Log.d("current user", user.toString());
    }

    /**
     * Customized Adapter for buttons in the listview
     */
    private class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        private List<String> list = new ArrayList<String>();
        private Context context;

        /**
         * Constructor for MycustomAdapter
         * @param list list of movie
         * @param context context
         */

        public MyCustomAdapter(List<String> l, Context c) {
            this.list = l;
            this.context = c;
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
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_admin_home_list, null);
            }

            final TextView listItemText = (TextView)view.findViewById(R.id.user_list);
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

            admBtn.setTag(position);
            lockBtn.setTag(position);
            //Log.d("user", String.valueOf(user.get(position)));
            //int idx = (Integer) view.getTag();
            if (user.get(position).isAdmin()) {
                admBtn.setText("Demote");
            } else {
                admBtn.setText("Make Admin");
            }
            if (user.get(position).isBanned()) {
                banBtn.setText("Unban");
            } else {
                banBtn.setText("Ban");
            }
            if (user.get(position).isLocked()) {
                lockBtn.setText("Unlock");
            } else {
                lockBtn.setText("Lock");
            }
            banBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int index = (Integer) v.getTag();
                    if (user.get(index).isBanned()) {
                        banBtn.setText("Ban");
                        Toast.makeText(AdminHomeActivity.this, "Unbanned " + user.get(index).getUsername(), Toast.LENGTH_SHORT).show();
                        user.get(index).setBanned(false);
                        userModel.unbanUser(user.get(index).getUsername());
                    } else {

                        banBtn.setText("Unban");
                        Toast.makeText(AdminHomeActivity.this, "Banned " + user.get(index).getUsername(), Toast.LENGTH_SHORT).show();
                        user.get(index).setBanned(true);
                        userModel.banUser(user.get(index).getUsername());
                    }
                    notifyDataSetChanged();
                }
            });
            admBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int index = (Integer) v.getTag();
                    if (user.get(index).isAdmin()) {
                        admBtn.setText("Make Admin");
                        Toast.makeText(AdminHomeActivity.this, "Demoted " + user.get(index).getUsername() + " to User", Toast.LENGTH_SHORT).show();
                        user.get(index).setAdmin(false);
                        userModel.removeAdmin(user.get(index).getUsername());
                    } else {
                        admBtn.setText("Demote");
                        Toast.makeText(AdminHomeActivity.this, "Made " + user.get(index).getUsername() + " to Admin", Toast.LENGTH_SHORT).show();
                        user.get(index).setAdmin(true);
                        userModel.makeAdmin(user.get(index).getUsername());
                    }
                    notifyDataSetChanged();
                }
            });
            lockBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int index = (Integer) v.getTag();
                    if (user.get(index).isLocked()) {
                        lockBtn.setText("Lock");
                        Toast.makeText(AdminHomeActivity.this, "Unlocked " + user.get(index).getUsername(), Toast.LENGTH_SHORT).show();
                        user.get(index).setLocked(false);
                        userModel.unlockUser(user.get(index).getUsername());
                    } else {
                        lockBtn.setText("Unlock");
                        Toast.makeText(AdminHomeActivity.this, "Locked " + user.get(index).getUsername(), Toast.LENGTH_SHORT).show();
                        user.get(index).setLocked(true);
                        userModel.lockUser(user.get(index).getUsername());
                    }
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
