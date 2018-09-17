package com.kos.work.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {
    public static final String TYPE_KEY = "type";
    private EditText name;
    private EditText price;
    private Button addBtn;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.add_item_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        addBtn = findViewById(R.id.add_Button);
        addBtn.setEnabled(false);

        type = getIntent().getStringExtra(TYPE_KEY);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = name.getText().toString();
                String priceValue = price.getText().toString();

                Item item = new Item(nameValue, priceValue, type);

                Intent intent = new Intent();
                intent.putExtra("item", item);
                intent.putExtra("name", nameValue);
                intent.putExtra("price", priceValue);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

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
    }
}
