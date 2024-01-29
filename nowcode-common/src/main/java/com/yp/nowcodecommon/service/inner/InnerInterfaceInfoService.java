package com.yp.nowcodecommon.service.inner;


import com.yp.nowcodecommon.model.entity.InterfaceInfo;

public interface InnerInterfaceInfoService {
    /**
     * 获取接口信息
     *
     * @param path   路径
     * @param method 方法
     * @return {@link InterfaceInfo}
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
