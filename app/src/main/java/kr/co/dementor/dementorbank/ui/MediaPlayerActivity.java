package kr.co.dementor.dementorbank.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import kr.co.dementor.dementorbank.R;
import kr.co.dementor.dementorbank.common.LogTrace;

/**
 * Created by dementor1 on 15. 12. 3..
 */
public class MediaPlayerActivity extends FragmentActivity {

    private VideoView mVideoView = null;
    private MediaController mMediaController = null;
    private boolean mIsNeedPlay = false;
    private int mSavedPlayTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mediaplayer_activity);

        mVideoView = (VideoView)findViewById(R.id.vvMediaView);

        //Uri uri = Uri.parse("http://www.dementor.co.kr/upload/TutorialVideo.mp4");

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.sample50);

        mVideoView.setVideoURI(uri);

        mVideoView.requestFocus();

        Toast.makeText(getApplicationContext(), "Back키를 눌러 Skip할 수 있습니다.", Toast.LENGTH_SHORT).show();

        mVideoView.setOnPreparedListener(mOnPreparedListener);

        mVideoView.setOnCompletionListener(mOnCompletionListener);

        mVideoView.setOnErrorListener(mOnErrorListener);

        mMediaController = new MediaController(this);

        mMediaController.setAnchorView(mVideoView);

        mVideoView.setMediaController(mMediaController);

        //mVideoView.setOnInfoListener(mOnInfoListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogTrace.d("stopPlayback");
        mVideoView.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mIsNeedPlay) {

            mVideoView.resume();

            LogTrace.d("seekTo : " + mSavedPlayTime);
            mVideoView.seekTo(mSavedPlayTime);

            mIsNeedPlay = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSavedPlayTime = mVideoView.getCurrentPosition();

        LogTrace.d("pause : " + mSavedPlayTime);

        mVideoView.pause();

        mIsNeedPlay = true;
    }


    MediaPlayer.OnPreparedListener mOnPreparedListener =  new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {

            LogTrace.d("start");
            mVideoView.start();

        }
    };



    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            LogTrace.d("finish");
            finish();
        }
    };


    MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            String errMsg = null;
            switch (what)
            {
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    errMsg = "MEDIA_ERROR_UNKNOWN";
                    break;
                case MediaPlayer.MEDIA_ERROR_IO:
                    errMsg = "MEDIA_ERROR_UNKNOWN";
                    break;
                case MediaPlayer.MEDIA_ERROR_MALFORMED:
                    errMsg = "MEDIA_ERROR_UNKNOWN";
                    break;
                case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                    errMsg = "MEDIA_ERROR_UNKNOWN";
                    break;
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    errMsg = "MEDIA_ERROR_UNKNOWN";
                    break;
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    errMsg = "MEDIA_ERROR_UNKNOWN";
                    break;
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    errMsg = "MEDIA_ERROR_UNKNOWN";
                    break;
                default:
                    errMsg = "MEDIA_ERROR_UNKNOWN ... what: " + what;
                    break;
            }

            Toast.makeText(getApplicationContext(), errMsg + " , extra : " + extra, Toast.LENGTH_SHORT);
            return false;
        }
    };

    MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            String infoMsg = null;
            switch (what)
            {
                case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    infoMsg = "MEDIA_INFO_BAD_INTERLEAVING";
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    infoMsg = "MEDIA_INFO_BUFFERING_END";
                    Toast.makeText(getApplicationContext(), infoMsg + " , extra : " + extra, Toast.LENGTH_SHORT);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    infoMsg = "MEDIA_INFO_BUFFERING_START";
                    Toast.makeText(getApplicationContext(), infoMsg + " , extra : " + extra, Toast.LENGTH_SHORT);
                    break;
                case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    infoMsg = "MEDIA_INFO_METADATA_UPDATE";
                    break;
                case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    infoMsg = "MEDIA_INFO_NOT_SEEKABLE";
                    break;
                case MediaPlayer.MEDIA_INFO_UNKNOWN:
                    infoMsg = "MEDIA_INFO_UNKNOWN";
                    Toast.makeText(getApplicationContext(), infoMsg + " , extra : " + extra, Toast.LENGTH_SHORT);
                    break;
                case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    infoMsg = "MEDIA_INFO_VIDEO_TRACK_LAGGING";
                    break;
                default:
                    infoMsg = "MEDIA_INFO_UNKNOWN ... what: " + what;
                    Toast.makeText(getApplicationContext(), infoMsg + " , extra : " + extra, Toast.LENGTH_SHORT);
                    break;
            }


            return false;
        }

    };
}
