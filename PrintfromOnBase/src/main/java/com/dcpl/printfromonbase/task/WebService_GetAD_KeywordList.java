package com.dcpl.printfromonbase.task;

import android.annotation.SuppressLint;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

//import android.util.Log;

public class WebService_GetAD_KeywordList {

    private static final String SOAP_ACTION = "http://tempuri.org/";
    private static String SOAP_METHOD = "";
    private static final String SOAP_NAMESPACE = "http://tempuri.org/";

    private static String SOAP_ADDRESS = "";
    private static final String TAG = "GetkeywordNameWebService";
    public WebService_GetAD_KeywordList.WSResult get(String soapmethod, String soapAddress, String sessionid,String doctype){
        ArrayList<String> keywordist = new ArrayList<>();
        ArrayList<String> datatypelist = new ArrayList<>();
        ArrayList<String> detaillist = new ArrayList<>();
        String sessionID = "";
        String documenttype = "";

        SOAP_METHOD = soapmethod;
        SOAP_ADDRESS = soapAddress;
        sessionID = sessionid;
        documenttype= doctype;



        SoapObject obj1,obj2;

        WebService_GetAD_KeywordList.WSResult wsResult1 = new WSResult();

        if(!sessionID.isEmpty()){
            try{

                PropertyInfo propSessionID = new PropertyInfo();
                PropertyInfo propdt = new PropertyInfo();


                SoapObject request = new SoapObject(SOAP_NAMESPACE,SOAP_METHOD);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

               propSessionID.setName("sessionID");
               propSessionID.setValue(sessionID);

                propdt.setName("doctype");
                propdt.setValue(documenttype);
                propdt.setType(String.class);



                request.addProperty(propSessionID);
                request.addProperty(propdt);


                httpTransport.call(SOAP_ACTION.concat(SOAP_METHOD), envelope);

                if (envelope.bodyIn instanceof SoapFault) {
                    String str = ((SoapFault) envelope.bodyIn).faultstring;
                    //  Log.i("", str);

                    return wsResult1;
                }
                else
                {

                    obj1 = (SoapObject) envelope.bodyIn;
                    obj2 =(SoapObject) obj1.getProperty(0);

                    for(int i=0; i<obj2.getPropertyCount(); i++)
                    {
                        if(i==0)
                        {
                            SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                            for(int j=0; j<obj3.getPropertyCount(); j++)
                            {
                                String s = obj3.getProperty(j).toString();
                                //   Log.d(TAG, i+"."+j + ". " + s);
                                keywordist.add(s);
                            }
                        }
                        else if (i==1) {
                            SoapObject obj3 = (SoapObject) obj2.getProperty(i);
                            for (int j = 0; j < obj3.getPropertyCount(); j++) {
                                String s = obj3.getProperty(j).toString();
                                //  Log.d(TAG,  i + "." + j + ". " + s);
                                datatypelist.add(s);
                            }
                        }
                        else if (i==2)
                        {
                            SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                            for(int j=0; j<obj3.getPropertyCount(); j++)
                            {
                                String s = obj3.getProperty(j).toString();
                                // Log.d(TAG, i+"."+j + ". " + s);
                                detaillist.add(s);
                            }

                        }

                        //Log.d(TAG, "sessionID in download method: "+ sessionID);
                        //wsResult1.sessionID = sessionID;
                        wsResult1.kwnamelist = keywordist;
                        wsResult1.kwdatatypelist = datatypelist;
                        wsResult1.kwdetaillist = detaillist;

                    }
                    return wsResult1;
                }
            }
            catch (SocketTimeoutException ex){
                // Log.d(TAG, "SocketTimeOutException: " + ex);
                ex.printStackTrace();
            }
            catch (SocketException ex){
                //    Log.d(TAG, "SocketException: " + ex);
                ex.printStackTrace();
            }
            catch(Exception ex) {
                //   Log.e(TAG, "Exception: " + ex);
                ex.printStackTrace();
            }
            return wsResult1;
        }
        else {
            wsResult1.setStatus("500");
            wsResult1.setMessage("onBase Server not connected. Please contact the admin!");
            return wsResult1;
        }
    }



    public class WSResult{
        public ArrayList<String> kwdatatypelist;
        public ArrayList<String> kwdetaillist;
        private String status;
        private String message;
        public ArrayList<String> kwnamelist;

        private void setStatus(String status) {
            this.status = status;
        }

        private void setMessage(String message) {

            this.message = message;
        }

        public ArrayList getkwnamelist() {
            return kwnamelist;
        }
        public ArrayList getkwdatatypelist() {
            return kwdatatypelist;
        }
        public ArrayList getkwdetailslist() {
            return kwdetaillist;
        }

    }

}
