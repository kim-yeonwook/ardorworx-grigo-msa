package ibs.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mecury.io.LocalProperties;
import mecury.log.Log;
import v3.venus.mod.Modular;

public class ProcessManager {
	
//	echo 1q2w3e4r%^ | sudo -S mosquitto_passwd -b /etc/mosquitto/passwd b40a7d29f3bb4e0c '!b40a7d29f3bb4e0c!'
//	명령어에 비밀번호를 주는것은 보안상 안좋음 // 일단 파일의 권한을 변경
	private static final String SUDO = LocalProperties.get("sudo", "/bin/bash -c echo 1q2w3e4r%^");
	private static final String HEADER = LocalProperties.get("header", "cmd.exe /c");
	
	
	ProcessBuilder process;
	
	public List<String> command;
	
	StringBuffer sb;
	
	public ProcessManager() {
		// TODO Auto-generated constructor stub
		this.process = new ProcessBuilder();
		this.sb = new StringBuffer();
		this.command = new ArrayList<String>();
		command.addAll(Arrays.asList(new String[] {"cmd.exe", "/c"}));
	}
	
	public void open(String command) throws Exception {
		try {
			process = new ProcessBuilder().command("cmd.exe", "/c", command);
			sb = new StringBuffer();
		} catch (Exception e) {
		}
	}
	
	public void setCommand(String command) throws Exception {
		this.command.add(command);
	}
	
	public void move(String path) throws Exception {
		process.directory(new File(path));
	}
	
	public String send() throws Exception {
		Process p = null;
		try {
			Log.info(Modular.ID, command.toArray());
			
			process.command(this.command);
			
			p = process.start();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "MS949"));
			
			String s = "";
			while ((s = br.readLine()) != null ) {
				this.sb.append(s);
				System.out.println(s);
			}
			
			try {p.getInputStream().close();} catch(Exception e) {}
			try {p.getErrorStream().close();} catch(Exception e) {}
			try {p.getOutputStream().close();} catch(Exception e) {}
			
			p.waitFor();
	
			p.destroy();
			
			return new String(this.sb);
		} catch (Exception e) {
			Log.info(Modular.ID, "Process Command Send Fail");
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
//				m.open("dir");
				m.setCommand("dir");;
				
				m.move("C:\\");
//				m.open("ipconfig");
				
				System.out.println(m.send());
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
