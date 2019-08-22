package difftool.util.builder.type;

public enum GitRevParse {

	SHOW_TOPLEVEL("--show-toplevel"),
	IS_INSIDE_WORK_TREE("--is-inside-work-tree"),
	VERIFY("--verify"),
	GIT_DIR("--git-dir"),
	IS_BARE_REPO("--is-bare-repository"),
	BRANCH_SHA1S("--branches"),
	IS_SHALLOW_REPO("--is-shallow-repository"),
	IS_INSIDE_GIT_DIR("--is-inside-git-dir");
	
	
	
	private String optionValue;
	
	GitRevParse(String optionValue) {
		this.optionValue = optionValue;
	}
	
	public String value() {
        return this.optionValue;
    }
}
