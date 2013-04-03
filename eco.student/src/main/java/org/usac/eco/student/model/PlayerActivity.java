/**
 * 
 */
package org.usac.eco.student.model;

import org.usac.eco.libdto.DTOCourse;

import org.usac.eco.student.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * @author Brian Estrada <brianseg014@gmail.com>
 *
 */
public class PlayerActivity extends EcoActivity
		implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener, OnErrorListener {
	
	private VideoView videoView;
	
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		
		Bundle extras = getIntent().getExtras();
		DTOCourse dtoCourse = new DTOCourse();
		dtoCourse.fromXML(extras.getString("course"));
		
		videoView = (VideoView) findViewById(R.id.VideoView);
        videoView.setVideoURI(Uri.parse(dtoCourse.getURI()));
        videoView.setOnBufferingUpdateListener(this);
        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        
        MediaController mediaController = new MediaController(PlayerActivity.this);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
        
        progressDialog = ProgressDialog.show(
        		PlayerActivity.this, 
        		"", 
        		getString(R.string.loading));
	}

	public void onPrepared(MediaPlayer mediaPlayer) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
	}

	public void onCompletion(MediaPlayer mediaPlayer) {
		// TODO Auto-generated method stub
		destroyActivity(mediaPlayer);
	}
	
	public void onBufferingUpdate(MediaPlayer mediaPlayer, int arg1) {
		// TODO Auto-generated method stub
		if(progressDialog == null)
			progressDialog.dismiss();
		if(arg1 < 100){
			progressDialog = ProgressDialog.show(
					PlayerActivity.this, 
					"", 
					getString(R.string.buffering) + String.valueOf(arg1) + "%");
		}
	}

	public boolean onError(MediaPlayer mediaPlayer, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("ERROR PLAYING");
		showError(mediaPlayer);
		return false;
	}
	
	private void showError(final MediaPlayer mediaPlayer){
		progressDialog.dismiss();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error));
        builder.setMessage(getString(R.string.errorStreaming));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				destroyActivity(mediaPlayer);
			}
		});
        AlertDialog alert = builder.create();
        alert.show();
	}
	
	private void destroyActivity(MediaPlayer mediaPlayer){
		mediaPlayer.release();
		PlayerActivity.this.finish();
	}

}
