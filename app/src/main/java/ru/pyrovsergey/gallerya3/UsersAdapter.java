package ru.pyrovsergey.gallerya3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.dto.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> userList = new ArrayList<>();

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, final int position) {
        final User user = userList.get(position);
        holder.userName.setText(user.getName());
        holder.positionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(App.getInstance(), String.valueOf(user.getId()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void updateUserList(List<User> list) {
        userList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout positionItem;
        TextView userName;

        ViewHolder(View itemView) {
            super(itemView);
            positionItem = itemView.findViewById(R.id.position_user_item);
            userName = itemView.findViewById(R.id.user_name_text_view);
        }
    }
}