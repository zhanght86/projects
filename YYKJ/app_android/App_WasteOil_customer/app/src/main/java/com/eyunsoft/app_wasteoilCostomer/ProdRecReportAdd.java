package com.eyunsoft.app_wasteoilCostomer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.eyunsoft.app_wasteoilCostomer.Model.NameToValue;
import com.eyunsoft.app_wasteoilCostomer.Model.TransferRec_Model;
import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;
import com.eyunsoft.app_wasteoilCostomer.Publics.MsgBox;
import com.eyunsoft.app_wasteoilCostomer.Publics.TitleSet;
import com.eyunsoft.app_wasteoilCostomer.bll.Category_BLL;
import com.eyunsoft.app_wasteoilCostomer.bll.SysPublic_BLL;
import com.eyunsoft.app_wasteoilCostomer.bll.TransRec_BLL;
import com.eyunsoft.app_wasteoilCostomer.utils.CustomCheckBox.CustomCheckBox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProdRecReportAdd extends AppCompatActivity {

    private Button btnSave;
    private Button btnDelete;
    private Button btnBack;

    private EditText editRecNo;
    private EditText editProNumber;
    private EditText editProDangerComponent;
    private EditText editRemark;
    private EditText editTransTime;
    private EditText editTransTraboo;
    private EditText editTransAddress;

    private Spinner dropCategory;
    private Spinner dropProShape;
    private Spinner dropProMeasureUnit;
    private Spinner dropProPack;


    public ArrayList<NameToValue> listCategory;
    public ArrayList<NameToValue> listProShape;
    public ArrayList<NameToValue> listProMeasureUnit;
    public ArrayList<NameToValue> listProHazardNature;
    public ArrayList<NameToValue> listProPack;

    int mYearStart, mMonthStart, mDayStart;
    final int DATE_DIALOGStart = 1;

    public CustomCheckBox customCheckBox;

    private  boolean isFirstLoad=true;//首次加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_rec_report_add);
        editRecNo=(EditText)findViewById(R.id.edit_RecNo) ;
        editProNumber=(EditText)findViewById(R.id.edit_ProNumber);

        editProDangerComponent=(EditText)findViewById(R.id.edit_ProDangerComponent);
        editRemark=(EditText)findViewById(R.id.edit_Remark);
        editTransTime=(EditText)findViewById(R.id.edit_TransferTime);
        editTransAddress=(EditText)findViewById(R.id.edit_TransferAddress);
        editTransTraboo=(EditText)findViewById(R.id.edit_TransferTaboo);

        dropCategory=(Spinner)findViewById(R.id.drop_Category);
        dropProMeasureUnit=(Spinner)findViewById(R.id.drop_ProMeasureUnit);
        dropProShape=(Spinner)findViewById(R.id.drop_ProShape);
        dropProPack = (Spinner) findViewById(R.id.dropProPack);

        final Calendar ca = Calendar.getInstance();
        mYearStart = ca.get(Calendar.YEAR);
        mMonthStart = ca.get(Calendar.MONTH);
        mDayStart = ca.get(Calendar.DAY_OF_MONTH);

        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date d1=new Date(time);
        String t1=format.format(d1);

        editTransTime.setText(t1);
        editTransTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOGStart);

            }
        });
        editTransTime.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDialog(DATE_DIALOGStart);
                    return true;
                }
                return false;
            }
        });
        InitForm();

        //返回
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //重置
        btnDelete=(Button)findViewById(R.id.btnDeleteAll);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitForm();
            }
        });

        //保存
        btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long comId=((App)getApplication()).getSysComID();
                long userId=((App)getApplication()).getCompanyUserID();
                long comBrId=((App)getApplication()).getSysComBrID();

                TransferRec_Model mo=new TransferRec_Model();
                mo.setRecNumber(editRecNo.getText().toString());
                mo.setCreateComCusID(userId);
                mo.setCreateComBrID(comBrId);
                mo.setCreateComID(comId);
                mo.setCreateIp("");
                mo.setRecComID(comId);
                mo.setRecComBrID(comBrId);


                NameToValue mapProCategory=(NameToValue)dropCategory.getSelectedItem();
                mo.setProCategory(Convert.ToInt64(mapProCategory.InfoValue.toString()));
                mo.setProCategoryName(mapProCategory.InfoName.toString().trim().replace(">>",""));

                mo.setProDangerComponent(editProDangerComponent.getText().toString().trim());


                // HashMap<String, Object> mapProHazardNature=(HashMap<String, Object>)drop.getSelectedItem();
                String natureStr="";
                List<String> listSelect=customCheckBox.getSelectedBoxContents();
                for(int i=0;i<listSelect.size();i++)
                {
                    natureStr+=listSelect.get(i)+",";
                }
                if (!TextUtils.isEmpty(natureStr)) {
                    natureStr = natureStr.substring(0, natureStr.length() - 1);
                }
                mo.setProHazardNature(natureStr.trim());

                NameToValue mapProMeasureUnit=(NameToValue)dropProMeasureUnit.getSelectedItem();
                mo.setProMeasureUnitName(mapProMeasureUnit.InfoName.toString());


                mo.setProNumber(editProNumber.getText().toString());

                NameToValue mapProPack = (NameToValue) dropProPack.getSelectedItem();
                mo.setProPackName(mapProPack.InfoName);

                NameToValue mapProShape=(NameToValue)dropProShape.getSelectedItem();

                mo.setProShape(Convert.ToInt64(mapProShape.InfoValue.toString()));
                mo.setProShapeName(mapProShape.InfoName.toString());
               // mo.setSysComID(comId);
                mo.setRemark(editRemark.getText().toString());
                mo.setRecSource(3);

                mo.setTransferAddress(editTransAddress.getText().toString());
                mo.setTransferTime(editTransTime.getText().toString());
                mo.setTransferTaboo(editTransTraboo.getText().toString());



                String mess="";
                String successStr="添加成功";
                if(TextUtils.isEmpty(editRecNo.getText())) {
                    mess=TransRec_BLL.TransRec_Add(mo);
                }
                else
                {
                    successStr="修改成功";
                    mess=TransRec_BLL.TransRec_Update(mo);
                }

                if(TextUtils.isEmpty(mess))
                {
                    MsgBox.Show(ProdRecReportAdd.this,successStr);
                    InitForm();
                }
                else
                {
                    MsgBox.Show(ProdRecReportAdd.this,mess);
                }


            }
        });


    }

    /**
     * 初始化窗体
     */
    public void InitForm() {

        TitleSet.SetTitle(this,21);
        long comId = ((App) getApplication()).getSysComID();
        listProHazardNature = SysPublic_BLL.GetProduct_HazardNature();
        if (isFirstLoad) {
            if (listProHazardNature != null && listProHazardNature.size() > 0) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < listProHazardNature.size(); i++) {
                    list.add(listProHazardNature.get(i).InfoName);
                }

                customCheckBox = new CustomCheckBox(this);
                customCheckBox.setCheckBoxs(list);

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layNature);
                linearLayout.addView(customCheckBox);
            }
        } else {
            customCheckBox.UnCheck();
        }

        //计量单位
        listProMeasureUnit = SysPublic_BLL.GetProduct_MeasureUnit();
        ArrayAdapter<NameToValue> arrayAdapterUnit = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listProMeasureUnit);
        dropProMeasureUnit.setAdapter(arrayAdapterUnit);

        //包装
        listProPack=SysPublic_BLL.GetProduct_Pack();
        ArrayAdapter<NameToValue> arrayAdapterProPack = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listProPack);
        dropProPack.setAdapter(arrayAdapterProPack);

        //危废形态
        listProShape = SysPublic_BLL.GetProduct_Shape();
        ArrayAdapter<NameToValue> arrayAdapterShape = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listProShape);
        dropProShape.setAdapter(arrayAdapterShape);

        //危废种类
        listCategory = Category_BLL.GetProductCategory(comId);
        ArrayAdapter<NameToValue> arrayAdapterCategory = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listCategory);
        dropCategory.setAdapter(arrayAdapterCategory);
        dropCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    long code=Convert.ToInt64(listCategory.get(position).InfoValue);
                    String natrueStr=Category_BLL.GetHazardNature(code);
                    if(!TextUtils.isEmpty(natrueStr)) {
                        customCheckBox.Check(natrueStr.split(","));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editRemark.setText("");
        editProDangerComponent.setText("");

        editProNumber.setText("");
        editRecNo.setText("");

        editTransAddress.setText("");
        editTransTraboo.setText("");
        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date d1=new Date(time);
        String t1=format.format(d1);

        editTransTime.setText(t1);

        editRecNo.setEnabled(false);


        if (isFirstLoad) {
            Intent intent = getIntent();
            String recNo = intent.getStringExtra("RecNo");
            if (!TextUtils.isEmpty(recNo)) {
                TitleSet.SetTitle(this,22);
                TransferRec_Model model = TransRec_BLL.TransRec_LoadData(recNo);

                if(model.IsExist()) {
                    editRecNo.setText(model.getRecNumber());
                    editProDangerComponent.setText(model.getProDangerComponent());
                    editProNumber.setText(model.getProNumber());

                    editRemark.setText(model.getRemark());

                    String t = format.format(Convert.ToDate(model.getTransferTime()));
                    editTransTime.setText(t);
                    editTransAddress.setText(model.getTransferAddress());
                    editTransTraboo.setText(model.getTransferTaboo());

                    for (int i = 0; i < listCategory.size(); i++) {
                        if (listCategory.get(i).InfoValue.equals(Convert.ToString(model.getProCategory()))) {
                            dropCategory.setSelection(i);
                            break;
                        }
                    }

                    for (int i = 0; i < listProMeasureUnit.size(); i++) {
                        if (listProMeasureUnit.get(i).InfoName.equals(model.getProMeasureUnitName())) {
                            dropProMeasureUnit.setSelection(i);
                            break;
                        }
                    }


                    for (int i = 0; i < listProPack.size(); i++) {
                        if (listProPack.get(i).InfoName.equals(Convert.ToString(model.getProPackName()))) {
                            dropProPack.setSelection(i);
                            break;
                        }
                    }

                    for (int i = 0; i < listProShape.size(); i++) {
                        if (listProShape.get(i).InfoValue.equals(Convert.ToString(model.getProShape()))) {
                            dropProShape.setSelection(i);
                            break;
                        }
                    }

                    customCheckBox.Check(model.getProHazardNature().split(","));
                }
            }
            isFirstLoad = false;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOGStart:
                return new DatePickerDialog(this, mdateListenerStart, mYearStart, mMonthStart, mDayStart);

        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void displayStart() {
        editTransTime.setText(new StringBuffer().append(mYearStart).append("-").append(mMonthStart + 1).append("-").append(mDayStart));
    }



    private DatePickerDialog.OnDateSetListener mdateListenerStart = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYearStart = year;
            mMonthStart = monthOfYear;
            mDayStart = dayOfMonth;
            displayStart();
        }
    };
}


