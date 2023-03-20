package com.dcpl.printfromonbase.task;

import android.content.Context;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.net.SocketTimeoutException;



public class WebService_GetAD_Login {

    private static final String SOAP_ACTION = "http://tempuri.org/";
    private static String SOAP_METHOD = "";
    private static final String SOAP_NAMESPACE = "http://tempuri.org/";

    private static String SOAP_ADDRESS = "";
    private static final String TAG = "LoginWebService";
    private Object WSResult;
    private String url;

    public WSResult login(Context context, String soapmethod, String soapAddress, String user, String pwd, String doMain) {

        String username = "";
        String password = "";
        String domain = "";

        // String docName = "";

        String urlString = "https://www.example.com/";

        SOAP_ADDRESS = soapAddress;
        username = user;
        password = pwd;
        domain = doMain;
        String LoginResult = "";

        //  HttpURLConnection con = (HttpURLConnection) new URL(urlString).openConnection();

        try {

            PropertyInfo propUserId = new PropertyInfo();
            PropertyInfo propPasswordId = new PropertyInfo();
            PropertyInfo propDomain = new PropertyInfo();


            SOAP_METHOD = soapmethod;
            SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS, 5000);
//            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
//            con.setConnectTimeout(1000);


            propUserId.setName("username");
            propUserId.setValue(username);
            propUserId.setType(String.class);
            propPasswordId.setName("password");
            propPasswordId.setValue(password);
            propPasswordId.setType(String.class);
            propDomain.setName("domain");
            propDomain.setValue(domain);
            propDomain.setType(String.class);


            request.addProperty(propUserId);
            request.addProperty(propPasswordId);
            request.addProperty(propDomain);

            httpTransport.call(SOAP_ACTION.concat(SOAP_METHOD), envelope);
            HttpsTrustManager.allowAllSSL();

            SoapObject soapResponse = (SoapObject) envelope.getResponse();
            LoginResult = soapResponse.getProperty("loginresponse").toString();
           // Log.d(TAG, "loginresponse : " + LoginResult);

            //con.setConnectTimeout(5000);
            WSResult wsResult1 = new WSResult();

            if (!LoginResult.isEmpty()) {
//                Log.d(TAG, "LoginResult in download method: " + LoginResult);
                wsResult1.loginResult = LoginResult;
                return wsResult1;
            }


        } catch (SocketTimeoutException ex) {
//            Log.d(TAG, "SocketTimeOutException: " + ex);
            ex.printStackTrace();
            WSResult wsResult1 = new WSResult();
            wsResult1.setMessage("Communication Problem. Invalid Server Path!!! Please Contact to Administrator.");
            return wsResult1;
        } catch (SocketException ex) {
//            Log.d(TAG, "SocketException: " + ex);
            ex.printStackTrace();
            WSResult wsResult1 = new WSResult();
            wsResult1.setMessage(ex.getMessage());
            return wsResult1;
        } catch (Exception ex) {
//            Log.e(TAG, "Exception: " + ex);
            ex.printStackTrace();
            WSResult wsResult1 = new WSResult();
            wsResult1.setMessage(ex.getMessage());
            return wsResult1;
        }
        // return (con.getResponseCode() == HttpURLConnection.HTTP_OK);

//        else {
//            // wsResult1.setStatus("500");
//            //   wsResult1.setMessage("loginresponse");

//        }
        return (WebService_GetAD_Login.WSResult) WSResult;
    }


    public class WSResult{
        private String message;
        private String loginResult = "";

        private void setMessage(String message) {
            this.message = message;
        }

        // public String getStatus() {
        //       return status;
        //   }

        public String getMessage() {
            return message;
        }
        public String getdocument() {
            return loginResult;
        }
    }

}
