package lab.standards.currencio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log; // Added for logging

import androidx.annotation.NonNull; // Added for NonNull annotation

import com.google.android.gms.ads.AdError; // Added for AdError
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback; // Added for FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError; // Added for LoadAdError
// import com.google.android.gms.ads.InterstitialAd; // Old import, will be replaced by new one below
import com.google.android.gms.ads.interstitial.InterstitialAd; // New import for InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback; // Added for InterstitialAdLoadCallback
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity {


    private static final String API_URL = "https://v3.exchangerate-api.com/bulk/a9a65e7e8b3545689fb87cc6/";


    List<ListCurrencies> currenciesList;
    RecyclerView recyclerView;
    private static String currency;
    private static String coin;
    private double USDTND;
    public String TND;
    public String USD;
    public String EURO;
    public String GBP;
    public String CAD;
    public String SAR;
    public String QAR;
    public String JPY;
    public String CNY;
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "SecondActivity"; // Added for logging


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-6759138947156191/6741017148", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        // Show the ad
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(SecondActivity.this);
                        }

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });

        Intent intent = getIntent();
        currency = intent.getStringExtra(MainActivity.EXTRA_CONVERT);
        coin = intent.getStringExtra(MainActivity.EXTRA_BASE);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerCurrency);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currenciesList = new ArrayList<>();

        getExchange();

        //currenciesList.add(new ListCurrencies("Tunisia", "TND", String.valueOf(TND), R.drawable.ic_tn));
        //currenciesList.add(new ListCurrencies("European Union", "EUR", currency, R.drawable.ic_eu));
        //currenciesList.add(new ListCurrencies("United Kingdom", "GBP", currency, R.drawable.ic_uk));
        //currenciesList.add(new ListCurrencies("United States", "USD", currency, R.drawable.ic_us));
        //currenciesList.add(new ListCurrencies("Bitcoin", "BTC", currency, R.drawable.ic_btc));

        //Currencies adapter = new Currencies(this, currenciesList);
        //recyclerView.setAdapter(adapter);
    }


    private void getExchange() {
        new myTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open:
                Intent open = new Intent(getApplicationContext(), OpenThirdActivity.class);
                startActivity(open);
                return true;
            case R.id.action_about:
                Intent about = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(about);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class myTask extends AsyncTask<String, String, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(API_URL + coin);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                JsonParser jsonParser = new JsonParser();
                JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) httpURLConnection.getContent()));
                JsonObject jsonObject = root.getAsJsonObject();
                String strTND = jsonObject.getAsJsonObject("rates").get("TND").toString();
                String strEUR = jsonObject.getAsJsonObject("rates").get("EUR").toString();
                String strGBP = jsonObject.getAsJsonObject("rates").get("GBP").toString();
                String strUSD = jsonObject.getAsJsonObject("rates").get("USD").toString();
                String strCNY = jsonObject.getAsJsonObject("rates").get("CNY").toString();
                String strSAR = jsonObject.getAsJsonObject("rates").get("SAR").toString();
                String strQAR = jsonObject.getAsJsonObject("rates").get("QAR").toString();
                String strCAD = jsonObject.getAsJsonObject("rates").get("CAD").toString();
                String strJPY = jsonObject.getAsJsonObject("rates").get("JPY").toString();
                System.out.println(strTND);
                System.out.println(strEUR);
                System.out.println(strGBP);
                System.out.println(strUSD);
                USDTND = Double.parseDouble(strTND) * Double.parseDouble(currency);
                TND = new DecimalFormat("##.#####").format(USDTND);
                EURO = new DecimalFormat("##.#####").format(Double.parseDouble(strEUR) * Double.parseDouble(currency));
                GBP = new DecimalFormat("##.#####").format(Double.parseDouble(strGBP) * Double.parseDouble(currency));
                USD = new DecimalFormat("##.#####").format(Double.parseDouble(strUSD) * Double.parseDouble(currency));
                SAR = new DecimalFormat("##.#####").format(Double.parseDouble(strSAR) * Double.parseDouble(currency));
                QAR = new DecimalFormat("##.#####").format(Double.parseDouble(strQAR) * Double.parseDouble(currency));
                CAD = new DecimalFormat("##.#####").format(Double.parseDouble(strCAD) * Double.parseDouble(currency));
                JPY = new DecimalFormat("##.#####").format(Double.parseDouble(strJPY) * Double.parseDouble(currency));
                CNY = new DecimalFormat("##.#####").format(Double.parseDouble(strCNY) * Double.parseDouble(currency));
                System.out.println(TND);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currenciesList.add(new ListCurrencies("Tunisia", "TND", TND, R.drawable.ic_tn));
                        currenciesList.add(new ListCurrencies("Canada", "CAD", CAD, R.drawable.ic_ca));
                        currenciesList.add(new ListCurrencies("European Union", "EUR", EURO, R.drawable.ic_eu));
                        currenciesList.add(new ListCurrencies("United Kingdom", "GBP", GBP, R.drawable.ic_uk));
                        currenciesList.add(new ListCurrencies("United States", "USD", USD, R.drawable.ic_us));
                        currenciesList.add(new ListCurrencies("Saudi Arabia", "SAR", SAR, R.drawable.ic_sa));
                        currenciesList.add(new ListCurrencies("Qatar", "QAR", QAR, R.drawable.ic_qa));
                        currenciesList.add(new ListCurrencies("China", "CNY", CNY, R.drawable.ic_ch));
                        currenciesList.add(new ListCurrencies("Japan", "JPY", JPY, R.drawable.ic_ja));

                        Currencies adapter = new Currencies(getApplicationContext(), currenciesList);
                        recyclerView.setAdapter(adapter);
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new String[0];
        }
    }
}
