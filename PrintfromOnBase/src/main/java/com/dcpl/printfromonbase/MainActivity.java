// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
package com.dcpl.printfromonbase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcpl.printfromonbase.fragments.PrintConfigureFragment;
import com.dcpl.printfromonbase.task.InitializationTask;
import com.dcpl.printfromonbase.task.LoadCapabilitiesTask;
import com.dcpl.printfromonbase.task.RequestPrintTask;
import com.dcpl.printfromonbase.task.WebService_GetSA_CloseSession;
import com.google.android.material.snackbar.Snackbar;
import com.hp.workpath.api.Workpath;
import com.hp.workpath.api.Result;
import com.hp.workpath.api.SsdkUnsupportedException;
import com.hp.workpath.api.job.JobInfo;
import com.hp.workpath.api.job.JobService;
import com.hp.workpath.api.job.JobletAttributes;
import com.hp.workpath.api.printer.PrintAttributes;
import com.hp.workpath.api.printer.PrintAttributesCaps;
import com.hp.workpath.api.printer.PrinterService;

import org.lucasr.twowayview.TwoWayView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dcpl.printfromonbase.FragmentActivity.sessionID;


/**
 * Main activity for Print Sample.
 */
public final class MainActivity extends AppCompatActivity{
    public static final String TAG = "[SAMPLE]" + "Print";

    public static final String ACTION_PRINT_COMPLETED = "com.hp.workpath.sample.printsample.ACTION_PRINT_COMPLETED";

    private static final String STATE_JOB_ID = "jobId";
    private static final String STATE_RID = "rid";


    /* Background task for Workpath SDK API initialization */
    private InitializationTask mInitializationTask;
    /**
     * Fragment to display attributes configuration UI
     */
    private PrintConfigureFragment mFragment = null;
    private View mContainer;
    private SharedPreferences mPrefs = null;
    private JobObserver mJobObserver = null;

    private String mJobId = null;
    private String mRid = null;
    private PrintAttributesCaps mCapabilities;

    private AlertDialog mAlertDialog;
    private Snackbar mSnackBar;

    private boolean mResumedFromFileBrowser = false;

    private String filePath;
    private String title = "com.hp.workpath.sample.printsample.Extra_Text";
    android.app.AlertDialog.Builder builder;
    final Context context = this;
    private String getClosesession = "SA_Closesession";
    ProgressBar pbProgressBar;
    private Result result;
    public static String ColorMode;
    public static String DuplexMode;
    public static String Autofit;
    public static String PaperSize;
    public static String CollateMode;
    public static String PaperSource;
    public static String DocumentFormat;
    public static String StapleMode;
    public static String PaperType;
    public static TextView integer_number;
    public static String copies;
    public static RadioButton radioDefault,radioAuto,radioColor,radioMono,radioDuplexDefault,radioNone,radioBook,radioFlip,radioAutoDefault,radioTrue,radioFalse,radiocollatedefault,radioNonCollated,radiocollated,radioA4, radioA3,radioletter,radiolegal,radioPSDefault,radioA5,radioA6,radioB5,radioPaperSourceDefault,radioPSAuto,radioTray_1,radioTray_2,radioTray_3,radioTray_4,radioDFAuto,radioText,radioJPEG,radioPCL5,radioPCL6,radioPS,radioPDF,radioTiff,radioSMDefault,radioSMNone,radioStaple,radioTop_Left,radioTop_Right,radio_Dual_Left,radio_Dual_right,radio_Dual_Top,radioPTDefault,radioPlain,radioLight,radioLabels,radioBond,radioColoured,radioMidWeight,radioCardStock;
    public static RadioGroup radioGroupColorMode,radioDuplesmode,radioGroupAutofitSetting,radioGroupPaperSizeSettings,radioCollateModesettings,radioPaperSource,radioDocumentFormat,radioStapleMode,radioPaperType;
    private Button btnIncrease;
    private Button btndecrease;
    private int mInteger = 1;
   ImageView ivPluses;
   ImageView ivMinus;
    private Animation animZoomIn;
    private Animation animZoomOut;
    RelativeLayout rlMainActivity;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        // find the text and button
        findViewElements();

        // add click listener
        addListener();

        executeprint();

        mJobObserver = new JobObserver(new Handler());
        rlMainActivity = findViewById(R.id.rlmainactivity);
        ivPluses = findViewById(R.id.ivPluses);
        ivPluses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                rlMainActivity.startAnimation(animZoomIn);
                // assigning that animation to
                // the image and start animation
          //      animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
            }
        });

        ivMinus = findViewById(R.id.ivMinus);
        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animZoomOut= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
                rlMainActivity.startAnimation(animZoomOut);
            }
        });
        integer_number = findViewById(R.id.integer_numbers);
        btnIncrease = findViewById(R.id.increase);
        btndecrease = findViewById(R.id.decrease);

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInteger++;
                copies = Integer.toString(mInteger);
                integer_number.setText(copies);

            }
        });

        btndecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInteger--;
                copies = Integer.toString(mInteger);
                integer_number.setText(copies);
            }
        });

        SharedPreferences pref = getSharedPreferences("MYPref", MODE_PRIVATE);
        copies = pref.getString(" inc", "1");
        integer_number.setText(copies);


        radioGroupColorMode = findViewById(R.id.radioGroupColorModes);
        radioDefault = findViewById(R.id.radioDefault);
        radioAuto = findViewById(R.id.radioAuto);
        radioColor = findViewById(R.id.radioColors);
        radioMono = findViewById(R.id.radioMono);

        radioDuplesmode = findViewById(R.id.radioGroupDuplexMode);
        radioDuplexDefault = findViewById(R.id.radioDefaults);
        radioNone = findViewById(R.id.radioNones);
        radioBook = findViewById(R.id.radioBooks);
        radioFlip = findViewById(R.id.radioFlips);

        radioGroupAutofitSetting = findViewById(R.id.radioGroupAutofit);
        radioAutoDefault = findViewById(R.id.radioAutofitDefault);
        radioTrue = findViewById(R.id.radioTrues);
        radioFalse = findViewById(R.id.radioFalses);

        radioPaperSource = findViewById(R.id.radioPaperSource);
        radioPaperSourceDefault = findViewById(R.id.radioDefultPS);
        radioPSAuto = findViewById(R.id.radioAutoPS);
        radioTray_1 = findViewById(R.id.radioTray_1);
        radioTray_2 = findViewById(R.id.radioTray_2);

        radioDocumentFormat = findViewById(R.id.radioGroupDocumentFormat);
        radioDFAuto = findViewById(R.id.radioAutoDF);
        radioJPEG = findViewById(R.id.radioJPEG);
        radioPDF = findViewById(R.id.radioPDF);
        radioTiff = findViewById(R.id.radioTiff);

        radioPaperType = findViewById(R.id.radioGroupPaperType);
        radioPlain = findViewById(R.id.radioPlains);
        radioLight = findViewById(R.id.radioLight);
        radioBond = findViewById(R.id.radioBonds);
        radioColoured = findViewById(R.id.radioColoured);

        radioStapleMode = findViewById(R.id.radioGroupStapleModes);
        radioSMDefault = findViewById(R.id.radioDefaultStaple);
        radioSMNone = findViewById(R.id.radioNoneStaple);
        radioStaple = findViewById(R.id.radioStaples);

        radioGroupPaperSizeSettings = findViewById(R.id.radioGroupPaperSizes);
        radioA4 = findViewById(R.id.radioA4s);
        radioA3 = findViewById(R.id.radioA3s);
        radioletter = findViewById(R.id.radioLetters);
        radiolegal = findViewById(R.id.radioLegals);

        radioCollateModesettings = findViewById(R.id.radioGroupCollatedMode);
        radiocollatedefault = findViewById(R.id.radioCollateDefault);
        radioNonCollated = findViewById(R.id.radioCollate);
        radiocollated = findViewById(R.id.radioNonCollated);


        radioDefault.setChecked(pref.getBoolean("Default",false));
        radioAuto.setChecked(pref.getBoolean("Auto",false));
        radioColor.setChecked(pref.getBoolean("Color",false));
        radioMono.setChecked(pref.getBoolean("Mono",false));

        radioDuplexDefault.setChecked(pref.getBoolean("DuplexMode",false));
        radioNone.setChecked(pref.getBoolean("None", false));
        radioBook.setChecked(pref.getBoolean("Book", false));
        radioFlip.setChecked(pref.getBoolean("Flip",false));

        radioAutoDefault.setChecked(pref.getBoolean("AutoDefault", false));
        radioTrue.setChecked(pref.getBoolean("true", false));
        radioFalse.setChecked(pref.getBoolean("false", false));

        radioA4.setChecked(pref.getBoolean("A4", false));
        radioA3.setChecked(pref.getBoolean("A3", false));
        radioletter.setChecked(pref.getBoolean("Letter", false));
        radiolegal.setChecked(pref.getBoolean("legal", false));


        radioColoured.setChecked(pref.getBoolean("PaperTypeDefault", false));
        radioPlain.setChecked(pref.getBoolean("Plain", false));
        radioLight.setChecked(pref.getBoolean("Light", false));
        radioBond.setChecked(pref.getBoolean("Bond", false));

        radioSMDefault.setChecked(pref.getBoolean("default", false));
        radioSMNone.setChecked(pref.getBoolean("None", false));
        radioStaple.setChecked(pref.getBoolean("Staple", false));

        radiocollatedefault.setChecked(pref.getBoolean("collatedeafult", false));
        radioNonCollated.setChecked(pref.getBoolean("Uncollated", false));
        radiocollated.setChecked(pref.getBoolean("Collate", false));





        radioGroupColorMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int CHECKEDID) {
                switch (CHECKEDID){
                    case R.id.radioDefault:
                        DuplexMode= "Default";
                        break;
                    case R.id.radioAuto:
                        DuplexMode="Auto";
                        break;
                    case R.id.radioColors:
                        DuplexMode= "Color";
                        break;
                    case R.id.radioMono:
                        DuplexMode= "Mono";
                        break;
                }
            }
        });

        radioDuplesmode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioGroupDuplexMode:
                        DuplexMode= " DuplexMode";
                        break;
                    case R.id.radioNone:
                        DuplexMode=" None";
                        break;
                    case R.id.radioBook:
                        DuplexMode= " Book";
                        break;
                    case R.id.radioFlip:
                        DuplexMode= " Flip";
                        break;
                }
            }
        });

        radioGroupAutofitSetting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioAutofitDefault:
                        Autofit= " AutoDefault";
                        break;
                    case R.id.radioTrues:
                        Autofit= " radiotrue";
                        break;
                    case R.id.radioFalses:
                        Autofit= " radiofalse";
                        break;
                }
            }
        });

        radioGroupPaperSizeSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.radioLetters:
                        PaperSize= " Letter";
                        break;
                    case R.id.radioLegals:
                        PaperSize= " legal";
                        break;
                    case R.id.radioA3s:
                        PaperSize= " A3";
                        break;
                    case R.id.radioA4s:
                        PaperSize= " A4";
                        break;
                }
            }
        });

        radioCollateModesettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioCollateDefault:
                        CollateMode= " collateddefault";
                        break;
                    case R.id.radioNonCollated:
                        CollateMode= " Uncollated";
                        break;
                    case R.id.radioCollate:
                        CollateMode= " Collate";
                        break;
                }
            }
        });

        radioPaperSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioDefault:
                        PaperSource= " PSDefault";
                        break;
                    case R.id.radioAuto:
                        PaperSource=" Auto";
                        break;
                    case R.id.radioTray_1:
                        PaperSource= " Tray_1";
                        break;
                    case R.id.radioTray_2:
                        PaperSource= " Tray_2";
                        break;
                }
            }
        });

        radioDocumentFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioAutoDF:
                        DocumentFormat=" Auto";
                        break;
                    case R.id.radioJPEG:
                        DocumentFormat=" JPEG";
                        break;
                   case R.id.radioPDF:
                        DocumentFormat=" PDF";
                        break;
                    case R.id.radioTiff:
                        DocumentFormat=" Tiff";
                        break;
                }
            }
        });

        radioStapleMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioDefaultStaple:
                        StapleMode= " Auto";
                        break;
                    case R.id.radioNoneStaple:
                        StapleMode=" None";
                        break;
                    case R.id.radioStaples:
                        StapleMode= " Staple";
                        break;
                }
            }
        });

        radioPaperType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioPlains:
                        PaperType=" Plain";
                        break;
                    case R.id.radioLight:
                        PaperType= " Light";
                        break;
                    case R.id.radioBonds:
                        PaperType= " Bond";
                        break;
                    case R.id.radioColoured:
                        PaperType= " Color";
                        break;
                }
            }

        });

    }

    public void executeprint()
    {
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        filePath = intent.getStringExtra("documentId");
        // Log.d(TAG,"filePath" +filePath );
        if (filePath == null){
            this.setTitle("Print Form");
            //Log.d(TAG,"Title from ThirdFragment : " +title);
        }
        else{
            this.setTitle("Print Form - "+ filePath);
            // Log.d(TAG,"Title from ThirdFragment : " + filePath);
        }
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register JobObserver to receive job state callbacks
        mJobObserver.register(getApplicationContext());

        mContainer.setEnabled(false);
        mInitializationTask = new InitializationTask(this);
        mInitializationTask.execute();

        if (!mResumedFromFileBrowser) {
            mFragment = new PrintConfigureFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.dataContainer, mFragment)
                    .commit();
        }
        mResumedFromFileBrowser = false;


        loadCapabilities();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mInitializationTask.cancel(true);
        mInitializationTask = null;

        // Unregister JobObserver
        mJobObserver.unregister(getApplicationContext());

        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }

        if (mSnackBar != null) {
            mSnackBar.dismiss();
            mSnackBar = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.version, menu);
        MenuItem versionMenu = menu.findItem(R.id.menuVersion);
        String version = "";
        try {
            Workpath sdkInfo = Workpath.getInstance();
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = getString(R.string.version, pInfo.versionName, pInfo.versionCode, sdkInfo.getVersionName(), sdkInfo.getVersionCode());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        versionMenu.setTitle(version);
        return true;
    }

    private void findViewElements() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mContainer = findViewById(R.id.container);
        pbProgressBar = findViewById(R.id.ProgressBarScreen);
    }

    private void addListener() {
        // Set listener for Print execution
//        findViewById(R.id.printButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                executePrint();
//            }
//
//        });

        findViewById(R.id.printButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                executePrint();
                findViewById(R.id.ProgressBarScreen).setVisibility(View.VISIBLE);

            }
        });

        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

               onBackPressed();
            }
        });


        // Set listener for Load Capabilities
//        findViewById(R.id.loadCapsButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//              loadCapabilities();
//            }
//        });
//
//        // Set listener for Load Defaults
//        findViewById(R.id.loadDefaultsButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                loadDefaults();
//            }
//        });
//
//        // Set listener for get Job Info
//        findViewById(R.id.getJobInfoButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                getJobInfo();
//            }
//        });

    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_JOB_ID, mJobId);
        outState.putString(STATE_RID, mRid);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mJobId = savedInstanceState.getString(STATE_JOB_ID);
        mRid = savedInstanceState.getString(STATE_RID);
    }

    public void setResumedFromFileBrowser(boolean value) {
        mResumedFromFileBrowser = value;
    }

    /**
     * Launches capabilities loading async task
     */
    private void loadCapabilities() {
        new LoadCapabilitiesTask(this, mFragment).execute();
    }
//
//    /**
//     * Launches defaults loading async task
//     */
//    private void loadDefaults() {
//        if (mCapabilities == null) {
//            showResult(getString(R.string.capabilities_not_loaded));
//        } else {
//            new LoadDefaultsTask(this, mFragment).execute();
//        }
//    }

    /**
     * Launches Print job
     */
    private void executePrint() {
        mJobId = null;
        mRid = null;
        new RequestPrintTask(MainActivity.this).execute();
    }

    /**
     * Obtain current job info
     */
    private void getJobInfo() {
        final Result result = new Result();

        if (mJobId == null) {
            showResult(getString(R.string.no_job_info));
        } else {
            JobInfo jobInfo = JobService.getJobInfo(getApplicationContext(), mJobId, result);
            if (result.getCode() != Result.RESULT_OK) {
                showResult("JobService.getJobInfo(): ", result);
            } else {
                showResult("JobInfo=" + Logger.build(jobInfo));
            }
        }
    }

    /**
     * Requests printer Print capabilities
     *
     * @param context {@link Context} to obtain data
     * @return {@link com.hp.workpath.api.printer.PrintAttributesCaps}
     */
    public PrintAttributesCaps requestCaps(final Context context, Result result) {
        if (result == null) {
            result = new Result();
        }

        // cache capabilities for building PrintAttributes
        mCapabilities = PrinterService.getCapabilities(context, result);

        if (result.getCode() == Result.RESULT_OK && mCapabilities != null) {
            Log.i(TAG, "Caps=" + Logger.build(mCapabilities));
        } else {
            showResult("PrinterService.getCapabilities(): ", result);
        }

        return mCapabilities;
    }

    public PrintAttributesCaps getCapabilities() {
        return mCapabilities;
    }

    public void setRid(String rid) {
        this.mRid = rid;
    }

    /**
     * Observer for submitted job
     */
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

                        Log.i(TAG, "Received jobId=" + mJobId);
                        showSnackBar(getString(R.string.job_id, mJobId));

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

                            final String jrid = JobService.monitorJobInForeground(MainActivity.this, mJobId,
                                    taskAttributes, intent);

                            Log.i(TAG, "MonitorJob request: " + jrid);
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
            pbProgressBar.setVisibility(View.GONE);

            builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false);
            builder.setMessage("Print another document");

            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new MainActivity.CloseWS(MainActivity.this).execute();


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
            pbProgressBar.setVisibility(View.GONE);
            showSnackBar("onFail: Received rid=" + rid + ", " + getString(R.string.job_failed));
        }

        @Override
        public void onCancel(final String rid) {
          //  Log.i(TAG, "onCancel: Received rid=" + rid);
            pbProgressBar.setVisibility(View.GONE);
            showSnackBar(getString(R.string.job_cancelled));
        }
    }

    public void handleComplete() {
        mContainer.setEnabled(true);
    }

    /**
     * Exception in could be because of following reasons
     * <ol>
     * <li>Library is not installed</li>
     * <li>Library update is needed</li>
     * <li>Version issue, unsupported</li>
     * </ol>
     */
    public void handleException(final Exception e) {
        String errorMsg;

        Log.e(TAG, e.getMessage());

        if (e instanceof SsdkUnsupportedException) {
            switch (((SsdkUnsupportedException) e).getType()) {
                case SsdkUnsupportedException.LIBRARY_NOT_INSTALLED:
                case SsdkUnsupportedException.LIBRARY_UPDATE_IS_REQUIRED:
                    errorMsg = getString(R.string.sdk_support_missing);
                    break;
                default:
                    errorMsg = getString(R.string.unknown_error);
            }
        } else {
            errorMsg = e.getMessage();
        }

        mAlertDialog = new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMsg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    public void showSnackBar(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSnackBar == null) {
                    mSnackBar = Snackbar.make(mContainer, "", Snackbar.LENGTH_INDEFINITE);
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

    public void showResult(final String msg) {
        showResult(msg, null);
    }

    public void showResult(final String msg, final Result result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Logger._DEBUG)
                    Log.d(TAG, msg);
                Toast.makeText(getApplicationContext(), (result!=null)?msg + Logger.build(result):msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    class CloseWS extends AsyncTask<Void, Void, WebService_GetSA_CloseSession.WSResult> {

        public WeakReference<MainActivity> mContextRef;

        CloseWS( MainActivity context) {
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



}
