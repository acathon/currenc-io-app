package lab.standards.currencio;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Currency extends ArrayAdapter<String> {

    int[] flags;
    String[] countries;
    Context context;

    public Currency(Context context, String[] countries, int[] flags) {
        super(context, R.layout.spinner_currencies, countries);

        this.context = context;
        this.countries = countries;
        this.flags = flags;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.spinner_currencies, null);

        TextView currency = (TextView) view.findViewById(R.id.country_name);
        ImageView flag = (ImageView) view.findViewById(R.id.flag);

        currency.setText(countries[position]);
        flag.setImageResource(flags[position]);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.spinner_currencies, null);
        TextView currency = (TextView) view.findViewById(R.id.country_name);
        ImageView flag = (ImageView) view.findViewById(R.id.flag);

        currency.setText(countries[position]);
        flag.setImageResource(flags[position]);

        return view;
    }
}


