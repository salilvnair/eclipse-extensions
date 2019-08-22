package difftool.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import difftool.service.GitHandlerUtil;



public class CompareWithHeadHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelectionService service = window.getSelectionService();
		IStructuredSelection structured = (IStructuredSelection) service.getSelection();
		List<?> selectedElements = structured.toList();
		if(selectedElements.size()>1) {
			MessageDialog.openError(
			window.getShell(),
			"Git",
			"You cannot select more than one file!");
		}
		IResource resource = (IResource)Platform.getAdapterManager().getAdapter(selectedElements.get(0), IResource.class);
		GitHandlerUtil.compareWithHead(resource);
		return null;
	}

}
