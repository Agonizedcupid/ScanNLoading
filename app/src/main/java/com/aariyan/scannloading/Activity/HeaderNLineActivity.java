package com.aariyan.scannloading.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aariyan.scannloading.Adapter.HeaderNLineAdapter;
import com.aariyan.scannloading.Database.DatabaseAdapter;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HeaderNLineActivity extends AppCompatActivity {

    private int orderId = 0;
    private String orderNumber, createdBy, orderDate, invoiceNo, address, sName;
    private int orderValue = 0;
    //UI
    private MaterialButton addProductBtn, closeBtn;
    private TextView storeName, delaAddress, orderNo, oId, value, cBy, oDate, iNo;
    private EditText barcodeEditText;
    private MaterialButton submitBtn;

    private int userId = 1;

    private RecyclerView lineRecyclerView;

    private List<LinesModel> linesList = new ArrayList<>();
    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_nline);

        databaseAdapter = new DatabaseAdapter(this);

        if (getIntent() != null) {
            orderId = getIntent().getIntExtra("orderId", 0);
            orderNumber = getIntent().getStringExtra("orderNumber");
            createdBy = getIntent().getStringExtra("createdBy");
            orderDate = getIntent().getStringExtra("orderDate");
            invoiceNo = getIntent().getStringExtra("invoiceNo");
            address = getIntent().getStringExtra("address");
            orderValue = getIntent().getIntExtra("value",0);
            sName = getIntent().getStringExtra("storeName");
        }

        initUI();
    }

    private void initUI() {
        lineRecyclerView = findViewById(R.id.linesRecyclerView);
        lineRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addProductBtn = findViewById(R.id.addProductBtn);
        closeBtn = findViewById(R.id.closeBtn);
        storeName = findViewById(R.id.storeName);
        delaAddress = findViewById(R.id.address);
        orderNo = findViewById(R.id.orderNumber);
        oId = findViewById(R.id.orderId);
        value = findViewById(R.id.orderValue);
        cBy = findViewById(R.id.createdBy);
        oDate = findViewById(R.id.orderDate);
        iNo = findViewById(R.id.invoiceNo);
        barcodeEditText = findViewById(R.id.barcodeEdtText);
        submitBtn = findViewById(R.id.barcodeSubmitBtn);

        //set the intent value:
        oId.setText(String.format("Order Id:      %s", orderId));
        value.setText(String.format("Order Value:      %s", orderValue));
        cBy.setText(String.format("Created By:      %s", createdBy));
        oDate.setText(String.format("Order Date:      %s", orderDate));
        iNo.setText(String.format("Invoice No:      %s", invoiceNo));
        delaAddress.setText(address);
        orderNo.setText(orderNumber);
        storeName.setText(sName);

        loadHeaderFromSQLite();
    }

    private void loadHeaderFromSQLite() {
        linesList.clear();
        linesList = databaseAdapter.getLinesByDateRouteNameOrderTypes(orderId);
        HeaderNLineAdapter adapter = new HeaderNLineAdapter(this, linesList);
        lineRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}