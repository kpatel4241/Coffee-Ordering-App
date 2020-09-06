package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**

 This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        // Getting value from Whipped Cream checkbox.
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        // Getting value from Chocolate checkbox.
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int total_price = calculatePrice(hasWhippedCream , hasChocolate);
        String orderSummary = createOrderSummary(total_price);

        displayMessage(orderSummary);

    }

    public void getEmailOrderSummary(View view)
    {
        // Getting value from Whipped Cream checkbox.
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        // Getting value from Chocolate checkbox.
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int total_price = calculatePrice(hasWhippedCream , hasChocolate);
        String message = createOrderSummary(total_price);

        // Creating the intent for email to provide Order Summary.
        String subject = "Starbucks Coffee Summaary";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public void increment(View view) {
        if(quantity == 100)
        {
            Toast.makeText(this , "You cannot exceed the quantiy by 100!" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity == -1)
        {
            Toast.makeText(this , "You cannot order below 0!" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean hasWhippedCream ,boolean hasChocolate)
    {
        int coffeePrice = 5;
        int whippedCreamPrice = 1;
        int choolatePrice = 2;
        if(hasWhippedCream)
        {
            coffeePrice = (coffeePrice + whippedCreamPrice);
        }
        else if(hasChocolate)
        {
            coffeePrice = (coffeePrice + choolatePrice);
        }
        else if(hasChocolate == true && hasWhippedCream == true)
        {
            coffeePrice = (coffeePrice + whippedCreamPrice + choolatePrice);
        }

        return coffeePrice*quantity;
    }

    public String createOrderSummary(int total_amount)
    {
        // getting Name from Exittext Name field.
        EditText nameEditText = (EditText) findViewById(R.id.name_text);
        String nameText = nameEditText.getText().toString();

        // Getting value from Whipped Cream checkbox.
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        // Getting value from Chocolate checkbox.
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //String customerName = nameText;
        String checkWhippedCream = "\nAdd Whipped Cream? : " + hasWhippedCream;
        String checkChocolate = "\nAdd Whipped Cream? : " + hasChocolate;
        String priceMessage = "\nTotal : $" + total_amount;

        priceMessage = "Name : "+nameText+checkWhippedCream+checkChocolate+"\nQuantity : "+quantity+ priceMessage + "\nThank you for Ordering from Starbucks.";

        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message)
    {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}