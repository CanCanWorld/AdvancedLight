package com.zrq.advancedlight.util;

import com.zrq.advancedlight.entity.Picture;

import java.util.List;

public class PictureConfig {
    private int MAX_PIC_SELECT = 9;

    private static PictureConfig instance;

    private OnSelectedPicFinish onSelectedPicFinish;

    public void setOnSelectedPicFinish(OnSelectedPicFinish onSelectedPicFinish) {
        this.onSelectedPicFinish = onSelectedPicFinish;
    }

    public OnSelectedPicFinish getOnSelectedPicFinish() {
        return onSelectedPicFinish;
    }

    private PictureConfig() {
    }

    public static PictureConfig getInstance() {
        if (instance == null) {
            instance = new PictureConfig();
        }
        return instance;
    }

    public int getMaxPicSelect() {
        return MAX_PIC_SELECT;
    }

    public void setMaxPicSelect(int maxPicSelect) {
        MAX_PIC_SELECT = maxPicSelect;
    }

    public interface OnSelectedPicFinish {
        void onSelectedPic(List<Picture> mSelectList);
    }
}
