package difftool.util.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import difftool.util.model.GitLog;
import difftool.util.model.GitLogData;

public class GitCompareDialog extends TitleAreaDialog {


	Combo commitHashIdCombo;
	Combo commitMessageCombo;
	Combo authorCombo;
    

    private String firstName;
    private String lastName;
	private String commitHashId;
	private String commitMessage;
	private String author;
	private GitLogData gitLogData;
    private List<GitLog> gitLog;
    private boolean cancelPressed;

    public GitCompareDialog(Shell parentShell, GitLogData gitLogData) {
        super(parentShell);  
        this.gitLog = gitLogData.getGitLog();
        this.gitLogData = gitLogData;
    }

    @Override
    public void create() {
        super.create();
        setTitle(this.gitLogData.getFileName());
        setMessage(this.gitLogData.getFilePath(), IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        
        //createFirstName(container);
        //createLastName(container);
        createCombo(container);
        return area;
    }
       
    private void createCombo(Composite parent) {
    	Label commitId = new Label(parent, SWT.NONE);
    	commitId.setText("Commit Id");
    	commitHashIdCombo = new Combo(parent, SWT.READ_ONLY | SWT.BORDER);
        GridData dataLastName = new GridData();
        dataLastName.grabExcessHorizontalSpace = true;
        dataLastName.horizontalAlignment = GridData.FILL;
        commitHashIdCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
    	
    	Label commitMsg = new Label(parent, SWT.NONE);
    	commitMsg.setText("Message");
    	commitMessageCombo = new Combo(parent, SWT.READ_ONLY | SWT.BORDER);
        GridData dataLastName1 = new GridData();
        dataLastName1.grabExcessHorizontalSpace = true;
        dataLastName1.horizontalAlignment = GridData.FILL;
        commitMessageCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
    	
    	Label authorName = new Label(parent, SWT.NONE);
    	authorName.setText("Author");
    	authorCombo = new Combo(parent, SWT.READ_ONLY | SWT.BORDER);

    	authorCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
    	
    	String [] hashIds  = new String[gitLog.size()];
    	String [] msgs  = new String[gitLog.size()];
    	String [] authors  = new String[gitLog.size()];
    	int i=0;
    	for( GitLog gitLogItr:gitLog) {
    		hashIds[i] = gitLogItr.getHashId();
    		msgs[i] = gitLogItr.getMessage();
    		authors[i] = gitLogItr.getAuthor();
    		i++;
    	}
    	//String [] choices = {gitLog.getHashId(), "2", "3", "4"};

    	commitHashIdCombo.setItems(hashIds);
    	commitHashIdCombo.addSelectionListener(new SelectionAdapter() {
    	      @Override
    	      public void widgetSelected(final SelectionEvent event)
    	      {
    	    	 // System.out.println("Combo1");
    	        int index = commitHashIdCombo.getSelectionIndex();
//
    	        commitMessageCombo.setItems(msgs[index]);
    	        
    	        
//
    	        commitMessageCombo.select(0);
    	        
    	        authorCombo.setItems(authors[index]);
    	        
    	        
//
    	        authorCombo.select(0);
    	      }
    	    });
 
    }

//    private void createFirstName(Composite container) {
//        Label lbtFirstName = new Label(container, SWT.NONE);
//        lbtFirstName.setText("First Name");
//
//        GridData dataFirstName = new GridData();
//        dataFirstName.grabExcessHorizontalSpace = true;
//        dataFirstName.horizontalAlignment = GridData.FILL;
//
//        txtFirstName = new Text(container, SWT.BORDER);
//        txtFirstName.setLayoutData(dataFirstName);
//    }
//
//    private void createLastName(Composite container) {
//        Label lbtLastName = new Label(container, SWT.NONE);
//        lbtLastName.setText("Last Name");
//
//        GridData dataLastName = new GridData();
//        dataLastName.grabExcessHorizontalSpace = true;
//        dataLastName.horizontalAlignment = GridData.FILL;
//        lastNameText = new Text(container, SWT.BORDER);
//        lastNameText.setLayoutData(dataLastName);
//    }



    @Override
    protected boolean isResizable() {
        return true;
    }

    // save content of the Text fields because they get disposed
    // as soon as the Dialog closes
    private void saveInput() {
        commitHashId = commitHashIdCombo.getText();
        commitMessage = commitMessageCombo.getText();
        author = authorCombo.getText();
    }

    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }
    
    

    @Override
	protected void cancelPressed() {
		// TODO Auto-generated method stub
    	cancelPressed = true;
		super.cancelPressed();
	}

	public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    @Override
    protected void configureShell(Shell newShell) {
     super.configureShell(newShell);
     newShell.setText("Git Log");
    }
//
//    @Override
//    protected Point getInitialSize() {
//     return new Point(500, 300);
//    }

	public String getCommitHashId() {
		return commitHashId;
	}

	public void setCommitHashId(String commitHashId) {
		this.commitHashId = commitHashId;
	}

	public String getCommitMessage() {
		return commitMessage;
	}

	public void setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isCancelPressed() {
		return cancelPressed;
	}

	public void setCancelPressed(boolean cancelPressed) {
		this.cancelPressed = cancelPressed;
	}
}
