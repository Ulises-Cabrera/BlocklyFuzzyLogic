package com.example.tutor_inteligente.ia;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tutor_inteligente.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class InstructionDialogFragment extends DialogFragment {

    TextView title;
    public Boolean status = false;
    private LineChart lineChart;
    private LineDataSet lineDataSet;
    private List<Integer> colors = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        return inflater.inflate(R.layout.dialog_instruction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.title);
        MaterialButton button = view.findViewById(R.id.btn_close);
        button.setOnClickListener(view1 -> dismiss());
        status = true;
        lineChart = view.findViewById(R.id.lineChart);
    }

    public void setGraph(String name, List<Double> list) {
        title.setText(name);

        // Creamos un set de datos
        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        lineEntries.add(new Entry((float) 0, 0));

        for (int i = 0; i < list.size(); i++) {
            lineEntries.add(new Entry(list.get(i).floatValue(), list.get(i + 1).floatValue() ));
            i++;
        }

        //lineEntries.add(new Entry((float) 0, 0));
        //lineEntries.add(new Entry((float) 1, 1));
        //lineEntries.add(new Entry((float) 4, 0));

        // Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, name);
        //lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setFillAlpha(128);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.GREEN);

        /*
        // Creamos un set de datos
        ArrayList<Entry> lineEntries2 = new ArrayList<Entry>();
        lineEntries2.add(new Entry((float) 3.5, 0));
        lineEntries2.add(new Entry((float) 5, 1));
        lineEntries2.add(new Entry((float) 6.5, 0));

        // Unimos los datos al data set
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries2, "Grafico de Fuzzylogic");
        //lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet2.setColor(Color.BLUE);
        lineDataSet2.setLineWidth(1f);
        lineDataSet2.setFillAlpha(128);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setFillColor(Color.BLUE);


        // Creamos un set de datos
        ArrayList<Entry> lineEntries3 = new ArrayList<Entry>();
        lineEntries3.add(new Entry((float) 6, 0));
        lineEntries3.add(new Entry((float) 9, 1));
        lineEntries3.add(new Entry((float) 9, 0));

        // Unimos los datos al data set
        LineDataSet lineDataSet3 = new LineDataSet(lineEntries3, "Grafico de Fuzzylogic");
        lineDataSet3.setColor(Color.RED);
        lineDataSet3.setLineWidth(1f);
        lineDataSet3.setFillAlpha(128);
        lineDataSet3.setDrawFilled(true);
        lineDataSet3.setFillColor(Color.RED);
        */

        // Asociamos al gráfico
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);


    }

    public void setGraphs(List<List<String>> vars) {
        title.setText("Grafica de variables");
        List<ILineDataSet> lineData = new ArrayList<>();

        for(int j= 0; j< vars.size(); j++){
            ArrayList<Entry> lineEntries = new ArrayList<Entry>();
            for(int i = 1; i< vars.get(j).size(); i++){
                lineEntries.add(new Entry(Float.parseFloat(vars.get(j).get(i)), Float.parseFloat(vars.get(j).get(i + 1)) ));
                i++;
            }
            // Unimos los datos al data set
            lineDataSet = new LineDataSet(lineEntries, vars.get(j).get(0));
            lineDataSet.setColor(colors.get(j));
            lineDataSet.setLineWidth(1f);
            lineDataSet.setFillAlpha(128);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillColor(colors.get(j));
            lineData.add(lineDataSet);
        }

        // Asociamos al gráfico
        LineData linesData = new LineData(lineData);
        lineChart.setData(linesData);
    }
}
