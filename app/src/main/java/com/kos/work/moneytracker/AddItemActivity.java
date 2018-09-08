package com.kos.work.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {
    private EditText name;
    private EditText price;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addBtn = findViewById(R.id.add_Button);
        addBtn.setEnabled(false);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override   // Вот так надо писАть
            public void afterTextChanged(Editable s) {
                if (!name.getText().toString().equals("") & !price.getText().toString().equals("")) {
                    addBtn.setEnabled(true);
                } else addBtn.setEnabled(false);
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override  // Вот так не надо писАть
            public void afterTextChanged(Editable s) {
                if (name.getText().toString().equals("") & price.getText().toString().equals("")) {
                    addBtn.setEnabled(false);
                }
                else {
                    if (name.getText().toString().equals("") | price.getText().toString().equals(""))
                        addBtn.setEnabled(false);
                    else {
                        addBtn.setEnabled(true);
                    }

                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = name.getText().toString();
                String itemPrice = price.getText().toString();
            }
        });
    }
}
