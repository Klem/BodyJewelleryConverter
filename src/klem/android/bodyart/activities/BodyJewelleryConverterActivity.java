package klem.android.bodyart.activities;




import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class BodyJewelleryConverterActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, JewelleryActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("jewellery").setContent(intent);
	    spec.setIndicator("jewellery", res.getDrawable(R.drawable.tab_jewellery));
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, ScrewActivity.class);
	    spec = tabHost.newTabSpec("screw").setContent(intent);
	    spec.setIndicator("screw", res.getDrawable(R.drawable.tab_screw));
	    tabHost.addTab(spec);


	    intent = new Intent().setClass(this, HookActivity.class);
	    spec = tabHost.newTabSpec("hook").setContent(intent);
	    spec.setIndicator("hook", res.getDrawable(R.drawable.tab_hook));
	    tabHost.addTab(spec);
	    
	    tabHost.setCurrentTab(0);
	    
	    //TODO MANAGE DATABASES CONNECTIONS
	    
    }
}