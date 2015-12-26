package com.jaisriram.highqualityrecorders;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class AudioList extends Activity {
	ListView lv1;
	Context context;
	ArrayAdapter<String> aa;
	TextView tv;
	String selectedItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audiolist);
		lv1 = (ListView) findViewById(R.id.lv1);
		Bundle s = getIntent().getExtras();
		String s1 = s.getString("audio");
		final ArrayList<String> MyFiles = new ArrayList<String>();
		File f = new File(s1);
		File[] files = f.listFiles();
		if (files.length == 0) {

		} else {
			for (int i = 0; i < files.length; i++)
				MyFiles.add(files[i].getName());
		}
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, MyFiles);

		lv1.setAdapter(aa);
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				final Uri clickedFile = Uri.parse((String) arg0
						.getItemAtPosition(position));
				selectedItem = lv1.getItemAtPosition(position).toString();
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

				popup.show();

			}

		});
	}
}
