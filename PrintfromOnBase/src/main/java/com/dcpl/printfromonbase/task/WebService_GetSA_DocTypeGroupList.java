package com.dcpl.printfromonbase.task;

import android.annotation.SuppressLint;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class WebService_GetSA_DocTypeGroupList {
    private static final String SOAP_ACTION = "http://tempuri.org/";
    private static String SOAP_METHOD = "";
    private static final String SOAP_NAMESPACE = "http://tempuri.org/";

    private static String SOAP_ADDRESS = "";
    private static final String TAG = "GetDocumentTypeWebService";

    public WebService_GetSA_DocTypeGroupList.WSResult get( String soapmethod, String soapAddress, String sessionid){

        String sessionID = sessionid;
        String DocTypeListResponse ="";
        SOAP_ADDRESS = soapAddress;
        // String DocTypeListResponse = "";
        ArrayList<String> list = new ArrayList<>();

        try{

            PropertyInfo propsessionid = new PropertyInfo();

            SOAP_METHOD = soapmethod;
            SoapObject request = new SoapObject(SOAP_NAMESPACE,SOAP_METHOD);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

            propsessionid.setName("sessionID");
            propsessionid.setValue(sessionID);

            request.addProperty(propsessionid);

            httpTransport.call(SOAP_ACTION.concat(SOAP_METHOD), envelope);

            // SoapObject soapResponse = (SoapObject) envelope.getResponse();
//            String Text = soapResponse.getProperty(1).toString();
//            System.out.println("get : "  + Text);

            ///      DocTypeListResponse = soapResponse.getProperty("documenttypelist") .toString();

            SoapObject obj1 = (SoapObject) envelope.bodyIn;
            SoapObject obj2 =(SoapObject) obj1.getProperty(0);

            for(int i=0; i<obj2.getPropertyCount(); i++)
            {
                SoapObject obj3 =(SoapObject) obj2.getProperty(i);
////                int id= Integer.parseInt(obj3.getProperty(0).toString());
//
                for(int j=0; j<obj3.getPropertyCount(); j++)
                {
                    String s = obj3.getProperty(j).toString();
//                    Log.d(TAG, j + ". " + s);
                    list.add(s);
                }
            }

//            for(int i = 0; i < soapResponse.getPropertyCount(); i++) {
//                list.add(soapResponse.getProperty(i).toString());
//                Log.d(TAG, i + ". " + soapResponse.getProperty(i).toString() );
//            }
//            Log.d(TAG, "List size: " + list.size() );

        }
        catch (SocketTimeoutException ex){
//            Log.d(TAG, "SocketTimeOutException: " + ex);
            ex.printStackTrace();
        }
        catch (SocketException ex){
//            Log.d(TAG, "SocketException: " + ex);
            ex.printStackTrace();
        }
        catch(Exception ex) {
//            Log.e(TAG, "Exception: " + ex);
            ex.printStackTrace();
        }

        WebService_GetSA_DocTypeGroupList.WSResult wsResult1 = new WebService_GetSA_DocTypeGroupList.WSResult();

        if(!list.isEmpty()){
//            Log.d(TAG, "getDocTypeList is fetched successfully");
            wsResult1.doctypelist = list;
            return wsResult1;
        }
        else {
            wsResult1.setStatus("500");
            wsResult1.setMessage("onBase Server not connected. Please contact the admin!");
            return wsResult1;
        }
    }


    public class WSResult{
        private String status;
        private String message;

        private ArrayList<String> doctypelist;

        private void setStatus(String status) {
            this.status = status;
        }

        private void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
        public ArrayList getGetdocumenttypelist() {
            return doctypelist;
        }


    }

}
