package difftool.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;

import difftool.util.DiffToolUtil;
import difftool.util.GitUtil;
import difftool.util.IllegalCommandException;
import difftool.util.ResourceUtil;
import difftool.util.builder.type.GitStash;
import difftool.util.model.GitLogData;
import difftool.util.model.GitStashData;
import difftool.util.model.GitStashListModel;
import difftool.util.model.GitStashShow;
import difftool.util.model.GitStatus;

public class GitHandlerUtil {

	private static final String UNTRACKED ="??";
	private static final String MODIFIED ="M";
	private static final String DELETED ="D";
	private static final String LINE_BREAK ="\n";
	private static final String COLON =":";
	
	public static void compareWithHead(IResource resource) {
		try{
			String fileFullPath = ResourceUtil.getFullPath(resource);				
			File file = new File(fileFullPath);
			String fileDirectoryPath  = file.getParent();
			if(GitUtil.isGitDir(fileDirectoryPath)) {
				String gitRootPath = GitUtil.getRootGitDir(fileDirectoryPath);				
				String fileRelativePath = ResourceUtil.getProjectRelativePath(resource);
				String fileName = ResourceUtil.getFileName(resource);
				GitUtil.downloadHeadFromRepo(gitRootPath,fileRelativePath, fileName);
				DiffToolUtil.launch(fileName,fileFullPath);
			}
		}
		catch(Exception e){
			
		}	
		
	}
	
	public static void compareWithHashId(IResource resource,String hashId) {
		try{
			String fileFullPath = ResourceUtil.getFullPath(resource);				
			File file = new File(fileFullPath);
			String fileDirectoryPath  = file.getParent();
			if(GitUtil.isGitDir(fileDirectoryPath)) {
				String gitRootPath = GitUtil.getRootGitDir(fileDirectoryPath);				
				String fileRelativePath = ResourceUtil.getProjectRelativePath(resource);
				String fileName = ResourceUtil.getFileName(resource);
				GitUtil.downloadRevisionFromRepo(gitRootPath,fileRelativePath,fileName, hashId);
				DiffToolUtil.launch(fileName,fileFullPath);
			}
		}
		catch(Exception e){
			
		}	
		
	}

	public static GitLogData getGitLogs(IResource resource) {
		try{
			GitLogData gitLogData = new GitLogData();
			String fileFullPath = ResourceUtil.getFullPath(resource);				
			File file = new File(fileFullPath);
			String fileDirectoryPath  = file.getParent();
			if(GitUtil.isGitDir(fileDirectoryPath)) {
				String gitRootPath = GitUtil.getRootGitDir(fileDirectoryPath);				
				String fileRelativePath = ResourceUtil.getProjectRelativePath(resource);
				gitLogData.setGitLog(GitUtil.getGitLogs(gitRootPath, fileRelativePath));
				gitLogData.setFilePath(fileRelativePath);
				gitLogData.setFileName(file.getName());
				return gitLogData;
			}
		}
		catch(Exception e){
			
		}
		return null;
	}
	
	public static String getGitStatus(IResource resource) {
		try{
			String fileFullPath = ResourceUtil.getFullPath(resource);				
			File file = new File(fileFullPath);
			String fileName = ResourceUtil.getFileName(resource);
			String projectName = resource.getProject().getName();
			String fileDirectoryPath=null;
			if(fileName.equals(projectName)) {
				fileDirectoryPath  = fileFullPath;
			}
			else {
				 fileDirectoryPath  = file.getParent();
			}
			if(GitUtil.isGitDir(fileDirectoryPath)) {
				String gitRootPath = GitUtil.getRootGitDir(fileDirectoryPath);				
				return GitUtil.gitStatus(gitRootPath);
			}
		}
		catch(Exception e){
			
		}
		return null;
	}
	
	public static GitStashData getGitStashStatus(IResource resource) {
		GitStashData gitStashData = new GitStashData();
		String fileName = ResourceUtil.getFileName(resource);
		boolean stashContainsTheFile = false;
		String projectName = resource.getProject().getName();
		gitStashData.setProjectName(projectName);
		String statusString = getGitStatus(resource);
		String[] statusStrings = statusString.split(LINE_BREAK);
		List<GitStatus> gitStatusList = new ArrayList<>();
		for(String statusData:statusStrings) {
			GitStatus gitStatus = new GitStatus();
			if(statusData.indexOf(MODIFIED)>-1) {
				gitStatus.setStatus("Modified");
				statusData = statusData.replace(MODIFIED, "").trim();
				if(statusData.contains(fileName)) {
					stashContainsTheFile = true;
				}
				gitStatus.setFileName(statusData);
			}
			if(statusData.indexOf(UNTRACKED)>-1) {
				gitStatus.setStatus("Added");
				statusData = statusData.replace(UNTRACKED, "").trim();
				if(statusData.contains(fileName)) {
					stashContainsTheFile = true;
				}
				gitStatus.setFileName(statusData);
			}
			if(statusData.indexOf(DELETED)>-1) {
				gitStatus.setStatus("Deleted");
				statusData = statusData.replace(DELETED, "").trim();
				if(statusData.contains(fileName)) {
					stashContainsTheFile = true;
				}
				gitStatus.setFileName(statusData);
			}
			gitStatusList.add(gitStatus);
		}
		if(!stashContainsTheFile) {
			fileName = null;
		}
		gitStashData.setFileName(fileName);
		gitStashData.setGitStatuses(gitStatusList);
		return gitStashData;
	}
	
	public static String getGitStash(IResource resource,String stashAdditionalOption,String fileType,String message) {
		GitStash gitStash = getStashOptionFromStringOption(stashAdditionalOption);
		return getGitStash(resource, gitStash, fileType, message);
	}
	
	public static String getGitStash(IResource resource,GitStash gitStash,String fileType,String message) {
		try{
			String fileFullPath = ResourceUtil.getFullPath(resource);				
			File file = new File(fileFullPath);
			String fileDirectoryPath  = file.getParent();			
			if(GitUtil.isGitDir(fileDirectoryPath)) {
				String gitRootPath = GitUtil.getRootGitDir(fileDirectoryPath);	
				String relativeFilePath = ResourceUtil.getProjectRelativePath(resource);
				if(fileType!=null && !relativeFilePath.equals(fileType)) {
					fileType = null;
				}
				return GitUtil.gitStash(gitRootPath, gitStash, message, fileType);
			}
		}
		catch(Exception e){
			
		}
		return "";
	}
	
	public static String gitStashApply(IResource resource,String stashId) {
		try{
			String fileFullPath = ResourceUtil.getFullPath(resource);				
			File file = new File(fileFullPath);
			String fileDirectoryPath  = file.getParent();
			if(GitUtil.isGitDir(fileDirectoryPath)) {
				String gitRootPath = GitUtil.getRootGitDir(fileDirectoryPath);	
				return GitUtil.gitStash(GitStash.APPLY,null,stashId,gitRootPath);
			}
		}
		catch(Exception e){
			
		}
		return "";
	}
	
	public static String gitStashDrop(IResource resource,String stashId) {
		try{
			String fileFullPath = ResourceUtil.getFullPath(resource);				
			File file = new File(fileFullPath);
			String fileDirectoryPath  = file.getParent();
			if(GitUtil.isGitDir(fileDirectoryPath)) {
				String gitRootPath = GitUtil.getRootGitDir(fileDirectoryPath);	
				return GitUtil.gitStash(GitStash.DROP,null,stashId,gitRootPath);
			}
		}
		catch(Exception e){
			
		}
		return "";
	}
	
	public static GitStashData gitStashList(IResource resource) {
		try{
			GitStashData gitStashData = new GitStashData();
			String fileName = ResourceUtil.getFileName(resource);
			String projectName = resource.getProject().getName();
			gitStashData.setFileName(fileName);
			gitStashData.setProjectName(projectName);
			String fileFullPath = ResourceUtil.getFullPath(resource);				
			File file = new File(fileFullPath);
			String fileDirectoryPath  = null;
			if(fileName.equals(projectName)) {
				fileDirectoryPath  = fileFullPath;
			}
			else {
				 fileDirectoryPath  = file.getParent();
			}
			if(GitUtil.isGitDir(fileDirectoryPath)) {
				String gitRootPath = GitUtil.getRootGitDir(fileDirectoryPath);				
				String stashString =  GitUtil.gitStashList(gitRootPath);
				String[] stashStrings = stashString.split(LINE_BREAK);	
				List<GitStashListModel> gitStashModels = new ArrayList<>();
				for(String stashStringItr:stashStrings) {
					String[] stashIdMessages = stashStringItr.split(COLON,2);
					GitStashListModel gitStashListModel = new GitStashListModel();
					gitStashListModel.setStashId(stashIdMessages[0].trim());
					gitStashListModel.setMessage(stashIdMessages[1].trim());
					List<GitStashShow> gitStashFiles = prepareStashShowFilesForStashId(gitRootPath,stashIdMessages[0].trim());
					gitStashListModel.setGitStashFiles(gitStashFiles);
					gitStashModels.add(gitStashListModel);
				}
				gitStashData.setGitStashes(gitStashModels);
				return gitStashData;
			}
		}
		catch(Exception e){
			
		}
		return null;
	}

	private static List<GitStashShow> prepareStashShowFilesForStashId(String gitRootPath, String stashId) throws IOException, InterruptedException, IllegalCommandException {
		String stashFileResultString =  GitUtil.gitStashShow(gitRootPath, stashId);
		String[] stashFilesStrings = stashFileResultString.split(LINE_BREAK);	
		 List<GitStashShow> gitStashShowList = new ArrayList<>(); 
		for(String stashFileString:stashFilesStrings) {
			GitStashShow gitStashShow = new GitStashShow();
			if(stashFileString.indexOf(MODIFIED)>-1) {
				stashFileString = stashFileString.replace(MODIFIED, "").trim();
				gitStashShow.setStatus("Modified");
			}
			else if(stashFileString.indexOf(DELETED)>-1) {
				stashFileString = stashFileString.replace(DELETED, "").trim();
				gitStashShow.setStatus("Deleted");
			}
			else {
				gitStashShow.setStatus("Added");
			}
			gitStashShow.setFileName(stashFileString);
			gitStashShowList.add(gitStashShow);
		}
		return gitStashShowList;
	}

	private static GitStash getStashOptionFromStringOption(String stashAdditionalOption) {
		if(stashAdditionalOption!=null) {
			for (GitStash stashOption : GitStash.values()) { 
				if(stashOption.value().equals(stashAdditionalOption)) {
					return stashOption;
				}
			}
		}
		return null;
	}
	
}
