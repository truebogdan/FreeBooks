package com.ntzk.freebooks;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<String>favorite=new ArrayList<>();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=this.getSharedPreferences("Favorite",MODE_PRIVATE);
        int nightmode=sharedPreferences.getInt("NightMode",0);
        if(nightmode==1)
            setNightMode(false);
        if(nightmode==2)
            setNightMode(true);
        Gson gson=new Gson();
        String favoriteFromSP=sharedPreferences.getString("Favs"," ");
        favorite=gson.fromJson(favoriteFromSP, favorite.getClass());
        if (favorite==null)
            favorite=new ArrayList<>();

    }
    @Override
    protected void onStop() {
        Gson gson=new Gson();
        String favoriteToSP=gson.toJson(favorite);
        SharedPreferences sharedPreferences=this.getSharedPreferences("Favorite",MODE_PRIVATE);
        sharedPreferences.edit().putString("Favs",favoriteToSP).apply();
        sharedPreferences.edit().putInt("NightMode",AppCompatDelegate.getDefaultNightMode()).apply();
        super.onStop();
    }

    public List<String> getFavorite() {
        return favorite;
    }
    public void setNightMode(boolean b) {
        if (b)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    private   int getNightModeFromSharedP()
    {
        SharedPreferences sharedPreferences=this.getSharedPreferences("Favorite",MODE_PRIVATE);
        int nightmode=sharedPreferences.getInt("NightMode",0);
        return nightmode;
    }
    public boolean isNightModeOn(){
        int nightmode=getNightModeFromSharedP();
        if(nightmode==1)
            return false;
        else if(nightmode==2)
            return true;
        else if((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)==Configuration.UI_MODE_NIGHT_YES)
            return true;
        else
            return false;
    }

}