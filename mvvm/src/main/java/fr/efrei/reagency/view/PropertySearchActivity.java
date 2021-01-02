package fr.efrei.reagency.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.Calendar;

import fr.efrei.reagency.R;

final public class PropertySearchActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = PropertySearchActivity.class.getSimpleName();

    private CheckBox chkHouse;
    private CheckBox chkFlat;
    private CheckBox chkOffice;
    private CheckBox chkStudent;
    private CheckBox chkNotSold;
    private CheckBox chkSold;
    private EditText priceMin;
    private EditText priceMax;
    private EditText surfaceMin;
    private EditText surfaceMax;
    private EditText roomMin;
    private EditText roomMax;
    private TextView delaySold;
    private Button btnSearch;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_search_property);

        chkHouse = findViewById(R.id.chkHouse);
        chkFlat = findViewById(R.id.chkFlat);
        chkOffice = findViewById(R.id.chkOffice);
        chkStudent = findViewById(R.id.chkStudent);
        chkNotSold = findViewById(R.id.propertyNotSold);
        chkSold = findViewById(R.id.propertySold);

        priceMin = findViewById(R.id.priceMin);
        priceMax = findViewById(R.id.priceMax);
        surfaceMin = findViewById(R.id.surfaceMin);
        surfaceMax = findViewById(R.id.surfaceMax);
        roomMin = findViewById(R.id.roomMin);
        roomMax = findViewById(R.id.roomMax);
        delaySold = findViewById(R.id.delaySold);
        btnSearch = findViewById(R.id.btnSearch);

        findViewById(R.id.clearSold).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delaySold.setText("");
            }
        });
        setOnClickListenerDateCreation();
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String dataBack = "";
        if (!chkHouse.isChecked() && !chkFlat.isChecked() && !chkOffice.isChecked() && !chkStudent.isChecked()) {
            Toast.makeText(PropertySearchActivity.this, R.string.search_property_type_no_check, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!chkNotSold.isChecked() && !chkSold.isChecked()) {
            Toast.makeText(PropertySearchActivity.this, R.string.search_property_status_no_check, Toast.LENGTH_SHORT).show();
            return;
        }
        String[] type = new String[4];
        type[0] = chkHouse.isChecked() ? "1" : "0";
        type[1] = chkFlat.isChecked() ? "1" : "0";
        type[2] = chkOffice.isChecked() ? "1" : "0";
        type[3] = chkStudent.isChecked() ? "1" : "0";
        String[] status = new String[2];
        status[0] = chkNotSold.isChecked() ? "1" : "0";
        status[1] = chkSold.isChecked() ? "1" : "0";

        int propertyPriceMin, propertyPriceMax, propertySurfaceMin, propertySurfaceMax;
        int propertyRoomMin, propertyRoomMax;
        String sDelay;

        String s = priceMin.getEditableText().toString();
        if (s.equals("")) propertyPriceMin = -1;
        else propertyPriceMin = Integer.parseInt(s);

        s = priceMax.getEditableText().toString();
        if (s.equals("")) propertyPriceMax = -1;
        else propertyPriceMax = Integer.parseInt(s);

        s = surfaceMin.getEditableText().toString();
        if (s.equals("")) propertySurfaceMin = -1;
        else propertySurfaceMin = Integer.parseInt(s);

        s = surfaceMax.getEditableText().toString();
        if (s.equals("")) propertySurfaceMax = -1;
        else propertySurfaceMax = Integer.parseInt(s);

        s = roomMin.getEditableText().toString();
        if (s.equals("")) propertyRoomMin = -1;
        else propertyRoomMin = Integer.parseInt(s);

        s = roomMax.getEditableText().toString();
        if (s.equals("")) propertyRoomMax = -1;
        else propertyRoomMax = Integer.parseInt(s);

        if (propertyPriceMin != -1 && propertyPriceMax != -1) {
            if (propertyPriceMin >= propertyPriceMax) {
                Toast.makeText(PropertySearchActivity.this, R.string.search_property_price_max_inf_min, Toast.LENGTH_SHORT).show();
                return;
            }
          }
        if (propertySurfaceMin != -1 && propertySurfaceMax != -1) {
            if (propertySurfaceMin >= propertySurfaceMax) {
                Toast.makeText(PropertySearchActivity.this, R.string.search_property_surface_max_inf_min, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (propertyRoomMin != -1 && propertyRoomMax != -1) {
            if (propertyRoomMin >= propertyRoomMax) {
                Toast.makeText(PropertySearchActivity.this, R.string.search_property_room_max_inf_min, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        sDelay = delaySold.getText().toString();
        StringBuilder builder = new StringBuilder();
        for(String s1 : type) {
            builder.append(s1);
        }
        String str = builder.toString();

        StringBuilder builder1 = new StringBuilder();
        for(String s1 : status) {
            builder1.append(s1);
        }
        String str1 = builder1.toString();
        dataBack = "delaySold="+sDelay+";"+"type="+ str+";"+
                "status="+str1+";"+
                "priceMin="+propertyPriceMin+";"+"priceMax="+propertyPriceMax+";"+
                "surfaceMin="+propertySurfaceMin+";"+"surfaceMax="+propertySurfaceMax+";"+
                "roomMin="+propertyRoomMin+";"+"roomMax="+propertyRoomMax;

        // Put the String to pass back into an Intent and close this activity
        Intent intent = new Intent();
        intent.putExtra("searchData", dataBack);
        setResult(RESULT_OK, intent);
        finish();

    }
    private void setOnClickListenerDateCreation() {
        findViewById(R.id.calendarSold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PropertySearchActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateCreationSet: yyyy-mm-dd: " + year + "-" + month + "-" + day);
                String date = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                delaySold.setText(date);
            }
        };
    }
}
