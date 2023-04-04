// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
package com.dcpl.printfromonbase;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.dcpl.printfromonbase.filebrowser.FileListFragment;
import static com.dcpl.printfromonbase.filebrowser.FileUtils.PATH;

import java.io.File;


/**Line 36-60
 * This is a Java class called FileBrowserActivity. It extends AppCompatActivity and implements two interfaces,
 * FragmentManager.OnBackStackChangedListener and FileListFragment.Callbacks.
 *
 * In the onCreate method, it sets the content view to a layout called activity_file_browser.
 * It then calls the findViewElements method to initialize the text and button elements.
 *
 * It initializes a FragmentManager object and sets it to mFragmentManager.
 * It also adds this as a listener for changes to the back stack in the FragmentManager.
 *
 * It sets the mPath variable to the path of the app's internal files directory using the getFilesDir method.
 * It then calls the addFragment method to add a new FileListFragment to the mFragmentManager with the mPath variable as an argument.
 * Finally, it calls the setNavigationPath method to set the path text view to display the mPath.
 */
public class FileBrowserActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener, FileListFragment.Callbacks {


    private FragmentManager mFragmentManager;

    private String mPath;
    private TextView mPathTextView;
    private HorizontalScrollView mHorizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        // find the text and button
        findViewElements();

        mFragmentManager = getFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        mPath = getFilesDir().getPath();
        addFragment(mPath);
        setNavigationPath(mPath);
    }

    private void findViewElements() {
        // Find the TextView and HorizontalScrollView views in the layout

        mPathTextView = findViewById(R.id.pathTextView);
        mHorizontalScrollView = findViewById(R.id.horizontalScrollView);
    }

    @Override
    protected void onPause() {
        // Finish the activity when it is paused

        super.onPause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackStackChanged() {

        // Get the number of entries in the fragment manager's back stack

        int count = mFragmentManager.getBackStackEntryCount();

        if (count > 0) {

            // If there are entries, get the most recent one
            FragmentManager.BackStackEntry fragment = mFragmentManager.getBackStackEntryAt(count - 1);
            mPath = fragment.getName();
        } else {

            // If there are no entries, use the default file path
            mPath = getFilesDir().getPath();
        }

        // Update the navigation path view with the current file path
        setNavigationPath(mPath);

        // Invalidate the options menu, causing it to be recreated
        invalidateOptionsMenu();
    }

    private void addFragment(String path) {

        // Create a new instance of the FileListFragment with the given path
        FileListFragment fragment = FileListFragment.newInstance(path);

        // Add the fragment to the fragment manager's back stack and commit the transaction
        mFragmentManager.beginTransaction()
                .add(R.id.mainFragmentContainer, fragment).commit();
    }


    /**Line 135-167
     * The replaceFragment method takes a File object, sets the mPath variable to the absolute path of the file,
     * creates a new FileListFragment instance with the mPath variable as an argument,
     * and replaces the current fragment in the mainFragmentContainer with the new fragment.
     * It also sets the fragment transition and adds the transaction to the back stack.
     *
     * The finishWithResult method takes a File object as a parameter, and if it is not null,
     * sets the result of the activity as RESULT_OK with an intent that includes the absolute path of the file.
     * If the file is null, it sets the result as RESULT_CANCELED. It then finishes the activity.
     *
     * The onFileSelected method is called when a file is selected in the FileListFragment.
     * If the file is not null, it checks whether it is a directory or a file. If it is a directory, it sets
     * the navigation path to the absolute path of the file and replaces the current fragment with a new FileListFragment
     * instance for the selected directory. If it is a file, it finishes the activity with the result set to
     * the absolute path of the selected file. If the file is null, it displays a toast message indicating an error in file selection
     *
     */
    private void replaceFragment(File file) {
        mPath = file.getAbsolutePath();

        FileListFragment fragment = FileListFragment.newInstance(mPath);
        mFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(mPath).commit();
    }

    private void finishWithResult(File file) {
        if (file != null) {
            setResult(RESULT_OK, new Intent().putExtra(PATH, file.getAbsolutePath()));
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public void onFileSelected(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                setNavigationPath(file.getAbsolutePath());
                replaceFragment(file);
            } else {
                finishWithResult(file);
            }
        } else {
            Toast.makeText(FileBrowserActivity.this, R.string.error_select_file,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void setNavigationPath(String absolutePath) {
        if (mPathTextView != null) {
            mPathTextView.setText(absolutePath);
        }
        mHorizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                mHorizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
