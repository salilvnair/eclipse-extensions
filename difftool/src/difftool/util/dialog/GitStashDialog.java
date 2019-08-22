package difftool.util.dialog;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import difftool.util.builder.type.GitStash;
import difftool.util.model.GitStashData;
import difftool.util.model.GitStashListModel;
import difftool.util.model.GitStashShow;
import difftool.util.model.GitStatus;

public class GitStashDialog extends TitleAreaDialog {
    
	@Override
	protected Point getInitialSize() {
		// TODO Auto-generated method stub
		return new Point(735,500);
	}

	Combo stashIdCombo;
	Combo stashMessageCombo;
	Combo stashFileChoiceCombo;
	Combo stashOptionCombo;
	Text stashMessageText;
	
	private String selectedStashId;
	private String selectedOption;
	private String stashMessage;
	private String selectedStashType;
    private List<GitStashListModel> gitStashes;
    private GitStashData gitStashData;
    private GitStash stashOption;
    private Table table;
    
    String [] choicesText = {"", "Include Untracked", "Include All", "Patch","Keep Index"};
    String [] choicesKey = {"", "-u", "-a", "-p","-k"};

    public GitStashDialog(Shell parentShell, GitStashData  gitStashData,GitStash stashOption) {
        super(parentShell);  
        this.gitStashData = gitStashData;
        this.gitStashes = gitStashData.getGitStashes();
        this.stashOption = stashOption;
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    protected Control createDialogArea(Composite parent) {
    	Composite area = (Composite) super.createDialogArea(parent);   
        if(stashOption.equals(GitStash.PUSH)) {
        	createGitStashDialog(area);
        	createGitStatusTableContent(area);
        }
        else if(stashOption.equals(GitStash.APPLY)||stashOption.equals(GitStash.DROP)) {
        	createApplyStashDialog(area);  
        	createGitStashFileTableContent(area);
        }        
        return area;
    }
    
    private void createGitStashFileTableContent(Composite area) {
   	 	Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));        
        GridLayout layout = new GridLayout(1, true);
        container.setLayout(layout);
        final Table table = new Table(container,SWT.V_SCROLL | SWT.H_SCROLL);
      
        this.table = table;
        table.setLinesVisible(true); 
        table.setHeaderVisible(true);
        String[] titles = { "Status", "File"};
        
	    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	      final TableColumn column = new TableColumn(table, SWT.FILL);
	      column.setText(titles[loopIndex]);
	      column.setWidth(table.getClientArea().width);
	    }
	       // Seed the table
		for (int k=0 ;k<6;k++) {
		   TableItem item = new TableItem(table, SWT.FILL);
		   item.setText(0, "");
		   item.setText(1, "");
		}
		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		    //table.getColumn(loopIndex).pack();    	   
		   	if(loopIndex==0) {
		      		 table.getColumn(loopIndex).setWidth(100);
		    }
		    else {
		       		 table.getColumn(loopIndex).setWidth(605);
		    }
		}		   
	    
		GridData tableGrid = new GridData();
		tableGrid.grabExcessHorizontalSpace = true;
		tableGrid.horizontalAlignment = GridData.FILL;
		table.setLayoutData(tableGrid);
    }
    
    private void toogleDataOfGitStashFileTableContent(Table table,List<GitStashShow> gitStashShowList,int titleSize) {
    	table.removeAll();
    	for (GitStashShow gitStashStatus :gitStashShowList) {
	        TableItem item = new TableItem(table, SWT.FILL);
	        item.setText(0, gitStashStatus.getStatus());
	        item.setText(1, gitStashStatus.getFileName());
	    }
       // Seed the table
       for (int loopIndex = 0; loopIndex < titleSize; loopIndex++) {
           //table.getColumn(loopIndex).pack();    	   
       	if(loopIndex==0) {
       		 table.getColumn(loopIndex).setWidth(100);
       	}
       	else {
       		table.getColumn(loopIndex).setWidth(605);
       	}
          
       }
       
       //table.setBounds(25, 25, 400, 200);

	}

	private Composite createApplyStashDialog(Composite area) {        
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        layout.verticalSpacing=2;
        container.setLayout(layout);
        createComboForApplyStash(area,container);
        return area;
    }
    
    private Composite createGitStashDialog(Composite area) {        
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));        
        GridLayout layout = new GridLayout(2, false);
        layout.verticalSpacing=10;
        container.setLayout(layout);
        createDialogForGitStash(container);
        return container;
    }
    
    private void createGitStatusTableContent(Composite parent) {
    	 Composite container = new Composite(parent, SWT.NONE);
         container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));        
         GridLayout layout = new GridLayout(1, true);
         container.setLayout(layout);
    	final Table table = new Table(container,SWT.V_SCROLL | SWT.H_SCROLL);
    	table.setLinesVisible(true); 
    	table.setHeaderVisible(true);
    	String[] titles = { "Status", "File"};
    	List<GitStatus> gitStatuses = this.gitStashData.getGitStatuses();

	    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	      final TableColumn column = new TableColumn(table, SWT.FILL);
	      column.setText(titles[loopIndex]);
	      column.setWidth(table.getClientArea().width);
	    }
	    for (GitStatus gitStatus :gitStatuses) {
	        TableItem item = new TableItem(table, SWT.FILL);
	        item.setText(0, gitStatus.getStatus());
	        item.setText(1, gitStatus.getFileName());
	    }
        // Seed the table
        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            //table.getColumn(loopIndex).pack();
        	if(loopIndex==0) {
        		 table.getColumn(loopIndex).setWidth(100);
        	}
        	else {
        		 table.getColumn(loopIndex).setWidth(605);
        	}
           
        }
        
        //table.setBounds(25, 25, 400, 200);
        GridData tableGrid = new GridData();
        tableGrid.grabExcessHorizontalSpace = true;
        tableGrid.horizontalAlignment = GridData.FILL;
        table.setLayoutData(tableGrid);
//        table.addListener(SWT.Resize, new Listener()
//        {
//            public void handleEvent(Event event)
//            {
//                column.setWidth(table.getClientArea().width);
//            }
//        });
    }
    
    
    private void createDialogForGitStash(Composite container) {
      Label stashFilter = new Label(container, SWT.NONE);
      stashFilter.setText("Stash");
      stashOptionCombo = new Combo(container, SWT.READ_ONLY | SWT.BORDER);
      GridData stashOpntionGrid = new GridData();
      stashOpntionGrid.grabExcessHorizontalSpace = true;
      stashOpntionGrid.horizontalAlignment = GridData.FILL;
      stashOptionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
      String fileName = gitStashData.getFileName();
      String projectName = gitStashData.getProjectName();
      String [] fileDirText = new String [2];
      if(fileName!=null) {
          fileDirText[0] = projectName;
    	  fileDirText[1]= fileName;
      }
      else {
    	  fileDirText = new String [1];
          fileDirText[0] = projectName;
      }

      Label stashOptionFilter = new Label(container, SWT.NONE);
      stashOptionFilter.setText("Stash Option");
      stashFileChoiceCombo = new Combo(container, SWT.READ_ONLY | SWT.BORDER);
      GridData stashFileGrid = new GridData();
      stashFileGrid.grabExcessHorizontalSpace = true;
      stashFileGrid.horizontalAlignment = GridData.FILL;
      stashFileChoiceCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
      
      
      stashOptionCombo.setItems(choicesText);
      stashOptionCombo.select(0);
      stashFileChoiceCombo.setItems(fileDirText);
      stashFileChoiceCombo.select(0);
      
      Label stashMessage = new Label(container, SWT.NONE);
      stashMessage.setText("Stash Message");
      GridData stashMessageGrid = new GridData();
      stashMessageGrid.grabExcessHorizontalSpace = true;
      stashMessageGrid.horizontalAlignment = GridData.FILL;
      stashMessageText = new Text(container, SWT.BORDER);
      stashMessageText.setLayoutData(stashMessageGrid);
		
	}

	private void createComboForApplyStash(Composite area,Composite parent) {
    	Label commitMsg = new Label(parent, SWT.NONE);
    	commitMsg.setText("Label");
    	stashMessageCombo = new Combo(parent, SWT.READ_ONLY | SWT.BORDER);
        GridData stashMsgGrid = new GridData();
        stashMsgGrid.grabExcessHorizontalSpace = true;
        stashMsgGrid.horizontalAlignment = GridData.FILL;
        stashMessageCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
        
    	Label commitId = new Label(parent, SWT.NONE);
    	commitId.setText("Stash Id");
    	stashIdCombo = new Combo(parent, SWT.READ_ONLY | SWT.BORDER);
        GridData stashIdGrid = new GridData();
        stashIdGrid.grabExcessHorizontalSpace = true;
        stashIdGrid.horizontalAlignment = GridData.FILL;
        stashIdCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
    	    	
    	String [] stashIds  = new String[gitStashes.size()];
    	String [] msgs  = new String[gitStashes.size()];
    	int i=0;
    	for( GitStashListModel gitStash:gitStashes) {
    		stashIds[i] = gitStash.getStashId();
    		msgs[i] = gitStash.getMessage();
    		i++;
    	}
    	//String [] choices = {gitLog.getHashId(), "2", "3", "4"};

    	stashMessageCombo.setItems(msgs);
    	stashMessageCombo.addSelectionListener(new SelectionAdapter() {
    	      @Override
    	      public void widgetSelected(final SelectionEvent event)
    	      {
    	    	 // System.out.println("Combo1");
    	        int index = stashMessageCombo.getSelectionIndex();
    	        stashIdCombo.setItems(stashIds[index]);    	        
    	        stashIdCombo.select(0);
    	       
    	        toogleDataOfGitStashFileTableContent(table, gitStashes.get(index).getGitStashFiles(),2);
    	      }
    	    });
 
    }

    @Override
    protected boolean isResizable() {
        return true;
    }

    // save content of the Text fields because they get disposed
    // as soon as the Dialog closes
    private void saveInput() {
    	if(stashOption.equals(GitStash.APPLY)||stashOption.equals(GitStash.DROP)) {
    		selectedStashId = stashIdCombo.getText();
    		stashMessage = stashMessageCombo.getText();
    	}
    	else {
        	stashMessage = stashMessageText.getText();
        	int optionIndex = Arrays.asList(choicesText).indexOf(stashOptionCombo.getText());
        	selectedOption = this.choicesKey[optionIndex];
        	selectedStashType = stashFileChoiceCombo.getText();
    	}
    }

    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }
    
   
    @Override
    protected void configureShell(Shell newShell) {
     super.configureShell(newShell);
     if(stashOption.equals(GitStash.APPLY)) {
         newShell.setText("Git Stash Apply");
     }
     else if(stashOption.equals(GitStash.DROP)) {
    	 newShell.setText("Drop A Stash");
     }
     else {
         newShell.setText("Git Stash");
     }
    }

	public String getSelectedStashId() {
		return selectedStashId;
	}

	public void setSelectedStashId(String selectedStashId) {
		this.selectedStashId = selectedStashId;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public String getSelectedStashType() {
		return selectedStashType;
	}

	public void setSelectedStashType(String selectedStashType) {
		this.selectedStashType = selectedStashType;
	}


	public String getStashMessage() {
		return stashMessage;
	}

	public void setStashMessage(String stashMessage) {
		this.stashMessage = stashMessage;
	}

}
