package com.rafaeldecker.reverseplayer;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;
import android.app.Activity;

public class MainActivity extends Activity implements OnClickListener {

	private static final int PLAYING_BACKWARD = -1;
	private static final int PLAYING_FORWARD = 1;

	private VideoView mVideoView;
	private Button mForwardButton;
	private Button mBackwardButton;

	private int mCurrentState;
	private int mVideoDuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupButtons();

		setupVideo();

	}

	private void setupVideo() {
		mVideoView = (VideoView) findViewById(R.id.video_view);

		mVideoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mCurrentState = PLAYING_FORWARD;
				mVideoDuration = mVideoView.getDuration();
				mVideoView.start();
			}
		});

		Uri forwardUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.normal);
		mVideoView.setVideoURI(forwardUri);
	}

	private void setupButtons() {
		mForwardButton = (Button) findViewById(R.id.forward_button);
		mForwardButton.setOnClickListener(this);

		mBackwardButton = (Button) findViewById(R.id.backward_button);
		mBackwardButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
		case R.id.backward_button: {
			if (mCurrentState != PLAYING_BACKWARD) {
				playBackward();
			}
			break;
		}

		case R.id.forward_button: {
			if (mCurrentState != PLAYING_FORWARD) {
				playForward();
			}
			break;
		}

		}

	}

	private void playForward() {
		mCurrentState = PLAYING_FORWARD;

		calculeSeekPosition();

		mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.normal));

	}

	private void playBackward() {
		mCurrentState = PLAYING_BACKWARD;

		calculeSeekPosition();

		mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.reverse));

	}

	private void calculeSeekPosition() {
		mVideoView.pause();

		final int seekTo = mVideoDuration - mVideoView.getCurrentPosition();

		mVideoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideoView.seekTo(seekTo);
				mVideoView.start();
			}
		});

	}

}
