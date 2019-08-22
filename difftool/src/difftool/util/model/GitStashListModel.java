package difftool.util.model;

import java.util.List;

public class GitStashListModel {
	private String stashId;
	private String message;
	private List<GitStashShow> gitStashFiles;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStashId() {
		return stashId;
	}
	public void setStashId(String stashId) {
		this.stashId = stashId;
	}
	public List<GitStashShow> getGitStashFiles() {
		return gitStashFiles;
	}
	public void setGitStashFiles(List<GitStashShow> gitStashFiles) {
		this.gitStashFiles = gitStashFiles;
	}
	
	
}
