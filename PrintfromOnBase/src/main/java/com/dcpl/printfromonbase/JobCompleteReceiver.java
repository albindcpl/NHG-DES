// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
package com.dcpl.printfromonbase;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dcpl.printfromonbase.fragments.PrintConfigureFragment;

/**
 * Simple Broadcast receiver to observe job completion intent
 */
public final class JobCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = MainActivity.TAG;

    public static final String RID_EXTRA = "rid";
    public static final String JOB_ID_EXTRA = "jobid";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String action = intent.getAction();
        final ComponentName component = intent.getComponent();

        // Retrieve the Job Id from the received Intent and the expected Job Id from the app's shared preferences
        // Verify that received Job Id is same as expected one
        final String jobId = intent.getStringExtra(JOB_ID_EXTRA);
        final String expectedJobId = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PrintConfigureFragment.CURRENT_JOB_ID, null);

        //    // Check if the received Intent matches the expected Job Id and the print completed action
        if (MainActivity.ACTION_PRINT_COMPLETED.equals(action) &&
                component != null && context.getPackageName().equals(component.getPackageName()) &&
                jobId.equals(expectedJobId)) {
           // Log.i(TAG, context.getString(R.string.received_complete_intent));
        }
    }
}
