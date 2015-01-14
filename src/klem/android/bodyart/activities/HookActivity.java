package klem.android.bodyart.activities;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HookActivity extends Activity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView textview = new TextView(this);
        textview.setText("Hook");
        setContentView(textview);
        
        setContentView(R.layout.main);
    }
}
