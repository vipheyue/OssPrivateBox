package com.example.heyue.ossprivatebox.browser;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultipleItem implements MultiItemEntity {
    public static final int IMG = 1;
    public static final int MOVIE = 2;
    public static final int MUSIC = 3;
    public static final int OTHER = 4;
    public static final int DIR = 5;
    private String fileUrl = "";

    private int itemType;

    public MultipleItem(String fileUrl, int itemType) {
        this.fileUrl = fileUrl;
        this.itemType = itemType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
