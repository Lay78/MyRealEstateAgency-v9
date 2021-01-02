package fr.efrei.reagency.view;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonObject;
import com.livefront.bridge.Bridge;
import com.livefront.bridge.SavedStateHandler;

import java.util.Calendar;

import fr.efrei.reagency.R;
import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.retrofit.RetrofitBuilder;
import fr.efrei.reagency.retrofit.RetrofitInterface;
import fr.efrei.reagency.viewmodel.PropertyDetailActivityViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fr.efrei.reagency.tools.Utils.imageViewToByte;

final public class PropertyDetailActivity
        extends AppCompatActivity {
    public static final String PROPERTY_EXTRA = "propertyExtra";

    //The tag used into this screen for the logs
    public static final String TAG = PropertyDetailActivity.class.getSimpleName();

    private DatePickerDialog.OnDateSetListener mDateCreationSetListener;
    private DatePickerDialog.OnDateSetListener mDateUpdateSetListener;

    private boolean isFirstType = true;
    private boolean isFirstStatus = true;

    private ImageView propImage;
    private TextView propType;
    private TextView propStatus;
    private TextView propPrice;
    private TextView propSurface;
    private TextView propRoom;
    private TextView propDescription;
    private TextView propAddress;
    private TextView propLatitude;
    private TextView propLongitude;
    private TextView propDateCreation;
    private TextView propDateUpdate;
    private TextView propDateSold;
    private TextView propAgentName;
    //private Button propSave;
    private Button btnConvertPrice;

    private PropertyDetailActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add to avoid TransactionTooLargeException
        if (savedInstanceState != null)
            Bridge.restoreInstanceState(this, savedInstanceState);

        // Add to avoid TransactionTooLargeException
        Bridge.initialize(getApplicationContext(), new SavedStateHandler() {
            @Override
            public void saveInstanceState(@NonNull Object target, @NonNull Bundle state) {
                Bridge.saveInstanceState(target, state);
            }

            @Override
            public void restoreInstanceState(@NonNull Object target, @Nullable Bundle state) {
                Bridge.restoreInstanceState(target, state);
            }
        });

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_property_detail);

        //Then we retrieved the widget we will need to manipulate into the
        propImage = findViewById(R.id.propImage);
        propType = findViewById(R.id.propType);
        propStatus = findViewById(R.id.propStatus);
        propPrice = findViewById(R.id.propPrice);
        propSurface = findViewById(R.id.propSurface);
        propRoom = findViewById(R.id.propRoom);
        propDescription = findViewById(R.id.propDescription);

        propAddress = findViewById(R.id.propAddress);
        propLatitude = findViewById(R.id.propLatitude);
        propLongitude = findViewById(R.id.propLongitude);
        propDateCreation = findViewById(R.id.propDateCreation);
        propDateUpdate = findViewById(R.id.propDateUpdate);
        propDateSold = findViewById(R.id.propDateSold);
        propAgentName = findViewById(R.id.propAgentName);
        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(PropertyDetailActivityViewModel.class);

        btnConvertPrice = findViewById(R.id.btnConvertPrice);
        btnConvertPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency("EUR");
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        Log.d("response", String.valueOf(res));

                        JsonObject rates = res.getAsJsonObject("rates");
                        int priceInEuros = Integer.valueOf(propPrice.getText().toString());
                        int multiplier = Integer.valueOf(rates.get("USD").toString());
                        int priceInDollars = priceInEuros * multiplier;
                        propPrice.setText(String.valueOf(priceInDollars));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });

        observeProperty();
    }

    // Add to avoid TransactionTooLargeException
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bridge.saveInstanceState(this, outState);
    }

    // Add to avoid TransactionTooLargeException
    @Override
    public void onDestroy() {
        super.onDestroy();
        Bridge.clear(this);
    }

    private void observeProperty() {
        viewModel.property.observe(this, new Observer<Property>() {
            @Override
            public void onChanged(Property property) {
                //Then we bind the Property and the UI
                int width = propImage.getWidth();
                int height = propImage.getHeight();

                Bitmap bitmap = BitmapFactory.decodeByteArray(property.imageBlob, 0, property.imageBlob.length);
                propImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, width, height, true));

                //propImage.setImageBitmap(bitmap);
                propType.setText(property.propertyType);
                if (property.propertyStatus.equals("Sold")) {
                    propStatus.setBackgroundResource(R.color.solid_red);
                } else {
                    propStatus.setBackgroundResource(R.color.solid_green);
                }
                propStatus.setTextColor(Color.WHITE);
                //propStatus.setTextColor(getResources().getColor(R.color.solid_white));
                propStatus.setText(property.propertyStatus);
                propPrice.setText(String.valueOf(property.propertyPrice) + " €");
                propSurface.setText(String.valueOf(property.propertySurface) + " m2");
                propRoom.setText(String.valueOf(property.propertyRoom));
                propDescription.setText(property.propertyDescription);

                propAddress.setText(property.propertyAddress);
                propLatitude.setText(property.propertyLatitude);
                propLongitude.setText(property.propertyLongitude);
                //String[] arrayString = property.propertyDateCreation.split(" ");
                propDateCreation.setText(property.propertyDateCreation);
                //arrayString = property.propertyDateUpdate.split(" ");
                propDateUpdate.setText(property.propertyDateUpdate);
                //arrayString = property.propertyDateSold.split(" ");
                propDateSold.setText(property.propertyDateSold);
                propAgentName.setText(property.propertyAgentName);
                propDescription.setText(property.propertyDescription);

                //Then we update the title into the actionBar
                getSupportActionBar().setTitle(R.string.property_detail);
            }
        });
    }
}