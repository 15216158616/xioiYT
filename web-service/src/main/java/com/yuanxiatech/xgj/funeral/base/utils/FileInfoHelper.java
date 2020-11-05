package com.yuanxiatech.xgj.funeral.base.utils;

import com.yuanxiatech.xgj.common.attach.model.FileInfo;
import com.yuanxiatech.xgj.common.attach.model.ImageFileInfo;
import com.yuanxiatech.xgj.core.json.ClassType;
import com.yuanxiatech.xgj.core.json.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class FileInfoHelper {

    /**
     * 从字符串获取文件信息
     *
     * @param fileInfoAryStr
     * @return
     */
    public static List<FileInfo> fromJsonArrayStr(String fileInfoAryStr) {
        List<FileInfo> list = new ArrayList<>();
        if (!fileInfoAryStr.isEmpty()) {
            list = JsonUtils.toObject(fileInfoAryStr, new ClassType<List<FileInfo>>() {
            });
        }
        if (list != null) {
            for (FileInfo fileInfo : list) {
                fileInfo.setOriginFile(true);
            }
        }
        return list;
    }

    public static List<ImageFileInfo> toImageInfoList(List<FileInfo> fileInfoList) {
        List<ImageFileInfo> imageFileInfoList = new ArrayList<>();
        if (fileInfoList != null) {
            ImageFileInfo imageFileInfo;
            for (FileInfo fileInfo : fileInfoList) {
                imageFileInfo = new ImageFileInfo(fileInfo.getFilename());
                fileInfo.copyTo(imageFileInfo);
                imageFileInfo.setOriginFile(true);
                imageFileInfoList.add(imageFileInfo);
            }
        }
        return imageFileInfoList;
    }
}
