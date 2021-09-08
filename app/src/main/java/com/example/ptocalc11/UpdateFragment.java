package com.example.ptocalc11;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.net.ParseException;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptocalc11.databinding.FragmentUpdateBinding;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdateFragment extends Fragment {

    private FragmentUpdateBinding binding;
    DatabaseHelper databaseHelper;
    RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    ArrayList  dates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Add the following lines to create RecyclerView
//        recyclerView = getView().findViewById(R.id.pto_recycler);
////        recyclerView.setHasFixedSize(true);
//        //recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
//
//        //View view = inflater.inflate(R.layout.fragment_update, container, false);
//        //recyclerView = view.findViewById(R.id.pto_recycler );
//
//        ArrayList<RecyclerData> dataList = new ArrayList<>();
//        dataList.add(new RecyclerData("08/31/2021"));
//        dataList.add(new RecyclerData("09/23/2021"));
//
//        adapter = new RecyclerViewAdapter(getContext(), dataList);
//
////        dates = new ArrayList();
////
////        for (int i = 0; i < RecyclerData.length; i++) {
////            images.add(.images);
////            names.add(Data.names);
////        }
//
//        recyclerView.setAdapter(adapter);

        binding = FragmentUpdateBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set default value for input date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // tomorrow
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        df.format("MM/dd/yyyy",calendar.getTime());

        TextView tv = getView().findViewById(R.id.edittext_date_value);
        tv.setText(df.format("MM/dd/yyyy",calendar.getTime()));


        binding.buttonSubmitPto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView etDateVal = getView().findViewById(R.id.edittext_date_value);
                Date test = StringToDate(etDateVal.getText().toString());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(StringToDate(etDateVal.getText().toString()));

                android.text.format.DateFormat df = new android.text.format.DateFormat();
                String dt = df.format("MM/dd/yyyy",calendar.getTime()).toString();
                AddData(dt);

                NavHostFragment.findNavController(UpdateFragment.this)
                        .navigate(R.id.action_UpdateFragment_to_MainFragment);
            }
        });

        //GetExistingPto();
        recyclerView = getView().findViewById(R.id.pto_recycler);

        ArrayList<RecyclerData> dataList = new ArrayList<>();
        dataList.add(new RecyclerData("08/31/2021"));
        dataList.add(new RecyclerData("09/23/2021"));
        dataList.add(new RecyclerData("10/12/2022"));

        adapter = new RecyclerViewAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this.getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void GetExistingPto(){

        recyclerView = getView().findViewById(R.id.pto_recycler);

        ArrayList<RecyclerData> dataList = new ArrayList<>();
        dataList.add(new RecyclerData("08/31/2021"));
        dataList.add(new RecyclerData("09/23/2021"));
        dataList.add(new RecyclerData("10/12/2022"));

        adapter = new RecyclerViewAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);

        int test = adapter.getItemCount();

//        ArrayList<String> dateList = new ArrayList<>();
//        dateList.add("08/29/2021");
//        dateList.add("08/16/2021");

        //databaseHelper = new DatabaseHelper(this.getActivity());

//        Cursor cursor = databaseHelper.getData();
//
//        if (cursor.moveToFirst()) {
//            do {
//                String dt = cursor.getString(cursor.getColumnIndex("ptoDate"));
//                dateList.add(dt);
//
//            } while(cursor.moveToNext());
//        }
//       cursor.close();


//        RecyclerView recyclerView = getView().findViewById(R.id.pto_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        adapter = new MyRecyclerViewAdapter(this.getActivity(), dateList);
//        adapter.setClickListener(getView());


//        ArrayAdapter adapter = new ArrayAdapter<String>(
//                this.getActivity(),
//                R.layout.date_list_item,
//                dateList.toArray(new String[dateList.size()])
//        );

//        RecyclerView.Adapter adapter2 = new ListAdapter() {
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//            }
//        };

        //RecyclerData recyclerData = getView().findViewById(R.id.recycler_view );



//        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.pto_recycler);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
//







    }


    private void AddData(String date) {
        boolean insertData = databaseHelper.addData(date);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
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
}