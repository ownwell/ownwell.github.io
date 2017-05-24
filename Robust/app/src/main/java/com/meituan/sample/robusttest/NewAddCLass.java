package com.meituan.sample.robusttest;

import com.meituan.robust.patch.annotaion.Add;

/**
 * Created by mivanzhang on 16/12/27.
 */
public class NewAddCLass {
    public static String get() {
        return ImageQualityUtil.getLargeUrl("asdasd");
    }
}
