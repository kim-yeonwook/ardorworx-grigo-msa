package ibs.test.signal;

public class Signal implements SignalIF {

	public String serial_no;
	public String comm_code;
	public long time;

	@Override
	public void setSerial_no(String serial_no) {
		// TODO Auto-generated method stub
		this.serial_no = serial_no;
	}
	

	@Override
	public void setComm_code(String comm_code) {
		// TODO Auto-generated method stub
		this.comm_code = comm_code;
	}

	@Override
	public String getSerial_no() {
		// TODO Auto-generated method stub
		return this.serial_no;
	}

	@Override
	public String getComm_code() {
		// TODO Auto-generated method stub
		return this.comm_code;
	}
	
}
