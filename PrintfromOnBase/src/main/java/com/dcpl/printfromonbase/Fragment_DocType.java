package com.dcpl.printfromonbase;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.EntityDeletionOrUpdateAdapter;

import com.dcpl.printfromonbase.task.WebService_AD_GetAllDocTypeList;
import com.dcpl.printfromonbase.task.WebService_GetAD_DocList;
import com.dcpl.printfromonbase.task.WebService_GetAD_DocTypeGroupList;
import com.dcpl.printfromonbase.task.WebService_GetAD_DocTypeList;
import com.dcpl.printfromonbase.task.WebService_GetAD_Document;
import com.dcpl.printfromonbase.task.WebService_GetAD_KeywordList;
import com.dcpl.printfromonbase.task.WebService_GetSA_CloseSession;
import com.dcpl.printfromonbase.task.WebService_GetSA_DocList;
import com.dcpl.printfromonbase.task.WebService_GetSA_DocTypeGroupList;
import com.dcpl.printfromonbase.task.WebService_GetSA_DocTypeList;
import com.dcpl.printfromonbase.task.WebService_GetSA_KeywordList;
import com.dcpl.printfromonbase.task.WebService_GetSA_AllDocTypeList;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_DocType extends Fragment {
  private static final String TAG = "mytag";
  private static final Object INPUT_METHOD_SERVICE = "";
  public static String UserName;
  public static String Password;
  public static String SessionID;
  public static String ad_username = "";
  public static String ad_password = "";
  public static String ad_domain = "";
  ListView lv;
  public static EditText edfullTextSearch;
  public   static Button btnfind;
  SpinnerAdapter All;
  public static ArrayList documenttypegrouplist = new ArrayList();
  String soapAddress = OnBase_Login_Screen.soapAddress;
  String getdoctypegroupsoapmethod = "SA_GetAllDocTypeGroupList";
  String getalldoctypesoapmethod = "SA_GetAllDocTypeList";
  String getselecteddocListsoapmethod = "SA_SelectedDocTypeList";
  String getkeywordListsoapmethod = "SA_GetKeywordList";
  String getdocListsoapmethod = "SA_GetDocList";

  String getdoctypegroupADsoapmethod = "AD_GetAllDocTypeGroupList";
  String getalldoctypeADsoapmethod = "AD_GetAllDocTypeList";
  String getselecteddocListADsoapmethod = "AD_SelectedDocTypeList";
  String getkeywordListADsoapmethod = "AD_GetKeywordList";
  String getdocListADsoapmethod = "AD_GetDocList";
  View v;
  SearchView searchView;
  Context context;
  public static RecyclerView recyclerView;
  private String SelectedDocTypeGroup;
  private String SelectedDocType;
  private String SelectedDTG;
  String fullTextSearch;
  private String textView;
  public static FirstFragmentAdapter keyword_adapter;
  public static ArrayList<String> doctypegrouplist = new ArrayList();
  public static ArrayList<String> alldoctypelist = new ArrayList();
  public static ArrayList<String> doctypelist = new ArrayList();

  public static ArrayList<String>kwnamelist  = new ArrayList<>();
  public static ArrayList kwdatatypelist = new ArrayList();
  public static ArrayList kwdetailslist = new ArrayList();
  public static ArrayList<String> keywordtypenamelist = new ArrayList<String>();
  public static ArrayList<String> keywordtypevaluelist = new ArrayList<String>();
  public static ArrayList<String> enteredkeywordtypenamelist = new ArrayList<String>();
  public static ArrayList enteredkeywordtypevaluelist = new ArrayList<String>();
  public static  ArrayList docnamelist = new ArrayList();
  public static ArrayList<Long> docidlist = new ArrayList<Long>();
  private long docid;
  ListView listView;
  public static  FragmentAdapter_Documents mMyAdapter;
  Button btnclear;
  public static Boolean adauth;
  public static Spinner spinner_DTG ;
  public static Spinner spinner_dt ;
  public static ArrayAdapter<String> adapter;
  static View view;
  private static Context mContext;


  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                           final Bundle savedInstanceState) {

//        if(FragmentActivity.ad==true){
//            SessionID = FragmentActivity.sessionID;
//            doctypegrouplist.add("All");
//            alldoctypelist.add("All");
//            new DocumentTypeGroupListWS(this).execute();
//            new DocumetTypeALLWS(Fragment_DocType.this).execute();
//            v = inflater.inflate(R.layout.firstfragment, container, false);
//
//            //  lv = v.findViewById(R.id.listitem);
//            spinner_DTG = v.findViewById(R.id.spSpinnerDTG);
//            spinner_dt = v.findViewById(R.id.spSpinnerDT);
//            recyclerView = (RecyclerView) v.findViewById(R.id.rvRecyclerView);
//
//            edfullTextSearch = v.findViewById(R.id.edFullTextSearch);
//            btnfind = v.findViewById(R.id.btnFind);
//
//            btnclear = v.findViewById(R.id.btnClear);
//            btnclear.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Fragment_Documents.clickeddocid = Long.valueOf(0);
//                    Fragment_Documents.listView.setAdapter(null);
//                    recyclerView.setAdapter(null);
//                    recyclerView.setAdapter(keyword_adapter);
//                    kwnamelist.clear();
//                    edfullTextSearch.setText("");
//                    edfullTextSearch.clearFocus();
//                    resetSpinner();
//                }
//            });
//            keyword_adapter = new FirstFragmentAdapter(getActivity(), kwnamelist, kwdatatypelist, kwdetailslist);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.setAdapter(keyword_adapter);
//            keyword_adapter.clear();
//            FirstFragmentAdapter.edittextlist.clear();
//
////        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////        recyclerView.setAdapter(keyword_adapter);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, doctypegrouplist);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner_DTG.setAdapter(adapter);
//
//
//            btnfind.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (edfullTextSearch.getText().toString().isEmpty())
//                        fullTextSearch = "null";
//                    else
//                        fullTextSearch = edfullTextSearch.getText().toString();
////                Log.d(TAG,fullTextSearch);
//
//                    Fragment_Documents.clickeddocid = Long.valueOf(0);
//                    enteredkeywordtypevaluelist.clear();
//                    enteredkeywordtypenamelist.clear();
////                System.out.println("listview size: " + recyclerView.getAdapter().getItemCount());
//                    for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
//
//
//                        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
//                        if (null != holder) {
//                            holder.itemView.findViewById(R.id.edKeyword).setVisibility(View.VISIBLE);
//                        }
//
////                    Log.d(TAG,"Keyword value " + i + ": " + FirstFragmentAdapter.edittextlist.get(i).toString());
//                        if (FirstFragmentAdapter.edittextlist.get(i).toString() == "" || FirstFragmentAdapter.edittextlist.get(i).toString().isEmpty() || FirstFragmentAdapter.edittextlist.get(i).toString() == null) {
//                        } else {
//                            enteredkeywordtypevaluelist.add(FirstFragmentAdapter.edittextlist.get(i).toString());
//                            enteredkeywordtypenamelist.add(keywordtypenamelist.get(i));
//                        }
//                    }
//                    docnamelist.clear();
//                    listView = Fragment_Documents.V.findViewById(R.id.listView);
//                    Fragment_Documents.progressBar.setVisibility(View.VISIBLE);
//                    mMyAdapter = new FragmentAdapter_Documents(getActivity(), docnamelist);
//                    listView.setAdapter(mMyAdapter);
//                    mMyAdapter.clear();
//                    new DocumentWebService(Fragment_DocType.this).execute();
//
//
//                }
//            });
//
//
//            spinner_DTG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view,
//                                           int position, long id) {
//
//                    if (spinner_DTG.getSelectedItem().toString() == "All") {
//                        Fragment_Documents.clickeddocid = Long.valueOf(0);
//                        Fragment_Documents.listView.setAdapter(null);
//                        alldoctypelist.clear();
//                        alldoctypelist.add("All");
//                        new DocumetTypeALLWS(Fragment_DocType.this).execute();
//                    } else {
//                        Fragment_Documents.clickeddocid = Long.valueOf(0);
//                        Fragment_Documents.listView.setAdapter(null);
//                        ;
//                        alldoctypelist.clear();
//                        alldoctypelist.add("All");
//                        SelectedDocTypeGroup = spinner_DTG.getSelectedItem().toString();
//                        SelectedDTG=SelectedDocTypeGroup;
//                        new DocumenrTypeListWS(Fragment_DocType.this).execute();
//                    }
//
//                    ArrayAdapter<String> all_dt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, alldoctypelist);
//                    all_dt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_dt.setAdapter(all_dt);
//
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//
//            });
//
//            spinner_dt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view,
//                                           int position, long id) {
//
//                    //Toast.makeText(AD_ImportActivity.this, spinnerDropDownView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                    if (spinner_dt.getSelectedItem().toString() == "All") {
//                        Fragment_Documents.clickeddocid = Long.valueOf(0);
//                        Fragment_Documents.listView.setAdapter(null);
//                        recyclerView.setAdapter(null);
//                        recyclerView.setAdapter(keyword_adapter);
//                        kwnamelist.clear();
//                        keyword_adapter.clear();
//                        FirstFragmentAdapter.edittextlist.clear();
//                        keyword_adapter.notifyDataSetChanged();
//                        SelectedDocType = "All";
//                    } else {
//                        Fragment_Documents.clickeddocid = Long.valueOf(0);
//                        Fragment_Documents.listView.setAdapter(null);
//                        recyclerView.setAdapter(keyword_adapter);
//                        kwnamelist.clear();
//                        keyword_adapter.clear();
//                        FirstFragmentAdapter.edittextlist.clear();
//                        keyword_adapter.notifyDataSetChanged();
//                        SelectedDocType = spinner_dt.getSelectedItem().toString();
//                        new KeywordListWS(Fragment_DocType.this).execute();
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    // TODO Auto-generated method stub
//
//                }
//            });
//
//            return v;
//        }
//       else{
    SessionID = FragmentActivity.sessionID;
    doctypegrouplist.add("All");
    alldoctypelist.add("All");
    new DTGLWS(this).execute();
    new DTALWS(Fragment_DocType.this).execute();
    v = inflater.inflate(R.layout.firstfragment, container, false);
    //  lv = v.findViewById(R.id.listitem);
    spinner_DTG = v.findViewById(R.id.spSpinnerDTG);
    spinner_dt = v.findViewById(R.id.spSpinnerDT);
    recyclerView = (RecyclerView) v.findViewById(R.id.rvRecyclerView);

    edfullTextSearch = v.findViewById(R.id.edFullTextSearch);
    btnfind = v.findViewById(R.id.btnFind);

    btnclear = v.findViewById(R.id.btnClear);
    btnclear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Fragment_Documents.clickeddocid = Long.valueOf(0);
        Fragment_Documents.listView.setAdapter(null);
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(keyword_adapter);
        kwnamelist.clear();
        edfullTextSearch.setText("");
        edfullTextSearch.clearFocus();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, doctypegrouplist);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_DTG.setAdapter(adapter);

        ArrayAdapter<String> aa_dtg = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dtg, doctypegrouplist);
        aa_dtg.setDropDownViewResource(R.layout.spinner_dtg);
        //SettingFragment the ArrayAdapter data on the Spinner
        spinner_DTG.setAdapter(aa_dtg);
      }
    });
    keyword_adapter = new FirstFragmentAdapter(getActivity(), kwnamelist, kwdatatypelist, kwdetailslist);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(keyword_adapter);
    keyword_adapter.clear();
    FirstFragmentAdapter.edittextlist.clear();

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(keyword_adapter);
    ArrayAdapter aa_dtg = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dtg,doctypegrouplist);
    aa_dtg.setDropDownViewResource(R.layout.spinner_dtg);
    spinner_DTG.setAdapter(aa_dtg);


    btnfind.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (edfullTextSearch.getText().toString().isEmpty())
          fullTextSearch = "null";
        else
          fullTextSearch = edfullTextSearch.getText().toString();
//                Log.d(TAG,fullTextSearch);

        Fragment_Documents.clickeddocid = Long.valueOf(0);
        enteredkeywordtypevaluelist.clear();
        enteredkeywordtypenamelist.clear();
//                System.out.println("listview size: " + recyclerView.getAdapter().getItemCount());
        for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {


          RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
          if (null != holder) {
            holder.itemView.findViewById(R.id.edKeyword).setVisibility(View.VISIBLE);
          }

//                    Log.d(TAG,"Keyword value " + i + ": " + FirstFragmentAdapter.edittextlist.get(i).toString());
          if (FirstFragmentAdapter.edittextlist.get(i).toString() == "" || FirstFragmentAdapter.edittextlist.get(i).toString().isEmpty() || FirstFragmentAdapter.edittextlist.get(i).toString() == null) {
          } else {
            enteredkeywordtypevaluelist.add(FirstFragmentAdapter.edittextlist.get(i).toString());
            enteredkeywordtypenamelist.add(keywordtypenamelist.get(i));
          }
        }
        docnamelist.clear();
        listView = Fragment_Documents.V.findViewById(R.id.listView);
        Fragment_Documents.progressBar.setVisibility(View.VISIBLE);
        mMyAdapter = new FragmentAdapter_Documents(getActivity(), docnamelist);
        listView.setAdapter(mMyAdapter);
        mMyAdapter.clear();
        new DWS(Fragment_DocType.this).execute();

      }
    });


    spinner_DTG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parent, View view,
                                 int position, long id) {

        if (spinner_DTG.getSelectedItem().toString() == "All") {
          spinner_DTG.setSelection(0);
          Fragment_Documents.clickeddocid = Long.valueOf(0);
          Fragment_Documents.listView.setAdapter(null);
          alldoctypelist.clear();
          alldoctypelist.add("All");
          new DTALWS(Fragment_DocType.this).execute();
        } else {
          Fragment_Documents.clickeddocid = Long.valueOf(0);
          Fragment_Documents.listView.setAdapter(null);
          alldoctypelist.clear();
          alldoctypelist.add("All");
          SelectedDocTypeGroup = spinner_DTG.getSelectedItem().toString();
          SelectedDTG=SelectedDocTypeGroup;
          new DTLWS(Fragment_DocType.this).execute();
        }

        ArrayAdapter aa_dt = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dtg, alldoctypelist);
        aa_dt.setDropDownViewResource(R.layout.spinner_dtg);
        spinner_dt.setAdapter(aa_dt);


      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }

    });

    spinner_dt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parent, View view,
                                 int position, long id) {

        //Toast.makeText(AD_ImportActivity.this, spinnerDropDownView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        if (spinner_dt.getSelectedItem().toString() == "All") {
          Fragment_Documents.clickeddocid = Long.valueOf(0);
          Fragment_Documents.listView.setAdapter(null);
          recyclerView.setAdapter(null);
          recyclerView.setAdapter(keyword_adapter);
          kwnamelist.clear();
          keyword_adapter.clear();
          FirstFragmentAdapter.edittextlist.clear();
          keyword_adapter.notifyDataSetChanged();
          SelectedDocType = "All";
        } else {
          Fragment_Documents.clickeddocid = Long.valueOf(0);
          Fragment_Documents.listView.setAdapter(null);
          recyclerView.setAdapter(keyword_adapter);
          kwnamelist.clear();
          keyword_adapter.clear();
          FirstFragmentAdapter.edittextlist.clear();
          keyword_adapter.notifyDataSetChanged();
          SelectedDocType = spinner_dt.getSelectedItem().toString();
          new KWLWS(Fragment_DocType.this).execute();
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

      }
    });

    return v;
  }



  public static void resetSpinner(){
    spinner_DTG.setSelection(0);
  }


  private class DTGLWS extends AsyncTask<Void, Void,
          WebService_GetSA_DocTypeGroupList.WSResult> {
    private final WeakReference<Fragment_DocType> mContextRef;

    DTGLWS(final Fragment_DocType context) {
      this.mContextRef = new WeakReference<>(context);
    }

    @Override
    protected WebService_GetSA_DocTypeGroupList.WSResult doInBackground(Void... voids) {
      WebService_GetSA_DocTypeGroupList WSDTGL = new WebService_GetSA_DocTypeGroupList();
      WebService_GetSA_DocTypeGroupList.WSResult wsResult = WSDTGL.get(getdoctypegroupsoapmethod, soapAddress, SessionID);
      doctypegrouplist.addAll(wsResult.getGetdocumenttypelist());
      return wsResult;
    }

    @Override
    protected void onPostExecute(WebService_GetSA_DocTypeGroupList.WSResult wsResult) {
      super.onPostExecute(wsResult);

    }
  }

  private class DTALWS extends AsyncTask<Void, Void,
          WebService_GetSA_AllDocTypeList.WSResult> {
    private final WeakReference<Fragment_DocType> mContextRef;

    DTALWS(final Fragment_DocType context) {
      this.mContextRef = new WeakReference<>(context);
    }

    @Override
    protected WebService_GetSA_AllDocTypeList.WSResult doInBackground(Void... voids) {
      WebService_GetSA_AllDocTypeList WSADTL = new WebService_GetSA_AllDocTypeList();
      WebService_GetSA_AllDocTypeList.WSResult wsResult = WSADTL.get(getalldoctypesoapmethod, soapAddress, SessionID);
      alldoctypelist.addAll(wsResult.getAlldoctypelist());
      Log.d(TAG, "Alldoctypelist: " + alldoctypelist);

      return wsResult;
    }

    @Override
    protected void onPostExecute(WebService_GetSA_AllDocTypeList.WSResult wsResult) {
      super.onPostExecute(wsResult);

    }
  }

  private class DTLWS extends AsyncTask<Void, Void, WebService_GetSA_DocTypeList.WSResult> {

    private final WeakReference<Fragment_DocType> mContextRef;

    DTLWS(final Fragment_DocType context) {
      this.mContextRef = new WeakReference<>(context);
    }
    @Override
    protected WebService_GetSA_DocTypeList.WSResult doInBackground(Void... voids) {
      WebService_GetSA_DocTypeList WSDTL = new WebService_GetSA_DocTypeList();
      WebService_GetSA_DocTypeList.WSResult wsResult = WSDTL.get(getselecteddocListsoapmethod, soapAddress, SessionID, SelectedDocTypeGroup);
      //docnamelist = wsResult.getGetdocumentnamelist();
      alldoctypelist.addAll(wsResult.getdoctypelist());
      //    Log.d(TAG,"doctypelist size: " + wsResult.getdoctypelist().size());
      return wsResult;
    }

    @Override
    protected void onPostExecute(WebService_GetSA_DocTypeList.WSResult wsResult) {
      super.onPostExecute(wsResult);
      keyword_adapter.notifyDataSetChanged();

    }

  }

  private class KWLWS extends AsyncTask<Void, Void, WebService_GetSA_KeywordList.WSResult> {

    private final WeakReference<Fragment_DocType> mContextRef;

    KWLWS(final Fragment_DocType context) {
      this.mContextRef = new WeakReference<>(context);
    }

    @Override
    protected WebService_GetSA_KeywordList.WSResult doInBackground(Void... voids) {
      WebService_GetSA_KeywordList WSDTL = new WebService_GetSA_KeywordList();
      WebService_GetSA_KeywordList.WSResult wsResult = WSDTL.get(getkeywordListsoapmethod, soapAddress, SessionID, SelectedDocType);
      return wsResult;
    }

    @Override
    protected void onPostExecute(WebService_GetSA_KeywordList.WSResult wsResult) {
      super.onPostExecute(wsResult);

      kwnamelist.clear();
      kwdatatypelist.clear();
      kwdetailslist.clear();
      keywordtypenamelist.clear();
      FirstFragmentAdapter.edittextlist.clear();
      try {
        int keywordcount = wsResult.getkwnamelist().size();

        for (int i = 0; i < keywordcount; i++) {
          String kwname = (String) wsResult.getkwnamelist().get(i);
          keywordtypenamelist.add(kwname);
          kwnamelist.add(kwname);

          String kwdatatype = (String) wsResult.getkwdatatypelist().get(i);
          kwdatatypelist.add(kwdatatype);


          String kwsize = (String) wsResult.getkwdetailslist().get(i);
          kwdetailslist.add(kwsize);

          FirstFragmentAdapter.edittextlist.put(i,"");


        }

        keyword_adapter.notifyDataSetChanged();
      }catch (Exception ex){}

//                Log.d(TAG,"keywordtypelist size: " + kwnamelist.size());
//
//               Log.d(TAG,"keyword type adap list size: " + keyword_adapter.getItemCount());


    }

  }

  class DWS extends AsyncTask<Void, Void, WebService_GetSA_DocList.WSResult> {

    private final WeakReference<Fragment_DocType> mContextRef;

    DWS(final Fragment_DocType context) {
      this.mContextRef = new WeakReference<>(context);
    }


    @Override
    protected WebService_GetSA_DocList.WSResult doInBackground(Void... voids) {
      WebService_GetSA_DocList WSDTL = new WebService_GetSA_DocList();
      if(SelectedDocType == null)
        SelectedDocType = "All";
      if(SelectedDTG == null)
        SelectedDTG = "All";

      WebService_GetSA_DocList.WSResult wsResult = WSDTL.get(getdocListsoapmethod, soapAddress, SessionID,SelectedDTG ,SelectedDocType,enteredkeywordtypenamelist,enteredkeywordtypevaluelist,fullTextSearch);
      docnamelist = wsResult.getGetdocumentnamelist();
      docidlist = wsResult.getGetdocumentidlist();
      //   Log.d(TAG, "Document Name List Size: " + docnamelist.size());
      return wsResult;
    }

    @Override
    protected void onPostExecute(WebService_GetSA_DocList.WSResult wsResult) {
      Fragment_Documents.progressBar.setVisibility(View.GONE);
      super.onPostExecute(wsResult);
      mMyAdapter.clear();
      mMyAdapter.addAll(docnamelist);
      mMyAdapter.notifyDataSetChanged();
      if(docnamelist.size()==0)
      {

        Toast.makeText(getActivity(), "No Document Found !!!", Toast.LENGTH_SHORT).show();
      }

      else{
        Toast.makeText(getActivity(),"Total document = "+docnamelist.size(), Toast.LENGTH_SHORT).show();
      }

    }
  }

//    private class DocumentTypeGroupListWS extends AsyncTask<Void, Void,
//            WebService_GetAD_DocTypeGroupList.WSResult> {
//        private final WeakReference<Fragment_DocType> mContextRef;
//
//        DocumentTypeGroupListWS(final Fragment_DocType context) {
//            this.mContextRef = new WeakReference<>(context);
//        }
//
//        @Override
//        protected WebService_GetAD_DocTypeGroupList.WSResult doInBackground(Void... voids) {
//            WebService_GetAD_DocTypeGroupList WSDTGL = new WebService_GetAD_DocTypeGroupList();
//            WebService_GetAD_DocTypeGroupList.WSResult wsResult = WSDTGL.get(getdoctypegroupADsoapmethod, soapAddress, SessionID);
//            doctypegrouplist.addAll(wsResult.getGetdocumenttypelist());
//
//            return wsResult;
//        }
//
//        @Override
//        protected void onPostExecute(WebService_GetAD_DocTypeGroupList.WSResult wsResult) {
//            super.onPostExecute(wsResult);
//
//        }
//    }
//
//    private class DocumetTypeALLWS extends AsyncTask<Void, Void, WebService_AD_GetAllDocTypeList.WSResult> {
//        private final WeakReference<Fragment_DocType> mContextRef;
//
//        DocumetTypeALLWS(final Fragment_DocType context) {
//            this.mContextRef = new WeakReference<>(context);
//        }
//
//        @Override
//        protected WebService_AD_GetAllDocTypeList.WSResult doInBackground(Void... voids) {
//            WebService_AD_GetAllDocTypeList WSADTL = new WebService_AD_GetAllDocTypeList();
//            WebService_AD_GetAllDocTypeList.WSResult wsResult = WSADTL.get(getalldoctypeADsoapmethod, soapAddress, SessionID);
//            alldoctypelist.addAll(wsResult.getAlldoctypelist());
//
//            return wsResult;
//        }
//
//        @Override
//        protected void onPostExecute(WebService_AD_GetAllDocTypeList.WSResult wsResult) {
//            super.onPostExecute(wsResult);
//
//        }
//    }
//
//    private class DocumenrTypeListWS extends AsyncTask<Void, Void, WebService_GetAD_DocTypeList.WSResult> {
//
//        private final WeakReference<Fragment_DocType> mContextRef;
//
//        DocumenrTypeListWS(final Fragment_DocType context) {
//            this.mContextRef = new WeakReference<>(context);
//        }
//        @Override
//        protected WebService_GetAD_DocTypeList.WSResult doInBackground(Void... voids) {
//            WebService_GetAD_DocTypeList WSDTL = new WebService_GetAD_DocTypeList();
//            WebService_GetAD_DocTypeList.WSResult wsResult = WSDTL.get(getselecteddocListADsoapmethod, soapAddress,SessionID, SelectedDocTypeGroup);
//            //docnamelist = wsResult.getGetdocumentnamelist();
//            alldoctypelist.addAll(wsResult.getdoctypelist());
//          //  Log.d(TAG,"doctypelist size: " + wsResult.getdoctypelist().size());
//            return wsResult;
//        }
//
//        @Override
//        protected void onPostExecute(WebService_GetAD_DocTypeList.WSResult wsResult) {
//            super.onPostExecute(wsResult);
//            keyword_adapter.notifyDataSetChanged();
//
//        }
//
//    }
//
//    private class KeywordListWS extends AsyncTask<Void, Void, WebService_GetAD_KeywordList.WSResult> {
//
//        private final WeakReference<Fragment_DocType> mContextRef;
//
//        KeywordListWS(final Fragment_DocType context) {
//            this.mContextRef = new WeakReference<>(context);
//        }
//
//        @Override
//        protected WebService_GetAD_KeywordList.WSResult doInBackground(Void... voids) {
//            WebService_GetAD_KeywordList WSDTL = new WebService_GetAD_KeywordList();
//            WebService_GetAD_KeywordList.WSResult wsResult = WSDTL.get(getkeywordListADsoapmethod, soapAddress, SessionID, SelectedDocType);
//            return wsResult;
//        }
//
//        @Override
//        protected void onPostExecute(WebService_GetAD_KeywordList.WSResult wsResult) {
//            super.onPostExecute(wsResult);
//
//            kwnamelist.clear();
//            kwdatatypelist.clear();
//            kwdetailslist.clear();
//            keywordtypenamelist.clear();
//            FirstFragmentAdapter.edittextlist.clear();
//            try {
//                int keywordcount = wsResult.getkwnamelist().size();
//
//                for (int i = 0; i < keywordcount; i++) {
//                    String kwname = (String) wsResult.getkwnamelist().get(i);
//                    keywordtypenamelist.add(kwname);
//                    kwnamelist.add(kwname);
//
//                    String kwdatatype = (String) wsResult.getkwdatatypelist().get(i);
//                    kwdatatypelist.add(kwdatatype);
//
//
//                    String kwsize = (String) wsResult.getkwdetailslist().get(i);
//                    kwdetailslist.add(kwsize);
//
//                    FirstFragmentAdapter.edittextlist.put(i,"");
//
//
//                }
//
//                keyword_adapter.notifyDataSetChanged();
//            }catch (Exception ex){}
//
////            Log.d(TAG,"keywordtypelist size: " + kwnamelist.size());
////
////            Log.d(TAG,"keyword type adap list size: " + keyword_adapter.getItemCount());
//
//
//        }
//
//    }
//
//    class DocumentWebService extends AsyncTask<Void, Void, WebService_GetAD_DocList.WSResult> {
//
//        private final WeakReference<Fragment_DocType> mContextRef;
//
//        DocumentWebService(final Fragment_DocType context) {
//            this.mContextRef = new WeakReference<>(context);
//        }
//
//
//        @Override
//        protected WebService_GetAD_DocList.WSResult doInBackground(Void... voids) {
//            WebService_GetAD_DocList WSDTL = new WebService_GetAD_DocList();
//            if(SelectedDocType == null)
//                SelectedDocType = "All";
//            if(SelectedDTG == null)
//                SelectedDTG = "All";
//            WebService_GetAD_DocList.WSResult wsResult = WSDTL.get(getdocListADsoapmethod, soapAddress,SessionID,SelectedDocTypeGroup,SelectedDocType,enteredkeywordtypenamelist,enteredkeywordtypevaluelist,fullTextSearch);
//
//            docnamelist = wsResult.getGetdocumentnamelist();
//            docidlist = wsResult.getGetdocumentidlist();
//
//            return wsResult;
//        }
//
//        @Override
//        protected void onPostExecute(WebService_GetAD_DocList.WSResult wsResult) {
//            Fragment_Documents.progressBar.setVisibility(View.GONE);
//            super.onPostExecute(wsResult);
//            mMyAdapter.clear();
//            mMyAdapter.addAll(docnamelist);
//            mMyAdapter.notifyDataSetChanged();
//        }
//    }
//
//

}


