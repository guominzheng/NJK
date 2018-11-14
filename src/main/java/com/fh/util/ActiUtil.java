// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:50:53
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Source File Name:   ActiUtil.java
package com.fh.util;

import com.fh.util.PageData;
import java.util.ArrayList;
import java.util.List;

public class ActiUtil {
    private static String[] pro = new String[]{"e62e953cbff942e38272cdc1edda945c", "e62e953cbff942e38272cdc1edda945c", "b14988fd69484145ad29135b3845225b", "b14988fd69484145ad29135b3845225b", "fb2181ab01ed4e058e1a6d07bfb257e6", "c47bdf401150425eaf18b19edf233d75", "c47bdf401150425eaf18b19edf233d75", "dd277f3c7bf44587b0677da3784916c0", "dd277f3c7bf44587b0677da3784916c0", "b5ead8e35caf4b9a9e39451ed00cb565", "f97562e6f90f4d89a3f7868c2e500c74", "f97562e6f90f4d89a3f7868c2e500c74", "723b1e45ed3e475eb5289420105bfa60", "147ec443ada74c4cb831f57b760ae64f", "699b32540f49454cbc916ae842366e92", "699b32540f49454cbc916ae842366e92", "fb2181ab01ed4e058e1a6d07bfb257e6"};
    private static String[] mark = new String[]{"e36ec1e66ff94cc6ac3aeb9f16c7a5c7", "778430f983e34c7eb1312218a477c9d1", "48040a2084474590976f0a3988c85e27", "41bbc3e14eae4663b0589c41d8faa654", "b19b24e9e1534aa1b8bc3c564b27a12d", "cc1cde15ae1f4839b96d81bdcc6d0277", "6727bee177be4e7caa068b08281d0c0a", "f88d565cd5c6495dad6158915c5bc459", "1600a349325b46778ee2cd047966ab52", "f5974b6b10354e838c14e3aee7081cae", "6beb2b0bb90b45c9b5df37b4994c1c36", "447d0e3200224af4a269af736aef023e", "8deffa3a23cc41358d346b7ec1981569", "d1bc14b36be147138b1684c1b09dd6f9", "cbc1f0cf9ce3414e8eece2dfdc0b0513", "b61599fbdbe6433ea8f91e40cc431fd1", "06b6e3fcb6784f0fb8a31df39c2d145a"};
    private static String[] mon = new String[]{"1920", "2580", "2200", "3060", "7200", "4800", "11220", "3500", "6500", "7200", "4400", "6250", "34000", "4000", "13000", "12000", "11000"};
    private static List<PageData> pageDataList = new ArrayList<PageData>();

    public static List<PageData> getlist(String USER_ID) {
        PageData ppd = null;
        for (int i = 0; i < 16; ++i) {
            ppd = new PageData();
            ppd.put("PRODUCT_ID", ActiUtil.pro[i]);
            ppd.put("REMARK_ID", ActiUtil.mark[i]);
            ppd.put("money", ActiUtil.mon[i]);
            ppd.put("USER_ID", USER_ID);
            ActiUtil.pageDataList.add(ppd);
        }
        return ActiUtil.pageDataList;
    }
}
