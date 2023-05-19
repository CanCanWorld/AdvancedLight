package com.zrq.advancedlight.activity.advanced;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.zrq.advancedlight.R;

public class CardViewActivity extends AppCompatActivity {

    private static final String TAG = "CardViewActivity";
    private CardView mCardView;
    private ImageView mIvCard;
    private SeekBar mSbRadius;
    private SeekBar mSbElevation;
    private SeekBar mSbPadding;
    private int width;
    private int height;
    private ViewGroup.LayoutParams layoutParams;
    private ViewGroup.LayoutParams mIvCardLayoutParams;
    private int cardWidth;
    private int cardHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        initView();
        initData();
    }

    private void initView() {
        mCardView = findViewById(R.id.card_view);
        mIvCard = findViewById(R.id.iv_card);
        mSbRadius = findViewById(R.id.seek_bar_radius);
        mSbElevation = findViewById(R.id.seek_bar_elevation);
        mSbPadding = findViewById(R.id.seek_bar_size);

        layoutParams = mCardView.getLayoutParams();
        mIvCardLayoutParams = mIvCard.getLayoutParams();
        width = layoutParams.width;
        height = layoutParams.height;
        cardWidth = mIvCardLayoutParams.width;
        cardHeight = mIvCardLayoutParams.height;
    }

    private void initData() {
        mSbRadius.setOnSeekBarChangeListener(new OnProgressChanged() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCardView.setRadius(progress);
            }
        });
        mSbElevation.setOnSeekBarChangeListener(new OnProgressChanged() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCardView.setElevation(progress);
            }
        });
        mSbPadding.setOnSeekBarChangeListener(new OnProgressChanged() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = 1-progress/100f;
                layoutParams.width = (int) (width *scale);
                layoutParams.height = (int) (height *scale);
                mIvCardLayoutParams.width = (int) (cardWidth *scale);
                mIvCardLayoutParams.height = (int) (cardHeight *scale);
                Log.d(TAG, "layoutParams.width: "+layoutParams.width);
                Log.d(TAG, "layoutParams.height: "+layoutParams.height);

                mCardView.setLayoutParams(layoutParams);
                mIvCard.setLayoutParams(mIvCardLayoutParams);
            }
        });
    }

    private abstract static class OnProgressChanged implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}