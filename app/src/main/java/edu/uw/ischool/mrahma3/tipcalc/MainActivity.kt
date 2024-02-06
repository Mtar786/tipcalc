package edu.uw.ischool.mrahma3.tipcalc

import android.icu.text.NumberFormat
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


class MainActivity : AppCompatActivity() {
    val tag: String = "tag"
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tipSpinner = findViewById<Spinner>(R.id.tipSpinner)

        val editText = findViewById<EditText>(R.id.editAmount)
        val tipButton = findViewById<Button>(R.id.tip)

        ArrayAdapter.createFromResource(
            this,
            R.array.tip_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tipSpinner.adapter = adapter
        }

        // enable button when entering amount in editText
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isEditing) {
                    isEditing = true
                    val userInput = validateInput(s.toString())
                    editText.setText(userInput)
                    editText.setSelection(editText.text.length)
                    isEditing = false
                }
                tipButton.isEnabled = s.toString().isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tipButton.setOnClickListener {
            val amount = editText.text.toString().replace("$", "").toDouble()
            Log.i(tag, "this amount:" + amount);
            val tipPercentage = tipSpinner.selectedItem.toString().replace("%", "").toDouble() / 100
            val tipAmount = amount * tipPercentage
            displayTip(tipAmount)
            editText.setText("")
        }
    }

    private fun displayTip(tipAmount: Double) {
        val formattedTip = formatAmount(tipAmount)
        val toastMessage = "Tip Amount: $formattedTip"
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private fun formatAmount(amount: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        return formatter.format(amount)
    }

    private fun validateInput(input: String): String {
        var validatedInput = input.replace(Regex("[^\\d.]"), "") // Remove non-numeric characters except decimal point

        // Ensure proper positioning of decimal point and limit decimal digits to two
        val decimalIndex = validatedInput.indexOf('.')
        if (decimalIndex != -1) {
            if (decimalIndex != validatedInput.length - 1) {
                val decimalPart = validatedInput.substring(decimalIndex + 1)
                if (decimalPart.length > 2) {
                    validatedInput = validatedInput.substring(0, decimalIndex + 3)
                }
            }
            if (decimalIndex == 0) {
                validatedInput = "0$validatedInput"
            }
        }

        // Add dollar sign if not present
        if (!validatedInput.startsWith("$")) {
            validatedInput = "$$validatedInput"
        }

        return validatedInput
    }
}