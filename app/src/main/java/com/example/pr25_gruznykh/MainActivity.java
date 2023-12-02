package com.example.pr25_gruznykh;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCrocodileSound, mBearSound, mLionSound, mSnakeSound, mMonkeySound, mDinosaurSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            createOldSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mCrocodileSound = loadSound("crocodile.ogg");
        mBearSound = loadSound("bear.ogg");
        mLionSound = loadSound("lion.ogg");
        mSnakeSound = loadSound("snake.ogg");
        mMonkeySound = loadSound("monkey.ogg");
        mDinosaurSound = loadSound("dinosaur.ogg");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton crocodileImageButton = findViewById(R.id.image_crocodile);
        crocodileImageButton.setOnClickListener(onClickListener);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton bearImageButton = findViewById(R.id.image_bear);
        bearImageButton.setOnClickListener(onClickListener);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton lionImageButton = findViewById(R.id.image_lion);
        lionImageButton.setOnClickListener(onClickListener);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton snakeImageButton = findViewById(R.id.image_snake);
        snakeImageButton.setOnClickListener(onClickListener);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton monkeyImageButton = findViewById(R.id.image_monkey);
        monkeyImageButton.setOnClickListener(onClickListener);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton dinosaurImageButton = findViewById(R.id.image_dinosaur);
        dinosaurImageButton.setOnClickListener(onClickListener);


//        lionImageButton.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                int eventAction = event.getAction();
//                if (eventAction == MotionEvent.ACTION_UP) {
//                    // Отпускаем палец
//                    if (mStreamID > 0)
//                        mSoundPool.stop(mStreamID);
//                }
//                if (eventAction == MotionEvent.ACTION_DOWN) {
//                    // Нажимаем на кнопку
//                    mStreamID = playSound(mLionSound);
//                }
//                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
//                    mSoundPool.stop(mStreamID);
//                }
//                return true;
//            }
//        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.image_lion) {
                playSound(mLionSound);
            } else if (v.getId() == R.id.image_bear) {
                playSound(mBearSound);
            } else if (v.getId() == R.id.image_crocodile) {
                playSound(mCrocodileSound);
            } else if (v.getId() == R.id.image_monkey) {
                playSound(mMonkeySound);
            } else if (v.getId() == R.id.image_dinosaur) {
                playSound(mDinosaurSound);
            } else if (v.getId() == R.id.image_snake) {
                playSound(mSnakeSound);
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mCrocodileSound = loadSound("crocodile.ogg");
        mBearSound = loadSound("bear.ogg");
        mLionSound = loadSound("lion.ogg");
        mSnakeSound = loadSound("snake.ogg");
        mMonkeySound = loadSound("monkey.ogg");
        mDinosaurSound = loadSound("dinosaur.ogg");

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}
