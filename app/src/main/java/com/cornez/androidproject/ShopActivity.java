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
        //click is a new click object
        click = new Click();
        //the Extra named "serializable" (the click object form the main activity) is assigned to click
        click = (Click) getIntent().getSerializableExtra("serializable");
        //the buttons and the views are initialized
        init();
        //the animating backgrounds are intialized
        initAnimation();
        //the onclick listeners are set
        setOnclickListeners();
    }
    //function that initializes the components of the shop layout
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
        //setting the attributes of the buttons based off the boolean data membes of the click object
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
    //function that initializes the animating backgrounds
    private void initAnimation(){
        LinearLayout linearLayout =(LinearLayout) findViewById(R.id.shopLayout);
        AnimationDrawable animationDrawable =(AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
    //function that sets the onclick listeners
    private void setOnclickListeners(){
        click1Btn.setOnClickListener(buyClickOne);
        click2Btn.setOnClickListener(buyClickTwo);
        passive1.setOnClickListener(UnlockPassive);
        passive2.setOnClickListener(buyPassiveTwo);
        prog1.setOnClickListener(UnlockProgBar);
        prog2.setOnClickListener(ProgbarUpgrade);
        homeBtn.setOnClickListener(backtoHome);
    }
    //onstart function that updates the button text for the prices of the different upgrades when the activity is started
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
    //onclick listener for the click1 button
    private View.OnClickListener buyClickOne = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getPerClickUpgrade1Cost();
            //checks the price with the total amount
            if(click.checkTotal(price)==true){
                //if it is true, then the total is decreased
                click.decreaseTotal(price);
                //the click is upgraded
                click.upgrade1PerClick();
                //the cost of the click is increased
                click.increaseUpgrade1Cost();
                //the button text is now set to the new price of the upgrade
                click1Btn.setText(click.getPerClickUpgrade1Cost().toString());
                //setting the text of byteview to the new total
                byteView.setText("Number of Bytes :"+click.getTotal().toString());

            }else{
                //if the user doesnt have the bytes then it prints a toast message
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }

        }
    };
    private View.OnClickListener buyClickTwo = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getPerClickUpgrade2Cost();
            //checks the price with the total amount
            if(click.checkTotal(price)==true){
                //if it is true, then the total is decreased
                click.decreaseTotal(price);
                //the click is upgraded
                click.upgrade2PerClick();
                //the cost of the click is increased
                click.increaseUpgrade2Cost();
                //the button text is now set to the new price of the upgrade
                click2Btn.setText(click.getPerClickUpgrade2Cost().toString());
                //setting the text of byteview to the new total
                byteView.setText("Number of Bytes :"+click.getTotal().toString());

            }else{
                //if the user doesnt have the bytes then it prints a toast message
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    private View.OnClickListener UnlockPassive = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getTurnOnPassiveCost();
            //checks the price with the total amount
            if(click.checkTotal(price)==true){
                //if it is true, then the total is decreased
                click.decreaseTotal(price);
                //set passive on is set to true
                click.setIsPassiveOn(true);
                click.setPerPassive(1);
                //the button becomes disabled because it is a one time purchase
                passive1.setEnabled(false);
                //setting the background to button_disabled
                passive1.setBackgroundResource(R.drawable.button_disabled);
                passive1.setTextColor(getApplication().getResources().getColor(R.color.black));
                passive1.setText(R.string.SOLD);
                //setting the text of the byteview to the new total
                byteView.setText("Number of Bytes :"+click.getTotal().toString());

            }else{
                //if the user doesnt have the bytes then it prints a toast message
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    private View.OnClickListener buyPassiveTwo = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getPerPassiveCost();
            //checks the price with the total amount
            if(click.checkTotal(price)==true){
                //if it is true, then the total is decreased
                click.decreaseTotal(price);
                //the passive click is upgraded
                click.upgradePassiveClick();
                //the cost of the upgrade is increased
                click.increaseUpgradePassiveCost();
                //the text is set to the new cost of the upgrade
                passive2.setText(click.getPerPassiveCost().toString());
                //the byte view is set to the new total
                byteView.setText("Number of Bytes :"+click.getTotal().toString());



            }else{
                //if the user doesnt have the bytes then it prints a toast message
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    //onclick listener to unlock the progress bar
    private View.OnClickListener UnlockProgBar = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getProgBarCost();
            //checks the price of the current total and the price of the upgrade
            if(click.checkTotal(price)==true){
                //if they can buy it then the total is decreased by the price
                click.decreaseTotal(price);
                //set progress bar is now set to true
                click.setProgBar(true);
                //the button becomes disabled because it is a one time purchase
                prog1.setEnabled(false);
                //sets the background to the button_disabled background
                prog1.setBackgroundResource(R.drawable.button_disabled);
                prog1.setTextColor(getApplication().getResources().getColor(R.color.black));
                prog1.setText(R.string.SOLD);
                //sets the text of the byteview with the new total
                byteView.setText("Number of Bytes : "+click.getTotal().toString());


            }else{
                //if the user doesnt have the bytes then it prints a toast message
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    //function for the progress rate upgrade
    private View.OnClickListener ProgbarUpgrade = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int price = click.getPerProgBarCost();
            //checks the price of the current total and the price of the upgrade
            if(click.checkTotal(price)==true){
                //if they can buy it then the total is decreased by the price
                click.decreaseTotal(price);
                //the progress bar is upgraded
                click.upgradeProgressBar();
                //the price of the upgrade now increases
                click.increaseProgressBarCost();
                //setting the text of the button to the new price
                prog2.setText(click.getPerProgBarCost().toString());
                //setting the byteView to the new total
                byteView.setText("Number of Bytes :"+click.getTotal().toString());


            }else{
                //if the user doesnt have the bytes then it prints a toast message
                Toast.makeText(ShopActivity.this, "Insufficient Bytes",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };
    //onclick listener for the home button
    private View.OnClickListener backtoHome = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //creates a new intent
            Intent returnIntent = new Intent();
            Bundle bundle = new Bundle();
            //puts the current click object in the bundle
            bundle.putSerializable("result",click);
            returnIntent.putExtras(bundle);
            //sends the intent to the main activity with the result
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    };



}
