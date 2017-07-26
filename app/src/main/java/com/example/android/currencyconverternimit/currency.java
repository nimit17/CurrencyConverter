package com.example.android.currencyconverternimit;

/**
 * Created by Nimit Arora on 11-07-2017.
 */

public class currency {
    private int imageID;
   private String country="india";
   private String currencyCode="INR";
   private String currencyName="Rupee";

  public currency(String country,String currencyCode,String  currencyName,int imageID )
  {
      this.country=country;
      this.currencyCode=currencyCode;
      this.currencyName=currencyName;
      this.imageID=imageID;
  }

    public String getCountry() {
        return country;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public int getImageID() {
        return imageID;
    }
}
