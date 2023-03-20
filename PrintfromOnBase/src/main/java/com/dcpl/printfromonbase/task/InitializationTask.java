// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
package com.dcpl.printfromonbase.task;

import android.os.AsyncTask;
import android.util.Log;

import com.dcpl.printfromonbase.MainActivity;
import com.dcpl.printfromonbase.R;
import com.hp.workpath.api.Workpath;
import com.hp.workpath.api.SsdkUnsupportedException;
import com.hp.workpath.api.job.JobService;
import com.hp.workpath.api.printer.PrinterService;


import java.lang.ref.WeakReference;

public class InitializationTask extends AsyncTask<Void, Void, InitializationTask.InitStatus> {

    private static final String TAG = MainActivity.TAG;

    private Exception mException = null;

    private final WeakReference<MainActivity> mContextRef;

    public InitializationTask(MainActivity context) {
        this.mContextRef = new WeakReference<>(context);
    }

    @Override
    protected InitStatus doInBackground(final Void... params) {
        InitStatus status = InitStatus.NO_ERROR;

        try {
            // initialize Workpath SDK
            Workpath.getInstance().initialize(mContextRef.get());
        } catch (SsdkUnsupportedException sue) {
            Log.e(TAG, "SDK is not supported!", sue);
            mException = sue;
            status = InitStatus.INIT_EXCEPTION;
        } catch (SecurityException se) {
            Log.e(TAG, "Security exception!", se);
            mException = se;
            status = InitStatus.INIT_EXCEPTION;
        }

        // Check if PrinterService is supported
        if (status == InitStatus.NO_ERROR
                && (!PrinterService.isSupported(mContextRef.get())
                || !JobService.isSupported(mContextRef.get()))) {
            // PrinterService is not supported on this device
            status = InitStatus.NOT_SUPPORTED;
        }

        return status;
    }

    @Override
    protected void onPostExecute(final InitStatus status) {
        if (status == InitStatus.NO_ERROR) {
            mContextRef.get().handleComplete();
            return;
        }

        switch (status) {
            case INIT_EXCEPTION:
                mContextRef.get().handleException(mException);
                break;
            case NOT_SUPPORTED:
                mContextRef.get().handleException(new Exception(mContextRef.get().getString(R.string.service_not_supported)));
                break;
            default:
                mContextRef.get().handleException(new Exception(mContextRef.get().getString(R.string.unknown_error)));
        }
    }

    public enum InitStatus {
        INIT_EXCEPTION,
        NOT_SUPPORTED,
        NO_ERROR
    }
}