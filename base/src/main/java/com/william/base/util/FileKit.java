/**
 * Copyright (c) 2011-2016, James Zhan 詹波 (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.william.base.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * FileKit.
 */
public class FileKit {
    /**
     * 删除文件
     *
     * @param file
     */
    public static void delete(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    delete(files[i]);
                }
            }
            file.delete();
        }
    }

    /**
     * Base64格式保存
     *
     * @param imgStr
     * @param fileName
     * @param saveDir
     * @return
     */
    public static String saveByBase64Str(String imgStr, String fileName, String saveDir) throws Exception {
        // 对字节数组字符串进行Base64解码
        if (StringUtils.isEmpty(imgStr)) // 图像数据为空
            return null;
        // Base64解码
        byte[] bytes = Base64.decodeBase64(imgStr);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        saveByInputStream(is, fileName, saveDir);
        return fileName;
    }

    /**
     * 将一个输入流保存为文件
     *
     * @param is
     * @param fileName
     * @param saveDir
     * @return
     */
    public static String saveByInputStream(InputStream is, String fileName, String saveDir) throws Exception {
        // 对字节数组字符串进行Base64解码
        if (is == null) // 输入流为空
            return null;
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(saveDir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = is.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }
        fos.flush();
        fos.close();
        is.close();
        return fileName;
    }
}
