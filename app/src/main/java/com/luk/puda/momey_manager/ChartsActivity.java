package com.luk.puda.momey_manager;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import Adapters.RecordAdapter;
import Fragments.PrefsFragment;
import Managers.DatabaseHelper;
import ModelsForDB.Record;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;


/**
 * Created by Lukas on 09.12.2017.
 */

public class ChartsActivity extends AppCompatActivity {



    RecordAdapter recordAdapter = null;
    ListView listView = null;
    List<Record> records = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charts_activity);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        getFragmentManager().beginTransaction().replace(android.R.id.content, new PlaceholderFragment()).commit();

    }

    /**
     * A fragment containing a pie chart.
     */
    public static class PlaceholderFragment extends Fragment {

        private PieChartView chart;
        private PieChartData data;
        Context con;

        private boolean hasLabels = true;
        private boolean hasLabelsOutside = false;
        private boolean hasCenterCircle = true;
        private boolean hasCenterText1 = true;
        private boolean hasCenterText2 = false;
        private boolean isExploded = false;
        private boolean hasLabelForSelected = false;
        DatabaseHelper db;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.charts_activity, container, false);

            chart = (PieChartView) rootView.findViewById(R.id.chart);
            //chart.setOnValueTouchListener(new ValueTouchListener());

            generateData();

            return rootView;
        }

        private void reset() {
            chart.setCircleFillRatio(1.0f);
            hasLabels = false;
            hasLabelsOutside = false;
            hasCenterCircle = false;
            hasCenterText1 = false;
            hasCenterText2 = false;
            isExploded = false;
            hasLabelForSelected = false;
        }

        @TargetApi(Build.VERSION_CODES.M)
        private void generateData() {
            db = new DatabaseHelper(getContext());

            int food = 0, shopping = 0, home = 0, traffic = 0, car = 0, free_time = 0,pc = 0, investment = 0, other = 0;

            List<SliceValue> values = new ArrayList<SliceValue>();

            List<Record> allRecords = db.getAllRecords();
            for (Record rec : allRecords){
                if (rec.getCategory().equals("Food")){
                    food += rec.getAmount();
                } else if (rec.getCategory().equals("Shopping")){
                    shopping += rec.getAmount();
                } else if (rec.getCategory().equals("Home")){
                    home += rec.getAmount();
                } else if (rec.getCategory().equals("Traffic")){
                    traffic += rec.getAmount();
                } else if (rec.getCategory().equals("Car")){
                    car += rec.getAmount();
                } else if (rec.getCategory().equals("Free time")){
                    free_time += rec.getAmount();
                } else if (rec.getCategory().equals("PC")){
                    pc += rec.getAmount();
                } else if (rec.getCategory().equals("Investment")){
                    investment += rec.getAmount();
                } else if (rec.getCategory().equals("Other")){
                    other += rec.getAmount();
                }

            }
            values.add(new SliceValue(food, getContext().getResources().getColor(R.color.food)));
            values.add(new SliceValue(shopping, getContext().getResources().getColor(R.color.shopping)));
            values.add(new SliceValue(home, getContext().getResources().getColor(R.color.home)));
            values.add(new SliceValue(traffic, getContext().getResources().getColor(R.color.traffic)));
            values.add(new SliceValue(car, getContext().getResources().getColor(R.color.car)));
            values.add(new SliceValue(free_time, getContext().getResources().getColor(R.color.free_time)));
            values.add(new SliceValue(pc, getContext().getResources().getColor(R.color.pc)));
            values.add(new SliceValue(investment, getContext().getResources().getColor(R.color.investment)));
            values.add(new SliceValue(other, getContext().getResources().getColor(R.color.other)));



            data = new PieChartData(values);
            data.setHasLabels(hasLabels);
            data.setHasLabelsOnlyForSelected(hasLabelForSelected);
            data.setHasLabelsOutside(hasLabelsOutside);
            data.setHasCenterCircle(hasCenterCircle);

            if (isExploded) {
                data.setSlicesSpacing(24);
            }

            if (hasCenterText1) {
                data.setCenterText1("Outcome graph");

                data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                        (int) getResources().getDimension(R.dimen.half_border)));
            }

            if (hasCenterText2) {
                data.setCenterText2("Charts (Roboto Italic)");

                data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                        (int) getResources().getDimension(R.dimen.half_border)));
            }

            chart.setPieChartData(data);
        }

        private void explodeChart() {
            isExploded = !isExploded;
            generateData();

        }

        private void toggleLabelsOutside() {
            // has labels have to be true:P
            hasLabelsOutside = !hasLabelsOutside;
            if (hasLabelsOutside) {
                hasLabels = true;
                hasLabelForSelected = false;
                chart.setValueSelectionEnabled(hasLabelForSelected);
            }

            if (hasLabelsOutside) {
                chart.setCircleFillRatio(0.7f);
            } else {
                chart.setCircleFillRatio(1.0f);
            }

            generateData();

        }

        private void toggleLabels() {
            hasLabels = !hasLabels;

            if (hasLabels) {
                hasLabelForSelected = false;
                chart.setValueSelectionEnabled(hasLabelForSelected);

                if (hasLabelsOutside) {
                    chart.setCircleFillRatio(0.7f);
                } else {
                    chart.setCircleFillRatio(1.0f);
                }
            }

            generateData();
        }

        private void toggleLabelForSelected() {
            hasLabelForSelected = !hasLabelForSelected;

            chart.setValueSelectionEnabled(hasLabelForSelected);

            if (hasLabelForSelected) {
                hasLabels = false;
                hasLabelsOutside = false;

                if (hasLabelsOutside) {
                    chart.setCircleFillRatio(0.7f);
                } else {
                    chart.setCircleFillRatio(1.0f);
                }
            }

            generateData();
        }


        private void prepareDataAnimation() {
            for (SliceValue value : data.getValues()) {
                value.setTarget((float) Math.random() * 30 + 15);
            }
        }

        private class ValueTouchListener implements PieChartOnValueSelectListener {

            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
