package fr.efrei.reagency.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import fr.efrei.reagency.R;
import fr.efrei.reagency.viewmodel.AddUserActivityViewModel;
import fr.efrei.reagency.viewmodel.AddUserActivityViewModel.Event;

import static fr.efrei.reagency.tools.Utils.imageViewToByte;

final public class AddUserActivity
        extends AppCompatActivity
        implements OnClickListener {

    //The tag used into this screen for the logs
    public static final String TAG = AddUserActivity.class.getSimpleName();

    private static int RESULT_LOAD_IMG = 1;

    private ImageView avatarImage;
    private TextInputLayout nameLayout;
    private TextInputEditText nameText;
    private TextInputLayout phoneNumberLayout;
    private TextInputEditText phoneNumberText;
    private TextInputLayout addressLayout;
    private TextInputEditText addressText;
    private TextInputLayout aboutLayout;
    private TextInputEditText aboutText;

    private AddUserActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        if (Build.VERSION.SDK_INT >= 23)
            setContentView(R.layout.activity_add_user);
        else
            setContentView(R.layout.activity_add_user_loolipop);

        //Then we retrieved the widget we will need to manipulate into the
        avatarImage = findViewById(R.id.avatar);
        nameLayout = findViewById(R.id.nameLayout);
        phoneNumberLayout = findViewById(R.id.phoneLayout);
        addressLayout = findViewById(R.id.addressLayout);
        aboutLayout = findViewById(R.id.aboutLayout);
        nameText = findViewById(R.id.name);
        phoneNumberText = findViewById(R.id.phone);
        addressText = findViewById(R.id.address);
        aboutText = findViewById(R.id.about);

        //We configure the click on the save button
        findViewById(R.id.btnSave).setOnClickListener(this);

        avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // select avatar Image();
                loadImagefromGallery(v);
            }
        });

        viewModel = new ViewModelProvider(this).get(AddUserActivityViewModel.class);

        observeEvent();
    }

    private void observeEvent() {
        viewModel.event.observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                if (event == Event.ResetForm) {
                    resetForm();
                } else if (event == Event.DisplayError) {
                    displayError();
                }
            }
        });
    }

    private void displayError() {
        Toast.makeText(this, R.string.cannot_add_user, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        //we first retrieve user's entries

        final String userName = nameLayout.getEditText().getText().toString();
        final String userPhoneNumber = phoneNumberLayout.getEditText().getText().toString();
        final String userAddress = addressLayout.getEditText().getText().toString();
        final String aboutUser = aboutLayout.getEditText().getText().toString();
        final byte[] avatarBlob = imageViewToByte(avatarImage);

        viewModel.saveUser(userName, userPhoneNumber, userAddress, aboutUser, avatarBlob);
    }

    private void resetForm() {
        avatarImage.setImageResource(R.drawable.ic_account);
        nameText.setText(null);
        phoneNumberText.setText(null);
        addressText.setText(null);
        aboutText.setText(null);
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImage);

                    int width = avatarImage.getWidth();
                    int height = avatarImage.getHeight();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    avatarImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, width, height, true));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}