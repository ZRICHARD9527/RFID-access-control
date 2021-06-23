package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcSteps {
	private static String driver; // 驱动
	private static String url; // 路径
	private static String username; // 用户名
	private static String password; // 密码

	/**
	 * 使用myjdbc文件中的配置信息获取一个Connection对象
	 * 
	 * @return 一个Connection对象
	 */
	public static Connection getConnection() {
		ResourceBundle bundle = ResourceBundle.getBundle("myJdbc");

		driver = bundle.getString("driver");
		url = bundle.getString("url");
		username = bundle.getString("username");
		password = bundle.getString("password");
		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("成功连接数据库");
		return conn;
	}

	public static PreparedStatement getPstmt(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static void release(Connection conn, PreparedStatement pstmt, ResultSet rs) { // 回收资源
		{
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		{
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		{
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ResultSet inQuiry(PreparedStatement pstmt) {
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return rs;
	}

	public static int doSomething(PreparedStatement pstmt) {
		int i = -2;
		try {
			i = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return i;
	}
}
