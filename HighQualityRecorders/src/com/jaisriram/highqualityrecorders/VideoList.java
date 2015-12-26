package com.jaisriram.highqualityrecorders;


import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VideoList extends Activity {
	ListView lv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videolist);
		lv1 = (ListView) findViewById(R.id.lv1);
		Bundle s=getIntent().getExtras();
		String s1=s.getString("video");
//         GetFiles(s1);
         ArrayList<String> MyFiles = new ArrayList<String>();
 	    File f = new File(s1);

 	    File[] files = f.listFiles();
 	    if (files.length == 0){
 	    	
 	    }
 	        
 	    else {
 	        for (int i=0; i<files.length; i++) 
 	            MyFiles.add(files[i].getName());
 	    
         lv1.setAdapter(new ArrayAdapter<String>(this,
        	        android.R.layout.simple_list_item_1,MyFiles ));
	
 	    }
		
	}
}

