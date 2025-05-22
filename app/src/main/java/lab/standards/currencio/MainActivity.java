package lab.standards.currencio;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_CONVERT = "lab.standards.currencio.EXTRA_CONVERT";
    public static final String EXTRA_BASE = "lab.standards.currencio.EXTRA_BASE";

    @BindView(R.id.btn_One)
    Button btn_1;
    @BindView(R.id.btn_Two)
    Button btn_2;
    @BindView(R.id.btn_Three)
    Button btn_3;
    @BindView(R.id.btn_Four)
    Button btn_4;
    @BindView(R.id.btn_Five)
    Button btn_5;
    @BindView(R.id.btn_Six)
    Button btn_6;
    @BindView(R.id.btn_Seven)
    Button btn_7;
    @BindView(R.id.btn_Eight)
    Button btn_8;
    @BindView(R.id.btn_Nine)
    Button btn_9;
    @BindView(R.id.btn_Zero)
    Button btn_0;
    @BindView(R.id.btn_Decimal)
    Button btn_;
    @BindView(R.id.btn_ClearLastDigit)
    ImageButton btn_drop;
    @BindView(R.id.btn_Clear)
    ImageButton btn_cls;
    @BindView(R.id.textDisplay)
    TextView input;
    @BindView(R.id.Currency)
    Spinner spinner;
    @BindView(R.id.btn_Convert)
    Button convert;


    private String[] currency = {"Tunisian Dinar", "European Union Euro", "Great Britain Pound", "United States Dollar"};
    private String[] coinbase = {"TND", "EUR", "GBP", "USD"};
    private int[] flags = {R.drawable.ic_tn, R.drawable.ic_eu, R.drawable.ic_uk, R.drawable.ic_us};
    private Currency adapter;
    private String coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btn_cls.setVisibility(View.INVISIBLE);

        adapter = new Currency(this, currency, flags);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                coin = coinbase[i];
                Toast.makeText(getApplicationContext(), currency[i], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // :p
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (input.getText().toString().isEmpty()) {
                    btn_cls.setVisibility(View.INVISIBLE);
                } else {
                    btn_cls.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick({R.id.btn_One, R.id.btn_Two, R.id.btn_Three, R.id.btn_Four, R.id.btn_Five, R.id.btn_Six, R.id.btn_Seven, R.id.btn_Eight,
            R.id.btn_Nine, R.id.btn_Zero, R.id.btn_Decimal, R.id.btn_ClearLastDigit, R.id.btn_Clear, R.id.btn_Convert})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_One:
                input.setText(input.getText() + "1");
                break;
            case R.id.btn_Two:
                input.setText(input.getText() + "2");
                break;
            case R.id.btn_Three:
                input.setText(input.getText() + "3");
                break;
            case R.id.btn_Four:
                input.setText(input.getText() + "4");
                break;
            case R.id.btn_Five:
                input.setText(input.getText() + "5");
                break;
            case R.id.btn_Six:
                input.setText(input.getText() + "6");
                break;
            case R.id.btn_Eight:
                input.setText(input.getText() + "8");
                break;
            case R.id.btn_Seven:
                input.setText(input.getText() + "7");
                break;
            case R.id.btn_Nine:
                input.setText(input.getText() + "9");
                break;
            case R.id.btn_Zero:
                input.setText(input.getText() + "0");
                break;
            case R.id.btn_Decimal:
                if (input.getText().toString().isEmpty()) {
                    input.setText(input.getText() + "0.");
                } else {
                    input.setText(input.getText() + ".");
                }
                break;
            case R.id.btn_Clear:
                input.setText("");
                break;
            case R.id.btn_ClearLastDigit:
                if (!input.getText().toString().isEmpty())
                    input.setText(input.getText().toString().substring(0, input.getText().toString().length() - 1));
                break;
            case R.id.btn_Convert:
                if (input.getText().toString().isEmpty()) {
                } else {
                    Intent intent = new Intent(this, SecondActivity.class);
                    String toConvert = input.getText().toString();
                    intent.putExtra(EXTRA_CONVERT, toConvert);
                    intent.putExtra(EXTRA_BASE, coin);
                    startActivity(intent);
                }
                break;
        }
    }
}