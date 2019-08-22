package difftool.util.builder;

import difftool.util.PlatformUtil;
import difftool.util.PlatformUtil.OSType;
import difftool.util.builder.type.BashCommand;

public class BashCommandBuilder {

	StringBuilder bashCommandBuilder = new StringBuilder();
	public static final String SPACE = " ";
	public static final String AMPERSAND = "&&";
	public OSType osType;
	
	public BashCommandBuilder() {
		this.osType = PlatformUtil.detectOS();
	}
	
	public BashCommandBuilder cd(String dirPath) {
		dirPath = resolveWindowPath(dirPath);
		bashCommandBuilder.append(BashCommand.CD.value()).append(SPACE).append(dirPath);
		return this;
	}
	
	public BashCommandBuilder next() {
		bashCommandBuilder.append(SPACE).append(AMPERSAND).append(SPACE);
		return this;
	}
	
	public BashCommandBuilder nextCommand(String... command) {
		bashCommandBuilder.append(SPACE).append(AMPERSAND).append(SPACE);
		int counter = 0;
		if(command.length>0) {
			for(String commandItr:command) {
				if(counter>0){
					bashCommandBuilder.append(SPACE).append(AMPERSAND).append(SPACE);
				}
				command(commandItr);
				counter++;
			}
		}
		return this;
	}
	
	public BashCommandBuilder command(String command) {
		bashCommandBuilder.append(command);
		return this;
	}
	
	public BashCommandBuilder redirect(String fileNameWithFullPath) {
		fileNameWithFullPath = resolveWindowPath(fileNameWithFullPath);
		bashCommandBuilder.append(SPACE).append(BashCommand.REDIRECT.value()).append(SPACE).append(fileNameWithFullPath);
		return this;
	}
	
	public String resolveWindowPath(String dirPath) {
		String bashPath = convertWindowLocationInLinux(dirPath);
		bashPath = convertWindowPathSlashInLinux(bashPath);
		return bashPath;
	}
	
	public String convertWindowLocationInLinux(String windowsPath) {
		return windowsPath.replace("C:", "/c");
	}
	
	public String convertWindowPathSlashInLinux(String windowsPath) {
		return windowsPath.replace("\\", "/");
	}
	
	public String build() {
		return bashCommandBuilder.toString();
	}
	
	public void clear() {
		bashCommandBuilder.setLength(0);
	}
	
}
