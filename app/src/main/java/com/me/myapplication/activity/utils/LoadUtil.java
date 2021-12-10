package com.me.myapplication.activity.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.me.myapplication.activity.bean.Pic;

import java.util.ArrayList;
import java.util.List;



public class LoadUtil {


    public static List<Pic> getPictureUri(Context context) {
        List<Pic> res = new ArrayList<>();
        String[] projection = {MediaStore.Images.Media._ID};
        String sortOrder =  MediaStore.Video.Media.DATE_ADDED + " DESC";
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, sortOrder, null);


        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                Long id = cursor.getLong(idColumn);

                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                );

                res.add(new Pic(contentUri));

        }

        return res;
    }

}
