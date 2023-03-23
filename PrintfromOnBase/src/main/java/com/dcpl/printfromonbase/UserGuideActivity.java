package com.dcpl.printfromonbase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

/**
 * This is an Android Activity class that displays a user guide in the form of a PDF document using a
 * third-party library called "PDFView". The PDF document is stored in the app's assets folder and is loaded into
 * the PDFView using the "fromAsset" method.
 *
 * The class extends the AppCompatActivity class and overrides its onCreate method, which is called when
 * the activity is created. In the onCreate method, the activity layout is set using the setContentView method,
 * and the PDFView is initialized and loaded with the PDF document.
 *
 * The class also contains a method called hideKeyboardFrom, which takes a context and a view as parameters
 * and uses the InputMethodManager to hide the soft keyboard from the specified view.
 *
 * In addition, the class sets a flag on the window to prevent the user from taking screenshots or
 * recording the screen while the activity is visible.
 *
 * Finally, the class contains an OnClickListener for an ImageView, which when clicked, navigates
 * the user back to the OnBase_Login_Screen activity by creating a new intent and starting the activity.
 */

public class UserGuideActivity extends AppCompatActivity {
    PDFView pdfView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_user_guide);
        pdfView =(PDFView) findViewById(R.id.pdfViewerr);
        pdfView.fromAsset("userguide.pdf").load();


        imageView = findViewById(R.id.ivBackkk);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), OnBase_Login_Screen.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(UserGuideActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
