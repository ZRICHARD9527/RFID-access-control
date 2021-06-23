package TEST;

import java.sql.Connection;
import java.sql.PreparedStatement;

import JDBC.JdbcSteps;

public class myThread extends Thread {

	public Connection conn;
	public String sql;
	public String uid;

	public myThread(Connection conn, String sql, String uid) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
		this.sql = sql;
		this.uid = uid;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String sql = this.sql;
		String uid = this.uid;
		PreparedStatement pstmt = JdbcSteps.getPstmt(conn, sql);

		try {
			pstmt.setString(1, uid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		JdbcSteps.doSomething(pstmt); // 数据库的增加和删除
		JdbcSteps.release(null, pstmt, null); // 释放资源
	}
}
