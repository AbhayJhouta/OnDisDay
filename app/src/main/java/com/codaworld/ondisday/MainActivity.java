package com.codaworld.ondisday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   RequestQueue rq;
    String dm="0";
    int con=10;
    String qute;
    public void requestplain()
   {    String [] srr=dm.split("-");
        String td=srr[0]+"/"+srr[1];
       String url="http://numbersapi.com/"+td+"/date";


       StringRequest apistr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               TextView gap = findViewById(R.id.textView2);
               gap.setText(response.toString());
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(MainActivity.this,"Cant Get",Toast.LENGTH_SHORT).show();
           }
       });


       rq.add(apistr);

   }
   void getquote()
   {String url="https://pixabay.com/api/?key=20772545-f4797c2d7a586470ac6f45a6d&q=quote&image_type=photo&pretty=true&per_page=199";
   qute=new String();
       JsonObjectRequest jsn1=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try {
                   JSONArray quote = response.getJSONArray("hits");
                   JSONObject w1=quote.getJSONObject(con);

                   qute=w1.getString("largeImageURL");
                   ImageView v=findViewById(R.id.imageView);
                   Glide.with(MainActivity.this).load(qute).into(v);

               }
               catch (JSONException e)
               {Toast.makeText(MainActivity.this,"exception in on response quote",Toast.LENGTH_SHORT).show();

               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(MainActivity.this,"cant get quote",Toast.LENGTH_SHORT).show();
           }
       });
       rq.add(jsn1);
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rq= Volley.newRequestQueue(this);
        Button b1=findViewById(R.id.button2);
        dm = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        requestplain();
        SharedPreferences shd=getSharedPreferences("data",MODE_PRIVATE);
        con=shd.getInt("con",0);
        if(con==175)
        {
            con=0;
        }
        else
            con++;
        SharedPreferences.Editor ed=shd.edit();
        ed.putInt("con",con);
        ed.apply();
        getquote();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dm = new SimpleDateFormat("MM-dd-yyyy").format(new Date());

                requestplain();

            }
        });
    }
}