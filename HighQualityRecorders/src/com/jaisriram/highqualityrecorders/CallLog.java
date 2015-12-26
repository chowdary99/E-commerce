package com.jaisriram.highqualityrecorders;

import java.io.File;
import java.util.ArrayList;

import com.jaisriram.highqualityrecorders.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

public class CallLog extends Activity {
	private final String TAG = "CallRecorder";
	private ListView fileList = null;
	public static final String FILE_DIRECTORY = "MyRecorder/recordedCalls";
	private ArrayAdapter<String> fAdapter = null;
	@SuppressWarnings("unused")
	private ArrayList<String> recordingNames = null;

	@SuppressWarnings("unused")
	private void loadRecordingsFromProvider() {
		fAdapter.clear();
		ContentResolver cr = getContentResolver();
		Cursor c = cr.query(RecordingProvider.CONTENT_URI, null, null, null,
				null);
		String[] names = new String[c.getCount()];
		int i = 0;

		if (c.moveToFirst()) {
			do {
				// Extract the recording names
				fAdapter.add(c.getString(RecordingProvider.DETAILS_COLUMN));
				i++;
			} while (c.moveToNext());
		}

		fAdapter.notifyDataSetChanged();
	}

	private void loadRecordingsFromDir() {
		fAdapter.clear();
		File dir = new File(RecordService.DEFAULT_STORAGE_LOCATION);
		String[] dlist = dir.list();

		for (int i = 0; i < dlist.length; i++) {
			fAdapter.add(dlist[i]);
		}
		fAdapter.notifyDataSetChanged();
	}

	private class CallItemClickListener implements
			AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			PopupMenu popup = new PopupMenu(getApplicationContext(), arg1);
			popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					switch (item.getItemId()) {
					case R.id.play:
						Intent i = new Intent(
								android.content.Intent.ACTION_VIEW);
						startActivity(i);
						break;
					case R.id.delete:
						removeItem(selectedItem);
						break;
					case R.id.share:
						String sharedDesc = null;
						sharedDesc = clickedFile.getPath();
						Intent sharingIntent = new Intent(
								Intent.ACTION_SEND);
						sharingIntent.setType("audio/*");
						sharingIntent.putExtra(
								android.content.Intent.EXTRA_ORIGINATING_URI,
								sharedDesc);
						startActivity(Intent.createChooser(sharingIntent,
								"Action using"));

						break;

					}
					return true;
				}

				private void removeItem(String selectedItem) {
					MyFiles.remove(selectedItem);
					aa.notifyDataSetChanged();

				}

			});
			
//			CharSequence s = (CharSequence) parent.getItemAtPosition(position);
//			Log.w(TAG, "CallLog just got an item clicked: " + s);
//			File f = new File(RecordService.DEFAULT_STORAGE_LOCATION + "/"
//					+ s.toString());
//			// showPromotionPieceDialog(s.toString(), position);
//			boolean useMediaController = true;
//
//			if (useMediaController) {
//				Intent playIntent = new Intent(getApplicationContext(),
//						CallPlayer.class); // Intent.ACTION_VIEW);
//				Uri uri = Uri.fromFile(f);
//				playIntent.setData(uri);
//				startActivity(playIntent);
//			} else {
//				playFile(s.toString());
//			}
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_log);

		// recordingNames = new String[0];
		fileList = (ListView) findViewById(R.id.play_file_list);

		Context context = getApplicationContext();
		fAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1);
		fileList.setAdapter(fAdapter);
		fileList.setOnItemClickListener(new CallItemClickListener());
	}

	public void onStart() {
		super.onStart();
		Log.i(TAG, "CallLog onStart");
	}

	public void onRestart() {
		super.onRestart();
		Log.i(TAG, "CallLog onRestart");
	}

	public void onResume() {
		super.onResume();
		Log.i(TAG,
				"CallLog onResume about to load recording list again, does this work?");

		loadRecordingsFromDir();
	}

	private void playFile(String fName) {
		Log.i(TAG, "playFile: " + fName);

		Context context = getApplicationContext();
		Intent playIntent = new Intent(context, PlayService.class);
		playIntent.putExtra(PlayService.EXTRA_FILENAME,
				RecordService.DEFAULT_STORAGE_LOCATION + "/" + fName);
		ComponentName name = context.startService(playIntent);
		if (null == name) {
			Log.w(TAG, "CallLog unable to start PlayService with intent: "
					+ playIntent.toString());
		} else {
			Log.i(TAG, "CallLog started service: " + name);
		}
	}

	public void onDestroy() {
		Context context = getApplicationContext();
		Intent playIntent = new Intent(context, PlayService.class);
		context.stopService(playIntent);

		super.onDestroy();
	}
}
