// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
package com.dcpl.printfromonbase.task;

import android.content.SharedPreferences;
import android.net.MailTo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;

import com.dcpl.printfromonbase.Fragment_Documents;
import com.dcpl.printfromonbase.MainActivity;
import com.dcpl.printfromonbase.fragments.PrintConfigureFragment;
import com.hp.workpath.api.CapabilitiesExceededException;
import com.hp.workpath.api.printer.NetworkCredentialsAttributes;
import com.hp.workpath.api.printer.PrintAttributes;
import com.hp.workpath.api.printer.PrintAttributesCaps;
import com.hp.workpath.api.printer.PrinterService;
import com.hp.workpath.api.printer.PrintletAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Async task to request print.
 */
public class RequestPrintTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = MainActivity.TAG;

    private final WeakReference<MainActivity> mContextRef;

    private final SharedPreferences mPrefs;

    private String mErrorMsg = null;
    private File FILE_PATH;
    private int Copies;

    public RequestPrintTask(final MainActivity context) {
        this.mContextRef = new WeakReference<>(context);
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    @Override
    protected String doInBackground(final Void... params) {
        try {

            final boolean settingsUi = mPrefs.getBoolean(PrintConfigureFragment.PREF_SHOW_SETTINGS, false);
            //  Log.i(TAG, "Settings UI:" + settingsUi);

            PrintAttributes attributes = null;

            if (!settingsUi) {
                final PrintAttributesCaps caps = mContextRef.get().getCapabilities();

                if (caps == null) {
                    //mErrorMsg = mContextRef.get().getString(R.string.capabilities_not_loaded);
                    return null;
                }


//                // Build PrintAttributes based on preferences values
//                final PrintAttributes.Duplex duplex = PrintAttributes.Duplex.valueOf(
//                        mPrefs.getString(PrintConfigureFragment.PREF_DUPLEX_MODE,
//                                PrintAttributes.Duplex.DEFAULT.name()));
                //    Log.i(TAG, "Selected Duplex:" + duplex.name());

//                final PrintAttributes.ColorMode cm = PrintAttributes.ColorMode.valueOf(
//                        mPrefs.getString(PrintConfigureFragment.PREF_COLOR_MODE,
//                                PrintAttributes.ColorMode.DEFAULT.name()));
                //   Log.i(TAG, "Selected Color Mode:" + cm.name());

//                final String saf = mPrefs.getString(PrintConfigureFragment.PREF_AUTOFIT,
//                        PrintAttributes.AutoFit.DEFAULT.name());
//                final PrintAttributes.AutoFit af = PrintAttributes.AutoFit.valueOf(saf);
                //  Log.i(TAG, "Selected Auto Fit: " + saf);
//
                final PrintAttributes.StapleMode sm = PrintAttributes.StapleMode.valueOf(
                        mPrefs.getString(PrintConfigureFragment.PREF_STAPLE_MODE,
                                PrintAttributes.ColorMode.DEFAULT.name()));
                //      Log.i(TAG, "Selected Staple Mode:" + sm.name());

//                final PrintAttributes.CollateMode collateMode = PrintAttributes.CollateMode.valueOf(
//                        mPrefs.getString(PrintConfigureFragment.PREF_COLLATE_MODE,
//                                PrintAttributes.CollateMode.DEFAULT.name()));
                //  Log.i(TAG, "Selected Collate Mode:" + collateMode.name());

//                final PrintAttributes.PaperSource psrc = PrintAttributes.PaperSource.valueOf(
//                        mPrefs.getString(PrintConfigureFragment.PREF_PAPER_SOURCE,
//                                PrintAttributes.PaperSource.DEFAULT.name()));
                //Log.i(TAG, "Selected Paper Source:" + psrc.name());
//
//                final PrintAttributes.PaperSize psz = PrintAttributes.PaperSize.valueOf(
//                        mPrefs.getString(PrintConfigureFragment.PREF_PAPER_SIZE,
//                                PrintAttributes.PaperSize.DEFAULT.name()));
                //       Log.i(TAG, "Selected Paper Size:" + psz.name());

                final PrintAttributes.PaperType paperType = PrintAttributes.PaperType.valueOf(
                        mPrefs.getString(PrintConfigureFragment.PREF_PAPER_TYPE,
                                PrintAttributes.PaperType.DEFAULT.name()));
                //  Log.i(TAG, "Selected Paper Type:" + paperType.name());

                final PrintAttributes.DocumentFormat dfmt = PrintAttributes.DocumentFormat.valueOf(
                        mPrefs.getString(PrintConfigureFragment.PREF_DOC_FORMAT,
                                PrintAttributes.DocumentFormat.AUTO.name()));
                //  Log.i(TAG, "Selected Document Format:" + dfmt.name());

//                final int copies = Integer.valueOf(
//                        mPrefs.getString(PrintConfigureFragment.PREF_COPIES, "1"));
                //   Log.i(TAG, "Selected copies: " + copies);

                String jobName = mPrefs.getString(PrintConfigureFragment.PREF_JOB_NAME, null);
                //   Log.i(TAG, "Selected jobName: " + jobName);

                final PrintAttributes.Source source = PrintAttributes.Source.valueOf(
                        mPrefs.getString(PrintConfigureFragment.PREF_SOURCE, PrintAttributes.Source.STORAGE.name()));

                //  Log.i(TAG, "Selected source: " + source);

                if (source == PrintAttributes.Source.STORAGE) {
                    String filePath = mPrefs.getString(PrintConfigureFragment.PREF_FILENAME, "");
                    //    Log.i(TAG, "Selected path: " + filePath);
                    if (!TextUtils.isEmpty(filePath) && !filePath.startsWith("/")) {
                        filePath = FILE_PATH + filePath;

                        // Log.d(TAG, "source: "+source);
                    } else {
                        // Log.i(TAG, "Absolute path has been entered: " + filePath);
                    }


                    attributes =
                            new PrintAttributes.PrintFromStorageBuilder(Uri.fromFile(new File(Fragment_Documents.getdocument)))
                                    .setCollateMode(collateMode())
                                    .setColorMode(colorMode())
                                    .setDuplex(duplex())
                                    .setAutoFit(af())
                                    .setStapleMode(sm())
                                    .setPaperSource(psrc())
                                    .setPaperSize(psz())
                                    .setPaperType(paperType())
                                    .setDocumentFormat(dfmt)
                                    .setCopies(Integer.parseInt(copies()))
                                    .setJobName(jobName)
                                    .build(caps);
                } else if (source == PrintAttributes.Source.HTTP) {
                    String fileUri = mPrefs.getString(PrintConfigureFragment.PREF_URI, "");
                    //Log.i(TAG, "Selected uri: " + fileUri);

                    String fileUriUsername = mPrefs.getString(PrintConfigureFragment.PREF_URI_USERNAME, "");
                    String fileUriPassword = mPrefs.getString(PrintConfigureFragment.PREF_URI_PASSWORD, "");

                    NetworkCredentialsAttributes networkCredentialsAttributes = null;
                    if (!TextUtils.isEmpty(fileUriUsername) && !TextUtils.isEmpty(fileUriPassword)) {
                        networkCredentialsAttributes = new NetworkCredentialsAttributes.Builder()
                                .setUserName(fileUriUsername)
                                .setPassword(fileUriPassword)
                                .build();
                    }

                    // building with common print attributes set
                    attributes = new PrintAttributes.PrintFromHttpBuilder(Uri.parse(fileUri))
                            .setCollateMode(collateMode())
                            .setColorMode(colorMode())
                            .setDuplex(duplex())
                            .setAutoFit(af())
                            .setStapleMode(sm())
                            .setPaperSource(psrc())
                            .setPaperSize(psz())
                            .setPaperType(paperType())
                            .setDocumentFormat(documentformat())
                            .setCopies(Integer.parseInt(copies()))
                            .setJobName(jobName)
                            .setNetworkCredentials(networkCredentialsAttributes)
                            .build(caps);
                } else if (source == PrintAttributes.Source.USB) {
                    String filePath = mPrefs.getString(PrintConfigureFragment.PREF_USB_FILENAME, "");

                    //Log.i(TAG, "Selected usb file: " + filePath);

                    // building with common print attributes set
                    attributes = new PrintAttributes.PrintFromUsbBuilder(filePath)
                            .setCollateMode(collateMode())
                            .setColorMode(colorMode())
                            .setDuplex(duplex())
                            .setAutoFit(af())
                            .setStapleMode(sm())
                            .setPaperSource(psrc())
                            .setPaperSize(psz())
                            .setDocumentFormat(documentformat())
                            .setCopies(Integer.parseInt(copies()))
                            .setJobName(jobName)
                            .build(caps);
                } else if (source == PrintAttributes.Source.STREAM) {
                    String filePath = mPrefs.getString(PrintConfigureFragment.PREF_STREAM_FILENAME, "");

                    //    Log.i(TAG, "Selected file for stream: " + filePath);

                    // can print from any InputStream, here FileInputStream as example
                    InputStream printStream = new FileInputStream(new File(filePath));

                    // building with common print attributes set
                    attributes = new PrintAttributes.PrintFromStreamBuilder(printStream)
                            .setCollateMode(collateMode())
                            .setColorMode(colorMode())
                            .setDuplex(duplex())
                            .setAutoFit(af())
                            .setStapleMode(sm())
                            .setPaperSource(psrc())
                            .setPaperSize(psz())
                            .setDocumentFormat(documentformat())
                            .setCopies(Integer.parseInt(copies()))
                            .setJobName(jobName)
                            .build(caps);
                }
            }

            final PrintletAttributes taskAttribs = new PrintletAttributes.Builder()
                    .setShowSettingsUi(settingsUi)
                    .build();

            // Submit the job
            String rid = PrinterService.submit(mContextRef.get(), attributes, taskAttribs);
            return rid;
        } catch (CapabilitiesExceededException cee) {
            mErrorMsg = "CapabilitiesExceededException: " + cee.getMessage();
        } catch (IllegalArgumentException iae) {
            mErrorMsg = "IllegalArgumentException: " + iae.getMessage();
        } catch (Exception ex) {
            mErrorMsg = "Unknown exception: " + ex.getMessage();
        }

        return null;
    }

    private PrintAttributes.DocumentFormat documentformat() {
        if (MainActivity.DocumentFormat == " Auto") {
            return PrintAttributes.DocumentFormat.AUTO;
        }  else if (MainActivity.DocumentFormat == " JPEG") {
            return PrintAttributes.DocumentFormat.JPEG;
        }
        else if (MainActivity.DocumentFormat == " PDF") {
            return PrintAttributes.DocumentFormat.PDF;
        }else {
            return PrintAttributes.DocumentFormat.TIFF;
        }
    }


    private PrintAttributes.PaperType paperType() {
        if (MainActivity.PaperType == "  Default") {
            return PrintAttributes.PaperType.DEFAULT;
        } else if (MainActivity.PaperType == " Plain") {
            return PrintAttributes.PaperType.PLAIN;
        } else if (MainActivity.PaperType == " Light") {
            return PrintAttributes.PaperType.LIGHT;
        } else if (MainActivity.PaperType == " Bond") {
            return PrintAttributes.PaperType.BOND;
        } else if (MainActivity.PaperType == " Labels") {
            return PrintAttributes.PaperType.LABELS;
        } else if (MainActivity.PaperType == " Color") {
            return PrintAttributes.PaperType.COLORED;
        } else if (MainActivity.PaperType == " MidWeight") {
            return PrintAttributes.PaperType.MID_WEIGHT;
        } else {
            return PrintAttributes.PaperType.CARD_STOCK;
        }
    }


    private PrintAttributes.PaperSource psrc() {

        if (MainActivity.PaperSource == "  PSDefault") {
            return PrintAttributes.PaperSource.DEFAULT;
        } else if (MainActivity.PaperSource == " Auto") {
            MainActivity.radioPSAuto.setVisibility(View.VISIBLE);
            return PrintAttributes.PaperSource.AUTO;
        } else if (MainActivity.PaperSource == " Tray_1") {
            MainActivity.radioTray_1.setVisibility(View.VISIBLE);
            return PrintAttributes.PaperSource.TRAY_1;
        } else {

            return PrintAttributes.PaperSource.TRAY_2;
        }
    }

    private PrintAttributes.StapleMode sm() {
        if (MainActivity.StapleMode == " Auto") {
            return PrintAttributes.StapleMode.DEFAULT;
        } else if (MainActivity.StapleMode == " None") {
            return PrintAttributes.StapleMode.NONE;
        } else  {
            return PrintAttributes.StapleMode.STAPLE;
        }

    }

    private String copies() {

        return MainActivity.copies;
    }

    private PrintAttributes.PaperSize psz() {
        if (MainActivity.PaperSize == " PSDefault") {
            return PrintAttributes.PaperSize.DEFAULT;
        } else if (MainActivity.PaperSize == " Letter") {
            return PrintAttributes.PaperSize.LETTER;
        } else if (MainActivity.PaperSize == " legal") {
            return PrintAttributes.PaperSize.LEGAL;
        } else if (MainActivity.PaperSize == " A3") {
            return PrintAttributes.PaperSize.A3;
        } else {
            return PrintAttributes.PaperSize.A4;
        }
    }

    private PrintAttributes.Duplex duplex() {
        if (MainActivity.DuplexMode == " DefaultMode") {
            return PrintAttributes.Duplex.DEFAULT;
        } else if (MainActivity.DuplexMode == " None") {
            return PrintAttributes.Duplex.NONE;
        } else if (MainActivity.DuplexMode == " Book") {
            return PrintAttributes.Duplex.BOOK;
        } else {
            return PrintAttributes.Duplex.FLIP;
        }
    }

    private PrintAttributes.AutoFit af() {
        if (MainActivity.Autofit == " AutoDefault") {
            return PrintAttributes.AutoFit.DEFAULT;
        } else if (MainActivity.Autofit == " radiotrue") {
            return PrintAttributes.AutoFit.TRUE;
        } else {
            return PrintAttributes.AutoFit.FALSE;
        }
    }

    private PrintAttributes.CollateMode collateMode() {
        if (MainActivity.CollateMode == " collatedefault") {
            return PrintAttributes.CollateMode.DEFAULT;
        } else if (MainActivity.CollateMode == "Collate") {
            return PrintAttributes.CollateMode.COLLATED;
        } else {
            return PrintAttributes.CollateMode.UNCOLLATED;
        }
    }

    private PrintAttributes.ColorMode colorMode() {
        if (MainActivity.ColorMode == "Default") {
            return PrintAttributes.ColorMode.DEFAULT;
        } else if (MainActivity.ColorMode == "Auto") {
            return PrintAttributes.ColorMode.AUTO;
        } else if (MainActivity.ColorMode == "Color") {
            return PrintAttributes.ColorMode.COLOR;
        } else {
            return PrintAttributes.ColorMode.MONO;
        }
    }
}
