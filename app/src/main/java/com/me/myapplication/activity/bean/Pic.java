package com.me.myapplication.activity.bean;

import android.net.Uri;

import java.util.Objects;

public class Pic {
    Uri uri;

    public Pic(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pic pic = (Pic) o;
        return Objects.equals(uri, pic.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }
}
