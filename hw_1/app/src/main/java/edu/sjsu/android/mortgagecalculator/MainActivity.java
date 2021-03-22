package edu.sjsu.android.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Mortgage Calculator Main Activity Class
 * Checks User Inputs and Calculates The Mortgage
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Components
    private TextView amtBorrowed_textView;
    private TextView interestRate_textView;
    private EditText amtBorrowed_editText;
    private TextView mortgageCalculated_textView;

    private SeekBar interestRate_seekBar;

    private RadioGroup rGroup;
    private RadioButton loanTerm15;
    private boolean loanTerm15_Check;
    private RadioButton loanTerm20;
    private RadioButton loanTerm30;

    private Button calculateButton;

    private CheckBox loansAndinsurance_checkBox;

    // Values Of Mortgage
    double Principle;
    double N;
    double J;
    double T;
    double interestRate = 10;

    /**
     *Standard On Create Method,
     *         Initializes UI Components, and sets listeners for all
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ThemeOverlay_AppCompat_Dark);
        setContentView(R.layout.activity_main);
        rGroup = (RadioGroup) findViewById(R.id.loanTerm_radioGroup);
        amtBorrowed_textView = findViewById(R.id.amtBorrowed_textView);
        interestRate_textView = findViewById(R.id.interestRate_textView);
        amtBorrowed_editText = findViewById(R.id.amtBorrowed_editText);
        interestRate_seekBar = findViewById(R.id.interestRate_seekBar);

        interestRate_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            float progressChangedValue = 10;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = (float)progress;
                interestRate = (float) progress;
                interestRate_textView.setText("Interest Rate: " + progressChangedValue + "%");
                hideSoftKeyboard(amtBorrowed_editText);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                hideSoftKeyboard(amtBorrowed_editText);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                interestRate_textView.setText("Interest Rate: " + progressChangedValue + "%");
                Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,
                Toast.LENGTH_SHORT).show();
                hideSoftKeyboard(amtBorrowed_editText);
            }
        });

        loanTerm15 = findViewById(R.id.loanTerm15_radioButton);
        loanTerm20 = findViewById(R.id.loanTerm20_radioButton);
        loanTerm30 = findViewById(R.id.loanTerm30_radioButton);
        setOnClickListener(loanTerm15);
        setOnClickListener(loanTerm20);
        setOnClickListener(loanTerm30);

        calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(this);

        mortgageCalculated_textView = findViewById(R.id.mortgageOutput_textView);

        loansAndinsurance_checkBox = findViewById(R.id.loansAndTaxes_checkBox);
    }


    /**
     * utility listener setter
     */
    private void setOnClickListener(View view){
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                hideSoftKeyboard(findViewById(R.id.main_view));
            }
        });
    }

    /**
     * On Click Method, responds to user actions
     * It Validates User Input and Displays the Mortgage
     * @param view
     */
    @Override
    public void onClick(View view) {
            hideSoftKeyboard(amtBorrowed_editText);

            //Principle Amount
            if(amtBorrowed_editText.getText().toString().equals("")){
                Toast.makeText(this, "Please enter a valid Amount",
                        Toast.LENGTH_LONG).show(); return;
            }
            else {
                try{
                    Principle = Double.parseDouble(amtBorrowed_editText.getText().toString());  //Principle Amount
                }
                catch (Exception e){
                    Toast.makeText(this, "Please enter a valid Amount",
                            Toast.LENGTH_LONG).show(); return;
                }
            }
            //Princle Amount End


            //Monthly Interest Rate
            J = interestRate/1200;

            //Checking the Term and Months of Loan
            if(loanTerm15.isChecked()){
                N = 15*12;
            }
            else if(loanTerm20.isChecked()){
                N = 20*12;
            }
            else if(loanTerm30.isChecked()){
                N = 30*12;
            }
            else {
                Toast.makeText(this, "Please Select The Term For Loan",
                        Toast.LENGTH_LONG).show(); return;
            }
            //Term and Months of Loan Ends

            //Loans And Insurance Check
            if(loansAndinsurance_checkBox.isChecked()){
                T = 0.001 * Principle;
            }
            else{
                T = 0;
            }
            System.out.println("T: " + T);
            //Loans And Insurance Check Ends

            //Calculate
            String calculatedMortgage = Calculate();
            mortgageCalculated_textView.setText("Your Calculated Mortgage Is:\n " + calculatedMortgage);
    }


    /**
     * Method To Calculate the Mortgage
     * @return the calculated Mortgage value
     */
    private String Calculate(){
        double M = 0;
        if(interestRate == 0){
            M = (Principle/N)+T;
        }
        else{
            M = (Principle * (J/(1-(Math.pow(1+J, -N))))) + T;
        }
        String outputRounded = String.format("%.2f", M);
        return outputRounded;
    }

    /**
     * A Utiility method to hide the keyboard when not focused
     */
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * A Utiility method to show the keyboard when not focused
     */
    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(amtBorrowed_editText, 0);
    }

}
