package com.jaisriram.highqualityrecorders;

import java.io.IOException;
import java.util.Date;

import com.jaisriram.highqualityrecorders.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;

import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class AudioRecorder extends Activity {
	TextView tv1;
	@SuppressWarnings("unused")
	private Button record, reset, stop;
	Chronometer c;
	long timeWhenStopped;
	private String filepath;
	MediaRecorder myRecorder;
	MediaPlayer mp;
	String file;
	String[] theNamesOfFiles;
	public static final String FILE_DIRECTORY = "MyRecorder/recordedCalls";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audiorecorder);
		tv1 = (TextView) findViewById(R.id.tv1);
		record = (Button) findViewById(R.id.b1);
		reset = (Button) findViewById(R.id.b2);
		stop = (Button) findViewById(R.id.b3);
		c = (Chronometer) findViewById(R.id.c1);
		filepath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/" + FILE_DIRECTORY + "/" + new Date().getTime()
				+ "Recording.3gpp";
		file = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/" + FILE_DIRECTORY + "/";
		myRecorder = new MediaRecorder();
		myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		myRecorder.setOutputFile(filepath);

	}

	public void open(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.b1:
			tv1.setText("Recording started");

			c.setBase(SystemClock.elapsedRealtime());
			c.start();

			try {
				myRecorder.prepare();
				myRecorder.start();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			record.setEnabled(false);
			stop.setEnabled(true);
			Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT)
					.show();

			break;
		case R.id.b2:
			tv1.setText("reset recording");
			c.setBase(SystemClock.elapsedRealtime());

			break;
		case R.id.b3:
			tv1.setText("Stop Recording");
			timeWhenStopped = c.getBase() - SystemClock.elapsedRealtime();
			c.stop();
			try {
				myRecorder.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}

			myRecorder.release();
			stop.setEnabled(false);
			record.setEnabled(true);
			AlertDialog.Builder b = new AlertDialog.Builder(this);
			b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int id) {

					Intent i1 = new Intent(getApplicationContext(),
							AudioList.class);
					i1.putExtra("audio", file);
					startActivity(i1);

				}
			});
			b.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					filepath = null;
					Toast.makeText(getApplicationContext(), "user cancelled",
							Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(),
							RecorderActivity.class);
					startActivity(i);
				}
			});
			AlertDialog a = b.create();
			a.setTitle("Do You Want To Save File ?");
			a.show();
		}

	}

}
