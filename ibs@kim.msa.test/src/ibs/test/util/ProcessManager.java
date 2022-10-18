package ibs.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mecury.log.Log;
import v3.venus.mod.Modular;

public class ProcessManager {
	
	ProcessBuilder process;
	List<String> command;
	
	public ProcessManager() {
		// TODO Auto-generated constructor stub
		this.process = new ProcessBuilder();
		this.command = new ArrayList<String>();
	}
	
	
	public ProcessManager setCommand(String command) throws Exception {
		this.command.add(command);
		return this;
	}
	
	public void move(String path) throws Exception {
		process.directory(new File(path));
	}
	
	public String send() throws Exception {
		Process p = null;
		try {
			Log.info(Modular.ID, command.toArray());
			process.command(command);
			
			p = process.start();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "MS949"));
			
			StringBuffer sb = new StringBuffer();
			String s = "";
			while ((s = br.readLine()) != null ) {
				sb.append(s).append("\n");
			}

			p.destroy();
			p.waitFor();
			
			Log.info(Modular.ID, sb);
			return new String(sb);
		} catch (Exception e) {
			Log.err(Modular.ID, "Process Command Send Fail", e);
			if (p!=null) {
				p.destroy();
				p.waitFor();
			}
			throw e;
		}
	}
	
	private static final class test {
		public static void main(String[] args) {
			try {
				ProcessManager m = new ProcessManager();
//				m.setCommand("cmd.exe");
				m.setCommand("cmd.exe").setCommand("/c");
//				m.setCommand("cmd.exe").setCommand("/c").setCommand("dir");
				m.move("C:\\");
				
				System.out.println(m.send());
				System.out.println("Close");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
