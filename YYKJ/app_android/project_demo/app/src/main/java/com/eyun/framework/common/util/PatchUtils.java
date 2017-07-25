package com.eyun.framework.common.util;


import com.eyun.framework.common.entity.PatchResult;

/**
 * PatchUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-10
 */
public class PatchUtils {

    /**
     * patch old apk and patch file to new apk
     * 
     * @param oldApkPath
     * @param patchPath
     * @param newApkPath
     * @return
     */
    public static native PatchResult patch(String oldApkPath, String patchPath, String newApkPath);
}