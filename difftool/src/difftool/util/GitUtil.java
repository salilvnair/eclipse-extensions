package difftool.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import difftool.util.builder.BashCommandBuilder;
import difftool.util.builder.GitCommandBuilder;
import difftool.util.builder.GitLogBuilder;
import difftool.util.builder.type.GitRevParse;
import difftool.util.builder.type.GitStash;
import difftool.util.builder.type.GitStatus;
import difftool.util.constant.ChickenConstant;
import difftool.util.model.GitLog;

public class GitUtil {

	static BashCommandBuilder bashCommandBuilder = new BashCommandBuilder();
	static GitCommandBuilder gitCommandBuilder = new GitCommandBuilder();
	public static final String TRUE ="true";
	public static final String CARROT ="\\^";
	public static final String FIND_LAST_REGEX ="$";
	public static final String EMPTY ="";
	public static final String COLON =":";
	
	public static void downloadRevisionFromRepo( String gitRootPath,String relativeFilePath,String fileName, String sha1HashId) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String gitShowHeadCommand = gitCommandBuilder.gitShow(sha1HashId,relativeFilePath).build();
		String bashCommand = bashCommandBuilder.cd(gitRootPath).nextCommand(gitShowHeadCommand).redirect(ChickenConstant.CHICKEN_REMOTE_PATH+fileName).build();
		BashUtil.execute(bashCommand, false);
	}
	
	public static void downloadHeadFromRepo( String gitRootPath,String relativeFilePath,String fileName) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String gitShowHeadCommand = gitCommandBuilder.gitShowHead(relativeFilePath).build();
		String bashCommand = bashCommandBuilder.cd(gitRootPath).nextCommand(gitShowHeadCommand).redirect(ChickenConstant.CHICKEN_REMOTE_PATH+fileName).build();
		BashUtil.execute(bashCommand, false);
	}
	
	public static boolean isGitDir(String projectPath) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String bashCommand = bashCommandBuilder.cd(projectPath).nextCommand(gitCommandBuilder.gitRevParse(GitRevParse.IS_INSIDE_WORK_TREE).build()).build();
		String result = BashUtil.execute(bashCommand, false);
		if(TRUE.equals(result)) {
			return true;
		}
		return false;
	}
	
	public static boolean isGitRootDir(String projectPath) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String bashCommand = bashCommandBuilder.cd(projectPath).nextCommand(gitCommandBuilder.gitRevParse(GitRevParse.IS_INSIDE_GIT_DIR).build()).build();
		String result = BashUtil.execute(bashCommand, false);
		if(TRUE.equals(result)) {
			return true;
		}
		return false;
	}
	
	public static String getRootGitDir(String projectPath) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String bashCommand = bashCommandBuilder.cd(projectPath).nextCommand(gitCommandBuilder.gitRevParse(GitRevParse.SHOW_TOPLEVEL).build()).build();
		String rootDir = BashUtil.execute(bashCommand, false);
		return rootDir;
	}
	
	public static String gitStatus(String gitRootPath) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String bashCommand = bashCommandBuilder.cd(gitRootPath).nextCommand(gitCommandBuilder.gitStatus(GitStatus.SHORT).build()).build();
		String result = BashUtil.execute(bashCommand, true);
		return result;
	}
	
	public static List<GitLog> getGitLogs(String gitRootPath, String fileRelativePath) throws IOException, InterruptedException, IllegalCommandException{
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		List<GitLog> gitLogs = new ArrayList<>();
		String bashCommand = bashCommandBuilder.cd(gitRootPath).nextCommand(gitCommandBuilder.gitLogPretty("\"%H\":\"%B\":\"%aN\"^", fileRelativePath).build()).build();
		String multilogCarrotString = BashUtil.execute(bashCommand, false);   
		multilogCarrotString = multilogCarrotString.replaceAll(CARROT+FIND_LAST_REGEX,EMPTY);
		String[] multiLineGitLogs = multilogCarrotString.split(CARROT);
		for(String gitMixedLogData:multiLineGitLogs) {
			String[] gitMixedLogs = gitMixedLogData.split(COLON);
			GitLogBuilder gitLogBuilder = new GitLogBuilder();			
			gitLogs.add(gitLogBuilder.setHashId(gitMixedLogs[0]).setMessage(gitMixedLogs[1]).setAuthor(gitMixedLogs[2]).build());
		}
		return gitLogs;
	}
	
	public static String gitStash(String gitRootPath,GitStash additionalOption,String message,String relativeFilePath) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String bashCommand = bashCommandBuilder.cd(gitRootPath).nextCommand(gitCommandBuilder.gitStash(GitStash.PUSH,additionalOption, message, relativeFilePath).build()).build();
		String result = BashUtil.execute(bashCommand, false);
		return result;
	}
	
	public static String gitStashList(String gitRootPath) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String bashCommand = bashCommandBuilder.cd(gitRootPath).nextCommand(gitCommandBuilder.gitStash(GitStash.LIST,null, null, null).build()).build();
		String result = BashUtil.execute(bashCommand, true);
		return result;
	}
	
	public static String gitStash(GitStash option,GitStash addtionalOption,String stashHashId,String gitRootPath) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String bashCommand = bashCommandBuilder.cd(gitRootPath).nextCommand(gitCommandBuilder.gitStash(option,addtionalOption,stashHashId).build()).build();
		String result = BashUtil.execute(bashCommand, true);
		return result;
	}
	
	public static String gitStashShow(String gitRootPath,String stashHashId) throws IOException, InterruptedException, IllegalCommandException {
		bashCommandBuilder.clear();
		gitCommandBuilder.clear();
		String gitShowFilesCommand = gitCommandBuilder.gitStashShowFile(GitStash.NAME_STATUS,stashHashId).build();
		gitCommandBuilder.clear();
		String gitShowUntrackedFilesCommand = gitCommandBuilder.gitStashShowUntrackedFile(stashHashId).build();
		String bashCommand = bashCommandBuilder.cd(gitRootPath).nextCommand(gitShowFilesCommand,gitShowUntrackedFilesCommand).build();
		String result = BashUtil.execute(bashCommand, true);
		return result;
	}
	
	
}
