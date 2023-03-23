package com.dcpl.printfromonbase;

/**
 * The Following code defines an activity called Activity2. This activity displays a list of items and allows the user to
 * download a file related to the selected item. The activity reads a JSON file called Form.json to obtain
 * the list of items and other related information such as the document ID and the image ID.
 * It uses a custom adapter called MyAdapter to display the list of items in a ListView.
 */


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

//import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dcpl.printfromonbase.task.DownloadService;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Properties;


public class Activity2 extends AppCompatActivity {


    private ArrayList<String> mRow, mDocumentId, mImage;
    private static final String TAG = "Activity2";
    public String jsonText;
    protected JSONObject jsonObject;
    protected JSONArray jsonArray;
    protected String documentId;
    private String soapAddress, username, password, title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_2);

        checkTitle();
        configProperties();
        getFiles();
        addAdapter();

    }
//configProperties(): Reads the config.properties file from the assets folder to get the SOAP endpoint address and login credentials.
    private void configProperties() {
        try {
            InputStream stream = this.getAssets().open("config.properties");
            Properties props = new Properties();
            props.load(stream);
//            Log.d(TAG, "props: " + props);
            soapAddress = props.getProperty("SOAP_ADDRESS");
            username = props.getProperty("username");
            password = props.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //getFiles(): Reads the Form.json file from the assets folder and extracts the necessary data to populate the ListView.
    private void getFiles() {

        try {
            InputStream in = getAssets().open("Form.json");

            //FileInputStream stream = new FileInputStream(file);
            if (in.available() > 0) {
                try {
                    byte[] buffer = new byte[in.available()];
                    in.read(buffer);
                    in.close();
                    jsonText = new String(buffer, "UTF-8");
                    //Log.d(TAG, "jsonText: " + jsonText);
//                    FileChannel fc = stream.getChannel();
//                    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                    mRow = new ArrayList<String>();
                    mDocumentId = new ArrayList<String>();
                    mImage = new ArrayList<String>();

                    //jsonText = Charset.defaultCharset().decode(bb).toString();
                    //Log.d(TAG, "jsonText: "+ jsonText);

//                    Log.d(TAG, "titleFromMain: " + title);
                    jsonObject = new JSONObject(jsonText);
//                    Log.d(TAG, "jsonObject: " + jsonObject);
                    JSONObject jsonFormObject = jsonObject.getJSONObject("Forms");
                    jsonArray = jsonFormObject.getJSONArray(title);
//                    Log.d(TAG, "jsonArray for the required form of " + title + ": " + jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        mRow.add(jsonObject1.getString("name"));
                        mDocumentId.add(jsonObject1.getString("documentId"));
                        mImage.add(jsonObject1.getString("imageId"));
                    }
//                    Log.d(TAG, "name from categories: " + mRow);
//                    Log.d(TAG, "documentId from categories: " + mDocumentId);
//                    Log.d(TAG, "imageId from categories: " + mImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//addAdapter(): Sets up the adapter for the ListView.
    public void addAdapter() {

        MyAdapter adapter = new MyAdapter(Activity2.this, mRow, mImage);
        ListView listView1 = findViewById(R.id.listView0);
        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (0 == 0) {
                    new DownloadFileTask(Activity2.this).execute();
                    title = mRow.get(i);
                    documentId = mDocumentId.get(i);
                }
            }
        });
    }


    //checkTitle(): Retrieves the title of the activity from the intent and sets the title of the activity accordingly.
    private void checkTitle() {
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");

        if (title.equals("")) {
            this.setTitle("Print Sample");
        } else {
            this.setTitle("Print Sample - " + title);
        }
    }



    //MyAdapter(): An adapter class that extends the ArrayAdapter class to bind the data to the ListView.
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList rTitle;
        ArrayList rImages;

        MyAdapter(Context c, ArrayList aTitle, ArrayList aImage) {

            super(c, R.layout.row, R.id.textView0, aTitle);
            this.context = c;
            this.rTitle = aTitle;
            this.rImages = aImage;
        }

        //getView(): Inflates the row view for each item in the ListView and populates it with the necessary data.
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView myTitle = row.findViewById(R.id.textView0);
            ImageView imageView = row.findViewById(R.id.image0);
            myTitle.setText(rTitle.get(position).toString());
            imageView.setImageResource(getResources().getIdentifier(rImages.get(position).toString(), "drawable", getPackageName()));
            return row;
        }
    }


    //DownloadFileTask(): An AsyncTask class that downloads a document related to the selected item in the ListView.
    // The SOAP endpoint address and login credentials are passed as parameters to the download function.
    private class DownloadFileTask extends AsyncTask<Void, Void, DownloadService.WSResult> {

        private final WeakReference<Activity2> mContextRef;

        DownloadFileTask(final Activity2 context) {
            this.mContextRef = new WeakReference<>(context);
        }

        @Override
        protected DownloadService.WSResult doInBackground(Void... voids) {
            DownloadService downloadService = new DownloadService();
            DownloadService.WSResult wsResult = downloadService.download(Activity2.this, documentId, soapAddress, username, password);
            String docPath = wsResult.getDocName();
//            Log.d(TAG, "Document: " + docPath);
            return wsResult;
        }


        /**The following is a method called onPostExecute with a parameter wsResult of type DownloadService.WSResult.

        The method first gets the docName value from wsResult and checks if it is not empty.
         If it's not empty, a toast message is created with the message from wsResult and shown at the center of the screen.
         Otherwise, a toast message is created with wsResult status and message, concatenated together, and also shown at
         the center of the screen.
         */
        @Override
        protected void onPostExecute(DownloadService.WSResult wsResult) {
            super.onPostExecute(wsResult);
            String docId = wsResult.getDocName();
            if (!docId.equals("")) {
                Toast toast = Toast.makeText(mContextRef.get(), wsResult.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            } else {
                Toast toast = Toast.makeText(mContextRef.get(), "Status : " + wsResult.getStatus() + "\nMessage : " + wsResult.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        }
    }
}
