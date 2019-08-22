package difftool.util.builder.type;

public enum BashCommand {

	CD("cd"),
	LS("ls"),
	REDIRECT(">");
	
	private String commandValue;
	
	BashCommand(String commandValue) {
		this.commandValue = commandValue;
	}
	
	public String value() {
        return this.commandValue;
    }
}
