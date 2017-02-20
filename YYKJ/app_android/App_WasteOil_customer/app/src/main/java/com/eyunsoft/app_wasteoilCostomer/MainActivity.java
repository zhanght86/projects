package com.eyunsoft.app_wasteoilCostomer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.eyunsoft.app_wasteoilCostomer.Model.CompanyCustomer_Model;
import com.eyunsoft.app_wasteoilCostomer.Model.SysNoticeRead_Model;

import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;
import com.eyunsoft.app_wasteoilCostomer.Publics.MsgBox;
import com.eyunsoft.app_wasteoilCostomer.Publics.NetWork;
import com.eyunsoft.app_wasteoilCostomer.Publics.TitleSet;
import com.eyunsoft.app_wasteoil.R;
import com.eyunsoft.app_wasteoilCostomer.bll.CompanyUser_BLL;
import com.eyunsoft.app_wasteoilCostomer.bll.Notice_BLL;

import com.eyunsoft.app_wasteoilCostomer.utils.LoadDialog.LoadDialog;
import com.eyunsoft.app_wasteoilCostomer.utils.PollingMsg.PollingService;
import com.eyunsoft.app_wasteoilCostomer.utils.PollingMsg.PollingUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class MainActivity extends AppCompatActivity {

    private long exitTime = 0;//退出次数



    public Button btnRec;//生产管理
    public Button btnReport;//生产上报
    public Button btnVehDisp;//签收确认
    public Button btnTranRecAccept;//抢单
    public Button btnNotice;//通知管理


    private Switch recStatus;





    //登录信息
    public TextView txtUserName;
    public TextView txtUserFullName;
    public TextView txtComBrName;

    private MyReceiver receiver = null;


    private  boolean isRunning=false;
    private boolean loginSuccess=false;

    //声明消息管理器
    NotificationManager mNotifyManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TitleSet.SetTitle(this,0);
        processThread();


        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PollingService.ACTION);
        MainActivity.this.registerReceiver(receiver, filter);

        //网点
        txtComBrName=(TextView)findViewById(R.id.txtComBrName);
        txtComBrName.setText(((App)getApplication()).getComBrName());

        //姓名
        txtUserFullName=(TextView)findViewById(R.id.txtUserFullname);
        txtUserFullName.setText(((App)getApplication()).getUserFullname());

        //帐号
        txtUserName=(TextView)findViewById(R.id.txtUserName);
        txtUserName.setText(((App)getApplication()).getUserName());

        recStatus=(Switch) findViewById(R.id.receiveStatus);
        recStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)//
                {
                    PollingUtils.stopPollingService(MainActivity.this, PollingService.class, PollingService.ACTION);
                    isRunning=false;
               }
                else
                {
                    PollingUtils.startPollingService(MainActivity.this, 5, PollingService.class, PollingService.ACTION);
                    isRunning=true;
                }
            }
        });

        //生产管理
        btnRec=(Button)findViewById(R.id.btnProRec);
        btnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetWork.isNetworkAvailable(MainActivity.this))
                {
                    MsgBox.Show(MainActivity.this,"网络不可用，请稍后再试");
                    return;
                }
                Intent myIntent=new Intent(MainActivity.this,ProdRec.class);
                startActivity(myIntent);
            }
        });

        //生产上报
        btnReport=(Button)findViewById(R.id.btnProdRecReport);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NetWork.isNetworkAvailable(MainActivity.this))
                {
                    MsgBox.Show(MainActivity.this,"网络不可用，请稍后再试");
                    return;
                }
                Intent myIntent=new Intent(MainActivity.this,ProdRecReport.class);
                startActivity(myIntent);
            }
        });

        //调度配送
        btnVehDisp=(Button)findViewById(R.id.btnVehDisp);
        btnVehDisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetWork.isNetworkAvailable(MainActivity.this))
                {
                    MsgBox.Show(MainActivity.this,"网络不可用，请稍后再试");
                    return;
                }

                Intent myIntent=new Intent(MainActivity.this,VehDisp.class);
                startActivity(myIntent);
            }
        });

        //抢单
        btnTranRecAccept=(Button)findViewById(R.id.btnTranRecAccept);
        btnTranRecAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetWork.isNetworkAvailable(MainActivity.this))
                {
                    MsgBox.Show(MainActivity.this,"网络不可用，请稍后再试");
                    return;
                }

                Intent myIntent=new Intent(MainActivity.this,TransferRecstate.class);
                startActivity(myIntent);
            }
        });



        btnNotice=(Button)findViewById(R.id.btnNotice);
        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!NetWork.isNetworkAvailable(MainActivity.this))
                {
                    MsgBox.Show(MainActivity.this,"网络不可用，请稍后再试");
                    return;
                }
                Intent myIntent=new Intent(MainActivity.this,CircleActivity.class);
                startActivity(myIntent);
            }
        });


        //司机不能接收
        if(((App)getApplication()).getIsVehicleDriver())
        {
            recStatus.setEnabled(false);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                if(isRunning)
                {
                    PollingUtils.stopPollingService(MainActivity.this, PollingService.class, PollingService.ACTION);
                }
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取广播数据
     *
     * @author jiqinlin
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            String noticeNumber=bundle.getString("NoticeRecNumber");
            int recType=Convert.ToInt32(bundle.getString("RecType"));
            long noticeId=bundle.getLong("NoticeID");
            System.out.println("noticeNumber:"+noticeNumber);

            long comId=((App)getApplication()).getSysComID();
            long userId=((App)getApplication()).getCompanyUserID();
            long comBrId=((App)getApplication()).getSysComBrID();

            SysNoticeRead_Model model=new SysNoticeRead_Model();
            model.setCreateUserID(userId);
            model.setCreateComBrID(comBrId);
            model.setCreateComID(comId);

            model.setNoticeID(noticeId);

            Notice_BLL.ReadNotice(model);

            if(recType==0)//普通
            {
                String msg="您有一条新的通知，点击查持详细!";
                Intent myIntent=new Intent(MainActivity.this,NoticeDetail.class);
                myIntent.putExtra("NoticeId",  noticeId);
                SendNotice(noticeId,"您有一条新的通知，点击查持详细",myIntent,msg,false,false);
            }
            else if(recType==1)//转运联单
            {
                String msg="点击查看明细!";
                Intent myIntent=new Intent(MainActivity.this,TransferrecInfo.class);

                myIntent.putExtra("RecNumber", noticeNumber);
                SendNotice(noticeId,"有新的联单通过审核",myIntent,msg,false,false);
            }
            else if(recType==2)//调度
            {
                String msg="点击查看明细!";
                Intent myIntent=new Intent(MainActivity.this,VehdispInfo.class);
                myIntent.putExtra("RecNo",  noticeNumber);
                SendNotice(noticeId,"有新的调度信息",myIntent,msg,false,false);
            }
        }
    }

    public void SendNotice(long requestCode,String title,Intent mIntent,String content,boolean isCover,boolean isRepeat)
    {
        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentTitle(title);
        builder.setContentText(content);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//这行代码会解决此问题
        mIntent.addFlags(Intent.FILL_IN_DATA);
      //  builder.addExtras(bundle);
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyManager.notify((int) requestCode, builder.build());

      /*  if(!isRepeat) {
            StatusBarNotification[] notifyList = mNotifyManager.getActiveNotifications();
            if (notifyList.length > 0) {
                for (int i = 0; i < notifyList.length; i++) {
                    if (notifyList[i].getId() == requestCode) {
                        if(isCover)
                        {
                            mNotifyManager.cancel(requestCode);
                            mNotifyManager.notify(requestCode, builder.build());
                        }
                        return;
                    }
                }
            }
        }
        else
        {

        }*/

    }

    //定义Handler对象
    private Handler handler =new Handler(){
        @Override
        //当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            //下载完成
            if(msg.what==1) {
                LoadDialog.dismiss(MainActivity.this);
                if(loginSuccess)
                {
                    Intent login=new Intent(MainActivity.this,CircleActivity.class);
                    startActivity(login);
                }else{
                    Intent login=new Intent(MainActivity.this,LoginCustomer.class);
                    startActivity(login);
                }
            }

        }
    };
    private void processThread() {
        //构建一个进度条
        new Thread() {
            public void run() {

                CompanyCustomer_Model model = new CompanyCustomer_Model();
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("App_WasteOil", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(sharedPreferences.getBoolean("isRemember",false)){
                        String userN=sharedPreferences.getString("userName","");
                        String userp=sharedPreferences.getString("userPwd","");
                        model.setLoginAccount(userN);
                        model.setLoginPwd(userp);
                    }else{
                        return;
                    }
                if(!NetWork.isNetworkAvailable(MainActivity.this))
                {
                    MsgBox.Show(MainActivity.this,"网络不可用，请稍后再试");
                    return;
                }

                long comID = ((App) getApplication()).getSysComID();
                String msg = CompanyUser_BLL.SysLogin_CompanyCustomer(model, comID);
                if (!TextUtils.isEmpty(msg)) {
                     if (msg.contains("anyType{}]"))//登录成功
                    {
                        String jsonStr = msg.substring(1, msg.length()).replace(", anyType{}]", "");
                        try {

                            JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                            String LoginAccount = jsonObject.getString("LoginAccount");
                            String ComCusNumber = jsonObject.getString("ComCusNumber");
                            String Fullname = jsonObject.getString("Fullname");
                            String ComCusComName = jsonObject.getString("ComCusComName");
                            String combrName = jsonObject.getString("ComBrName");
                            String ComName = jsonObject.getString("ComName");
                            String ComGrade = jsonObject.getString("ComGrade");
                            long ComCusID = Convert.ToInt64(jsonObject.getString("ComCusID"));
                            long ComArea = Convert.ToInt64(jsonObject.getString("ComArea"));
                            long comBrID = Convert.ToInt64(jsonObject.getString("ComBrID"));
                            comID = Convert.ToInt64(jsonObject.getString("ComID"));

                            ((App) getApplication()).setUserName(model.getLoginAccount());
                            ((App) getApplication()).setCompanyUserID(ComCusID);
                            ((App) getApplication()).setSysComBrID(comBrID);
                            ((App) getApplication()).setSysComID(comID);
                            ((App) getApplication()).setUserFullname(Fullname);
                            ((App) getApplication()).setComBrName(combrName);
                                loginSuccess=true;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                Message msgs = new Message();
                msgs.what=1;
                handler.sendMessage(msgs);
            }
        }.start();
    };

}