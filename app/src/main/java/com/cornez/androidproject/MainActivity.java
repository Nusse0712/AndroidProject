package com.cornez.androidproject;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Handler;

import android.widget.Toast;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
private ProgressBar progressBar;
private ImageButton clickerBtn;
private TextView cookieView;
private Button settingsBtn;
private Button shopBtn;
private Click click;
private TextView progView;
private int prog =0;
private Dialog myDialog;
private SeekBar volumeBar, sfxBar;
private AudioManager audioManager0, audioManager1;
private MediaPlayer player; //need to create another player for the SFX sound
private static int currentVol;
private static int currentSFX;
public static int LAUNCH_SHOP = 1;
private MediaPlayer mp;
Handler handler = new Handler();
Runnable runnable;
int delay = 10000;
private File FILE_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);
        init();
        initAnimation();
        setOnclickListeners();
        setUpAudio();
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void setUpAudio(){
        myDialog = new Dialog(this);
        play(); //plays the music


        audioManager0 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager0.setStreamVolume(AudioManager.STREAM_MUSIC,100, 0);

        audioManager1 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager1.setStreamVolume(AudioManager.STREAM_MUSIC,100, 0);


        currentSFX = 100;
        currentVol = 100;
    }


    private void init(){
        clickerBtn = (ImageButton)findViewById(R.id.clicker);
        settingsBtn = (Button) findViewById(R.id.settingsBtn);
        shopBtn = (Button) findViewById(R.id.shopBtn);
        cookieView = (TextView) findViewById(R.id.clickText);
        progressBar = (ProgressBar) findViewById(R.id.progBar);
        click = new Click();
        progView = (TextView) findViewById(R.id.progText);
        player = MediaPlayer.create(this, R.raw.actualmusic);
        FILE_ = new File(getApplicationContext().getFilesDir()+"/data.txt");


    }
    private void initAnimation(){
        LinearLayout linearLayout =(LinearLayout) findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable =(AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
    private void setOnclickListeners(){
        clickerBtn.setOnClickListener(clickBtn);
        settingsBtn.setOnClickListener(settings);
        shopBtn.setOnClickListener(shop);
    }
    public void play(){
        if(player == null){
            player = MediaPlayer.create(this,R.raw.actualmusic); //had the .mp3 saved as 'music'

        }
        if(mp == null){
            mp = MediaPlayer.create(this, R.raw.click);
        }
        player.setLooping(true);
        player.start();
    }
    public static int getCurrentVol() {
        return currentVol;
    }

    public static int getCurrentSFX() {
        return currentSFX;
    }


    //a function that is used for the apply button in the popup
    public void applySettings(View view){
        volumeBar.setProgress(volumeBar.getProgress()); //set the progess of the bars first
        sfxBar.setProgress(sfxBar.getProgress());
        audioManager0.setStreamVolume(AudioManager.STREAM_MUSIC,volumeBar.getProgress(), 0);

        //toast message to indicate it was applied
        Toast.makeText(this, "Applied", Toast.LENGTH_SHORT).show();
        Log.v(" ", "Applied");
    }
    public void closeOnClick(View view){
        //before closing, get the current progress of both bars so when popup is reopened, the bars are at the same as when exited
        currentVol = volumeBar.getProgress();
        currentSFX = sfxBar.getProgress();
        myDialog.hide();
    }

    public void reset(View view){
        click = new Click();
        cookieView.setText(click.getTotal().toString());
        progView.setVisibility(View.VISIBLE);
        prog = 0;
        progressBar.setProgress(prog);
        Toast.makeText(this,"Game Reset" , Toast.LENGTH_SHORT).show();
    }
    public void saveData(String data) throws IOException {
        FileOutputStream fos;

        fos = openFileOutput(FILE_.getName(),MODE_PRIVATE);
        toLog(FILE_.getPath() + FILE_.getName());
        fos.write(data.getBytes());
        toLog(data);
        data = null;

        if(fos != null){
            fos.close();
        }
    }
    public void loadData() throws IOException{
        String NAME_OF_FILE = getFilesDir().getAbsolutePath() + "/data.txt";
        FileInputStream fis = new FileInputStream(new File(NAME_OF_FILE));
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String data = " ";

        try {
            data = br.readLine();
            //processData(data);
            click.setTotal(toInt(data));
            cookieView.setText(data);
            toLog("Data: " + data);
        }catch (IOException e){
            e.printStackTrace();
        }

        br.close();
        fis.close();
    }
    public String toSString(int i){
        String s = Integer.toString(i);
        return s;
    }

    public int toInt(String s){
        int i = Integer.parseInt(s);
        return i;
    }

    public int toInt(StringBuilder sb){
        String s = sb.toString();
        int i = Integer.parseInt(s);
        return i;
    }

    public boolean toBool(StringBuilder sb){
        String s = sb.toString();
        if(s.toLowerCase() == "true")
            return true;
        else
            return false;
    }

    private void toLog(String msg){
        final String TAG = "MainActivity";
        Log.i(TAG,msg + " " + click.getTotal());
    }



    private View.OnClickListener clickBtn = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            mp.start();
            clickerBtn.animate().rotation(clickerBtn.getRotation()-10).start();
            int num = click.getPerClick();
            click.increaseTotal(num);
            if(click.getProgBarStatus()==true) {

                if (prog <= 199) {
                    prog += click.getProgressRate();

                }
                if (prog >= 200) {
                    click.increaseTotal(600);
                    prog = 0;


                }
                progressBar.setProgress(prog);
            }
            cookieView.setText(click.getTotal().toString());
        }
    };
    private View.OnClickListener settings = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            myDialog.setContentView(R.layout.custompopup);
            myDialog.show(); //on click shows the dialog box

            volumeBar = myDialog.findViewById(R.id.musicBar);
            volumeBar.setMax(22);
            volumeBar.setProgress(getCurrentVol());

            sfxBar = myDialog.findViewById(R.id.SFXbar);
            sfxBar.setMax(22);
            sfxBar.setProgress(getCurrentSFX());

        }
    };
    private View.OnClickListener shop = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,ShopActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("serializable",click);
            intent.putExtras(bundle);
            startActivityForResult(intent, LAUNCH_SHOP);

        }
    };

    @Override
    protected void onStop(){
        super.onStop();
        player.pause();
        try {
            saveData(toSString(click.getTotal()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        player.start();
    }
    @Override
    protected void onStart(){
        super.onStart();
        overridePendingTransition(R.anim.shop_in,R.anim.main_out);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SHOP) {
            if(resultCode == Activity.RESULT_OK){
                  click = (Click) data.getSerializableExtra("result");
                  cookieView.setText(click.getTotal().toString());
                  if(click.getProgBarStatus()==true){
                      progView.setVisibility(View.INVISIBLE);
                  }

            }
        }
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                click.increaseTotal(click.getPerPassive());
                cookieView.setText(click.getTotal().toString());
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }




}
