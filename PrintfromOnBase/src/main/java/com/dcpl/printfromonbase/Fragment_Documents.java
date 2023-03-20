package com.dcpl.printfromonbase;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.dcpl.printfromonbase.fragments.PrintConfigureFragment;
import com.dcpl.printfromonbase.task.InitializationTask;
import com.dcpl.printfromonbase.task.WebService_GetSA_CloseSession;
import com.dcpl.printfromonbase.task.WebService_GetSA_Document;
import com.google.android.material.snackbar.Snackbar;
import com.hp.workpath.api.CapabilitiesExceededException;
import com.hp.workpath.api.Result;
import com.hp.workpath.api.SsdkUnsupportedException;
import com.hp.workpath.api.Workpath;
import com.hp.workpath.api.job.JobInfo;
import com.hp.workpath.api.job.JobService;
import com.hp.workpath.api.job.JobletAttributes;
import com.hp.workpath.api.printer.PrintAttributes;
import com.hp.workpath.api.printer.PrintAttributesCaps;
import com.hp.workpath.api.printer.PrinterService;
import com.hp.workpath.api.printer.PrintletAttributes;


import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.dcpl.printfromonbase.FragmentActivity.sessionID;
import static com.dcpl.printfromonbase.MainActivity.ACTION_PRINT_COMPLETED;

public class Fragment_Documents extends Fragment {

    File FILE_PATH = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/");

    public static ListView listView;
    public String title = "com.hp.workpath.sample.printsample.Extra_Text";
    public static FragmentAdapter_Documents mMyAdapter;
    ArrayList docnamelist = new ArrayList();
    private ArrayList<Long> docidlist = new ArrayList<Long>();
    String soapAddress = OnBase_Login_Screen.soapAddress;
    String getdocListsoapmethod = "SA_GetDocument";
    static String TAG ;
    int cat_id = 0;
    static View V;
    String Password;
    String buttonclicked;
    //    String doctype;
    String DocType;
    private boolean tvTitle;
    private EditText DateEdit;
    private EditText ToDate;
    private FragmentManager supportFragmentManager;
    public static Long clickeddocid = Long.valueOf(0);
    public static String getdocument;
    Button btnPreview;
    Button btnPrint;
    Button btnOptions;
    private InitializationTask mInitializationTask;
    private JobObserver mJobObserver;
    private String mRid = null;
    private String mJobId = null;

    AlertDialog.Builder builder;
    public static ProgressBar progressBar;
    String getClosesession= "SA_Closesession";
    String GetCloseSession = "AD_Closesession";
    private Snackbar mSnackBar;

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        new LoadCapabilitiesTask(context,PrintPropertyScreen.mFragment).execute();
        V = inflater.inflate(R.layout.secondfragment, container, false);

        mJobObserver = new JobObserver(new Handler());
            listView = V.findViewById(R.id.listView);
            progressBar = V.findViewById(R.id.pbProgressBar);
            mMyAdapter = new FragmentAdapter_Documents(getActivity().getApplicationContext(), docnamelist);
            listView.setAdapter(mMyAdapter);
            listView.setSelector(R.drawable.green_text);
            onclickListener();
            btnPreview = V.findViewById(R.id.btnPreview);
            btnPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickeddocid == 0) {
                        Toast.makeText(getActivity(), "Select a Document!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        buttonclicked = "preview";
                        Intent intent = new Intent(getActivity(), PreviewActivity.class);
                        startActivity(intent);
                        new GDWS(Fragment_Documents.this).execute();
                        // Log.d("Clicked ",buttonclicked);
                    }
                }
            });
            btnPrint = V.findViewById(R.id.btnPrint);
            btnPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickeddocid == 0) {
                        Toast.makeText(getActivity(), "Select a Document!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        buttonclicked = "print";
                        FragmentActivity.pbDocuments.setVisibility(View.VISIBLE);
                        new GDWS(Fragment_Documents.this).execute();
                        //Log.d("Clicked ",buttonclicked);

                         }

                }
            });

            btnOptions = V.findViewById(R.id.btnOption);
            btnOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickeddocid == 0) {
                        Toast.makeText(getActivity(), "Select a Document!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        buttonclicked = "option";
                        new GDWS(Fragment_Documents.this).execute();
                        // Log.d("Clicked ",buttonclicked);

                    }
                }
            });
            return V;
        }


    private void onclickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                clickeddocid = Fragment_DocType.docidlist.get(position);

                // Log.d(TAG, "doc id " + String.valueOf(docidlist));
                // openFragment(clickeddocid);

            }
        });
    }

    void executeoption() {
        Intent intent = new Intent(this.getView().getContext(), MainActivity.class);
        intent.putExtra("Title", title);
        intent.putExtra("documentId", getdocument);
        startActivity(intent);

    }


    @Override
    public void onResume() {
        super.onResume();

        // Register JobObserver to receive job state callbacks
        mJobObserver.register(getActivity());

    }


    @Override
    public void onPause() {
        super.onPause();


        mJobObserver.unregister(getActivity());


    }
    private class GDWS extends AsyncTask<Void, Void, WebService_GetSA_Document.WSResult> {

        private final WeakReference<Fragment_Documents> mContextRef;

        int copies = 1;
        PrintAttributesCaps capps;
        Result result;
        Context context;
        PrintAttributes attributes;

        GDWS(final Fragment_Documents context) {
            this.mContextRef = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            context = getActivity();
            result = new Result();
        }

        @Override
        protected WebService_GetSA_Document.WSResult doInBackground(Void... voids) {
            WebService_GetSA_Document WSGetDoc = new WebService_GetSA_Document();
            WebService_GetSA_Document.WSResult wsResult = WSGetDoc.document(getActivity(),getdocListsoapmethod, soapAddress, Fragment_DocType.SessionID, clickeddocid);
            return wsResult;
        }


        @Override
        protected void onPostExecute(WebService_GetSA_Document.WSResult wsResult) {
            super.onPostExecute(wsResult);
           getdocument = wsResult.getDocName();
           // getdocument = "/storage/emulated/0/Download/Userguide.pdf";
          //  File file = new File(Environment.getExternalStorageDirectory(),"/Userguide.pdf");
             Log.d(TAG, "GetDocument: "+ getdocument);
            final File file = new File(getdocument);
            switch (buttonclicked) {
                case "preview":
                    //  Log.d("Preview ","successfully");
                    PreviewActivity.progressBar.setVisibility(View.GONE);
                    PreviewActivity.pdfView.fromFile(file).load();

                    break;
                case "option":
                    //  Log.d("Option ","successfully");
                    executeoption();

                    break;
                case "print":
                    //  Log.d("Print ","successfully");
                    FragmentActivity.pbDocuments.setVisibility(View.VISIBLE);
                    try {
                        Workpath.getInstance().initialize(getActivity());
                        capps = PrinterService.getCapabilities(context, result);

                        attributes = new PrintAttributes.PrintFromStorageBuilder(Uri.fromFile(new File(getdocument)))

                                .setCollateMode(PrintAttributes.CollateMode.COLLATED.DEFAULT)
                                .setColorMode(PrintAttributes.ColorMode.DEFAULT)
                                .setDuplex(PrintAttributes.Duplex.DEFAULT)
                                .setAutoFit(PrintAttributes.AutoFit.DEFAULT)
                                .setStapleMode(PrintAttributes.StapleMode.DEFAULT)
                                .setPaperSource(PrintAttributes.PaperSource.DEFAULT)
                                .setPaperSize(PrintAttributes.PaperSize.DEFAULT)
                                .setPaperType(PrintAttributes.PaperType.DEFAULT)
                                .setDocumentFormat(PrintAttributes.DocumentFormat.AUTO)
                                .setCopies(copies)
                                .build(capps);


                         PrintletAttributes taskAttribs = new PrintletAttributes.Builder()
                         .setShowSettingsUi(false)
                         .build();

                         String rid = PrinterService.submit(getActivity(), attributes, taskAttribs);
                       // Log.i(TAG, "Job submitted with rid = " + rid);
                        Log.d("Job submitted with rid " ,rid);


                    } catch (CapabilitiesExceededException e) {
                        e.printStackTrace();
                    } catch (SsdkUnsupportedException e) {
                        e.printStackTrace();
                    }

//                    builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(false);
//                    builder.setMessage("Print another document");
//
//                    builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
////                        Intent mainIntent = new Intent(MainActivity.this,LoginActivity.class);
////                        startActivity(mainIntent);
//                            //  finishAffinity();
//                            //  finish();
//                           // getActivity().finishAffinity();
//                            new Fragment_Documents.CloseWS(Fragment_Documents.this).execute();
//
//
//                        }
//                    });
//                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            Fragment_Documents.clickeddocid = Long.valueOf(0);
//                            Fragment_Documents.listView.setAdapter(null);
//                            Fragment_DocType.recyclerView.setAdapter(null);
//                            Fragment_DocType.recyclerView.setAdapter(Fragment_DocType.keyword_adapter);
//                            Fragment_DocType.kwnamelist.clear();
//                            Fragment_DocType.edfullTextSearch.setText("");
//                            Fragment_DocType.edfullTextSearch.clearFocus();
//                            Fragment_DocType.resetSpinner();
//                        }
//                    });
//
//                    builder.show();


                    break;
            }

        }
    }



    class CloseWS extends AsyncTask<Void, Void, WebService_GetSA_CloseSession.WSResult> {

        public  WeakReference<Fragment_Documents> mContextRef;

        CloseWS( Fragment_Documents context) {
            this.mContextRef = new WeakReference<>(context);
        }


        @Override
        protected WebService_GetSA_CloseSession.WSResult doInBackground(Void... voids) {
            WebService_GetSA_CloseSession WSClose = new WebService_GetSA_CloseSession();
            WebService_GetSA_CloseSession.WSResult wsResult = WSClose.close(getClosesession, OnBase_Login_Screen.soapAddress, sessionID);
            return wsResult;
        }

        @Override
        protected void onPostExecute(WebService_GetSA_CloseSession.WSResult wsResult) {

            String close = wsResult.getlogOffresult();
            if (!close.equals("")) {
                getActivity().finishAffinity();

            }
        }
    }

    private class JobObserver extends JobService.AbstractJobletObserver {

        public JobObserver(final Handler handler) {
            super(handler);
        }

        public void onProgress(final String rid, final JobInfo jobInfo) {
//            Log.i(TAG, "onProgress: Received rid=" + rid);
//            Log.i(TAG, "JobInfo=" + Logger.build(jobInfo));
            if (rid.equals(mRid)) {
                if (mJobId == null) {
                    if (jobInfo.getJobId() != null) {
                        mJobId = jobInfo.getJobId();

//                        Log.i(TAG, "Received jobId=" + mJobId);
//                        showSnackBar(getString(R.string.job_id, mJobId));

                        if (mPrefs.getBoolean(PrintConfigureFragment.PREF_MONITORING_JOB, false)) {
                            // Store Job Id in order to verify it in the Broadcast Receiver
                            mPrefs.edit().putString(PrintConfigureFragment.CURRENT_JOB_ID, mJobId).apply();

                            final Intent intent = new Intent(getActivity(), JobCompleteReceiver.class);
                            intent.setAction(ACTION_PRINT_COMPLETED);
                            intent.putExtra(JobCompleteReceiver.RID_EXTRA, rid);
                            intent.putExtra(JobCompleteReceiver.JOB_ID_EXTRA, mJobId);

                            final boolean showProgress =
                                    mPrefs.getBoolean(PrintConfigureFragment.PREF_SHOW_JOB_PROGRESS, true);

                            // Monitor the job completion
                            final JobletAttributes taskAttributes =
                                    new JobletAttributes.Builder().setShowUi(showProgress).build();

                            final String jrid = JobService.monitorJobInForeground(getActivity(), mJobId,
                                    taskAttributes, intent);

                            // Log.i(TAG, "MonitorJob request: " + jrid);
                        }
                    }
                }
            }
        }

        @Override
        public void onComplete(final String rid, final JobInfo jobInfo) {
//            Log.i(TAG, "onComplete: Received rid=" + rid);
//            Log.i(TAG, "JobInfo=" + Logger.build(jobInfo));
            if (jobInfo.getJobType() == JobInfo.JobType.PRINT) {
                //   showSnackBar(getString(R.string.job_completed, jobInfo.getJobName()));
            }
            FragmentActivity.pbDocuments.setVisibility(View.INVISIBLE);

            builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setMessage("Print another document");

            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                        Intent mainIntent = new Intent(MainActivity.this,LoginActivity.class);
//                        startActivity(mainIntent);
                    //  finishAffinity();
                    //  finish();
                    // getActivity().finishAffinity();
                    new Fragment_Documents.CloseWS(Fragment_Documents.this).execute();


                }
            });
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Fragment_Documents.clickeddocid = Long.valueOf(0);
                    Fragment_Documents.listView.setAdapter(null);
                    Fragment_DocType.recyclerView.setAdapter(null);
                    Fragment_DocType.recyclerView.setAdapter(Fragment_DocType.keyword_adapter);
                    Fragment_DocType.kwnamelist.clear();
                    Fragment_DocType.edfullTextSearch.setText("");
                    Fragment_DocType.edfullTextSearch.clearFocus();
                    Fragment_DocType.resetSpinner();
                }
            });

            builder.show();



        }

        @Override
        public void onFail(final String rid, final Result result) {
            // Log.i(TAG, "onFail: Received rid=" + rid + ", " + Logger.build(result));
            showSnackBar("onFail: Received rid=" + rid + ", " + getString(R.string.job_failed));
        }

        @Override
        public void onCancel(final String rid) {
            //  Log.i(TAG, "onCancel: Received rid=" + rid);
            showSnackBar(getString(R.string.job_cancelled));
        }
    }
    public void showSnackBar(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSnackBar == null) {
                    //   mSnackBar = Snackbar.make(mContainer, "", Snackbar.LENGTH_INDEFINITE);
//                    View snackBarView = mSnackBar.getView();
//                    TextView tv = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//                    tv.setMaxLines(3);
                }
                mSnackBar.setText(text);
                mSnackBar.setActionTextColor(getResources().getColor(R.color.snackbar_button_color));
                mSnackBar.setAction(getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mSnackBar != null) {
                            mSnackBar.dismiss();
                            mSnackBar = null;
                        }
                    }
                }).show();
            }
        });
    }

}