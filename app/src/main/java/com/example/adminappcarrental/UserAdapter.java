package com.example.adminappcarrental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminappcarrental.Model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.adminappcarrental.UserActivity.ListUser;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHoder> {
private Context context;
private ArrayList<User> listUser = ListUser;

    public UserAdapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @NonNull
    @NotNull
    @Override
    public UserAdapter.ViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserAdapter.ViewHoder holder, int position) {
        User user = listUser.get(position);

        holder.tvUserName.setText(user.getUsername());
        holder.tvEmail.setText(user.getEmail());
        holder.tvPhoneNumber.setText(user.getPhonenumber());
        holder.tvBirthDay.setText(user.getDateofbirth());
        Glide.with(context).load(user.getAvatar()).into(holder.imgUserAvatar);
        holder.tvAddress.setText(user.getAddress());

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvEmail, tvPhoneNumber, tvBirthDay, tvAddress;
        public ImageView imgUserAvatar;
        public LinearLayout viewInforUser;
        public View mView;

        public ViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            mView = itemView;

            viewInforUser = mView.findViewById(R.id.linear_user_layout);
            imgUserAvatar = mView.findViewById(R.id.img_user_avatar);
            tvUserName = mView.findViewById(R.id.tv_name_user);
            tvEmail = mView.findViewById(R.id.tv_email_user);
            tvPhoneNumber = mView.findViewById(R.id.tv_phonenumber_user);
            tvBirthDay = mView.findViewById(R.id.tv_birthday);
            tvAddress = mView.findViewById(R.id.tv_address_user);
        }
    }
}
