// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
package com.dcpl.printfromonbase.task;

import android.os.AsyncTask;

import com.dcpl.printfromonbase.MainActivity;
import com.dcpl.printfromonbase.R;
import com.dcpl.printfromonbase.fragments.PrintConfigureFragment;
import com.hp.workpath.api.Result;
import com.hp.workpath.api.printer.PrintAttributesCaps;;

import java.lang.ref.WeakReference;

/**
 * Async task to request device capabilities for print.
 */
public class LoadCapabilitiesTask extends AsyncTask<Void, Void, PrintAttributesCaps> {

    private final WeakReference<MainActivity> mContextRef;

    private Result result;

    private final WeakReference<PrintConfigureFragment> mFragment;

    public LoadCapabilitiesTask(final MainActivity context, final PrintConfigureFragment fragment) {
        this.mContextRef = new WeakReference<>(context);
        this.mFragment = new WeakReference<>(fragment);
        this.result = new Result();
    }

    @Override
    protected PrintAttributesCaps doInBackground(final Void... params) {
        return mContextRef.get().requestCaps(mContextRef.get(), result);
    }

    @Override
    protected void onPostExecute(final PrintAttributesCaps caps) {
        super.onPostExecute(caps);

        if (result.getCode() == Result.RESULT_OK) {
            mFragment.get().loadCapabilities(caps);
            mContextRef.get().showResult(mContextRef.get().getString(R.string.loaded_caps));
        }
    }
}
