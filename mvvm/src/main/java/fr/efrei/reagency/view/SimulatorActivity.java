package fr.efrei.reagency.view;

import androidx.appcompat.app.AppCompatActivity;
import fr.efrei.reagency.R;
//import fr.efrei.reagency.viewmodel.SimulatorActivityViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class SimulatorActivity extends AppCompatActivity {

    private EditText propertyPrice;
    private EditText financialContribution;
    private EditText loanRate;
    private EditText loanDuration;

    private Button button;

    private TextView monthlyPayment;
    private TextView totalPayments;
    private TextView creditCost;

    //private SimulatorActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        propertyPrice = findViewById(R.id.propertyPrice);
        financialContribution = findViewById(R.id.financialContribution);
        loanRate = findViewById(R.id.loanRate);
        loanDuration = findViewById(R.id.loanDuration);

        button = findViewById(R.id.button);

        monthlyPayment = findViewById(R.id.monthlyPayment);
        totalPayments = findViewById(R.id.totalPayments);
        creditCost = findViewById(R.id.creditCost);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLoan();
            }
        });
    }

    public void calculateLoan(){
        String et1, et2, et3, et4;

        et1 = propertyPrice.getText().toString();
        et2 = financialContribution.getText().toString();
        et3 = loanRate.getText().toString();
        et4 = loanDuration.getText().toString();

        //check if all fields are filled
        if (et1.matches("") || et2.matches("") || et3.matches("") || et4.matches("")) {
            Toast.makeText(this, "You must complete all fields !", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            double _propertyPrice = Integer.parseInt(propertyPrice.getText().toString());
            double _financialContribution = Integer.parseInt(financialContribution.getText().toString());

            double amountToRepay = _propertyPrice - _financialContribution;
            double _loanRate = Double.parseDouble(loanRate.getText().toString());
            double _loanDuration = Integer.parseInt(loanDuration.getText().toString());

            double monthlyPaymentResult = (amountToRepay * _loanRate / 1200) / (1 - Math.pow(1 + _loanRate / 1200, -1 * _loanDuration));
            double totalPaymentResult = monthlyPaymentResult * _loanDuration;
            double creditCostResult = totalPaymentResult - amountToRepay;

            monthlyPayment.setText(new DecimalFormat("##.00").format(monthlyPaymentResult));
            totalPayments.setText(new DecimalFormat("##.00").format(totalPaymentResult));
            creditCost.setText(new DecimalFormat("##.00").format(creditCostResult));
        }
    }
}