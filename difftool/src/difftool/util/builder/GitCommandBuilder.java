package difftool.util.builder;

import difftool.util.builder.type.GitRevParse;
import difftool.util.builder.type.GitStash;
import difftool.util.builder.type.GitStatus;

public class GitCommandBuilder {
	StringBuilder gitCommandBuilder = new StringBuilder();
	public static final String SPACE = " ";
	public static final String QUOTE = "'";
	public static final String HEAD = "HEAD";
	public static final String COLON = ":";
	//cd '/c/MyHDD/D/Eclipse Workspace/Adopt/git_local_up/adopt'&& git log -n 1 --pretty=format:"{"hash":"%H","msg":"%B","author":"%aN"}" -- src/com/att/adopt/frontload/helper/util/StackTraceFrame.java
	//git rev-parse --is-inside-work-tree
	
	public GitCommandBuilder gitLogPretty(String format,String path) {
		gitCommandBuilder.append("git log --all --pretty=format:").append(format).append(SPACE).append("--").append(SPACE).append(path);
		return this;
	}
	
	public GitCommandBuilder gitRevParse(GitRevParse option) {
		gitCommandBuilder.append("git rev-parse").append(SPACE).append(option.value());
		return this;
	}
	
	public GitCommandBuilder gitStatus(GitStatus option) {
		gitCommandBuilder.append("git status").append(SPACE).append(option.value());
		return this;
	}
	
	public GitCommandBuilder gitShow(String hashId, String relativeFilePath) {
		gitCommandBuilder.append("git show").append(SPACE).append(hashId).append(COLON).append(relativeFilePath);
		return this;
	}
	
	public GitCommandBuilder gitShowHead(String relativeFilePath) {
		gitCommandBuilder.append("git show").append(SPACE).append(HEAD).append(COLON).append(relativeFilePath);
		return this;
	}
	
	public GitCommandBuilder gitStash(GitStash option,GitStash additionalOption,String stashId) {
		gitCommandBuilder.append("git stash").append(SPACE).append(option.value());
		if(additionalOption!=null) {
			gitCommandBuilder.append(SPACE).append(additionalOption.value());
		}
		gitCommandBuilder.append(SPACE).append(stashId);
		return this;
	}
	
	public GitCommandBuilder gitStashShowFile(GitStash additionalOption,String stashId) {
		gitCommandBuilder.append("git stash").append(SPACE).append(GitStash.SHOW.value()).append(SPACE).append(stashId).append(SPACE).append(additionalOption.value());
		return this;
	}
	
	public GitCommandBuilder gitStashShowUntrackedFile(String stashId) {
		gitCommandBuilder.append("git ls-tree -r").append(SPACE).append(stashId).append("^3 --name-status");
		return this;
	}
	
	public GitCommandBuilder gitStash(GitStash option,GitStash additionalOption,String message,String relativeFilePath) {
		gitCommandBuilder.append("git stash");
		if(relativeFilePath==null && message==null) {
			gitCommandBuilder.append(SPACE).append(option.value());
			if(additionalOption!=null) {
				gitCommandBuilder.append(SPACE).append(additionalOption.value());
			}
		}
		else if(relativeFilePath==null && message!=null) {
			gitCommandBuilder.append(SPACE).append(option.value());
			if(additionalOption!=null) {
				gitCommandBuilder.append(SPACE).append(additionalOption.value());
			}
			gitCommandBuilder.append(SPACE).append(GitStash.MESSAGE.value()).append(SPACE).append(QUOTE).append(message).append(QUOTE);
		}
		else if(relativeFilePath!=null && message==null) {
			gitCommandBuilder
			.append(SPACE).append(option.value());
			if(additionalOption!=null) {
				gitCommandBuilder.append(SPACE).append(additionalOption.value());
			}
			gitCommandBuilder.append(SPACE).append(relativeFilePath);
		}
		else if(relativeFilePath!=null && message!=null) {
			gitCommandBuilder
			.append(SPACE).append(option.value());
			if(additionalOption!=null) {
				gitCommandBuilder.append(SPACE).append(additionalOption.value());
			}
			gitCommandBuilder.append(SPACE).append(GitStash.MESSAGE.value())
			.append(SPACE).append(QUOTE).append(message).append(QUOTE).append(relativeFilePath);
		}
		return this;
	}
	
	public String build() {
		return gitCommandBuilder.toString();
	}
	
	public void clear() {
		gitCommandBuilder.setLength(0);
	}
}
