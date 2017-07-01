package com.harpreetdev.demoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.harpreetdev.demoapp.adapters.RecyclerViewAdapter;
import com.harpreetdev.demoapp.model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    EditText editTexttSearch;
    Button buttonSearch;
    String searchTerm;
    private List<ListItem> listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTexttSearch = (EditText) findViewById(R.id.edittext_search);
        buttonSearch = (Button) findViewById(R.id.button_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_tracks);
        listitems = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideVirtualKeyBoard();

                searchTerm = editTexttSearch.getText().toString().trim();
                if(TextUtils.isEmpty(searchTerm))
                {
                    Toast.makeText(getApplicationContext(),"please enter a valid artist name",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                            .getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();
                    if( ! isConnected)
                    {
                        Toast.makeText(getApplicationContext(),"No Internet Please check your connection",Toast.LENGTH_SHORT).show();

                    }else loadData();


                }
            }

            private void hideVirtualKeyBoard() {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

            }

            private void loadData() {



                Uri builtUri = Uri.parse(Data.BASEURL).buildUpon()
                        .appendQueryParameter("term",searchTerm)
                        .build();

                try {
                    URL url = new URL(builtUri.toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                            url.toString(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Data.RESPONSE = response;
                                    listitems.clear();
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        int result = obj.getInt("resultCount");
                                        if(result == 0)
                                        {
                                            Toast.makeText(getApplicationContext(),"No results Found",Toast.LENGTH_SHORT).show();
                                        }else
                                            {
                                                JSONArray jsonarray = obj.getJSONArray("results");
                                                for(int i = 0;i < result; i++)
                                            {
                                                JSONObject songobj = jsonarray.getJSONObject(i);
                                                ListItem listItem = new ListItem(
                                                        songobj.getString("trackName")
                                                        ,songobj.getString("artistName")
                                                        ,songobj.getString("primaryGenreName")
                                                        ,songobj.getString("trackPrice")
                                                        ,songobj.getString("trackTimeMillis")
                                                        ,songobj.getString("artworkUrl100")
                                                );
                                                listitems.add(listItem);
                                            }
                                            adapter = new RecyclerViewAdapter(listitems,getApplicationContext());
                                            recyclerView.setAdapter(adapter);
                                        }
                                        } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(),"Data Missing,check Log",Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }


                                    Log.d("Response------>>>>",response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }





            }
        });

    }
}
