package fr.efrei.reagency.adapter;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import org.jetbrains.annotations.NotNull;

import fr.efrei.reagency.R;
import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.view.UserDetailActivity;
import fr.efrei.reagency.adapter.UsersAdapter.UserViewHolder;
import fr.efrei.reagency.bo.User;

public final class UsersAdapter
        extends Adapter<UserViewHolder> {

    //The ViewHolder class
    //Each Widget is created as an attribut in order to update the UI
    public static final class UserViewHolder
            extends ViewHolder {
        private final ImageView avatarImage;
        private final TextView name;
        private final TextView phoneNumber;
        private final TextView address;
        private final TextView about;

        public static View itemViewUserSelected;
        public static View curUserSelectedView = null;
        public static User curSelectedUser = null;
        public static boolean viewUserSelected = false;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            //We find the references of the widgets
            avatarImage = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            address = itemView.findViewById(R.id.address);
            about = itemView.findViewById(R.id.about);
        }

        public void update(@NotNull final User user) {
            //We update the UI binding the current user to the current item
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.avatarBlob, 0, user.avatarBlob.length);
            avatarImage.setImageBitmap(bitmap);
            name.setText(user.name);
            phoneNumber.setText(user.phoneNumber);
            address.setText(user.address);
            about.setText(user.about);

            //We handle the click on the current item in order to display a new activity
            itemView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //We create the intent that display the UserDetailActivity.
                    //The current user is added as an extra
                    //The User class implement the "Serializable" interface so I can put the whole object as an extra
//          final Intent intent = new Intent(itemView.getContext(), UserDetailActivity.class);
//          intent.putExtra(UserDetailActivity.USER_EXTRA, user);
//          itemView.getContext().startActivity(intent);

                    itemViewUserSelected = itemView;
                    curSelectedUser = user;
                    if (viewUserSelected == false) {
                        curUserSelectedView = v;
                        viewUserSelected = true;
                        v.setBackgroundResource(R.color.solid_gray);
                    } else {
                        if (curUserSelectedView == v) {
                            v.setBackgroundResource(R.color.solid_white);
                            viewUserSelected = false;
                        } else {
                            curUserSelectedView.setBackgroundResource(R.color.solid_white);
                            viewUserSelected = true;
                            curUserSelectedView = v;
                            v.setBackgroundResource(R.color.solid_gray);
                        }
                    }

                }

            });
        }

    }

    private final List<User> users;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //We create the ViewHolder
        final View view;
        if (Build.VERSION.SDK_INT >= 23)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_user, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_user_loolipop, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        //We update the ViewHolder
        holder.update(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
