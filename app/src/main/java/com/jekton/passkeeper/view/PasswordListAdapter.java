package com.jekton.passkeeper.view;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jekton.passkeeper.R;

import java.util.List;


/**
 * @author Jekton
 */

class PasswordListAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Pair<String, String>> mPasswords;


    public PasswordListAdapter(Context context) {
        mContext = context;
    }


    public void setPasswords(List<Pair<String, String>> passwords) {
        mPasswords = passwords;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mPasswords == null ? 0 : mPasswords.size();
    }


    @Override
    public Pair<String, String> getItem(int position) {
        return mPasswords == null ? null : mPasswords.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_item_password, null);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Pair<String, String> data = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.key.setText(data.first);
        holder.password.setText(data.second);
        return convertView;
    }


    private static class ViewHolder {
        TextView key;
        TextView password;

        public ViewHolder(View view) {
            key = view.findViewById(R.id.key);
            password = view.findViewById(R.id.password);
        }
    }
}
