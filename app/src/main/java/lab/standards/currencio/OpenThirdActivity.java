package lab.standards.currencio;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class OpenThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_third);
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

        TextView textView1 = (TextView) findViewById(R.id.text_disclaimer);
        TextView textView2 = (TextView) findViewById(R.id.text_plans);
        textView1.setText(Html.fromHtml(getString(R.string.disclaimer)));
        textView2.setText(Html.fromHtml(getString(R.string.plans)));

    }

}
