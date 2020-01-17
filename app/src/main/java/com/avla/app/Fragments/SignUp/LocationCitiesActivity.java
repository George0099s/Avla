package com.avla.app.Fragments.SignUp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.avla.app.Adapter.LocationCityAdapter;
import com.avla.app.Constants;
import com.avla.app.Database.AppDatabase;
import com.avla.app.Interface.IServer;
import com.avla.app.Interface.SignUpInterface;
import com.avla.app.Interface.TokenDao;
import com.avla.app.Model.ModelLocation;
import com.avla.app.Model.Payload;
import com.avla.app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationCitiesActivity extends AppCompatActivity  {
    private static final String TAG = "LocationCitiesActivity";
    private LocationCityAdapter locationCityAdapter;
    private RecyclerView locationRecycerView;
    private String countryId, countryName;
    private ArrayList<String> citiesList;
    private Context mContext;
    private SignUpInterface signUpInterface;
    private Button closeBtn;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_cities);
        initViews();
    }



    private void initViews() {
        AppDatabase db = Room.databaseBuilder(this,
                AppDatabase.class, "avlaDB")
                .allowMainThreadQueries()
                .build();
        TokenDao tokenDao = db.tokenDao();

        closeBtn = findViewById(R.id.close_btn);
        mContext = this;
        countryName = getIntent().getStringExtra("country");
        countryId =  getIntent().getStringExtra("id");
        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
        locationRecycerView = findViewById(R.id.location_recycler);

        getAllCities(String.valueOf(tokenDao.getToken().get(0)),countryId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        signUpInterface = (SignUpInterface) getParent();
    }

    private void getAllCities(String token, String countryId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.getavla.com/") // Адрес сервера
                .addConverterFactory(GsonConverterFactory.create()) // говорим ретрофиту что для сериализации необходимо использовать GSON
                .build();

        IServer service = retrofit.create(IServer.class);
        Call<ModelLocation> call = service.getAllCities(countryId, token);
        call.enqueue(new Callback<ModelLocation>() {
            @Override
            public void onResponse(Call<ModelLocation> call, Response<ModelLocation> response) {
                citiesList = new ArrayList<>();
                ModelLocation list = response.body();
                List<Payload> payload = list.getPayload();
                for (int i = 0; i < payload.size(); i++) {
                    citiesList.add(payload.get(i).getName());
                }
                locationRecycerView.setLayoutManager(new LinearLayoutManager(mContext));
                locationCityAdapter = new LocationCityAdapter (citiesList, mContext, sharedPreferences);
                locationRecycerView.setAdapter(locationCityAdapter);
            }

            @Override
            public void onFailure(Call<ModelLocation> call, Throwable t) {
                Log.d(TAG, "onResponse: signUp fail " + t.getMessage());

            }
        });
    }
}