package difftool.util.builder.type;

public enum GitStatus {

	SHORT("-s"),
	PORCELIEN("--porcelain");
	
	
	
	private String optionValue;
	
	GitStatus(String optionValue) {
		this.optionValue = optionValue;
	}
	
	public String value() {
        return this.optionValue;
    }
}
