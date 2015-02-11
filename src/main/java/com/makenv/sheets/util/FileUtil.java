package com.makenv.sheets.util;

import java.io.*;

/**
 * Created by alei on 2015/1/27.
 */
public class FileUtil {

    private static String WEB_ROOT_PATH;

    public static String getNameExtension(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            return filename.substring(i + 1);
        }
        return "";
    }

    public static String getName(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            return filename.substring(0, i);
        }
        return filename;
    }

    public static void copy(File src, File dest) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        byte[] buff = new byte[1024];
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            int r;
            while ((r = fis.read(buff)) > -1) {
                fos.write(buff, 0, r);
            }
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception x) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception x) {
                }
            }
        }
    }

    /**
     * 创建一个目录
     *
     * @param dir
     * @throws RuntimeException ,创建目录失败会抛出此异常
     */
    public static void createDir(File dir) {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("Can't create the dir [" + dir + "]");
        }
    }

    public static void createDir(String dir) {
        createDir(new File(dir));
    }

    /**
     * 删除一个文件或者目录
     *
     * @param file
     */
    public static boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        } else if (file.isDirectory()) {
            File[] _files = file.listFiles();
            for (File _f : _files) {
                if (!delete(_f)) {
                    return false;
                }
            }
            return file.delete();
        }
        return false;
    }

    public static boolean delete(String file) {
        File _file = new File(file);
        if (_file.exists()) {
            delete(_file);
        }
        return false;
    }

    /**
     * 删除一个目录下的除exculde指定的后缀名外的所有子文件或子目录
     *
     * @param file
     */
    public static void cleanFolder(File file, String exculde) {
        if (!file.isDirectory())
            return;

        File[] _files = file.listFiles();
        for (File _f : _files) {
            if (_f.getName().endsWith(exculde))
                continue;
            delete(_f);
        }
    }

    /**
     * 删除一个目录下的除exculde指定的后缀名外的所有子文件或子目录
     *
     * @param file
     */
    public static void cleanFolder(File file) {
        if (!file.isDirectory())
            return;

        File[] _files = file.listFiles();
        for (File _f : _files) {
            delete(_f);
        }
    }

    public static void cleanFolder(String file) {
        File _file = new File(file);
        if (_file.exists()) {
            cleanFolder(_file);
        } else {
            _file.mkdir();
        }
    }

    public static String readFile(String file) throws IOException {
        return readFile(file, "utf-8");
    }

    public static String readFile(String file, String encode) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode == null ? "utf-8" : encode));
            StringBuffer _result = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                _result.append(line.trim());
            }
            return _result.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static boolean writeFile(String targetFile, String contents) {
        BufferedWriter _output = null;
        try {
            _output = new BufferedWriter(new FileWriter(targetFile));
            _output.write(contents);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (_output != null) {
                try {
                    _output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean isExist(String fileName) {
        File _file = new File(fileName);
        return _file.exists();
    }

    public static void initWebRootPath(String rootPath) {
        WEB_ROOT_PATH = rootPath;
    }

    public static String getWebRootPath() {
//        if (StringUtil.isEmpty(WEB_ROOT_PATH)) {
//            WEB_ROOT_PATH = new File(FileUtil.class.getResource("/").getPath()).getParentFile().getParent();
//        }
        return WEB_ROOT_PATH;
    }

    public static String getBookPath() {
        return getWebRootPath() + File.separator + "books";
    }

    public static String getBookTemplatePath() {
        return getBookPath() + File.separator + "template";
    }

    public static String getBookUserPath() {
        return getBookPath() + File.separator + "users";
    }

    public static void checkAndCreateDir(String userId) {
        String path = getBookUserPath() + File.separator + userId;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
