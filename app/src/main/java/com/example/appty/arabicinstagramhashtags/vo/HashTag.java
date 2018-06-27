package com.example.appty.arabicinstagramhashtags.vo;

/**
 * Created by appty on 20/06/18.
 */

public class HashTag {

    String stringTag;
    int mediaCount;

    public HashTag(String stringTag, int mediaCount) {
        this.stringTag = stringTag;
        this.mediaCount = mediaCount;
    }

    public String getStringTag() {
        return stringTag;
    }

    public int getMediaCount() {
        return mediaCount;
    }
}
