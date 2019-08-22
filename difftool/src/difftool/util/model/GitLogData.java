package difftool.util.model;

import java.util.List;

public class GitLogData {

	private String fileName;
	private String filePath;
	private List<GitLog> gitLog;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<GitLog> getGitLog() {
		return gitLog;
	}
	public void setGitLog(List<GitLog> gitLog) {
		this.gitLog = gitLog;
	}
	
	
}
