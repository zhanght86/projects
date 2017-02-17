package com.yiyun.wasteoilcustom.bll;


import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.entity.ResultMsg;
import com.chengyi.android.util.Convert;
import com.example.fornet.WebServiceUtils;
import com.yiyun.wasteoilcustom.model.ProduceRec_Model;
import com.yiyun.wasteoilcustom.model.TransferRec_Model;
import com.yiyun.wasteoilcustom.model.VehicleDispath_Model;
import com.yiyun.wasteoilcustom.util.WastoilWebServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Identity.NAMESPACE;


/**
 * Created by Administrator on 2016/12/26.
 */

public class ProdRec_BLL {
    static String  webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";
    static HashMap<String, Object> properties  = new HashMap<>();


    /**
     * 生产单-加载数据
     * @param recNumber
     * @return
     */
    public static void ProdRec_LoadData(String recNumber)
    {
        String methodName = "ProdRec_LoadData";

        properties.clear();
        properties.put("recNumber",recNumber);

        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties, "ProduceRec_Model", ProduceRec_Model.class, new WebServiceUtils.WebServiceCallBack() {
            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject regSopa) {
                ProduceRec_Model mo=new ProduceRec_Model();
                int cout = regSopa.getPropertyCount();
                if (cout > 0) {
                    for (int i = 0; i < regSopa.getPropertyCount(); i++) {
                        String value = regSopa.getProperty(i).toString();
                        if (value.contentEquals("anyType{}")) {
                            value = "";
                        }

                        mo.setProperty(i, value);
                    }
                    mo.setExist(true);
                }
                Scope.activity.scope.key("ProdRec_LoadData").val(mo);
            }
        });
    }
    /**
     * 添加生产单
     * @param model
     * @return
     */
    public static void ProdRec_Add(ProduceRec_Model model)
    {
        final String methodName = "ProdRec_Add";
        

       properties.clear();
        properties.put("mo",model);
        properties.put("recNumber","");

        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties, "ProduceRec_Model", ProduceRec_Model.class, new WebServiceUtils.WebServiceCallBack() {
            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject regSopa) {
                String resatult= regSopa.getProperty(0).toString();
                ResultMsg resultMsg=new ResultMsg();
                if (resatult.startsWith("[anyType{}"))//执行成功
                {resultMsg.setTure(true);
                } else if (resatult.endsWith("anyType{}]")) {
                    resultMsg.setMsg( resatult.replace("[", "").replace("anyType{}]", ""));
                } else
                    resultMsg.setMsg("未知错误");
                Scope.activity.scope.key(methodName).val(resultMsg);
            }
        });
    }

    /**
     * 修改生产单
     * @param model
     * @return
     */
    public static void ProdRec_Update(ProduceRec_Model model){
        final String methodName = "ProdRec_Update";
        
       properties.clear();
        properties.put("mo",model);

        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties, "ProduceRec_Model", ProduceRec_Model.class, new WebServiceUtils.WebServiceCallBack() {
                    //WebService接口返回的数据回调到这个方法中
                    @Override
                    public void callBack(SoapObject regSopa) {
                        String resatult = regSopa.getProperty(0).toString();
                        ResultMsg resultMsg = new ResultMsg();
                        if (resatult.equals("anyType{}")) {
                            resultMsg.setTure(true);resultMsg.setMsg("修改成功");
                        } else {
                            resultMsg.setMsg("修改失败");
                        }
                        Scope.activity.scope.key(methodName).val(resultMsg);
                    }
        });
    }

    /**
     * 删除生产单
     * @param recNumber
     * @return
     */
    public static void ProdRec_Delete(String recNumber)
    {
        final String methodName = "ProdRec_Delete";
        

       properties.clear();
        properties.put("recNumber",recNumber);

        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties, "ProduceRec_Model", ProduceRec_Model.class, new WebServiceUtils.WebServiceCallBack() {
            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject regSopa) {
                String resatult = regSopa.getProperty(0).toString();
                ResultMsg resultMsg = new ResultMsg();
                if (resatult.equals("anyType{}")) {
                    resultMsg.setTure(true);
                    resultMsg.setMsg("删除成功");
                } else {
                    resultMsg.setMsg("删除失败");
                }
                Scope.activity.scope.key(methodName).val(resultMsg);
            }
        });
    }


    /**
     *  订单查询
     * @param Condition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static void ProdRec_Select(final String Condition, int pageIndex, int pageSize) {

        final String methodName = "ProdRec_Select";
        properties.clear();

        properties.put("pageIndex", pageIndex);
        properties.put("pageSize", pageSize);
        properties.put("Condition", "[" + Condition + "]");
        properties.put("orderField", "");
        properties.put("orderType", "");

        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService("http://125.46.79.254:8211/Offer/Produce.asmx", methodName, properties, null, null, new WebServiceUtils.WebServiceCallBack() {
            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject regSopa) {
                String jsonStr = regSopa.getProperty(0).toString();
                List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                try {
                    if (!jsonStr.equals("anyType{}")) {
                        JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();

                            if (jsonObject.has("RecNumber")) {
                                hashMap.put("RecNumber", jsonObject.get("RecNumber"));
                            } else {
                                continue;

                            }
                            if (jsonObject.has("RecTime")) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String t = format.format(Convert.ToDate(jsonObject.get("RecTime").toString()));
                                hashMap.put("RecTime", t);
                            } else {
                                hashMap.put("RecTime", "");
                            }

                            if (jsonObject.has("ProCategoryName")) {

                                hashMap.put("ProCategoryName", jsonObject.get("ProCategoryName"));
                            } else {
                                continue;
                            }

                            if (jsonObject.has("ProShapeName")) {
                                hashMap.put("ProShapeName", jsonObject.get("ProShapeName"));
                            } else {
                                hashMap.put("ProShapeName", "--");
                            }


                            if (jsonObject.has("ProNumber")) {
                                hashMap.put("ProNumber", jsonObject.get("ProNumber").toString() + jsonObject.get("ProMeasureUnitName").toString());
                            } else {
                                hashMap.put("ProNumber", "--");
                            }

                            if (jsonObject.has("Remark")) {
                                hashMap.put("Remark", jsonObject.get("Remark"));
                            } else {
                                hashMap.put("Remark", "");
                            }

                            if (jsonObject.has("CreateComBrName")) {
                                hashMap.put("CreateComBrName", jsonObject.get("CreateComBrName"));
                            } else {
                                hashMap.put("CreateComBrName", "");
                            }

                            if (jsonObject.has("CreateUserFullname")) {
                                hashMap.put("CreateUserFullname", jsonObject.get("CreateUserFullname"));
                            } else {
                                hashMap.put("CreateUserFullname", "");
                            }

                            if (jsonObject.has("ProHazardNature")) {
                                hashMap.put("ProHazardNature", jsonObject.get("ProHazardNature"));
                            } else {
                                hashMap.put("ProHazardNature", "");
                            }

                            list.add(hashMap);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Scope.activity.scope.key(methodName).val(list);
            }
        });
    }
}
