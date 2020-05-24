package file;

import java.io.File;

public final class FileUtils {
	    /**
	     * 功能：获取操作用户当前目录。
	     *
	     * @return 操作系统用户目录
	     */
	    public static File getUserDir() {
	        String userDir = System.getProperty("user.dir");
	        return new File(userDir);
	    }

	    /**
	     * 功能：获取操作系统用户目录。
	     *
	     * @return 操作系统用户目录
	     */
	    public static File getUserHomeDir() {
	        String userHomeDir = System.getProperty("user.home");
	        return new File(userHomeDir);
	    }

	    /**
	     * 功能：获取操作系统临时目录。
	     *
	     * @return 操作系统临时目录
	     */
	    public static File getTmpDir() {
	        String userHomeDir = System.getProperty("tmp.dir");
	        return new File(userHomeDir);
	    }

	    /**
	     * 功能：获取一个文件的根目录
	     *
	     * @return 当前根目录
	     */
	    public static File getRoot() {
	        return getRoot(new File("/").getAbsoluteFile());
	    }

	    /**
	     * 功能：获取一个文件的根目录
	     * 举例：d:\temp\2020\04\abc.jpg → d:
	     *
	     * @param file 文件名
	     * @return 该文件的根目录
	     */
	    public static File getRoot(File file) {
	        //判断当前的文件对象是否为空, 为空返回传入的文件对象, 如果不为空则获取文件的根目录
	        return file.getParentFile() == null ? file : getRoot(file.getParentFile());
	    }

	    /**
	     * 功能：获取文件扩展名
	     * 示例：a.jpg → .jpg
	     *
	     * @param fileName 文件名
	     * @return 带点的扩展名
	     */
	    public static String getFileExtensions(String fileName) {
	        //获取最会一个 '.' 的索引值
	        int index = fileName.lastIndexOf(".");
	        //如果此文件名没有需要的索引值则返回null
	        if (index == -1) {
	            return null;
	        }
	        //有符合的索引值则返回带'.'的文件后缀名
	        return fileName.substring(index);
	    }

	    /**
	     * 定义枚举类, 存储容量单位转换信息
	     */
	    public enum FileSizeUnit {
	        //1byte
	        B(1),
	        //K = 1024 * B
	        K(1024),
	        //1M = 1024 * 1024B
	        M(1024 * 1024),
	        //1G = 1024 * 1024 * 1024B
	        G(1024 * 1024 * 1024),
	        //1T = 1024
	        T(1024 * 1024 * 1024 * 1024);

	        private long size;

	        private FileSizeUnit(long size) {
	            this.size = size;
	        }

	        public long getSize() {
	            return this.size;
	        }
	    }

	    /**
	     * 功能：获取对应单位的文件大小
	     * 示例：a.jpg文件原始大小是1275个字节，如果参数单位是k，则返回2K，如果m，则返回0M
	     *
	     * @param file 文件
	     * @param unit 单位，如K、M、G、T
	     * @return 对应单位的文件大小
	     */
	    public static long getFileSize(File file, FileSizeUnit unit) {
	        //获取当前文件的大小,单位为byte
	        long fileSize = file.length();
	        //根据传入的单位进行计算
	        long mod = fileSize % unit.getSize();
	        fileSize = fileSize / unit.getSize();
	        if (mod > 0) {
	            fileSize += 1;
	        }
	        return fileSize;
	    }

	    /**
	     * 功能:传入文件大小的数值,单位byte, 获取相应单位的文件大小值
	     * @param fileSize
	     * @param unit
	     * @return
	     */
	    public static long getFileSize(long fileSize, FileSizeUnit unit) {
	        //根据选定的转换模式进行计算
	        long mod = fileSize % unit.getSize();
	        fileSize = fileSize / unit.getSize();
	        if (mod > 0) {
	            fileSize += 1;
	        }
	        return fileSize;
	    }

	    /**
	     * 功能：获取某文件所在磁盘的总空间
	     * 示例：如果传了d:\temp\a.jpg文件，能计算出d盘的总空间
	     *
	     * @param file 文件
	     * @param unit 单位，如K、M、G、T
	     * @return 对应单位的文件大小
	     */
	    public static long getTotalSpace(File file, FileSizeUnit unit) {
	        //获取当前文件在所在磁盘的大小
	        long totalSpace = getRoot(file).getTotalSpace();
	        //根据传入单位进行单位转换
	        long fileSize = getFileSize(totalSpace, unit);
	        return fileSize;
	    }

	    /**
	     * 功能：获取某文件所在磁盘的可用空间
	     * 示例：如果传了d:\temp\a.jpg文件，能计算出d盘的可用空间
	     *
	     * @param unit 单位，如K、M、G、T
	     * @return 磁盘的可用空间大小getFreeSpace()
	     */
	    public static long getFreeSpace(File file, FileSizeUnit unit) {
	        //获取当前文件所在磁盘的剩余空间大小
	        long freeSpace = getRoot(file).getFreeSpace();
	        //根据传入单位进行单位转换
	        long fileSize = getFileSize(freeSpace, unit);
	        return fileSize;
	    }

	    /**
	     * 功能：递归删除文件，如果是目录，则目录下所有的文件和子目录都删除。
	     *
	     * @param file 文件或文件夹
	     * @return 删除是否成功
	     */
	    public static boolean deletes(File file) {
	        return deleteFile(file);
	    }

	    private static boolean deleteFile(File file) {
	        File[] files = file.listFiles();
	        for (File deleteFile : files) {
	            if (deleteFile.isDirectory()) {
	                //如果是文件夹，则递归删除下面的文件后再删除该文件夹
	                if (!deleteFile(deleteFile)) {
	                    //如果失败则返回
	                    return false;
	                }
	            } else {
	                if (!deleteFile.delete()) {
	                    //如果失败则返回
	                    return false;
	                }
	            }
	        }
	        return file.delete();
	    }
	}
