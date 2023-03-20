package com.dcpl.printfromonbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dcpl.printfromonbase.fragments.PrintConfigureFragment;
import com.dcpl.printfromonbase.task.WebService_GetSA_CloseSession;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
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

import static com.dcpl.printfromonbase.FragmentActivity.getClosesession;
import static com.dcpl.printfromonbase.FragmentActivity.sessionID;
import static com.dcpl.printfromonbase.MainActivity.ACTION_PRINT_COMPLETED;

public class PreviewActivity extends AppCompatActivity {
    File FILE_PATH = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/");

    public static PDFView pdfView;
    private String pdfFileName;
    String docPath = "";
    private int pageNumber;
    public static ProgressBar progressBar;
    private Context context;

    Button btnprinted;
    Button btnBack;

    android.app.AlertDialog.Builder builder;
    final Context Context = this;
    private JobObserver mJobObserver;
    private String mRid = null;
    private String mJobId = null;
    private SharedPreferences mPrefs = null;
    private Snackbar mSnackBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        pdfView = findViewById(R.id.pdfBook1);
        progressBar = findViewById(R.id.ProgressBarPreview);
        mJobObserver = new JobObserver (new Handler());
//        mJobObserver = new JobObserver (new Handler());
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                finish();
            }
        });
        btnprinted = findViewById(R.id.btnPrinted);
        btnprinted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Log.d("Print ","successfully");
progressBar.setVisibility(View.VISIBLE);
                int copies=1;
                PrintAttributesCaps caps ;
                Result result;
                Context context;
                context=getApplicationContext();
                result= new Result();

                try {
                    Workpath.getInstance().initialize(getApplicationContext());
                    caps = PrinterService.getCapabilities(context, result);


                    PrintAttributes attributes ;
                    attributes = new PrintAttributes.PrintFromStorageBuilder(Uri.fromFile(new File(Fragment_Documents.getdocument)))
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
                            .build(caps);


                    final PrintletAttributes taskAttribs = new PrintletAttributes.Builder()
                            .setShowSettingsUi(false)
                            .build();
                    // Submit the job
                    String rid = PrinterService.submit(getApplicationContext(), attributes, taskAttribs);
                }
                catch (CapabilitiesExceededException e) {
                    e.printStackTrace();
                } catch (SsdkUnsupportedException e) {
                    e.printStackTrace();
                }


//                builder = new android.app.AlertDialog.Builder(PreviewActivity.this);
//                builder.setCancelable(false);
//                builder.setMessage("Print another document");
//
//                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        Intent mainIntent = new Intent(MainActivity.this,LoginActivity.class);
////                        startActivity(mainIntent);
//                        new PreviewActivity.CloseWS(PreviewActivity.this).execute();
//                        //  finish();
//
//
//                    }
//                });
//                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        Fragment_Documents.clickeddocid = Long.valueOf(0);
//                        Fragment_Documents.listView.setAdapter(null);
//                        Fragment_DocType.recyclerView.setAdapter(null);
//                        Fragment_DocType.recyclerView.setAdapter(Fragment_DocType.keyword_adapter);
//                        Fragment_DocType.kwnamelist.clear();
//                        Fragment_DocType.edfullTextSearch.setText("");
//                        Fragment_DocType.edfullTextSearch.clearFocus();
//                        Fragment_DocType.resetSpinner();
//                        onBackPressed();
//
////                            super.onBackPressed();
////                            super.finish();
//                    }
//                });
//
//                builder.show();
//
//                PrintScreenActivity.pbPrintScreen.setVisibility(View.GONE);

//                Intent intent = new Intent(PreviewActivity.this, PrintPropertyScreen.class);
//                startActivity(intent);
//                finish();
            }
        });
        pdfFileName = Fragment_Documents.getdocument;
        File file = new File(docPath);
        if (file.exists()) {
            pdfView.fromFile(file)
                    .defaultPage(pageNumber)
                    .enableAnnotationRendering(true)
                    .onPageChange((OnPageChangeListener) this)
                    .enableAnnotationRendering(true)
                    .onLoad((OnLoadCompleteListener) this)
                    .onError((OnErrorListener) this)
                    .scrollHandle(new DefaultScrollHandle(context))
                    .load();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register JobObserver to receive job state callbacks
        mJobObserver.register(getApplicationContext());

    }


    @Override
    public void onPause() {
        super.onPause();


        mJobObserver.unregister(getApplicationContext());


    }



    class CloseWS extends AsyncTask<Void, Void, WebService_GetSA_CloseSession.WSResult> {

        public WeakReference<PreviewActivity> mContextRef;

        CloseWS(PreviewActivity context) {
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
                finishAffinity();

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

                            final Intent intent = new Intent(getApplicationContext(), JobCompleteReceiver.class);
                            intent.setAction(ACTION_PRINT_COMPLETED);
                            intent.putExtra(JobCompleteReceiver.RID_EXTRA, rid);
                            intent.putExtra(JobCompleteReceiver.JOB_ID_EXTRA, mJobId);

                            final boolean showProgress =
                                    mPrefs.getBoolean(PrintConfigureFragment.PREF_SHOW_JOB_PROGRESS, true);

                            // Monitor the job completion
                            final JobletAttributes taskAttributes =
                                    new JobletAttributes.Builder().setShowUi(showProgress).build();

                            final String jrid = JobService.monitorJobInForeground(PreviewActivity.this, mJobId,
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
            progressBar.setVisibility(View.GONE);

            builder = new android.app.AlertDialog.Builder(PreviewActivity.this);
            builder.setCancelable(false);
            builder.setMessage("Print another document");

            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new PreviewActivity.CloseWS(PreviewActivity.this).execute();


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

                    onBackPressed();
                    finish();
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
        runOnUiThread(new Runnable() {
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
