package difftool.util;

import java.io.IOException;

public class BashUtil {

	
	 public static String execute(String cmd,boolean produceLineBreak)
		        throws IOException, InterruptedException, IllegalCommandException{
		 
		        if (!authorizedCommand(cmd)) {
		        	throw new IllegalCommandException();
		        }

		        String result = "";
		        final Process p = Runtime.getRuntime().
		            exec(String.format("C:\\progra~1\\git\\bin\\bash.exe -c \"%s\"", cmd));
		        final ProcessResultReader stderr = new ProcessResultReader(
		                p.getErrorStream(), "STDERR",produceLineBreak);
		        final ProcessResultReader stdout = new ProcessResultReader(
		                p.getInputStream(), "STDOUT",produceLineBreak);
		        stderr.start();
		        stdout.start();
		        final int exitValue = p.waitFor();
		        if (exitValue == 0){
		            result = stdout.toString();
		        }
		        else{
		            result = stderr.toString();
		            if("".equals(result)) {
		            	if(stdout!=null && !"".equals(stdout.sb.toString())) {
		            		result= stdout.toString();
		            	}
		            }
		        }
		        return result;
		}
	 
	    public static boolean authorizedCommand(String cmd){
//	        //Run commands which pre-approved here.
//	        if (cmd.equals("dir")) {
//	            return true;
//	        }
	        return true;
	    }
	
	
}
