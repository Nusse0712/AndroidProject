package com.cornez.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends Activity {
    private Click click;
    private Button click1Btn;
    private Button click2Btn;
    private Button passive1;
    private Button passive2;
    private Button prog1;
    private Button prog2;
    private Button homeBtn;
    private TextView byteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        click = new Click();
        click = (Click) getIntent().getSerializableExtra("serializable");
        init();
        initAnimation();
        setOnclickListeners();
    }

    private void init(){
        click1Btn = (Button) findViewById(R.id.button);
        click2Btn = (Button) findViewById(R.id.button2);
        passive1 = (Button) findViewById(R.id.button3);
        passive2 = (Button) findViewById(R.id.button4);
        prog1 = (Button) findViewById(R.id.button5);
        prog2 = (Button) findViewById(R.id.button6);
        homeBtn = (Button) findViewById(R.id.HomeBtn);
        byteView = (TextView) findViewById(R.id.numBytes);
        click1Btn.setText(click.getPerClickUpgrade1Cost().toString());
        click2Btn.setText(click.getPerClickUpgrade2Cost().toString());
        if(click.getIsPassiveOn()){
            passive1.setTextColor(getApplication().getResources().getColor(R.color.black));
            passive1.setText(R.string.SOLD);
            passive1.setBackgroundResource(R.drawable.button_disabled);
            passive1.setEnabled(false);
        }else {
            passive1.setText(click.getTurnOnPassiveCost().toString());
        }
        if(click.getProgBarStatus()){
            prog1.setTextColor(getApplication().getResources().getColor(R.color.black));
            prog1.setText(R.string.SOLD);
            prog1.setBackgroundResource(R.drawable.button_disabled);
            prog1.setEnabled(false);
        }else {
            prog1.setText(click.getProgBarCost().toString());
        }
        passive2.setText(click.getPerPassiveCost().toString());

        prog2.setText(click.getPerProgBarCost().toString());
        byteView.setText("Number of Bytes :"+click.getTotal().toString());



    }
    private void initAnimation(){
        LinearLayout linearLayout =(LinearLayout) findViewById(R.id.shopLayout);
        AnimationDrawable animationDrawable =(AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

    private void setOnclickListeners(){
        click1Btn.setOnClickListener(buyClickOne);
        click2Btn.setOnClickListener(buyClickTwo);
        passive1.setOnClickListener(UnlockPassive);
        passive2.setOnClickListener(buyPassiveTwo);
        prog1.setOnClickListener(UnlockProgBar);
        prog2.setOnClickListener(ProgbarUpgrade);
        homeBtn.setOnClickListener(backtoHome);
    }
    @Override
    protected void onStart(){
        super.onStart();
        overridePendingTransition(R.anim.main_in, R.anim.shop_out);
        click1Btn.setText(click.getPerClickUpgrade1Cost().toString());
        click2Btn.setText(click.getPerClickUpgrade2Cost().toString());
        if(click.getIsPassiveOn()){
            passive1.setTextColor(getApplication().getResources().getColor(R.color.black));
            passive1.setText(R.string.SOLD);
            passive1.setBackgroundResource(R.drawable.button_disabled);
            passive1.setEnabled(false);
        }else {
            passive1.setText(click.getTurnOnPassiveCost().toString());
        }

        if(click.getProgBarStatus()){
            prog1.setTextColor(getApplication().getResources().getColor(R.color.black));
            prog1.setText(R.string.SOLD);
            prog1.setBackgroundResource(R.drawable.button_disabled);
            prog1.setEnabled(false);
        }else {
            prog1.setText(click.getProgBarCost().toString());
        }
        passive2.setText(click.getPerPassiveCost().toString());

        prog2.setText(click.getPerProgBarCost().toString());
        byteView.setText("Number of Bytes :"+click.getTotal().toString());

    }
    private View.OnClickListener buyClickOne = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getPerClickUpgrade1Cost();
            if(click.checkTotal(price)==true){
                click.decreaseTotal(price);
                click.upgrade1PerClick();
                click.increaseUpgrade1Cost();
                click1Btn.setText(click.getPerClickUpgrade1Cost().toString());
                byteView.setText("Number of Bytes :"+click.getTotal().toString());

            }else{
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }

        }
    };
    private View.OnClickListener buyClickTwo = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getPerClickUpgrade2Cost();
            if(click.checkTotal(price)==true){
                click.decreaseTotal(price);
                click.upgrade2PerClick();
                click.increaseUpgrade2Cost();
                click2Btn.setText(click.getPerClickUpgrade2Cost().toString());
                byteView.setText("Number of Bytes :"+click.getTotal().toString());

            }else{
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    private View.OnClickListener UnlockPassive = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getTurnOnPassiveCost();
            if(click.checkTotal(price)==true){
                click.decreaseTotal(price);
                click.setIsPassiveOn(true);
                click.setPerPassive(1);
                passive1.setEnabled(false);
                passive1.setBackgroundResource(R.drawable.button_disabled);
                passive1.setTextColor(getApplication().getResources().getColor(R.color.black));
                passive1.setText(R.string.SOLD);
                byteView.setText("Number of Bytes :"+click.getTotal().toString());

            }else{
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    private View.OnClickListener buyPassiveTwo = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getPerPassiveCost();
            if(click.checkTotal(price)==true){
                click.decreaseTotal(price);
                click.upgradePassiveClick();
                click.increaseUpgradePassiveCost();
                passive2.setText(click.getPerPassiveCost().toString());
                byteView.setText("Number of Bytes :"+click.getTotal().toString());



            }else{
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    private View.OnClickListener UnlockProgBar = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getProgBarCost();
            if(click.checkTotal(price)==true){
                click.decreaseTotal(price);
                click.setProgBar(true);
                prog1.setEnabled(false);
                prog1.setBackgroundResource(R.drawable.button_disabled);
                prog1.setTextColor(getApplication().getResources().getColor(R.color.black));
                prog1.setText(R.string.SOLD);
                byteView.setText("Number of Bytes : "+click.getTotal().toString());


            }else{
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    private View.OnClickListener ProgbarUpgrade = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getPerProgBarCost();
            if(click.checkTotal(price)==true){
                click.decreaseTotal(price);
                click.upgradeProgressBar();
                click.increaseProgressBarCost();
                prog2.setText(click.getPerProgBarCost().toString());
                byteView.setText("Number of Bytes :"+click.getTotal().toString());


            }else{
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    private View.OnClickListener backtoHome = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent returnIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("result",click);
            returnIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    };



}
