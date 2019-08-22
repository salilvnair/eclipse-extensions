package difftool.util.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import difftool.util.model.GitStatus;

public class GitLogDialog extends TitleAreaDialog {
    
	@Override
	protected Point getInitialSize() {
		// TODO Auto-generated method stub
		return new Point(735,400);
	}
	private List<GitStatus> gitStatuses;

    public GitLogDialog(Shell parentShell, List<GitStatus> gitStatuses) {
        super(parentShell);  
        this.gitStatuses = gitStatuses;
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    protected Control createDialogArea(Composite parent) {
    	Composite area = (Composite) super.createDialogArea(parent);   
    	createGitLogTableContent(area);        
        return area;
    }
        
    private void createGitLogTableContent(Composite parent) {
    	 Composite container = new Composite(parent, SWT.FILL);
         container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));        
         GridLayout layout = new GridLayout(1, true);
         container.setLayout(layout);
    	final Table table = new Table(container,SWT.VERTICAL| SWT.H_SCROLL);
    	table.setLinesVisible(true); 
    	table.setHeaderVisible(true);
    	String[] titles = { "Status", "File"};    	
    	
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
        tableGrid.grabExcessVerticalSpace = true;
        tableGrid.verticalAlignment = GridData.FILL;
        tableGrid.horizontalAlignment = GridData.FILL;
        table.setLayoutData(tableGrid);
    }
    
    @Override
    protected boolean isResizable() {
        return true;
    }

    @Override
    protected void okPressed() {
        super.okPressed();
    }
    
   
    @Override
    protected void configureShell(Shell newShell) {
     super.configureShell(newShell);
     newShell.setText("Git Status");
    }

}
