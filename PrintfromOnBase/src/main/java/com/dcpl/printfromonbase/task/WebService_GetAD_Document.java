package com.dcpl.printfromonbase.task;

import android.os.Environment;
import android.util.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;

public class WebService_GetAD_Document {

    private static final File FILE_PATH = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/");
    private static final String TAG = "";
    ;
    private static String SOAP_METHOD = "";
    private static final String SOAP_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ACTION = "http://tempuri.org/";
    private static String SOAP_ADDRESS = "";

    public WebService_GetAD_Document.WSResult doc(String soapMethod, String soapAddress, String sessionid, long docid){

        WebService_GetAD_Document.WSResult wsResult = new WebService_GetAD_Document.WSResult();
        String docPath ="" ;
        SOAP_ADDRESS = soapAddress;

        try {
            PropertyInfo propSessionID= new PropertyInfo();
            PropertyInfo propDocumentId = new PropertyInfo();

          //  Log.d(TAG, "docId: "+docid);

            SOAP_METHOD = soapMethod;
            SoapObject request2 = new SoapObject(SOAP_NAMESPACE,SOAP_METHOD);
            SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope2.dotNet = true;
            envelope2.setOutputSoapObject(request2);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);

            propSessionID.setName("sessionID");
            propSessionID.setValue(sessionid);
            //    propUserName.setType(String.class);

            propDocumentId.setName("docId");
            propDocumentId.setValue(docid);
            propDocumentId.setType(Long.class);

            request2.addProperty(propSessionID);
            request2.addProperty(propDocumentId);

            androidHttpTransport.call(SOAP_ACTION + SOAP_METHOD, envelope2);
            SoapObject resultDocument = (SoapObject) envelope2.getResponse();
           // Log.d(TAG, "resultDocument: " + resultDocument.getProperty("doc"));
            SoapPrimitive obj = (SoapPrimitive) resultDocument.getProperty("doc");

            String Doc = (String) obj.getValue();
            byte[] pdfAsBytes = Base64.decode(Doc, Base64.DEFAULT);
            docPath = "filefromob" + "." + "pdf";

            File folder =  new File(String.valueOf(FILE_PATH));
            if(!folder.exists()){
                folder.mkdir();
            }
            File file =  new File(FILE_PATH , docPath);
            if(file.exists())
                file.delete();
            //   File file = new File(context.getCacheDir(),docPath);
            String absolutePath = file.getAbsolutePath();
           // Log.d(TAG, "absoluteFilePath: " + absolutePath);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(pdfAsBytes);
            fos.flush();
            fos.close();
           // Log.d(TAG, "file created successfully: " + fos);
            wsResult.setDocName(absolutePath);

        } catch (Exception ex) {
           // Log.e(TAG, "Exception: " + ex);
            wsResult.setMessage("File path not exists: " + FILE_PATH + ", message: " +ex.getMessage());
            return wsResult;
        }
        return wsResult;
    }


    public static class WSResult{
        private String status;
        private String message;
        private String docName ="";
        private String DocumentResult ="";

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

        public String GetDocumentResult() {
            return DocumentResult;
        }
    }}
