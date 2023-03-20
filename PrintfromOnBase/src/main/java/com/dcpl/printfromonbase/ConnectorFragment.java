package com.dcpl.printfromonbase;

//Refer to splash activity from explanation about these imports
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//ContextCompat is a utility class that provides methods for accessing resources and other functionality related
// to the application context in a backward-compatible way. It allows developers to use certain features that are
// only available on newer versions of Android on older devices that don't support those features.
import androidx.core.content.ContextCompat;


//This line of code is an import statement in Java for the Fragment class from the AndroidX library.
//
//AndroidX is a package library for Android development that provides backward-compatibility features for newer Android APIs.
// Fragments are a fundamental part of building user interfaces in Android applications. They represent reusable portions of
// the UI that can be combined to create a complete user interface.
//
//By importing the Fragment class from the AndroidX library, you can create and use fragments in your Android application code.
import androidx.fragment.app.Fragment;

//FragmentTransaction is a class in the AndroidX Fragment API that represents a transaction for adding, removing,
// replacing, or modifying fragments within an activity.
//
//By importing the FragmentTransaction class from the AndroidX library, you can create and execute transactions
// for fragments in your Android application code. This allows you to dynamically add, remove, or replace
// fragments in your app's user interface, providing a more dynamic and interactive experience for users.
import androidx.fragment.app.FragmentTransaction;

public class ConnectorFragment extends Fragment {

    //variable of type View, which can refer to any type of view in an Android user interface.
    // The variable is declared but not initialized, so it currently has the value null.
    View V;

    // public static variable of type TextView, which represents a text view in an Android user interface.
    // The variable is declared but not initialized, so it currently has the value null. The public keyword means
    // that this variable can be accessed from other classes.
    public static TextView tvConnector;
    public static TextView tvSettings;

    // variable of type ImageView, which represents an image view in an Android user interface
    ImageView ivSettings;
    ImageView ivCP;
    String attributes;
    //int color =  Color.parseColor("#ffcc66");


    //@Override is an annotation that indicates that the following method overrides a method in a superclass or interface.
    // In this case, the onCreate() method is overriding the corresponding method in the Activity superclass.
    @Override

    //public void onCreate(Bundle savedInstanceState) is the signature of the method. It specifies that the method has a
    // public access modifier, a void return type (meaning it doesn't return any value), and takes a single parameter of type
    // Bundle named savedInstanceState.

    //super.onCreate(savedInstanceState) is a call to the onCreate() method of the Activity superclass, which performs some
    // basic setup for the activity, such as creating the activity's window and initializing its state. This call must be made
    // at the beginning of the overridden onCreate() method to ensure that the superclass setup is performed
    // before any custom logic.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        new LoadCapabilitiesTask(context,PrintPropertyScreen.mFragment).execute();

//public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) is the signature of the method.
// It specifies that the method has a public access modifier, returns a View object, and takes three parameters: inflater of type
// LayoutInflater, container of type ViewGroup, and savedInstanceState of type Bundle.
//
//inflater is used to inflate the fragment's layout resource file into a View object. The container parameter provides the parent
// ViewGroup for the fragment's layout, if one is needed. savedInstanceState contains any saved state information for the fragment,
// such as if the fragment was previously destroyed and is now being recreated.
        V= inflater.inflate(R.layout.connectorandauthenticationfragment, container, false);

//tvConnector variable has been declared but not initialized in Line 42,
//findViewById(R.id.tvConnectorPath) is a method call on V that searches the view hierarchy for a view
// with the specified ID, tvConnectorPath
//he result of the findViewById() method is assigned to tvConnector, which means that tvConnector will
// reference the TextView view with the ID tvConnectorPath in the fragment or activity's layout. This allows the
// code to interact with the TextView programmatically, such as setting its text, visibility, or event listeners.
        tvConnector = V.findViewById(R.id.tvConnectorPath);
//        ivCP = V.findViewById(R.id.ivCP);
//        ivSettings = V.findViewById(R.id.ivSetting);
//        tvConnector.setHighlightColor(Color.GRAY);
//        tvConnector.setBackgroundResource(R.color.colorPrimaryDark);
        tvConnector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btnMale.setBackgroundResource(R.drawable.background);// change background your button like this

                //tvConnector.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.preference_summary_color)); -
                // sets the background color of the tvConnector TextView to a color defined in the app's resources using
                // ContextCompat.getColor(). The getActivity() method is used to obtain the Activity context for
                // the fragment or activity.
                tvConnector.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.preference_summary_color));// use this to set color as background
              //  ivCP.setVisibility(View.VISIBLE);

//tvSettings.setBackgroundResource(android.R.color.white); - sets the background color of the tvSettings
// TextView to a color defined in the Android system resources using android.R.color.white.
                tvSettings.setBackgroundResource(android.R.color.white);

    //Fragment fragment= new SettingFragment(); - creates a new instance of
                // the SettingFragment class, which is another fragment that will be displayed in the app's UI.
                Fragment fragment= new SettingFragment();

                //getFragmentManager() gets the FragmentManager instance for the current fragment. This is used to start a new
                // FragmentTransaction using beginTransaction(). The FragmentTransaction allows you to add, remove, or replace
                // fragments in the current fragment's layout.
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

//transaction.replace(R.id.fragment2, fragment), replace() is a method of the FragmentTransaction class that is used to replace a
// fragment with a new fragment. The first parameter R.id.fragment2 is the ID of the view group that the fragment will be placed into.
// The second parameter fragment is the new Fragment that will replace the existing one.
//
//In this case, R.id.fragment2 refers to the ID of a view group in the parent layout that is used to display the child fragment.
// The fragment variable holds an instance of the new fragment that will replace the existing one.
                transaction.replace(R.id.fragment2, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity


//addToBackStack(null) is a method of the FragmentTransaction class that adds the current transaction to the back stack.
// The back stack is a list of transactions that have been executed to navigate through the app's various screens or UI states.
//
//When you add a transaction to the back stack, the user can navigate back to the previous screen or UI
// state by pressing the back button. This is a convenient way to manage the app's navigation and allow the user
// to easily move between different screens or states.
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();

            }
        });


        /**tvSettings is a TextView that is obtained by calling findViewById()
         * on the fragment's view. The R.id.tvDefaultSettings parameter is the ID of the TextView in the XML layout
         * file that defines the fragment's UI.

         setOnClickListener() is then called on the tvSettings object, which sets up a listener for click events on the
         TextView. When the user clicks on the TextView, the code inside the onClick() method will be executed.

         In the onClick() method, tvConnector is a TextView object that is obtained by calling findViewById() on the
         fragment's view. The R.id.tvConnectorPath parameter is the ID of another TextView in the XML layout file that
         defines the fragment's UI.

         setBackgroundColor() is then called on the tvConnector object to change the background color of the TextView.
         ContextCompat.getColor() is used to obtain a color value from the app's resources. The getActivity() method is
         called to get a reference to the parent activity.

         Finally, setBackgroundResource() is called on tvSettings to change its background color to android.R.color.darker_gray.
         This sets the background color of the TextView to a predefined system color.
         *
         */

        tvSettings = V.findViewById(R.id.tvDefaultSettings);
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvConnector.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));// use this to set color as background


                tvSettings.setBackgroundResource(android.R.color.darker_gray);
             //   ivSettings.setVisibility(View.VISIBLE);
//                Fragment fragment= new ScanAttributesFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment2, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
//                transaction.addToBackStack(null);  // this will manage backstack
//                transaction.commit();


            }
        });



        return V;
    }
}
