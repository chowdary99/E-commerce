package com.jaisriram.highqualityrecorders;


import com.jaisriram.highqualityrecorders.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class RecorderActivity extends Activity {
	protected static final int RQS_RECORDING = 1;
	protected static final int REQUEST_VIDEO_CAPTURED = 1;
	ImageView audio, video, call;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorder);
		audio = (ImageView) findViewById(R.id.iv2);
		video = (ImageView) findViewById(R.id.iv1);
		call = (ImageView) findViewById(R.id.iv3);
		audio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				  Intent i=new Intent(getApplicationContext(), AudioRecorder.class);
				    startActivity(i);
			}
		});
		video.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
				startActivityForResult(intent, REQUEST_VIDEO_CAPTURED);

			}

		});
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), CallRecorder.class);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri uriVideo;
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK && requestCode == REQUEST_VIDEO_CAPTURED) {
			uriVideo = data.getData();
			Toast.makeText(getApplicationContext(), uriVideo.getPath(),
					Toast.LENGTH_LONG).show();
			 
//			Intent i1=new Intent(getApplicationContext(), VideoList.class);
//			i1.putExtra("video", filename );
//			startActivity(i1);
			 }
		
		 else {
			Toast.makeText(getApplicationContext(), "User Cancelled!",
					Toast.LENGTH_LONG).show();
		}
	}


}
