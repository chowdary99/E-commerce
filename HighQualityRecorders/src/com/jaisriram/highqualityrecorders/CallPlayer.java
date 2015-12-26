package com.jaisriram.highqualityrecorders;

import com.jaisriram.highqualityrecorders.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;


public class CallPlayer
    extends Activity
    implements MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener
{
    private static final String TAG = "CallRecorder";
    private AudioPlayerControl aplayer = null;
    private MediaController controller = null;
    private ViewGroup anchor = null;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        anchor = (ViewGroup)findViewById(R.id.playerlayout);

        if (aplayer != null) {
            Log.i(TAG, "CallPlayer onCreate called with aplayer already allocated, destroying old one.");
            aplayer.destroy();
            aplayer = null;
        }
        if (controller != null) {
            Log.i(TAG, "CallPlayer onCreate called with controller already allocated, destroying old one.");
            controller = null;
        }

        Intent i = getIntent();
        String path = i.getData().getEncodedPath();

        Log.i(TAG, "CallPlayer onCreate with data: " + path);
        try {
            aplayer = new AudioPlayerControl(path, this);

        } catch (java.io.IOException e) {
            Log.e(TAG, "CallPlayer onCreate failed while creating AudioPlayerControl", e);
            Toast t = Toast.makeText(this, "CallPlayer received error attempting to create AudioPlayerControl: " + e, Toast.LENGTH_LONG);
            t.show();
            finish();
        }
    }

    public void onDestroy() {
        Log.i(TAG, "CallPlayer onDestroy");
        if (aplayer != null) {
            aplayer.destroy();
            aplayer = null;
        }
        super.onDestroy();
    }

	private class MyMediaController extends MediaController
    {
        public MyMediaController(Context c, boolean ff) {
            super(c, ff);
        }

    }

    // MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp)
    {
        Log.i(TAG, "CallPlayer onPrepared about to construct MediaController object");
        controller = new MediaController(this, true); 

        controller.setMediaPlayer(aplayer);
        controller.setAnchorView(anchor);
        controller.setEnabled(true);
        controller.show(); 
//        aplayer.getDuration();
        
    }

    public boolean onInfo(MediaPlayer mp, int what, int extra)
    {
        Log.i(TAG, "CallPlayer onInfo with what " + what + " extra " + extra);
        return false;
    }

    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        Log.e(TAG, "CallPlayer onError with what " + what + " extra " + extra);
        Toast t = Toast.makeText(this, "CallPlayer received error (what:" + what + " extra:" + extra + ") from MediaPlayer attempting to play ", Toast.LENGTH_LONG);
        t.show();
        finish();
        return true;
    }

    public void onCompletion(MediaPlayer mp)
    {
        Log.i(TAG, "CallPlayer onCompletion, finishing activity");
        finish();
    }
}
