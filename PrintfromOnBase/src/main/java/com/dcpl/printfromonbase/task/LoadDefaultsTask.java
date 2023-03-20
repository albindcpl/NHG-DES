// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
package com.dcpl.printfromonbase.task;

import android.os.AsyncTask;

import com.dcpl.printfromonbase.Logger;
import com.dcpl.printfromonbase.MainActivity;
import com.dcpl.printfromonbase.fragments.PrintConfigureFragment;
import com.hp.workpath.api.Result;
import com.hp.workpath.api.printer.PrintAttributes;
import com.hp.workpath.api.printer.PrinterService;

import java.lang.ref.WeakReference;

/**
 * Async task to request device defaults for Print.
 */
public class LoadDefaultsTask extends AsyncTask<Void, Void, PrintAttributes> {
    private final WeakReference<MainActivity> mContextRef;
    private final WeakReference<PrintConfigureFragment> mFragment;

    private Result result;

    public LoadDefaultsTask(final MainActivity context, final PrintConfigureFragment fragment) {
        this.mContextRef = new WeakReference<>(context);
        this.mFragment = new WeakReference<>(fragment);
        this.result = new Result();
    }

    @Override
    protected PrintAttributes doInBackground(final Void... params) {
        return PrinterService.getDefaults(mContextRef.get(), result);
    }

    @Override
    protected void onPostExecute(final PrintAttributes defaults) {
        super.onPostExecute(defaults);

        if (defaults != null && result.getCode() == Result.RESULT_OK) {
            mContextRef.get().showResult("Defaults=" + Logger.build(defaults));
            mFragment.get().setDefaultPrintAttributes(defaults);
        } else {
            mContextRef.get().showResult("PrinterService.getDefaults(): ", result);
        }
    }
}
