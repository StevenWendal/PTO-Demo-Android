package com.example.ptocalc11;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.net.ParseException;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import com.example.ptocalc11.databinding.FragmentMainBinding;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonTakePto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                NavHostFragment
                        .findNavController(MainFragment.this)
                        .navigate(R.id.action_MainFragment_to_UpdateFragment);
            }
        });

        LoadCurrentState();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadCurrentState(){

        // load configured values
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String maxHours = prefs.getString("maxHours", "Not Specified");
        String currentHours = prefs.getString("currentHours", "Not Specified");

        TextView tvMax = (TextView) getView().findViewById(R.id.max_hours_value);
        tvMax.setText(maxHours); // String.valueOf(lux));

        TextView tvCurrent = (TextView) getView().findViewById(R.id.current_hours_value);
        tvCurrent.setText(currentHours);

        // get accrual threshold description
        String accrualRate = prefs.getString("accrualRate","Not Specified");
        String[] rateListVals = getResources().getStringArray(R.array.rate_values);
        String[] rateListText = getResources().getStringArray(R.array.rate_entries);
        int idx = Arrays.asList(rateListVals).indexOf(accrualRate);
        String desc = rateListText[idx];
        if (desc.length() > 0) accrualRate += " (" + desc + ")";

        TextView tvRateVal = (TextView) getView().findViewById(R.id.rate_value);
        tvRateVal.setText(accrualRate);

        TextView tvThreshold = (TextView) getView().findViewById(R.id.use_by_value);

        //============================================================================

        // first accrual date of 2021 - 2021.01.02
        // apply accrual value every 14 days
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(2021,0,2); // month starts @ 0;

        Date accrualStart = StringToDate("2021-01-02");
        Date currentDate = Calendar.getInstance().getTime();

        int hoursToGo =  StringToInt(maxHours) - StringToInt(currentHours);
        double rate = StringToDouble(prefs.getString("accrualRate","Not Specified"));

        // lastUpdate = date the current hours value was updated
        // target date = hours to go / accrual rate / 8 + lastUpdate


        // bail if the input values are not set
        if (rate == 0 ) {
            tvThreshold.setText("Not able to calculate");
            return;
        }

        // days to accrue
        int daysToAccrue = (int)(hoursToGo / rate / 8 );
        if (daysToAccrue < 0 ) {
            tvThreshold.setText("Too Late! You're Loosing Time");
            return;
        }

        // periods to accrue - 10 working days in each accrual period;
        // +1 gives the next period
        int accrualPeriodDiff = (int)(daysToAccrue / 10) + 1;

        // get next accrual date

        //extView tvThreshold = (TextView) getView().findViewById(R.id.use_by_value);
        //tvThreshold.setText(accrualStart.toString());

        long milDiff =  currentDate.getTime() - accrualStart.getTime();

        // get number of accrual periods (every 10 working days or 2 weeks)
        int accrualPeriods = (int)(TimeUnit.DAYS.convert(milDiff, TimeUnit.MILLISECONDS) / 14)  + accrualPeriodDiff;

        // get future date: accrual start + accrual days
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(accrualStart);
        calendar.add(Calendar.DAY_OF_YEAR, accrualPeriods * 14);

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        df.format("MM/dd/yyyy",calendar.getTime());
        tvThreshold.setText(df.format("MM/dd/yyyy",calendar.getTime()));
        //tvThreshold.setText(String.valueOf(accrualPeriods));
    }

    private Date StringToDate(String s){

        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH:mm:ss");
            result  = dateFormat.parse(s);
        }

        catch(ParseException | java.text.ParseException e){
            e.printStackTrace();

        }
        return result ;
    }

    private double StringToDouble(String s){
        try {
            return Double.parseDouble(s);
        } catch(NumberFormatException nfe) {
            return 0;
        }
    }

    private int StringToInt(String s){

        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException nfe) {
            return 0;
        }
    }
}