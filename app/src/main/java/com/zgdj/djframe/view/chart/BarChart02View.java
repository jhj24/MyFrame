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
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.utils.Utils;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.event.click.BarPosition;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

// 横向柱状
public class BarChart02View extends DemoView {

    private static final String TAG = "BarChart02View";
    private BarChart chart = new BarChart();

    //标签轴
    private List<String> chartLabels = new LinkedList<>();
    private List<BarData> chartData = new LinkedList<>();

    private int lineColor = ContextCompat.getColor(getContext(), R.color.bids_excellent);//XY 轴线的颜色
    private int tickTextSize = Utils.dp2px(getContext(), 12);//XY轴上刻度字体大小

    //图宽高
    private int w;
    private int h;

    public BarChart02View(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public BarChart02View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BarChart02View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
//        chartLabels();
//        chartDataSet();
        chartRender();

        //綁定手势滑动事件
        this.bindTouch(this, chart);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        if (null != chart) chart.setChartRange(w, h);
        this.w = w;
        this.h = h;
    }

    private void chartRender() {
        try {
            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int[] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(DensityUtil.dip2px(getContext(), 40), ltrb[1], ltrb[2], ltrb[3]);

            chart.disableScale(); // 禁止缩放
            //仅能横向移动
            chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
//            chart.disablePanMode();// 禁用平移模式
//            chart.enablePanMode(); //激活平移模式
//            chart.setTitle("");
//            chart.addSubtitle("");
            chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);
            chart.setTitleAlign(XEnum.HorizontalAlign.LEFT);

            //数据源
            chart.setDataSource(chartData);
            chart.setCategories(chartLabels);

            //轴标题
//            chart.getAxisTitle().setLeftTitle("");
//            chart.getAxisTitle().setLowerTitle("");
//            chart.getAxisTitle().setRightTitle("");

            //数据轴
            chart.getDataAxis().setAxisMax(2);
            chart.getDataAxis().setAxisMin(0);
            chart.getDataAxis().setAxisSteps(1);


            chart.getDataAxis().getTickLabelPaint().
                    setColor(Color.parseColor("#333333"));

//            chart.setXTickMarksOffsetMargin(500f); //
            //设置下标字体大小
            chart.getDataAxis().getTickLabelPaint().setTextSize(tickTextSize);
            chart.getDataAxis().setTickLabelMargin(Utils.dp2px(getContext(), 6));
            chart.getDataAxis().getAxisPaint().setStrokeWidth(4f);// X轴线宽度
            chart.getDataAxis().getAxisPaint().setColor(lineColor);//X轴线颜色
            chart.getDataAxis().getTickMarksPaint().setStrokeWidth(4f);//X轴上刻度宽度
            chart.getDataAxis().getTickMarksPaint().setColor(lineColor);
            chart.getDataAxis().setLabelFormatter(value -> {
                // TODO Auto-generated method stub
                String label = value.substring(0, value.indexOf("."));
                return label;
            });

            //网格
//            chart.getPlotGrid().showHorizontalLines();
//            chart.getPlotGrid().showVerticalLines();
//            chart.getPlotGrid().showEvenRowBgColor();
            // 顶部图例隐藏
            chart.getPlotLegend().hide();
            // 顶部图例字体大小
            chart.getPlotLegend().getPaint().setTextSize(Utils.dp2px(getContext(), 8));

            //标签轴文字旋转-45度
//            chart.getCategoryAxis().setTickLabelRotateAngle(-45f);
            //横向显示柱形
            chart.setChartDirection(XEnum.Direction.VERTICAL);
            //在柱形顶部显示值
            chart.getBar().setItemLabelVisible(false);
            chart.getBar().getItemLabelPaint().setTextSize(22);
            //设置右标字体大小
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(tickTextSize);
            chart.getCategoryAxis().getAxisPaint().setStrokeWidth(4f);//Y轴线宽度
            chart.getCategoryAxis().getAxisPaint().setColor(lineColor);//Y轴线颜色
            chart.getCategoryAxis().getTickMarksPaint().setStrokeWidth(4f);//Y轴上刻度宽度
            chart.getCategoryAxis().getTickMarksPaint().setColor(lineColor);
            chart.getCategoryAxis().setTickLabelMargin(Utils.dp2px(getContext(), 10));
            chart.setItemLabelFormatter(value -> {
                // TODO Auto-generated method stub
                DecimalFormat df = new DecimalFormat("#");
                String label = df.format(value).toString();
//                String label = value.toString().substring(0, value.toString().indexOf("."));
                return label;
            });

            //激活点击监听
            chart.ActiveListenItemClick();
            chart.showClikedFocus();


			/*
			chart.setDataAxisPosition(XEnum.DataAxisPosition.BOTTOM);
			chart.getDataAxis().setVerticalTickPosition(XEnum.VerticalAlign.BOTTOM);

			chart.setCategoryAxisPosition(XEnum.CategoryAxisPosition.LEFT);
			chart.getCategoryAxis().setHorizontalTickAlign(Align.LEFT);
			*/


        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.toString());
        }


    }

    public void setChartData(List<List<Double>> lists) {
        String[] titles = {"总计", "合格", "优良"};
        // ContextCompat.getColor(getContext(), R.color.bids_total)
        // ContextCompat.getColor(getContext(), R.color.bids_qualified)
        // ContextCompat.getColor(getContext(), R.color.bids_excellent)
        int[] colors = {Color.argb(200, 247, 150, 108),
                Color.argb(200, 255, 87, 34),
                Color.argb(200, 0, 150, 136)};
        chartData.clear();
        for (int i = 0; i < titles.length; i++) {
            BarData data = new BarData(titles[i], lists.get(i), colors[i]);
            chartData.add(data);
        }
        invalidate();
    }

    //添加宽度
    public void setAddWidth(int width) {
        chart.setChartRange(this.w + width, h);
        Logs.debug("w:" + w + ";h: " + h);
    }

    public void chartLabels(List<String> labels) {
        chartLabels.clear();
        for (String str : labels) {
            chartLabels.add(str);
        }

//        invalidate();
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
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            triggerClick(event.getX(), event.getY());
        }
        return true;
    }


    //触发监听
    private void triggerClick(float x, float y) {
        BarPosition record = chart.getPositionRecord(x, y);
        if (null == record) return;

        BarData bData = chartData.get(record.getDataID());
        Double bValue = bData.getDataSet().get(record.getDataChildID());

       /* Toast.makeText(this.getContext(),
                "info:" + record.getRectInfo() +
                        " Key:" + bData.getKey() +
                        " Current Value:" + Double.toString(bValue),
                Toast.LENGTH_SHORT).show();*/

        String values = Double.toString(bValue);
        ToastUtils.showShort(chartLabels.get(record.getDataChildID()) + " \n" +
                bData.getKey() + " : " + values.substring(0, values.indexOf(".")));

        chart.showFocusRectF(record.getRectF());
        chart.getFocusPaint().setStyle(Paint.Style.FILL);
        if (record.getDataID() == 0) {//总计
            chart.getFocusPaint().setColor(Color.argb(255, 247, 150, 108));
        } else if (record.getDataID() == 1) {//合格
            chart.getFocusPaint().setColor( Color.argb(255, 255, 87, 34));
        } else if (record.getDataID() == 2) {// 优良
            chart.getFocusPaint().setColor(Color.argb(255, 0, 150, 136));
        }

//        chart.getFocusPaint().setStyle(Paint.Style.STROKE);
//        chart.getFocusPaint().setStrokeWidth(3);
//        chart.getFocusPaint().setColor(Color.GREEN);
        this.invalidate();
    }
}
