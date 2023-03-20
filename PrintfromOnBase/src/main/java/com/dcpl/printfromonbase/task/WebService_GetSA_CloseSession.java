package com.dcpl.printfromonbase.task;

import android.content.Context;

import com.google.gson.JsonObject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.net.SocketTimeoutException;

//import android.util.Log;

public class WebService_GetSA_CloseSession {

    private static final String SOAP_ACTION = "http://tempuri.org/";
    private static String SOAP_METHOD = "";
    private static final String SOAP_NAMESPACE = "http://tempuri.org/";

    private static String SOAP_ADDRESS = "";
    private static final String TAG = "LogoffWebService";
    private Object WSResult;
    private String url;

    public WSResult close( String soapmethod, String soapAddress, String sessionID) {

        String sessionid = "";

        String urlString = "https://www.example.com/";

        SOAP_ADDRESS = soapAddress;
        sessionid = sessionID;


        try {

            PropertyInfo propSessionId = new PropertyInfo();


            SOAP_METHOD = soapmethod;
            SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

            propSessionId.setName("sessionID");
            propSessionId.setValue(sessionid);
            propSessionId.setType(String.class);

            request.addProperty(propSessionId);

            httpTransport.call(SOAP_ACTION.concat(SOAP_METHOD), envelope);
            SoapObject soapResponse = (SoapObject) envelope.getResponse();
            String logOffResponse = soapResponse.getProperty("logoffresponse").toString();

          //  Log.d(TAG, "logOff Result : " + logOffResponse);

            WSResult wsResult1 = new WSResult();

            if (!logOffResponse.isEmpty()) {
//                Log.d(TAG, "LoginResult in download method: " + LoginResult);
                wsResult1.closeResult = logOffResponse;
                return wsResult1;
            }


        } catch (Exception ex) {
         //   Log.e(TAG, "Exception: " + ex);
            ex.printStackTrace();
            WSResult wsResult1 = new WSResult();
            wsResult1.closeResult=ex.getMessage();
            return wsResult1;
        }

        return null;
    }

        public class WSResult{
        private String closeResult = "";

        public String getlogOffresult() {
            return closeResult;
        }
    }

}
