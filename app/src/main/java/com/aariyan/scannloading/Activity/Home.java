package com.aariyan.scannloading.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.scannloading.Adapter.HeaderLinesAdapter;
import com.aariyan.scannloading.Adapter.UserAdapter;
import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Database.SharedPreferences;
import com.aariyan.scannloading.MainActivity;
import com.aariyan.scannloading.Model.HeaderLinesModel;
import com.aariyan.scannloading.Model.OrderModel;
import com.aariyan.scannloading.Model.RouteModel;
import com.aariyan.scannloading.Model.UserModel;
import com.aariyan.scannloading.Network.APIs;
import com.aariyan.scannloading.Network.ApiClient;
import com.aariyan.scannloading.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private String userName = "";
    private int userId = 1;

    private RecyclerView recyclerView;

    private RadioButton loadingBtn, queueBtn;

    private CardView datePicker;
    private TextView dateText;
    private Spinner orderSpinner, routeSpinner;
    private Button getLoadingBtn;

    private RequestQueue requestQueue;

    private ProgressBar topProgressbar;
    private TextView warningMessage;
    private List<OrderModel> orderList = new ArrayList<>();
    private List<RouteModel> routeList = new ArrayList<>();

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    int day, month, year;
    String date = "";

    private static int selectedRoute, selectedOrder;

    private List<HeaderLinesModel> headerLinesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        requestQueue = Volley.newRequestQueue(this);

        if (getIntent() != null) {
            userName = getIntent().getStringExtra("name");
            userId = getIntent().getIntExtra("id", userId);
        }

        setTitle(userName);

        initUI();

        populateSpinner();
    }

    private void populateSpinner() {
        topProgressbar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = new SharedPreferences(Home.this);

        String appendedUrl = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL) + "RouteAndOrdertypes.php";

        JsonObjectRequest array = new JsonObjectRequest(Request.Method.GET, appendedUrl, null,
                this::parseJson,
                e -> {
                    warningMessage.setVisibility(View.VISIBLE);
                    warningMessage.setText("Error: " + e.getMessage());
                    topProgressbar.setVisibility(View.GONE);
                });

        requestQueue.add(array);

    }

    private void parseJson(JSONObject object) {
        try {
            //Taking the root data as JSON Array:
            //JSONArray array = new JSONArray(response.body().string());
            //checking to know, if the data is not available:
            //Clearing the list if any data already in:
            orderList.clear();
            routeList.clear();
            //Making the warning text Disable:
            warningMessage.setVisibility(View.GONE);

            JSONArray routes = object.getJSONArray("routes");
            if (routes.length() > 0) {
                for (int i = 0; i < routes.length(); i++) {
                    JSONObject single = routes.getJSONObject(i);
                    int Routeid = single.getInt("Routeid");
                    String Route = single.getString("Route");
                    int NotInUse = single.getInt("NotInUse");
                    int InActive = single.getInt("InActive");
                    int NewRec = single.getInt("NewRec");
                    int LocationId = single.getInt("LocationId");
                    String Rmessage = single.getString("Rmessage");
                    String MinOrderLevel = single.getString("MinOrderLevel");
                    int DoNotInvoice = single.getInt("DoNotInvoice");

                    RouteModel model = new RouteModel(
                            Routeid, Route, NotInUse, InActive, NewRec, LocationId, Rmessage, MinOrderLevel, DoNotInvoice
                    );
                    routeList.add(model);
                }

                routeSpinnerFunc(routeList);
            }

            JSONArray order = object.getJSONArray("ordertypes");
            if (order.length() > 0) {
                for (int i = 0; i < order.length(); i++) {
                    JSONObject single = order.getJSONObject(i);
                    int OrderTypeId = single.getInt("OrderTypeId");
                    String OrderType = single.getString("OrderType");

                    OrderModel model = new OrderModel(OrderTypeId, OrderType);
                    orderList.add(model);
                }

                orderSpinnerFunc(orderList);
            }

            topProgressbar.setVisibility(View.GONE);
            //If any error happen make it visible and show a warning message:
            warningMessage.setVisibility(View.GONE);
            //warningMessage.setText("No data found!");

        } catch (Exception e) {
            //If any error happen make it visible and show a warning message:
            warningMessage.setVisibility(View.VISIBLE);
            warningMessage.setText("Error: " + e.getMessage());
            topProgressbar.setVisibility(View.GONE);
        }

    }

    private void routeSpinnerFunc(List<RouteModel> routeList) {
        //Spinner items
        ArrayAdapter<RouteModel> dataAdapter = new ArrayAdapter<RouteModel>(Home.this,
                android.R.layout.simple_spinner_item, routeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        routeSpinner.setAdapter(dataAdapter);
        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedRoute = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void orderSpinnerFunc(List<OrderModel> orderList) {
        //Spinner items
        ArrayAdapter<OrderModel> dataAdapter = new ArrayAdapter<OrderModel>(Home.this,
                android.R.layout.simple_spinner_item, orderList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(dataAdapter);
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedOrder = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initUI() {

        topProgressbar = findViewById(R.id.topProgressbar);
        warningMessage = findViewById(R.id.warningMessage);

        recyclerView = findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        datePicker = findViewById(R.id.dateCardView);
        dateText = findViewById(R.id.dateTextView);
        routeSpinner = findViewById(R.id.routeSpinner);
        orderSpinner = findViewById(R.id.orderIdSpinner);
        getLoadingBtn = findViewById(R.id.getLoadingBtn);

        getLoadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPIs();
            }
        });


        loadingBtn = findViewById(R.id.loadingRadioBtn);
        queueBtn = findViewById(R.id.queueRadioBtn);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        loadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        queueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void callAPIs() {

        APIs apIs = ApiClient.getClient().create(APIs.class);
        Call<ResponseBody> call = apIs.getOrderLines(selectedRoute, selectedOrder, date, userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject finalResponse = new JSONObject(response.body().string());
                    JSONArray Headers = finalResponse.getJSONArray("Headers");

                    Toast.makeText(Home.this, ""+response.body().string(), Toast.LENGTH_SHORT).show();
                    Log.d("TEST_RESULT", response.body().string());
                    headerLinesList.clear();
                    if (Headers.length() > 0) {
                        for (int i = 0; i < Headers.length(); i++) {
                            JSONObject single = Headers.getJSONObject(i);
                            String storeName = single.getString("StoreName");
                            int OrderId = single.getInt("OrderId");
                            HeaderLinesModel model = new HeaderLinesModel(storeName, OrderId);
                            headerLinesList.add(model);
                        }

                        HeaderLinesAdapter adapter = new HeaderLinesAdapter(Home.this, headerLinesList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showDate() {

        datePickerDialog = new DatePickerDialog(Home.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                //Month
                int j = i1 + 1;

                //date = i + "-" + j + "-" + i2;
                //date = i2 + "-" + j + "-" + i;
                date = i + "-" + j + "-" + i2;
                //2022-1-15
                dateText.setText(date);

            }
            //}, day, month, year);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        new DatePickerDialog(AddTimeActivity.this, null, calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        datePickerDialog.show();
    }
}