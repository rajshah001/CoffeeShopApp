
package com.example.android.coffeeshop;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.coffeeshop.R;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    int RatePerCup = 5;
    boolean hasWhip;
    boolean haschoco;
    String whipadd;
    String chocadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayMessage(CreateOrderSummery());
    }

    public String CreateOrderSummery(){

        /** Taking all the inputs and storing them to respective variables for further manipulation */

        CheckBox checkWhipped = (CheckBox) findViewById(R.id.whipped_checkbox);
        boolean hasWhipped = checkWhipped.isChecked();
        hasWhip = hasWhipped;

        CheckBox checkchoco = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = checkchoco.isChecked();
        haschoco = hasChocolate;

        EditText nameField = (EditText) findViewById(R.id.name_view);
        String name = nameField.getText().toString();

        EditText contactfield = (EditText) findViewById(R.id.contact_view);
        String contact = contactfield.getText().toString();

        /** Code for adding error message if name field is Empty
         */
        if(TextUtils.isEmpty(name)) {
            nameField.setError("Enter your name here");
            return "";
        }

        /** Code for adding error message if contact field is Empty
         */
        if(TextUtils.isEmpty(contact)) {
            contactfield.setError("Enter your Mobile Number here");
            return "";
        }

        /** Code for converting the true and false values to added and not added*/
        if (hasWhipped) {
            whipadd = "Added";
        } else {
            whipadd = "Not Added";
        }

        if (haschoco) {
            chocadd = "Added";
        } else {
            chocadd = "Not Added";
        }

        /** Creating the String for Order Summary
         */
        String order_summary = "Name : " + name;
        order_summary += "\nContact Number : " + contact;
        order_summary += "\nWhipped Cream : " + whipadd;
        order_summary += "\nChocolate : " + chocadd;
        order_summary += "\nQuantity : " + quantity;
        order_summary += "\nPrice : "+ "\u20B9" + CalculatePrice();
        order_summary += "\n\nThank You!";
        RatePerCup = 5;


        String[] TO = {"rajshah3062001@gmail.com"};

        /** This code takes us to the Email app and show the order summary there */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary for " + name);
        intent.putExtra(Intent.EXTRA_EMAIL, TO);
        intent.putExtra(Intent.EXTRA_TEXT, order_summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        /** This returns the summary to the app */
        return order_summary;
    }


    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You can't have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You can't have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     * It calculates the price of the total bill.
     */
    public int CalculatePrice() {
        if (hasWhip) {
            RatePerCup += 1;
        }

        if (haschoco) {
            RatePerCup += 2;
        }

        return  quantity * RatePerCup;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     * @ param message
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
