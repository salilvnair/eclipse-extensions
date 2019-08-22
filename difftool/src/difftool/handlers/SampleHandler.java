//package difftool.handlers;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.core.commands.AbstractHandler;
//import org.eclipse.core.commands.ExecutionEvent;
//import org.eclipse.core.commands.ExecutionException;
//import org.eclipse.core.resources.IResource;
//import org.eclipse.core.runtime.Platform;
//import org.eclipse.jface.action.IContributionItem;
//import org.eclipse.jface.action.MenuManager;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.viewers.IStructuredSelection;
//import org.eclipse.jface.window.Window;
//import org.eclipse.swt.widgets.Menu;
//import org.eclipse.ui.ISelectionService;
//import org.eclipse.ui.IWorkbenchWindow;
//import org.eclipse.ui.handlers.HandlerUtil;
//import org.eclipse.ui.internal.WorkbenchWindow;
//
//import com.google.gson.Gson;
//
//import difftool.dialog.DialogBuilder;
//import difftool.dialog.GitDialog;
//import difftool.dialog.MyTitleAreaDialog;
//import difftool.util.BashUtil;
//
//public class SampleHandler extends AbstractHandler {
//
//	@Override
//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		// set selection service
//		ISelectionService service = window.getSelectionService();
//		// set structured selection
//		IStructuredSelection structured = (IStructuredSelection) service.getSelection();
//		List<?> selectedElements = structured.toList();
//		String[] diffFiles = new String[2];
//		int counter=0;
//	 
//		for(Object selectedElement:selectedElements) {
//		IResource resource = (IResource)Platform.getAdapterManager().getAdapter(selectedElement, IResource.class);		
//		diffFiles[counter]=resource.getLocation().toPortableString();
//		//resource.getProjectRelativePath()
//		//resource.getFullPath()
//		//resource.getProject().getFullPath()
//		//resource.getProject().getLocation()      .toPortableString()
//		counter++;
//		}
//		//check if it is an IFile
////		if (structured.getFirstElement() instanceof IFile) {
////			// get the selected file
////			IFile file = (IFile) structured.getFirstElement();
////			// get the path
////			IPath path = file.getLocation();
////			System.out.println(path.toPortableString());
////		}
//	 
//		//check if it is an ICompilationUnit
////		if (structured.getFirstElement() instanceof ICompilationUnit) {
////			ICompilationUnit cu = (ICompilationUnit) structured.getFirstElement();
////			System.out.println(cu.getElementName());
////		}
//		//Platform.getInstallLocation().getURL()
//		//Bundle bundle = FrameworkUtil.getBundle(getClass());
//
//		//IPath stateLoc = Platform.getStateLocation(bundle);
//		
////		if(selectedElements.size()>2) {
////			MessageDialog.openInformation(
////					window.getShell(),
////					"Difftool",
////					"You cannot select more than 2 files!");
////			return null;
////		}
////		String[] diffFiles = new String[2];
////		int i=0;
////		for(Object selectedElement:selectedElements) {
////			IResource resource = (IResource)Platform.getAdapterManager().getAdapter(selectedElement, IResource.class);
////			diffFiles[i]=resource.getLocation().toPortableString();
////			i++;
////		}
////		try {
////			Process process = new ProcessBuilder("C:\\MyHDD\\D\\Software\\DiffMerge_4_2_0_697_stable_x64\\sgdm.exe",diffFiles[0],diffFiles[1]).start();
////			System.out.println(process);
////			process.getInputStream();
////			//postWindowOpen(window);
////			return null;
////		}
////		catch(Exception ex) {
////			System.out.println(ex);
////		}		
//		String s="";
//		
////		 try{
////	            String command = "cd '/c/MyHDD/D/Eclipse Workspace/Adopt/git_local_up/adopt'" + 
////	            		"&& git rev-parse --is-inside-work-tree";
////	             s = BashUtil.execute(command,true);
////	            System.out.println(s);
////	        }
////	        catch(Exception e){
////	            e.printStackTrace();
////	        }
//        List<GitDialog> gitDialogs = new ArrayList<>();
//		 try{
//			 //C:/MyHDD/F/Adopt/development/working/runtime-EclipseApplication/Tets/src/AnotherTest.java
//			 // /c/
//	            String command = "cd '/c/MyHDD/D/Eclipse Workspace/Adopt/git_local_up/adopt'" + 
//	            		"&& git log -n 2 --pretty=format:\"%H\":\"%B\":\"%aN\"^ -- src/com/att/adopt/frontload/helper/util/StackTraceFrame.java";
//	             s = BashUtil.execute(command,false);
//	             s=s.replaceAll("\\^$", "");
//	             String s1[] = s.split("\\^");
//	             for(int i=0; i<s1.length;i++) {
//	            	 String s2[] = s1[i].split(":");
//		             DialogBuilder db = new DialogBuilder();
//		             db.setHashId(s2[0]).setMessage(s2[1]).setAuthor(s2[2]);
//		             gitDialogs.add(db.build());
//	             }	             
//	            System.out.println(s);
//	            Gson gson = new Gson();
//	            gson.toJson(s);
//	        }
//	        catch(Exception e){
//	            e.printStackTrace();
//	        }
//
//		try {
//			String diffRemotePath = "C:\\git\\remote\\";
//			String fileName = diffFiles[0];
//			Path p = Paths.get(fileName);
//			String remoteFile = p.getFileName().toString();
//			String difftool = "";
//			difftool = "C:\\git\\difftool\\winmerge\\wm.exe";
//			//"C:\\MyHDD\\D\\Software\\DiffMerge_4_2_0_697_stable_x64\\sgdm.exe"
//			//Process process = new ProcessBuilder(difftool,diffFiles[0],diffRemotePath+remoteFile).start();
//			//"C:/MyHDD/D/Software/winmerge/wm.exe" -e -ub -dl "Base" -dr "Mine"			
//			//System.out.println(process);
//			//process.getInputStream();
//			//postWindowOpen(window);
//			//window.getShell().setText("Ohoo");
//			MyTitleAreaDialog dialog = new MyTitleAreaDialog(window.getShell(),gitDialogs);
//			dialog.create();
//			MyTitleAreaDialog.setDialogHelpAvailable(false);
//			if (dialog.open() == Window.OK) {
//			    System.out.println(dialog.getFirstName());
//			    System.out.println(dialog.getLastName());
//			}
////			ElementListSelectionDialog dialog =
////				    new ElementListSelectionDialog(window.getShell(), new LabelProvider());
////				dialog.setElements(new String[] { "Linux", "Mac", "Windows" });
////				dialog.setTitle("Which operating system are you using");
////				//dialog.set
////				// user pressed cancel
////				if (dialog.open() != Window.OK) {
////				        return false;
////				}
////				Object[] result = dialog.getResult();
//			return null;
//		}
//		catch(Exception ex) {
//		System.out.println(ex);
//	}
//		
//		MessageDialog.openInformation(
//				window.getShell(),
//				"Difftool",
//				s);
//		return null;
//	}
//    public void postWindowOpen(IWorkbenchWindow window)
//    {
//    
//    	
//    			if(window instanceof WorkbenchWindow) {
//    			    MenuManager menuManager = ((WorkbenchWindow)window).getMenuManager();
//
//
//    			    Menu menu = menuManager.getMenu();
//
//    			    //you'll need to find the id for the item
//    			    String itemId = "difftool.menus.sampleCommand";
//    			    IContributionItem item = menuManager.find(itemId);
//
//    			    // remember position, TODO this is protected
//    			  //  int controlIdx = menu.indexOf(mySaveAction.getId());
//
//    			    if (item != null) {
//    			        // clean old one
//    			        menuManager.remove(item);
//
//    			        // refresh menu gui
//    			        menuManager.update();
//    			    }
//    			}
//    }
//}
