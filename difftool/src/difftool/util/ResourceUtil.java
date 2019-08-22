package difftool.util;

import org.eclipse.core.resources.IResource;

public class ResourceUtil {
	
	public static String getFullPath(IResource resource) {
		return resource.getLocation().toPortableString();
	}
	
	public static String getProjectRelativePath(IResource resource) {
		return resource.getProjectRelativePath().toPortableString();
	}
	
	public static String getProjectFullPath(IResource resource) {
		return resource.getProject().getFullPath().toPortableString();
	}
	
	public static String getProjectLocation(IResource resource) {
		return resource.getProject().getLocation().toPortableString();
	}
	
	public static String getFileName(IResource resource) {
		return resource.getName();
	}
	
	
}
