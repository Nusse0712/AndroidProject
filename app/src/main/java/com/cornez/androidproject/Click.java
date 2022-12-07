package com.cornez.androidproject;


import java.io.Serializable;

public class Click implements Serializable {
    private int perClick;
    private int total;
    private Boolean isPassiveOn;
    private Boolean isProgBarOn;
    private int perPassive;
    private int progressRate;


    //cost
    private int perClickUpgrade1Cost;
    private int perClickUpgrade2Cost;
    private int turnOnPassiveCost;
    private int perPassiveCost;
    private int turnOnProgBarCost;
    private int perProgBarCost;


    public Click(){//when storage is learned this will be changed to have the values passed into it
        perClick = 1;
        total = 0;
        isPassiveOn = false;
        isProgBarOn = false;
        perPassive = 1;
        perClickUpgrade1Cost = 50;
        perClickUpgrade2Cost = 200;
        turnOnPassiveCost = 500;
        perPassiveCost = 50;
        turnOnProgBarCost = 600;
        progressRate = 1;
        perProgBarCost = 50;
    }

    //getters
    public int getPerClick(){
        return perClick;
    }

    public Integer getTotal() {
        return total;
    }

    public Boolean getIsPassiveOn(){
        return isPassiveOn;
    }

    public int getPerPassive(){
        return perPassive;
    }

    public int getPerClickUpgrade1Cost(){
        return perClickUpgrade1Cost;
    }

    public int getPerClickUpgrade2Cost(){
        return perClickUpgrade2Cost;
    }

    public int getTurnOnPassiveCost(){
        return turnOnPassiveCost;
    }

    public int getPerPassiveCost(){
        return perPassiveCost;
    }
    public int getProgBarCost(){     return turnOnProgBarCost;}
    public boolean getProgBarStatus(){return isProgBarOn;}
    public int getPerProgBarCost(){return getPerProgBarCost();}
    public int getProgressRate(){
        return progressRate;
    }
    //setters
    public void setPerClick(int perClick){
        this.perClick = perClick;
    }

    public void setTotal(int total){
        this.total = total;
    }

    public void setIsPassiveOn(Boolean isPassiveOn){
        this.isPassiveOn = isPassiveOn;
    }

    public void setPerPassive(int perPassive){
        this.perPassive = perPassive;
    }

    public void setPerClickUpgrade1Cost(int perClickUpgrade1Cost){
        this.perClickUpgrade1Cost = perClickUpgrade1Cost;
    }

    public void setPerClickUpgrade2Cost(int perClickUpgrade2Cost){
        this.perClickUpgrade2Cost = perClickUpgrade2Cost;
    }

    public void setProgBar(boolean isProgBarOn){
        this.isProgBarOn = isProgBarOn;
    }



    public void setPerPassiveCost(int perPassiveCost){
        this.perPassiveCost = perPassiveCost;
    }

    //functions
    public void upgrade1PerClick(){
        perClick += Global.CLICKUPGRADE1;
    }

    public void upgrade2PerClick(){
        perClick += Global.CLICKUPGRADE2;
    }

    public void upgradePassiveClick(){
        perPassive += Global.PASSIVEUPGRADE;
    }

    public void upgradeProgressBar(){progressRate++;}

    public void increaseUpgrade1Cost(){
        perClickUpgrade1Cost *= Global.UPGRADE1COSTMULT;
    }

    public void increaseUpgrade2Cost(){
        perClickUpgrade2Cost *= Global.UPGRADE2COSTMULT;
    }

    public void increaseUpgradePassiveCost(){
        perPassiveCost *= Global.UPGRADEPASSIVECOSTMULT;
    }

    public void increaseProgressBarCost(){perProgBarCost *= Global.UPGRADEPROGBARMULT;}

    public void increaseTotal(int clicks){//clicks can be from user clicks or passive clicks
        total += clicks;
    }

    public void decreaseTotal(int price){
        total -= price;
    }

    public boolean checkTotal(int price){
        if (total > price){
            return true;
        }
        else{
            return false;
        }
    }
}