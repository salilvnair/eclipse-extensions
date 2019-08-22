package difftool.util;

import java.io.IOException;
import difftool.util.constant.ChickenConstant;

public class DiffToolUtil {

	//public static String diffTool = "C:\\chicken-home\\difftool\\winmerge\\wm.exe";
	public static String diffTool = "C:\\chicken-home\\difftool\\diffmerge\\dm.exe";
	//public static String diffTool = "C:\\chicken-home\\difftool\\meld\\mld.exe";
	
	public static void launch(String compareSource , String comapareTarget) throws IOException {
		new ProcessBuilder(diffTool,ChickenConstant.CHICKEN_REMOTE_PATH+compareSource,comapareTarget).start();
	}
	
	
	
	
}
