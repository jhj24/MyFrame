package com.zgdj.djframe.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.zgdj.djframe.R;
import com.zgdj.djframe.bean.LineChartBean;
import com.zgdj.djframe.utils.Utils;

import org.xclcharts.chart.LineChart;
import org.xclcharts.chart.LineData;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;

import java.util.LinkedList;
import java.util.List;

/**
 * @author (xcl_168 @ aliyun.com)
 * @ClassName LineChart01View
 * @Description 折线图的例子
 */
public class LineChart01View extends DemoView {

    private String TAG = "LineChart01View";
    private LineChart chart = new LineChart();

    //标签集合
    private LinkedList<String> labels = new LinkedList<>();
    private LinkedList<LineData> chartData = new LinkedList<>();

    private int tickTextSize = Utils.dp2px(getContext(), 12);//XY轴上刻度字体大小
    private int lineColor = ContextCompat.getColor(getContext(), R.color.bids_excellent);//XY 轴线的颜色
    private Paint mPaintTooltips = new Paint(Paint.ANTI_ALIAS_FLAG);


    public LineChart01View(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public LineChart01View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LineChart01View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        chartLabels();
//        chartDataSet();
        chartRender();

        //綁定手势滑动事件
        this.bindTouch(this, chart);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }

    private void chartRender() {
        try {

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int[] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            //限制Tickmarks可滑动偏移范围
//            chart.setXTickMarksOffsetMargin(ltrb[2] - 20.f);
//            chart.setYTickMarksOffsetMargin(ltrb[3] - 20.f);

            chart.disableScale(); // 禁止缩放
            chart.disablePanMode();// 禁用平移模式

            //显示边框
//            chart.showRoundBorder();


            //设定数据源
            chart.setCategories(labels);
            chart.setDataSource(chartData);

            //数据轴最大值
            chart.getDataAxis().setAxisMax(100);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(10);

            //背景网格
//            chart.getPlotGrid().showHorizontalLines();
            //chart.getPlotGrid().showVerticalLines();
//            chart.getPlotGrid().showEvenRowBgColor();
//            chart.getPlotGrid().showOddRowBgColor();

            chart.getPlotGrid().getHorizontalLinePaint().setStrokeWidth(2);
            chart.getPlotGrid().setHorizontalLineStyle(XEnum.LineStyle.DASH);
            chart.getPlotGrid().setVerticalLineStyle(XEnum.LineStyle.DOT);

            chart.getPlotGrid().getHorizontalLinePaint().setColor(Color.RED);
            chart.getPlotGrid().getVerticalLinePaint().setColor(Color.BLUE);

            //隐藏顶部图例
            chart.getPlotLegend().hide();
            // 顶部图例字体大小
            chart.getPlotLegend().getPaint().setTextSize(Utils.dp2px(getContext(), 6));

//            chart.setTitle("折线图(Line Chart)");
//            chart.addSubtitle("(XCL-Charts Demo)");

//            chart.getAxisTitle().setLowerTitle("(年份)");

            //设置底部标字体大小
//            chart.getCategoryAxis().getTickLabelPaint().setTextSize(Utils.dp2px(getContext(), 10));
            //设置左标字体大小
            chart.getDataAxis().getTickLabelPaint().setTextSize(tickTextSize);
            chart.getDataAxis().setTickLabelMargin(Utils.dp2px(getContext(), 4));
            chart.getDataAxis().getAxisPaint().setStrokeWidth(4f);// Y轴线宽度
            chart.getDataAxis().getAxisPaint().setColor(lineColor);//Y轴线颜色
            chart.getDataAxis().getTickMarksPaint().setStrokeWidth(4f);//Y轴上刻度宽度
            chart.getDataAxis().getTickMarksPaint().setColor(lineColor);
            chart.getDataAxis().hideTickMarks();//隐藏刻度
            chart.getDataAxis().setLabelFormatter(value -> {
                // TODO Auto-generated method stub
                String tmp = value.substring(0, value.indexOf(".")) + "%";
                return tmp;
            });

            //设置底部下标字体大小
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(tickTextSize);
            chart.getCategoryAxis().setTickLabelMargin(Utils.dp2px(getContext(), 10));
            chart.getCategoryAxis().getAxisPaint().setStrokeWidth(4f);//X轴线宽度
            chart.getCategoryAxis().getAxisPaint().setColor(lineColor);//X轴线颜色
            chart.getCategoryAxis().getTickMarksPaint().setStrokeWidth(4f);//X轴上刻度宽度
            chart.getCategoryAxis().getTickMarksPaint().setColor(lineColor);
            chart.getCategoryAxis().hideTickMarks(); //隐藏刻度
            //标签轴文字旋转-45度
//            chart.getCategoryAxis().setTickLabelRotateAngle(-45f);

            //激活点击监听
            chart.ActiveListenItemClick();
            //为了让触发更灵敏，可以扩大6px的点击监听范围
            chart.extPointClickRange(Utils.dp2px(getContext(), 10));
            chart.showClikedFocus();

            //绘制十字交叉线
//            chart.showDyLine();
//            chart.getDyLine().setDyLineStyle(XEnum.DyLineStyle.Vertical);

			/*
			//想隐藏轴的可以下面的函数来隐藏
			chart.getDataAxis().hide();
			chart.getCategoryAxis().hide();
			//想设置刻度线属性的可用下面函数
			chart.getDataAxis().getTickMarksPaint()
			chart.getCategoryAxis().getTickMarksPaint()
			//想设置刻度线标签属性的可用下面函数
			chart.getDataAxis().getAxisTickLabelPaint()
			chart.getCategoryAxis().getAxisTickLabelPaint()
			*/

//            chart.getPlotArea().extWidth(100.f);

            //调整轴显示位置
            chart.setDataAxisLocation(XEnum.AxisLocation.LEFT);
            chart.setCategoryAxisLocation(XEnum.AxisLocation.BOTTOM);

            //收缩绘图区右边分割的范围，让绘图区的线不显示出来
            chart.getClipExt().setExtRight(0.f);


            //test x坐标从刻度线而不是轴开始
            //chart.setXCoordFirstTickmarksBegin(true);
            //chart.getCategoryAxis().showTickMarks();
            //chart.getCategoryAxis().setVerticalTickPosition(XEnum.VerticalAlign.MIDDLE);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 选择标段
     *
     * @param type
     */
    public void selectBids(int type, List<LineChartBean> list) {
        if (type == 0) {//显示全部
            setChartAllData(list);
        } else {
            //显示标段1/2/3/4/5
            if (list.size() > 0 && type < list.size()) {
                LineChartBean bean = list.get(type - 1);
                setChartData(bean.getData(), bean.getName(), bean.getColor());
            }
        }
    }


    /**
     * 选择标段
     *
     * @param data  数据
     * @param name
     * @param color
     */
    public void setChartData(LinkedList<Double> data, String name, int color) {
        chartData.clear();
        LineData lineData = new LineData(name, data, color);
        lineData.setDotStyle(XEnum.DotStyle.RING);
        chartData.add(lineData);
        this.invalidate();
    }


    /**
     * 添加 数据
     *
     * @param list
     */
    public void setChartAllData(List<LineChartBean> list) {
        if (chartData == null) chartData = new LinkedList<>();
        chartData.clear();
        for (LineChartBean bean : list) {
            LineData lineData = new LineData(bean.getName(), bean.getData(), bean.getColor());
            lineData.setDotStyle(XEnum.DotStyle.RING);
            chartData.add(lineData);
        }
        this.invalidate();
    }


    private void chartDataSet() {

        //Line 1
        LinkedList<Double> dataSeries1 = new LinkedList<>();
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        dataSeries1.add(0d);
        LineData lineData1 = new LineData("标段1", dataSeries1, Color.parseColor("#C23531"));
        lineData1.setDotStyle(XEnum.DotStyle.RING);


        //Line 2
        LinkedList<Double> dataSeries2 = new LinkedList<>();
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        dataSeries2.add((double) 0);
        LineData lineData2 = new LineData("标段2", dataSeries2, Color.parseColor("#2F4554"));
        lineData2.setDotStyle(XEnum.DotStyle.RING);
        // 设置点大小
        //lineData2.getPlotLine().getPlotDot().setDotRadius(radius);

        //Line 3
        LinkedList<Double> dataSeries31 = new LinkedList<>();
        dataSeries31.add((double) 10);
        dataSeries31.add((double) 20);
        dataSeries31.add((double) 30);
        dataSeries31.add((double) 40);
        dataSeries31.add((double) 50);
        dataSeries31.add((double) 60);
        dataSeries31.add((double) 0);
        dataSeries31.add((double) 0);
        dataSeries31.add((double) 60);
        dataSeries31.add((double) 40);
        LineData lineData31 = new LineData("标段3", dataSeries31, Color.parseColor("#61A0A8"));
        lineData31.setDotStyle(XEnum.DotStyle.RING);


        //Line 4
        LinkedList<Double> dataSeries41 = new LinkedList<>();
        dataSeries41.add((double) 0);
        dataSeries41.add((double) 70);
        dataSeries41.add((double) 60);
        dataSeries41.add((double) 50);
        dataSeries41.add((double) 40);
        dataSeries41.add((double) 50);
        dataSeries41.add((double) 20);
        dataSeries41.add((double) 0);
        dataSeries41.add((double) 0);
        LineData lineData41 = new LineData("标段4", dataSeries41, Color.parseColor("#D48265"));
        lineData41.setDotStyle(XEnum.DotStyle.RING);

        //Line 5
        LinkedList<Double> dataSeries51 = new LinkedList<>();
        dataSeries51.add((double) 0);
        dataSeries51.add((double) 0);
        dataSeries51.add((double) 0);
        dataSeries51.add((double) 0);
        dataSeries51.add((double) 0);
        dataSeries51.add((double) 0);
        dataSeries51.add((double) 0);
        dataSeries51.add((double) 0);
        dataSeries51.add((double) 0);
        LineData lineData51 = new LineData("标段5", dataSeries51, Color.parseColor("#91C7AE"));
        lineData51.setDotStyle(XEnum.DotStyle.RING);


        chartData.add(lineData1);
        chartData.add(lineData2);
        chartData.add(lineData31);
        chartData.add(lineData41);
        chartData.add(lineData51);


        //=============================== 放弃 ===================================================

        //Line 3
        LinkedList<Double> dataSeries3 = new LinkedList<>();
        dataSeries3.add(65d);
        dataSeries3.add(75d);
        dataSeries3.add(55d);
        dataSeries3.add(65d);
        dataSeries3.add(95d);
        LineData lineData3 = new LineData("圆点", dataSeries3, Color.rgb(123, 89, 168));
//        lineData3.setDotStyle(XEnum.DotStyle.DOT);
//        lineData3.setDotRadius(20);
        //lineData3.setLabelVisible(true);
        //lineData3.getDotLabelPaint().setTextAlign(Align.CENTER);

        //Line 4
        LinkedList<Double> dataSeries4 = new LinkedList<>();
        dataSeries4.add(50d);
        dataSeries4.add(60d);
        dataSeries4.add(80d);
        dataSeries4.add(84d);
        dataSeries4.add(90d);
        LineData lineData4 = new LineData("棱形", dataSeries4, Color.rgb(84, 206, 231));
        lineData4.setDotStyle(XEnum.DotStyle.PRISMATIC);
        //把线弄细点
        lineData4.getLinePaint().setStrokeWidth(2);

//        lineData4.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.CIRCLE);
//        lineData4.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.GREEN);
//        lineData4.setLabelVisible(true);

        //Line 5
        LinkedList<Double> valuesE = new LinkedList<>();
        valuesE.add(0d);
        valuesE.add(80d);
        valuesE.add(85d);
        valuesE.add(90d);
        LineData lineData5 = new LineData("定制", valuesE, Color.rgb(234, 142, 43));
        lineData5.setDotRadius(15);
        lineData5.setDotStyle(XEnum.DotStyle.TRIANGLE);

        //Line 2
        LinkedList<Double> dataSeries6 = new LinkedList<>();
        dataSeries6.add((double) 50);
        dataSeries6.add((double) 52);
        dataSeries6.add((double) 53);
        dataSeries6.add((double) 55);
        dataSeries6.add((double) 40);
        LineData lineData6 = new LineData("圆环2", dataSeries6, Color.rgb(75, 166, 51));
        lineData6.setDotStyle(XEnum.DotStyle.RING2);
        lineData6.getPlotLine().getDotPaint().setColor(Color.RED);
        lineData6.setLabelVisible(true);
        lineData6.getPlotLine().getPlotDot().setRingInnerColor(Color.GREEN);
        lineData6.getPlotLine().getPlotDot().setRing2InnerColor(Color.GREEN);
        lineData6.setLineStyle(XEnum.LineStyle.DASH);
        lineData6.getDotLabelPaint().setColor(Color.rgb(212, 64, 39));
        lineData6.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.rgb(57, 172, 241));
        lineData6.getLabelOptions().getBox().setBorderLineColor(Color.YELLOW);


//        chartData.add(lineData3);
//        chartData.add(lineData4);
//        chartData.add(lineData5);
//        chartData.add(lineData6);
    }

    private void chartLabels() {
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("7");
        labels.add("8");
        labels.add("9");
        labels.add("10");
        labels.add("11");
        labels.add("12");
    }

    @Override
    public void render(Canvas canvas) {
        try {
            chart.render(canvas);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        if (event.getAction() == MotionEvent.ACTION_UP) {
            triggerClick(event.getX(), event.getY());
        }
        super.onTouchEvent(event);
        return true;
    }


    //触发监听
    private void triggerClick(float x, float y) {

        //交叉线
        if (chart.getDyLineVisible()) chart.getDyLine().setCurrentXY(x, y);
        if (!chart.getListenItemClickStatus()) {
            //交叉线
            if (chart.getDyLineVisible()) this.invalidate();
        } else {
            PointPosition record = chart.getPositionRecord(x, y);
            if (null == record) {
                if (chart.getDyLineVisible()) this.invalidate();
                return;
            }
            LineData lData = chartData.get(record.getDataID());
            Double lValue = lData.getLinePoint().get(record.getDataChildID());

            float r = record.getRadius();
            chart.showFocusPointF(record.getPosition(), r + r * 0.5f);
            chart.getFocusPaint().setStyle(Paint.Style.FILL);
//            chart.getFocusPaint().setStrokeWidth(3);
           /* if (record.getDataID() >= 3) {
                chart.getFocusPaint().setColor(Color.BLUE);
            } else {
                chart.getFocusPaint().setColor(Color.RED);
            }*/
            switch (record.getDataID()) {
                case 0:
                    chart.getFocusPaint().setColor(ContextCompat.getColor(getContext(), R.color.bids_1));
                    break;
                case 1:
                    chart.getFocusPaint().setColor(ContextCompat.getColor(getContext(), R.color.bids_2));
                    break;
                case 2:
                    chart.getFocusPaint().setColor(ContextCompat.getColor(getContext(), R.color.bids_3));
                    break;
                case 3:
                    chart.getFocusPaint().setColor(ContextCompat.getColor(getContext(), R.color.bids_4));
                    break;
                case 4:
                    chart.getFocusPaint().setColor(ContextCompat.getColor(getContext(), R.color.bids_5));
                    break;
            }

            //在点击处显示tooltip
            mPaintTooltips.setColor(Color.WHITE);
            mPaintTooltips.setTextSize(24f);
            //chart.getToolTip().setCurrentXY(x,y);
            chart.getToolTip().setCurrentXY(record.getPosition().x, record.getPosition().y);
            chart.getToolTip().setMargin(20f);
            chart.getToolTip().getBackgroundPaint().setColor(Color.parseColor("#80000000"));
            chart.getToolTip().addToolTip((record.getDataChildID() + 1) + "月份", mPaintTooltips);
//            chart.getToolTip().addToolTip(lData.getLabel() + " : " + lValue + "%", mPaintTooltips);
//            chart.getToolTip().addToolTip(lValue + "%", mPaintTooltips);


            //当前标签对应的其它点的值
            int cid = record.getDataChildID();
            String xLabels = "";
            for (LineData data : chartData) {
                if (cid < data.getLinePoint().size()) {
                    xLabels = Double.toString(data.getLinePoint().get(cid));
                    chart.getToolTip().addToolTip(data.getLabel() + " : " + xLabels + "%", mPaintTooltips);
                }
            }


            this.invalidate();
        }


    }
}
