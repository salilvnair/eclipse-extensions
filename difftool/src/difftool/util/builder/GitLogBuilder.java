package difftool.util.builder;

import difftool.util.model.GitLog;

public class GitLogBuilder {
	private GitLog gitLog;
	
	public GitLogBuilder() {
		this.gitLog = new GitLog();
	}
	
	public GitLogBuilder setHashId(String hash) {
		this.gitLog.setHashId(hash);
		return this;
	}
	
	public GitLogBuilder setAuthor(String author) {
		this.gitLog.setAuthor(author);
		return this;
	}
	
	
	public GitLogBuilder setBranchName(String branchName) {
		this.gitLog.setBranchName(branchName);
		return this;
	}
	
	
	public GitLogBuilder setMessage(String msg) {
		this.gitLog.setMessage(msg);
		return this;
	}
	
	
	public GitLog build() {
		return this.gitLog;
	}
	
}
