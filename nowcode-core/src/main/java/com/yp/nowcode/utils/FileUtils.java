package com.yp.nowcode.utils;

import cn.hutool.core.io.FileUtil;
import com.yp.nowcode.model.enums.FileUploadBizEnum;
import com.yp.nowcodecommon.common.ErrorCode;
import com.yp.nowcodecommon.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * @author yp
 * @date: 2024/1/13
 */
public class FileUtils {

    /**
     * 校验图片文件
     *
     * @param multipartFile     文件
     * @param fileUploadBizEnum 业务类型
     */
    public static void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        } else if (FileUploadBizEnum.EXCEL_FILE.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("xlsx").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }

}
