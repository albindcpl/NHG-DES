package com.dcpl.printfromonbase.task;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;



public class WebService_GetSA_DocList {

    private static final String SOAP_ACTION = "http://tempuri.org/";
    private static String SOAP_METHOD = "";
    private static final String SOAP_NAMESPACE = "http://tempuri.org/";

    private static String SOAP_ADDRESS = "";
    private static final String TAG = "GetDocListWebService";

    public WebService_GetSA_DocList.WSResult get(String soapmethod, String soapAddress, String SessionID,String doctypegroup , String doctype ,ArrayList kwtype, ArrayList kwvalue,String fulltextsearchvalue){
        ArrayList<String> namelist = new ArrayList<>();
        ArrayList<Long> idlist = new ArrayList<>();
        String sessionid = "";
        String documenttype = "";
        String dtg ="";
        if(kwtype.size()==0)
        {
            kwtype.add("null");
            kwvalue.add("null");
        }
        List<String> kwType = kwtype;
        List<String> kwValue = kwvalue;
        String fullTextSearch = "";

        SOAP_METHOD = soapmethod;
        SOAP_ADDRESS = soapAddress;
        sessionid = SessionID;
        documenttype= doctype;
        dtg = doctypegroup;


        fullTextSearch = fulltextsearchvalue;



        SoapObject obj1,obj2;

        WebService_GetSA_DocList.WSResult wsResult1 = new WSResult();
//
//        Log.i("", "Information of all passing param:");
//        Log.i("", "doctype - " + documenttype);
//        Log.i("", "full text - " + fullTextSearch);
        for (String s:kwType) {
         //   Log.i("", "kw type - " + s);
        }
        for (String s:kwValue) {
          //  Log.i("", "kw value - " + s);
        }

        if(!sessionid.isEmpty()){
            try{

                PropertyInfo propsessionID = new PropertyInfo();
                PropertyInfo propPassword = new PropertyInfo();
                PropertyInfo propdt = new PropertyInfo();
                PropertyInfo propfullTextSearch = new PropertyInfo();
                PropertyInfo propdtg = new PropertyInfo();


                SoapObject request = new SoapObject(SOAP_NAMESPACE,SOAP_METHOD);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

                propsessionID.setName("sessionID");
                propsessionID.setValue(sessionid);


                propdt.setName("doctype");
                propdt.setValue(documenttype);
                propdt.setType(String.class);

                propfullTextSearch.setName("fulltextsearchvalue");
                propfullTextSearch.setValue(fullTextSearch);
                propfullTextSearch.setType(String.class);

                propdtg.setName("doctypegroup");
                propdtg.setValue(dtg);
                propdtg.setType(String.class);


                request.addProperty(propsessionID);
                request.addProperty(propdt);
                request.addProperty(propfullTextSearch);
                request.addProperty(propdtg);

                SoapObject soapLogs = new SoapObject(SOAP_NAMESPACE, "kwtype");
                for (String i : kwType){
                    soapLogs.addProperty("string", i);
                }
                request.addSoapObject(soapLogs);
                SoapObject soapLogs1 = new SoapObject(SOAP_NAMESPACE, "kwvalue");
                for (String i : kwValue){
                    soapLogs1.addProperty("string", i);
                }
                request.addSoapObject(soapLogs1);

                SoapSerializationEnvelope IPenvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                IPenvelope.dotNet = true;
                IPenvelope.setOutputSoapObject(request);

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
                               // Log.d(TAG, i+"."+j + ". " + s);
                                namelist.add(s);
                            }
                        }
                        else if (i==1)
                        {
                            SoapObject obj3 =(SoapObject) obj2.getProperty(i);
                            for(int j=0; j<obj3.getPropertyCount(); j++)
                            {
                                String s = obj3.getProperty(j).toString();
                              //  Log.d(TAG, "doclist"+i+"."+j + ". " + s);
                                idlist.add(Long.valueOf(s));
                            }

                        }
                       // Log.d(TAG, "getDocList is fetched successfully");
//                        wsResult1.username = username;
                        wsResult1.docnamelist = namelist;
                        wsResult1.docidlist = idlist;

                    }
                    return wsResult1;
                }
            }
            catch (SocketTimeoutException ex){
               // Log.d(TAG, "SocketTimeOutException: " + ex);
                ex.printStackTrace();
            }
            catch (SocketException ex){
               // Log.d(TAG, "SocketException: " + ex);
                ex.printStackTrace();
            } catch (HttpResponseException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            catch(Exception ex) {
//               // Log.e(TAG, "Exception: " + ex);
//                ex.printStackTrace();
//            }
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
        private String username = "";
        private ArrayList<Long> docidlist = new ArrayList<Long>();
        private ArrayList<String> docnamelist;

        private void setStatus(String status) {
            this.status = status;
        }

        private void setMessage(String message) {

            this.message = message;
        }

        public ArrayList getGetdocumentnamelist() {
            return docnamelist;
        }
        public ArrayList getGetdocumentidlist() {
            return  docidlist;
        }
    }

}
