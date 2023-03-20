package com.dcpl.printfromonbase.task;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
//import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class DownloadService {

    private static final String FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
    private static final String SOAP_ACTION = "https://www.onbase.com/en/";
    private static String SOAP_METHOD = "";
    private static final String SOAP_NAMESPACE = "https://www.onbase.com/en/";
    //private static final String SOAP_ADDRESS = "http://192.168.1.210/OBWebService/OnBaseWebService.asmx";
    private static String SOAP_ADDRESS = "";
    private static final String TAG = "DownloadService";

    public WSResult download(Context context, String documentID, String soapAddress, String user, String pwd){

        String username = "";
        String password = "";
        String docName = "";

        SOAP_ADDRESS = soapAddress;
        username = user;
        password = pwd;

        String sessionId = login(username, password);
        WSResult wsResult1 = new WSResult();

        if(!sessionId.isEmpty()){
            wsResult1 = downloadDoc(context, sessionId,documentID);

            if(wsResult1.getDocName().isEmpty()){
                wsResult1.setStatus("500");
                wsResult1.setMessage(wsResult1.getMessage() + " or file not available on onBase Server!");
            }else{
                wsResult1.setStatus("200");
                wsResult1.setMessage("Redirecting...");
            }
            logout(sessionId);
            return wsResult1;
        }
        else {
            wsResult1.setStatus("500");
            wsResult1.setMessage("onBase Server not connected. Please contact the admin!");
            return wsResult1;
        }
    }

    private String login(String username, String password){

        String sessionId = "";

        try{

            PropertyInfo propUserId = new PropertyInfo();
            PropertyInfo propPasswordId = new PropertyInfo();

            SOAP_METHOD = "FragmentActivity";
            SoapObject request = new SoapObject(SOAP_NAMESPACE,SOAP_METHOD);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

            propUserId.setName("username");
            propUserId.setValue(username);
            propUserId.setType(String.class);
            propPasswordId.setName("password");
            propPasswordId.setValue(password);
            propPasswordId.setType(String.class);

            request.addProperty(propUserId);
            request.addProperty(propPasswordId);

            httpTransport.call(SOAP_ACTION.concat(SOAP_METHOD), envelope);
            SoapObject soapResponse = (SoapObject) envelope.getResponse();
            sessionId = soapResponse.getProperty("session_ID").toString();

            //Log.d(TAG, "SessionID : " + sessionId);

        }
        catch (SocketTimeoutException ex){
            //Log.d(TAG, "SocketTimeOutException: " + ex);
            ex.printStackTrace();
        }
        catch (SocketException ex){
           // Log.d(TAG, "SocketException: " + ex);
            ex.printStackTrace();
        }
        catch(Exception ex) {
            //Log.e(TAG, "Exception: " + ex);
            ex.printStackTrace();
        }
        return sessionId;
    }

    private WSResult downloadDoc(Context context, String sesId, String docId){

        WSResult wsResult = new WSResult();
        String docPath = "";

        try {
            PropertyInfo propSessionId = new PropertyInfo();
            PropertyInfo propDocumentId = new PropertyInfo();

           // Log.d(TAG, "docId: "+docId);

            SOAP_METHOD = "GetDocument";
            SoapObject request2 = new SoapObject(SOAP_NAMESPACE,SOAP_METHOD);
            SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope2.dotNet = true;
            envelope2.setOutputSoapObject(request2);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);

            propSessionId.setName("sessionID");
            propSessionId.setValue(sesId);
            propSessionId.setType(String.class);
            propDocumentId.setName("docId");
            propDocumentId.setValue(docId);
            propDocumentId.setType(Long.class);

            request2.addProperty(propSessionId);
            request2.addProperty(propDocumentId);

            androidHttpTransport.call(SOAP_ACTION + SOAP_METHOD, envelope2);
            SoapObject resultDocument = (SoapObject) envelope2.getResponse();
            //Log.d(TAG, "resultDocument: " + resultDocument.getProperty("doc"));
            SoapPrimitive obj = (SoapPrimitive) resultDocument.getProperty("doc");
            SoapPrimitive extension = (SoapPrimitive) resultDocument.getProperty("type");
//            Log.d(TAG, "obj: "+obj.getName());
           // Log.d(TAG, "obj.getValue.betClass: "+obj.getValue().getClass());
            String Doc = (String) obj.getValue();
            byte[] pdfAsBytes = Base64.decode(Doc, Base64.DEFAULT);
            docPath = docId + "." + extension;

            File folder =  new File(FILE_PATH);
            if(!folder.exists()){
                folder.mkdir();
            }
            File file =  new File(FILE_PATH , docPath);
            //File file = new File(context.getCacheDir(),docPath);
            String absolutePath = file.getAbsolutePath();
          //  Log.d(TAG, "absoluteFilePath: " + absolutePath);
//            Log.d(TAG, "file: "+ file);
//            Log.d(TAG, "document create: " + file);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(pdfAsBytes);
            fos.flush();
            fos.close();
           // Log.d(TAG, "file: " + fos);
            wsResult.setDocName(docPath);

        } catch (Exception ex) {
          //  Log.e(TAG, "Exception: " + ex);
            wsResult.setMessage("File path not exists: " + FILE_PATH + ", message: " +ex.getMessage());
            return wsResult;
        }
        return wsResult;
    }

    private void logout(String sesId){
        try{
            PropertyInfo propSessionId = new PropertyInfo();

            //SOAP_METHOD = "Logoff";
            SoapObject request = new SoapObject(SOAP_NAMESPACE,SOAP_METHOD);  //FragmentActivity is SOAP_METHOD

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

            propSessionId.setName("sessionID");
            propSessionId.setValue(sesId);
            propSessionId.setType(String.class);

            request.addProperty(propSessionId);

            httpTransport.call(SOAP_ACTION.concat(SOAP_METHOD), envelope);
            SoapObject soapResponse = (SoapObject) envelope.getResponse();
            String logOffResponse = soapResponse.getProperty("response").toString();

          //  Log.d(TAG, "logOff Result : " + logOffResponse);

        }catch (Exception ex) {
          //  Log.e(TAG, "Exception: " + ex);
        }
    }

    public class WSResult{
        private String status;
        private String message;
        private String docName = "";

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

        private void setDocName(String docName) {
            this.docName = docName;
        }

        public String getDocName() {
            return docName;
        }
    }
}
