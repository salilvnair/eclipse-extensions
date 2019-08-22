package difftool.util;

public class PlatformUtil {
	private static String OS = System.getProperty("os.name").toLowerCase();
	public enum OSType {
        WINDOWS, LINUX, MAC, OTHER
    };
    public static OSType detectOS() {
        if (isWindows()) {
        	return OSType.WINDOWS;
        } else if (isMac()) {
        	return OSType.MAC;
        } else if (isUnix()) {
        	return OSType.LINUX;
        } else {
        	return OSType.OTHER;
        }
    }

    private static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    private static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    private static boolean isUnix() {
        return (OS.indexOf("nux") >= 0);
    }
}
