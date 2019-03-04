package com.zgdj.djframe.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.view.CustomerDialog;
import com.zgdj.djframe.view.ScrollerNumberPicker;

import java.util.ArrayList;

/**
 * description: 29种表单 -- mmp
 * author: Created by Mr.Zhang on 2018/7/4
 */
public class FormUtil {
    private static FormUtil formUtil;
    private FormCallBack callBack;
    private Form1Call form1Call; // -- 表单1 回调
    private Form2Call form2Call; // -- 表单2 回调
    private Form4Call form4Call; // -- 表单4 回调
    private Form7Call form7Call; // -- 表单7 回调

    private Form27Call form27Call; // -- 表单27 回调


    public static FormUtil getInstance() {
        if (formUtil == null)
            formUtil = new FormUtil();
        return formUtil;
    }


    //添加表单layout
    public void createFormView(Activity activity, int type, LinearLayout layout) {
        View view = activity.getLayoutInflater().inflate(getResource(type), null);
        layout.addView(view);
        switch (type) {
            case 1:
            case 10:
                setForm1(view, activity);
                break;
            case 2:
                setForm2(view);
                break;
            case 3:
                setForm2(view, "查验合格", "纠正差错后合格", "纠正差错后再报");
                break;
            case 4:
            case 23:
            case 24:
            case 25:
            case 26:
                setForm4(view); // 单个输入框
                break;
            case 5:
                setForm5(view);
                break;
            case 6:
                setForm6(view);
                break;
            case 7:
                setForm7(view);
                break;
            case 8:
            case 9:
                setForm8(view);
                break;
            case 11:
            case 12:
                setForm11(view);
                break;
            case 13:
                setForm13(view);
                break;
            case 27:
                setForm27(view, activity);
                break;
        }
    }

    private Handler handler;

    //设置表单-1内容
    @SuppressLint("HandlerLeak")
    private void setForm1(View view, Activity activity) {
        //radio button
        RadioGroup group = view.findViewById(R.id.radio_group);
        RadioButton radio1 = view.findViewById(R.id.radio_1);
        RadioButton radio2 = view.findViewById(R.id.radio_2);
        RadioButton radio3 = view.findViewById(R.id.radio_3);
        TextView setTimeMiddle = view.findViewById(R.id.text_select_time_middle);
        TextView setTime = view.findViewById(R.id.text_select_time);
        TextView setHour = view.findViewById(R.id.text_select_time_hour);
        ArrayList<String> hourList = new ArrayList<>();

        radio1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                group.clearCheck();
                if (form1Call != null)
                    form1Call.getChecked(0);
            }
        });
        radio2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radio1.setChecked(false);
                if (form1Call != null)
                    form1Call.getChecked(1);
            }
        });
        radio3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radio1.setChecked(false);
                if (form1Call != null)
                    form1Call.getChecked(2);
            }
        });
        setTimeMiddle.setOnClickListener(v -> {
            if (activity != null) {
                DatePickUtils datePickUtils = new DatePickUtils();
                datePickUtils.createDatePick(activity);
                datePickUtils.setBack((year, month, day) -> {
                    setTimeMiddle.setText(year + "年" + month + "月" + day + "日");
                    if (form1Call != null)
                        form1Call.getEdit(year + "年" + month + "月" + day + "日");
                }).show();
            }
        });

        setTime.setOnClickListener(v -> {
            if (activity != null) {
                DatePickUtils datePickUtils = new DatePickUtils();
                datePickUtils.createDatePick(activity);
                datePickUtils.setBack((year, month, day) -> {
                    setTime.setText(year + "年" + month + "月" + day + "日");
                    if (form1Call != null)
                        form1Call.getTime(year + "年" + month + "月" + day + "日");
                }).show();
            }
        });

        // 设置数据
        for (int i = 1; i < 24; i++) {
            hourList.add(i + "时");
        }
        setHour.setOnClickListener(v -> {
            //弹框
            CustomerDialog dialog = new CustomerDialog(activity, R.layout.dialog_time_hour);
            dialog.setDlgIfClick(true);
            dialog.setOnCustomerViewCreated((window, dlg) -> {
                ScrollerNumberPicker picker = window.findViewById(R.id.nScrollView);
                Button ok = window.findViewById(R.id.dialog_btn_ok);
                picker.setData(hourList);
                picker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
                    @Override
                    public void endSelect(int id, String text) {
                        if (!picker.isScrolling()) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = text;
                            handler.sendMessage(message);
                        }
                    }

                    @Override
                    public void selecting(int id, String text) {

                    }
                });
                ok.setOnClickListener(v1 -> dialog.dismissDlg());
            });
            dialog.showDlg();
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        setHour.setText((String) msg.obj);
                        if (form1Call != null) {
                            String info = (String) msg.obj;
                            if (info.contains("时"))
                                info = info.replace("时", "");
                            form1Call.getHour(info);
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    //设置表单-2内容
    private void setForm2(View view) {

        setForm2(view, "");
    }

    // 设置表单 - 3内容
    private void setForm2(View view, String... args) {
        RadioGroup group = view.findViewById(R.id.radio_group);
        if (args.length >= 3) {
            RadioButton radio1 = view.findViewById(R.id.radio_1);
            RadioButton radio2 = view.findViewById(R.id.radio_2);
            RadioButton radio3 = view.findViewById(R.id.radio_3);

            radio1.setText(args[0]);
            radio2.setText(args[1]);
            radio3.setText(args[2]);
        }
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_1://--1
                    if (form2Call != null)
                        form2Call.getChecked(0);
                    break;
                case R.id.radio_2://--2
                    if (form2Call != null)
                        form2Call.getChecked(1);
                    break;
                case R.id.radio_3://--3
                    if (form2Call != null)
                        form2Call.getChecked(2);
                    break;
            }
        });
    }

    //设置表单 -- 4内容
    private void setForm4(View view) {
        EditText edit = view.findViewById(R.id.edit);
        TextView record = view.findViewById(R.id.text_suggestion_record);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                record.setText(s.toString().length() + "/300");
                if (form4Call != null) {
                    form4Call.getEditResult(0, s.toString());
                }
            }
        });

    }

    //设置表单 -- 5内容
    private void setForm5(View view) {
        RadioGroup group = view.findViewById(R.id.radio_group);
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_1://--1
                    if (form2Call != null)
                        form2Call.getChecked(0);
                    break;
                case R.id.radio_2://--2
                    if (form2Call != null)
                        form2Call.getChecked(1);
                    break;
                case R.id.radio_3://--3
                    if (form2Call != null)
                        form2Call.getChecked(2);
                    break;
                case R.id.radio_4://--3
                    if (form2Call != null)
                        form2Call.getChecked(3);
                    break;
            }
        });
    }

    //设置表单 -- 6内容
    private void setForm6(View view) {
        RadioGroup group = view.findViewById(R.id.radio_group);
        RadioButton radio1 = view.findViewById(R.id.radio_1);
        RadioButton radio2 = view.findViewById(R.id.radio_2);
        RadioButton radio3 = view.findViewById(R.id.radio_3);
        radio3.setVisibility(View.GONE);
        radio1.setText("同意按计划进行本次锚索灌浆作业");
        radio2.setText("补充、完善准备工作后重新申报");
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_1://--1
                    if (form2Call != null)
                        form2Call.getChecked(0);
                    break;
                case R.id.radio_2://--2
                    if (form2Call != null)
                        form2Call.getChecked(1);
                    break;
            }
        });
    }

    //设置表单 -- 7内容
    private void setForm7(View view) {
        RadioGroup group = view.findViewById(R.id.radio_group);
        EditText edit = view.findViewById(R.id.edit);
        TextView record = view.findViewById(R.id.text_suggestion_record);
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_1://--1
                    if (form7Call != null)
                        form7Call.getChecked(0);
                    break;
                case R.id.radio_2://--2
                    if (form7Call != null)
                        form7Call.getChecked(1);
                    break;
            }
        });
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                record.setText(s.toString().length() + "/300");
                if (form7Call != null)
                    form7Call.getEditResult(s.toString());
            }
        });

    }

    //设置表单 -- 8内容
    private void setForm8(View view) {
        // find view
        RelativeLayout layoutTime = view.findViewById(R.id.layout_time);
        View line1 = view.findViewById(R.id.line1);
        EditText editText = view.findViewById(R.id.edit_middle);
        RadioGroup group = view.findViewById(R.id.radio_group);
        RadioButton radio0 = view.findViewById(R.id.radio_0);
        RadioButton radio1 = view.findViewById(R.id.radio_1);
        TextView textMiddle = view.findViewById(R.id.text_1_info);
        TextView setTimeMiddle = view.findViewById(R.id.text_select_time_middle);
        RadioButton radio2 = view.findViewById(R.id.radio_2);
        RadioButton radio3 = view.findViewById(R.id.radio_3);

        //set view
        layoutTime.setVisibility(View.GONE);
        line1.setVisibility(View.GONE);
        radio0.setVisibility(View.VISIBLE);
        radio0.setText("检查合格，同意开仓");
        radio1.setText("完成");
        radio2.setText("完善施工准备后另行申请开仓");
        textMiddle.setText("工序质量检验后重新申报");
        radio3.setVisibility(View.GONE);
        setTimeMiddle.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);

        // set listener
        radio0.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radio1.setChecked(false);
                if (form1Call != null)
                    form1Call.getChecked(0);
            }
        });

        radio1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                group.clearCheck();
                if (form1Call != null)
                    form1Call.getChecked(1);
            }
        });
        radio2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radio1.setChecked(false);
                if (form1Call != null)
                    form1Call.getChecked(2);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (form1Call != null)
                    form1Call.getEdit(s.toString());
            }
        });
    }

    //设置表单 -- 11 / 12 内容
    private void setForm11(View view) {
        EditText edit = view.findViewById(R.id.edit);
        EditText edit2 = view.findViewById(R.id.edit2);
        EditText edit3 = view.findViewById(R.id.edit3);

        TextView record = view.findViewById(R.id.text_suggestion_record);
        TextView record2 = view.findViewById(R.id.text_suggestion_record2);
        TextView record3 = view.findViewById(R.id.text_suggestion_record3);
        RelativeLayout layout2 = view.findViewById(R.id.layout_edit2);
        RelativeLayout layout3 = view.findViewById(R.id.layout_edit3);
        View line2 = view.findViewById(R.id.line2);
        View line3 = view.findViewById(R.id.line3);

        layout2.setVisibility(View.VISIBLE);
        line2.setVisibility(View.VISIBLE);
        layout3.setVisibility(View.VISIBLE);
        line3.setVisibility(View.VISIBLE);

        edit.addTextChangedListener(new TextChange(0, record));
        edit2.addTextChangedListener(new TextChange(1, record2));
        edit3.addTextChangedListener(new TextChange(2, record3));

    }

    //设置表单 -- 13 内容
    private void setForm13(View view) {
        EditText edit = view.findViewById(R.id.edit);
        EditText edit2 = view.findViewById(R.id.edit2);

        TextView record = view.findViewById(R.id.text_suggestion_record);
        TextView record2 = view.findViewById(R.id.text_suggestion_record2);
        RelativeLayout layout2 = view.findViewById(R.id.layout_edit2);
        View line2 = view.findViewById(R.id.line2);

        layout2.setVisibility(View.VISIBLE);
        line2.setVisibility(View.VISIBLE);

        edit.addTextChangedListener(new TextChange(0, record));
        edit2.addTextChangedListener(new TextChange(1, record2));

    }

    private class TextChange implements TextWatcher {

        private int pos;
        private TextView record;

        public TextChange(int pos, TextView record) {
            this.pos = pos;
            this.record = record;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            record.setText(s.toString().length() + "/300");
            if (form4Call != null) {
                form4Call.getEditResult(0, s.toString());
            }
        }
    }


    //设置表单 - 27内容
    private void setForm27(View view, Activity activity) {
        TextView setTime = view.findViewById(R.id.text_select_time);
        setTime.setOnClickListener(v -> {
            DatePickUtils datePickUtils = new DatePickUtils();
            datePickUtils.createDatePick(activity)
                    .setBack((year, month, day) -> {
                        ToastUtils.showShort(year + "年" + month + "月" + day + "日");
                    })
                    .show();
        });
    }


    //获取layout 布局
    private int getResource(int type) {
        @LayoutRes int res = 0;
        switch (type) {
            case 1:
            case 8:
            case 9:
            case 10:
                res = R.layout.layout_form_1;
                break;
            case 2:
                res = R.layout.layout_form_2;
                break;
            case 3:
            case 6:
                res = R.layout.layout_form_2;
                break;
            case 4:
            case 11:
            case 12:
            case 13:
            case 14:
            case 23:
            case 24:
            case 25:
            case 26:
                res = R.layout.layout_form_4;
                break;
            case 5:
                res = R.layout.layout_form_5;
                break;
            case 7:
                res = R.layout.layout_form_7;
                break;
            case 27:
                res = R.layout.layout_form_27;
                break;
        }

        return res;
    }


    // 设置回调
    public void setCallBack(FormCallBack callBack, int type) {
        switch (type) {
            case 1:
            case 8:
            case 9:
            case 10:
                form1Call = (Form1Call) callBack;
                break;
            case 2:
            case 3:
            case 5:
            case 6:
                form2Call = (Form2Call) callBack;
                break;
            case 4:
            case 11:
            case 12:
            case 13:
            case 14:
            case 23:
            case 24:
            case 25:
            case 26:
                form4Call = (Form4Call) callBack;
                break;
            case 7:
                form7Call = (Form7Call) callBack;
                break;
            case 27:
                form27Call = (Form27Call) callBack;
                break;
        }

    }

    //获取表的索引
    public int getFormIndex(String info) {
        int index = 0;
        if (info.contains("单元工程检查验收申请表")) {
            index = 1;
        } else if (info.contains("测量成果报审表")) {
            index = 2;
        } else if (info.contains("施工放样单") || info.contains("施工放样报验单")) {
            index = 3;
        } else if (info.contains("工程建设标准强制性条文执行记录表")) {
            index = 4;
        } else if (info.contains("喷射混凝土开仓证")) {
            index = 5;
        } else if (info.contains("预应力锚索灌浆许可证")) {
            index = 6;
        } else if (info.contains("预应力锚索张拉许可证")) {
            index = 7;
        } else if (info.contains("混凝土开仓准浇证")) {
            index = 8;
        } else if (info.contains("灌浆准灌证")) {
            index = 9;
        }/* else if (info.contains("压力钢管制作单元工程检查验收申请表")) {
            index = 10;
        } else if (info.contains("压力钢管制作放样切割施工记录表")) {
            index = 11;
        } else if (info.contains("压力钢管制作组圆施工记录表")) {
            index = 12;
        } else if (info.contains("压力钢管制作摞节、对装施工记录表")) {
            index = 13;
        } else if (info.contains("压力钢管制作焊接过程记录表")) {
            index = 14;
        } else if (info.contains("压力钢管焊接缺陷返修卡")) {
            index = 15;
        } else if (info.contains("压力钢管制作防腐质量施工记录表")) {
            index = 16;
        } else if (info.contains("压力钢管制作焊接缺陷返修卡")) {
            index = 17;
        } else if (info.contains("压力钢管安装单元工程检查验收申请表")) {
            index = 18;
        } else if (info.contains("压力钢管安装焊缝外观质量评定表")) {
            index = 19;
        } else if (info.contains("压力钢管安装一、二类焊缝内部质量、表面清除及局部凹坑焊补施工记录表")) {
            index = 20;
        } else if (info.contains("压力钢管安装焊接过程记录表")) {
            index = 21;
        } else if (info.contains("压力钢管安装单元验收评定申请表")) {
            index = 22;
        }*/ else if (info.contains("开挖联合验收表")) {
            index = 23;
        } else if (info.contains("地质及施工缺陷处理联合检验认定表")) {
            index = 24;
        } else if (info.contains("模板检查成果表")) {
            index = 25;
        } else if (info.contains("埋件布置图")) {
            index = 26;
        }/* else if (info.contains("固结灌浆工程检查孔验收合格证")) {
            index = 27;
        } else if (info.contains("抬动观测记录汇总统计表")) {
            index = 28;
        } else if (info.contains("帷幕灌浆工程检查孔验收合格证")) {
            index = 29;
        }*/
        return index;
    }


    public interface FormCallBack {

    }

    public interface Form1Call extends FormCallBack {
        void getChecked(int checkedPos); //radioButton 下标

        void getTime(String time); //时间

        void getHour(String hour);//小时

        void getEdit(String info); // 获取内容信息
    }

    public interface Form2Call extends FormCallBack {
        void getChecked(int pos); //获取radio button 下标
    }

    public interface Form4Call extends FormCallBack {
        void getEditResult(int index, String result);
    }

    public interface Form27Call extends FormCallBack {
        void getDate(String time); //时间
    }

    public interface Form7Call extends FormCallBack {
        void getEditResult(String result); //内容

        void getChecked(int pos); //获取radio button 下标
    }

}
