package BEAN;

public class message {

	public int errorCode;
	public String msg;
	public String[] data;
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public message(int errorCode, String msg, String[] data) {
		super();
		this.errorCode = errorCode;
		this.msg = msg;
		this.data = data;
	}
	public message() {
		// TODO Auto-generated constructor stub
	}
}
