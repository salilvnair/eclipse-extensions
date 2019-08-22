package difftool.util.model;

import java.util.List;

public class GitStashData {

	private String fileName;
	private String projectName;
	private List<GitStashListModel> gitStashes;
	private List<GitStatus> gitStatuses;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public List<GitStashListModel> getGitStashes() {
		return gitStashes;
	}
	public void setGitStashes(List<GitStashListModel> gitStashes) {
		this.gitStashes = gitStashes;
	}
	public List<GitStatus> getGitStatuses() {
		return gitStatuses;
	}
	public void setGitStatuses(List<GitStatus> gitStatuses) {
		this.gitStatuses = gitStatuses;
	}
}
