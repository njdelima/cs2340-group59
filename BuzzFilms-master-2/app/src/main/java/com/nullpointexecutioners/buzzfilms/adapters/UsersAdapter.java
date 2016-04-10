package com.nullpointexecutioners.buzzfilms.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nullpointexecutioners.buzzfilms.R;
import com.nullpointexecutioners.buzzfilms.Users;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<Users> {

    private Context context;
    private int layoutResourceId;
    private List<Users> users;

    /**
     * Constructor for ReviewAdapter
     * @param context for adapter
     * @param layoutResourceId layout
     * @param users data
     */
    public UsersAdapter(Context context, int layoutResourceId, List<Users> users) {
        super(context, layoutResourceId, users);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UsersHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UsersHolder();
            holder.userUsername = (TextView) row.findViewById(R.id.user);
            holder.userName = (TextView) row.findViewById(R.id.name);
            holder.userStatus = (TextView) row.findViewById(R.id.user_status);

            row.setTag(holder);
        } else {
            holder = (UsersHolder) row.getTag();
        }

        Users user = users.get(position);
        holder.userUsername.setText(user.getUsername());
        holder.userName.setText(user.getName());
        holder.userStatus.setText(user.getStatus());

        return row;
    }

    static class UsersHolder {
        public TextView userUsername;
        public TextView userName;
        public TextView userStatus;
    }
}
