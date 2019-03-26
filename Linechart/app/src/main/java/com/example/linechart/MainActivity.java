package com.example.linechart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LineChart lineChart;
    private TextView mtextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart = (LineChart)findViewById(R.id.mlinechart);
        mtextView = (TextView)findViewById(R.id.content);
        initLineChart();

    }


    private void initLineChart(){

        Description description = new Description();

        description.setText("give a mark");

        description.setTextSize(16);

        lineChart.setDescription(description);

        String[] xData = {"1", "2", "3", "4", "5"};//预设的x轴的数据

        String[] yData = {"80", "85", "80","90", "95"};//预设的y轴的数据

        LineData lineData = getData(xData, yData);

        lineChart.setData(lineData);

        lineChart.setAutoScaleMinMaxEnabled(false);

        lineChart.setBorderWidth(1f);//设置边框的宽度（粗细）

        lineChart.setDrawBorders(true);//显示图形的边框（边界）

        lineChart.setDragXEnabled(true);//在放大的情况下，能否拖动x轴

        lineChart.setDragYEnabled(true);

        lineChart.setTouchEnabled(true);//设置为false的话，界面就像是一个图片

        lineChart.setBackgroundColor(Color.parseColor("#d1d1d1"));

        lineChart.setDrawMarkers(true);//设置是否显示

        lineChart.setMarker(new IMarker() {//设置imarker可以设置点击数据的时候出现的图形。

            @Override

            public MPPointF getOffset() {

                return null;

            }



            @Override

            public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {

                return null;

            }



            @Override

            public void refreshContent(Entry e, Highlight highlight) {



            }



            @Override

            public void draw(Canvas canvas, float posX, float posY) {

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

                paint.setColor(Color.GREEN);

                paint.setTextSize(22f);

                canvas.drawText("here", posX, posY, paint);

            }

        });



        lineChart.animateX(500);

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //得到包含此柱状图的 数据集
                LineDataSet dataSets = (LineDataSet) lineChart.getLineData().getDataSetForEntry(e);
                mtextView.setText("被点击的柱状图名称：\n" + dataSets.getLabel() + "X轴：" + (int) e.getX() + "    Y轴" + e.getY());

                StringBuffer allBarChart = new StringBuffer();
                allBarChart.append("所有柱状图：\n");
                for (ILineDataSet dataSet : lineChart.getLineData().getDataSets()) {
                    Entry entry = dataSet.getEntryForIndex((int) e.getX());
                    allBarChart.append(dataSet.getLabel())
                            .append("X轴：")
                            .append((int) entry.getX())
                            .append("    Y轴")
                            .append(entry.getY())
                            .append("\n");
                }
//                dataSet2TextView.setText(allBarChart.toString());
            }

            @Override
            public void onNothingSelected() {
            }
        });

    }



    private LineData getData(String[] xd, String[] yd){

        //节点

        ArrayList<Entry> nodeData = new ArrayList<>();

        for (int i = 0; i < xd.length; i ++){

            nodeData.add(new Entry(Float.parseFloat(xd[i]), Float.parseFloat(yd[i])));

        }

        LineDataSet lineDataSet = new LineDataSet(nodeData, "Score for fisrt five time");

        lineDataSet.setDrawFilled(true);//就是折线图下面是否有阴影填充，这样看起来就像是起伏的山脉

        lineDataSet.setFillColor(Color.GREEN);

        lineDataSet.setDrawCircles(true);//数据是否用圆圈显示

        lineDataSet.setCircleColor(Color.BLACK);//显示数据的圆的颜色

        lineDataSet.setCircleRadius(4f);//显示数据的圆的半径

        lineDataSet.setCircleColors(Color.BLACK, Color.GRAY, Color.BLUE, Color.GREEN, Color.RED);//显示的几个数据的园的颜色

        lineDataSet.setDrawCircleHole(true);//数据是否用环形圆圈（同心圆）显示

        lineDataSet.setCircleHoleColor(Color.YELLOW);//同心圆内圆的颜色，即圆孔的颜色

        lineDataSet.setCircleHoleRadius(2f);//内圆的半径

        lineDataSet.setColor(Color.GRAY);//折线的颜色，以及label前面图形的颜色

        lineDataSet.setValueTextSize(10f);//数据的字体大小

        lineDataSet.setHighlightEnabled(true);//设置是否显示十字架的凸显样式

        lineDataSet.setHighLightColor(Color.BLACK);//设置图形样式的颜色

        lineDataSet.setDrawHorizontalHighlightIndicator(false);//设置凸显样式水平图形显隐

        lineDataSet.setDrawVerticalHighlightIndicator(true);//设置凸显样式垂直图形显隐

        lineDataSet.setHighlightLineWidth(1f);//点击数据会出现十字架形状的定位显示，设置该十字架的宽度

        lineDataSet.setLineWidth(2f);//设置折线的宽度

        lineDataSet.setFormSize(10f);//label前面的图形的大小

        lineDataSet.setForm(Legend.LegendForm.CIRCLE);//设置图例的图形的形状

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置折线的形状：直线、梯形、贝塞尔曲线

        lineDataSet.setCubicIntensity(0.5f);//修改CUBIC_BEZIER模式下面的曲线的一个参数，会让曲线变的很奇怪

        return new LineData(lineDataSet);

    }

}
