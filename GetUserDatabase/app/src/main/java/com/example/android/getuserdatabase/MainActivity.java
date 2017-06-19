package com.example.android.getuserdatabase;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.getuserdatabase.Entities.Result;
import com.example.android.getuserdatabase.Entities.User;
import com.example.android.getuserdatabase.Utils.RandomAPI;
import com.example.android.getuserdatabase.Utils.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private static final String RETROFIT_URL = "https://randomuser.me/";

    private Button getUser;
    RetrofitService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUser = (Button) findViewById(R.id.getUserBtn);
        getUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getUserBtn:
                // do network call
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RETROFIT_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(RetrofitService.class);
                retrofit2.Call<RandomAPI> call = service.getRandomUser();
                call.enqueue(new retrofit2.Callback<RandomAPI>() {
                    @Override
                    public void onResponse(retrofit2.Call<RandomAPI> call, retrofit2.Response<RandomAPI> response) {
                        if(response.isSuccessful()){
                            //myList = new ArrayList<User>();
                            String currentName = "";
                            String currentAddress = "";
                            String currentEmail = "";
                            String currentGender = "";
                            String currentPhone = "";
                            String currentCell = "";
                            String currentDOB = "";
                            String currentNat = "";
                            String currentReg = "";
                            String currentImg = "";

                            RandomAPI randomAPI = response.body();

                            for(Result result:randomAPI.getResults()){
                                //Log.d(TAG, "onResponse: Name is " + result.getName());

                                //currentName = result.getName().toString();
                                currentName = result.getName().getTitle() + " " + result.getName().getFirst() + " " + result.getName().getLast();
                                //currentAddress = result.getLocation().toString();
                                currentAddress = result.getLocation().getStreet() + " " + result.getLocation().getCity() + " " + result.getLocation().getState() + " " + result.getLocation().getPostcode();
                                currentEmail = result.getEmail();
                                currentGender = result.getGender();
                                currentPhone = result.getPhone();
                                currentCell = result.getCell();
                                currentDOB = result.getDob();
                                currentNat = result.getNat();
                                currentReg = result.getRegistered();
                                currentImg = result.getPicture().getMedium();
                                User currentUser = new User(currentName,currentAddress,currentEmail, currentGender,currentPhone,currentCell,currentDOB,currentNat,currentReg,currentImg);

                                // add user to the database
                                Log.d(TAG, "onResponse: My user is " + currentName);
                                //loadUser();
                                addUser(currentName,currentAddress,currentEmail,currentImg);
                            }
                        }
                        else{
                            Log.d(TAG, "onResponse: ABANDON THE SHIP!");
                        }
                    }


                    @Override
                    public void onFailure(retrofit2.Call<RandomAPI> call, Throwable t) {

                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });

                break;
            default:
                break;
        }
    }

    private void addUser(String currentName, String currentAddress, String currentEmail, String currentImg) {

        ContentValues value = new ContentValues();
        value.put(UserContract.UserEntry.COLUMN_NAME, currentName);
        value.put(UserContract.UserEntry.COLUMN_ADDRESS, currentAddress);
        value.put(UserContract.UserEntry.COLUMN_EMAIL, currentEmail);
        value.put(UserContract.UserEntry.COLUMN_PICTURE, currentImg);

        Uri userInsertUri = getContentResolver()
                .insert(UserContract.UserEntry.CONTENT_URI, value);
        long userRowId = ContentUris.parseId(userInsertUri);

        if(userRowId > 0) {
            Toast.makeText(this, "Inserted User record.", Toast.LENGTH_SHORT).show();
        }

    }

    private User mapUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.UserEntry._ID)));
        user.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME)));
        user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_ADDRESS)));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_EMAIL)));
        user.setImage(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PICTURE)));
        return user;
    }

    // Will need it in App B
    private void loadUser() {
        Cursor cursor = getContentResolver().query(UserContract.USER_CONTENT_URI, null, null, null, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                User user = mapUser(cursor);
                //movies.add(mapMovie(cursor));
                Toast.makeText(this, user.getName(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, movie.toString(), Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Cursor is null", Toast.LENGTH_SHORT).show();
        }
    }
}
