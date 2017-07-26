package com.example.android.currencyconverternimit;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Double> {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    ArrayList<currency> fromCurrencyArray=new ArrayList<>();
    ArrayList<currency> toCurrencyArray=new ArrayList<>();
    static String fromCurrencyCode="USD";
    private static final String stringURL="http://api.fixer.io/latest?base=";
    private Double rate= 1d;
    static String toCurrencyCode="USD";
    private Double result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface type = Typeface.createFromAsset(getAssets(),"font/quicksandbold.otf");
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if(actionBarTitleView != null){
            actionBarTitleView.setTypeface(type);
        }


        Spinner fromSpinner=(Spinner)findViewById(R.id.fromSpinner);
        Spinner toSpinner=(Spinner) findViewById(R.id.toSpinner);
        fromCurrencyArray=getCurrencyArray();
        toCurrencyArray=getCurrencyArray();
        currencyAdapter fromAdapter=new currencyAdapter(this,fromCurrencyArray);
        currencyAdapter toAdapter=new currencyAdapter(this,toCurrencyArray);
        fromAdapter.setDropDownViewResource(R.layout.spinner_layout1);
        toAdapter.setDropDownViewResource(R.layout.spinner_layout1);
        fromSpinner.setAdapter(fromAdapter);
        toSpinner.setAdapter(toAdapter);
        getLoaderManager().initLoader(1,null,MainActivity.this);
        TextView from=(TextView) findViewById(R.id.from);

        from.setTypeface(type);

        TextView from2=(TextView) findViewById(R.id.to);
        from2.setTypeface(type);



        Button button=(Button) findViewById(R.id.button) ;
        button.setTypeface(type);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               currency selectedCurrency=fromCurrencyArray.get(position);
               fromCurrencyCode=selectedCurrency.getCurrencyCode();
                getLoaderManager().restartLoader(1,null,MainActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                   fromCurrencyCode="USD";
            }
        });
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency selectedCurrency=toCurrencyArray.get(position);
                toCurrencyCode=selectedCurrency.getCurrencyCode();
                getLoaderManager().restartLoader(1,null,MainActivity.this);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                toCurrencyCode="USD";
            }
        });
    }

    public ArrayList<currency> getCurrencyArray()
    {
        ArrayList<currency> currencyArray=new ArrayList<>();
        currencyArray.add(new currency("United States","USD","Dollar",R.drawable.unitedstates));
        currencyArray.add(new currency("China","CNY","Yuan",R.drawable.china));
        currencyArray.add(new currency("Japan","JPY","Yen",R.drawable.japan));
        currencyArray.add(new currency("European Union","EUR","Euro",R.drawable.euro));
        currencyArray.add(new currency("United Kingdom","GBP","Pound",R.drawable.unitedkingdom));
        currencyArray.add(new currency("India","INR","Rupee",R.drawable.india));
        currencyArray.add(new currency("Brazil","BRL","Real",R.drawable.brazil));
        currencyArray.add(new currency("Canada","CAD","Dollar",R.drawable.canada));
        currencyArray.add(new currency("Korea (South)","KRW","Won",R.drawable.southkorea));
        currencyArray.add(new currency("Russia","RUB","Ruble",R.drawable.russia));
        currencyArray.add(new currency("Australia","AUD","Dollar",R.drawable.australia));
        currencyArray.add(new currency("Mexico","MXN","Peso",R.drawable.mexico));
        currencyArray.add(new currency("Indonesia","IDR","Rupiah",R.drawable.indonesia));
        currencyArray.add(new currency("Turkey","TRY","Lira",R.drawable.turkey));
        currencyArray.add(new currency("Switzerland","CHF","Franc",R.drawable.switzerland));
        currencyArray.add(new currency("Bulgaria","BGN","Lev",R.drawable.bulgaria));
        currencyArray.add(new currency("Czech Republic","CZK","Koruna",R.drawable.czechrepublic));
        currencyArray.add(new currency("Denmark","DKK","Krone",R.drawable.denmark));
        currencyArray.add(new currency("Hong Kong","HKD","Dollar",R.drawable.hongkong));
        currencyArray.add(new currency("Croatia","HRK","Kuna",R.drawable.croatia));
        currencyArray.add(new currency("Hungary","HUF","Forint",R.drawable.hungary));
        currencyArray.add(new currency("Israel","ILS","Shekel",R.drawable.israel));
        currencyArray.add(new currency("Malaysia","MYR","Ringgit",R.drawable.malaysia));
        currencyArray.add(new currency("Norway","NOK","Krone",R.drawable.norway));
        currencyArray.add(new currency("New Zealand","NZD","Dollar",R.drawable.newzealand));
        currencyArray.add(new currency("Philippines","PHP","Peso",R.drawable.philippines));
        currencyArray.add(new currency("Poland","PLN","Zloty",R.drawable.poland));
        currencyArray.add(new currency("Romania","RON","New Leu",R.drawable.romania));
        currencyArray.add(new currency("Sweden","SEK","Krona",R.drawable.sweden));
        currencyArray.add(new currency("Singapore","SGD","Dollar",R.drawable.singapore));
        currencyArray.add(new currency("Thailand","THB","Baht",R.drawable.thailand));
        currencyArray.add(new currency("South Africa","ZAR","Rand",R.drawable.southafrica));
        return currencyArray;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        Log.i(LOG_TAG, "createUrl: ");
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void convert(View view) {
        Log.i(LOG_TAG, "convert: ");
        if(!isNetworkAvailable()) {

            Log.i(LOG_TAG, "connectionFalse ");
            Toast.makeText(getBaseContext(),"NO INTERNET CONNECTION",Toast.LENGTH_LONG).show();

        }
        getLoaderManager().restartLoader(1,null,MainActivity.this);
    }

    @Override
    public Loader<Double> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader: ");
        return new CurrencyAsyncLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<Double> loader, Double data) {
        Log.i(LOG_TAG, "onLoadFinished: ");
        rate=data;
        Typeface type = Typeface.createFromAsset(getAssets(),"font/quicksandbold.otf");
        Double value;
        EditText fromCurrencyField=(EditText) findViewById(R.id.fromCurrency);
        fromCurrencyField.setTypeface(type);
        String text=fromCurrencyField.getText().toString();
        if(text.trim().length()==0) {
            value = Double.valueOf(1); }
        else
        { value = Double.parseDouble(text); }
        result = rate * value;
        TextView display = (TextView) findViewById(R.id.toCurrency);
        display.setTypeface(type);
        display.setText("" + result);

    }



    @Override
    public void onLoaderReset(Loader<Double> loader) {
        Log.i(LOG_TAG, "onLoaderReset: ");

    }


    private static class CurrencyAsyncLoader extends AsyncTaskLoader<Double>
    {

        public CurrencyAsyncLoader(Context context) {
            super(context);
            Log.i(LOG_TAG, "CurrencyAsyncLoader: ");
        }

        @Override
        public Double loadInBackground() {
            Log.i(LOG_TAG, "loadInBackground: ");
            String Url=stringURL+fromCurrencyCode;
            URL url=createUrl(Url);
            Log.i(LOG_TAG,Url );
            String jsonresponse="";
            try {
                jsonresponse =HttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            double result= 0;
            try {
                result = getExchangeRate(jsonresponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onStartLoading() {
            Log.i(LOG_TAG, "onStartLoading: ");
            forceLoad();
        }
    }

    private static String HttpRequest(URL url) throws IOException {
        Log.i(LOG_TAG, "HttpRequest: ");
        String jsonResponse = "";
        if(url==null)
        { return jsonResponse; }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e(LOG_TAG, "HttpRequest:incorrectResponseCode",e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readStream(InputStream inputStream) throws IOException {
        Log.i(LOG_TAG, "readStream: ");
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static Double getExchangeRate(String Jsonresponse) throws JSONException {
        Log.i(LOG_TAG, "getExchangeRate: ");
        if(fromCurrencyCode==toCurrencyCode)
        {
            Log.i(LOG_TAG, "getExchangeRate:ifCondition ");
            Double ans=Double.valueOf(1);
            return ans;}
        JSONObject root=new JSONObject(Jsonresponse);
        JSONObject rateObject=root.getJSONObject("rates");
        Double rate=rateObject.getDouble(toCurrencyCode);

        return rate;

    }
}
