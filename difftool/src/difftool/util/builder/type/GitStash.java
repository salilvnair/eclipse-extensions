package difftool.util.builder.type;

public enum GitStash {
	APPLY("apply"),
	DROP("drop"),
	PUSH("push"),
	SAVE("save"),
	POP("pop"),
	REMOVE("remove"),
	MESSAGE("-m"),
	INCLUDE_UNTRACKED("-u"),
	INCLUDE_ALL("-a"),
	SHOW("show"),
	NAME_ONLY("--name-only"),
	NAME_STATUS("--name-status"),
	LIST("list");
	
	
	
	private String optionValue;
	
	GitStash(String optionValue) {
		this.optionValue = optionValue;
	}
	
	public String value() {
        return this.optionValue;
    }
}
