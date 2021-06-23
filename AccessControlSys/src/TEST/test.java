package TEST;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Http.Http45;
import JDBC.JdbcSteps;
import RFID.ContinueRead;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import BEAN.message;

public class test extends Thread{

	private static String urlString = "http://192.168.43.43:8133/";
	private static ArrayList<String> idlist = new ArrayList<String>();
	
	private static Connection conn;

	public static String uid;
	
	public static void main(String[] args) {
		

		
		System.out.println("开始启动服务");
		conn = JdbcSteps.getConnection();

		initSer(conn);// 初始化

		// 开始串口监听
		ContinueRead cRead = new ContinueRead();
		int i = cRead.startComPort();
		if (i == 1) {
			// 如果串口打开就开始监听
			cRead.start();
		} else {
			System.out.println("串口连接失败");
		}
//测试代码
//		myThread thread=new myThread(conn, "INSERT INTO u_ids ( u_id ) VALUES (?);");
//		thread.start();
		
		while (true) {
			try {
				String sql="DELETE FROM u_ids WHERE ( u_id = ? ) ";
				System.out.println("向服务器请求删除列表");
				String re=Http45.doPost(urlString+"user/getInvalid", "{\"g_id\":1}");
				System.out.println("-----------服务器回传数据："+re);
				//JSONObject jsonObject = (JSONObject)JSONObject.parse(re);
				
				message mes=JSON.parseObject(re,message.class); 
					
				if(mes.errorCode==0) {
					
					try {
						for(String b:mes.data) {
							
							if(updata(conn, b, sql)>0)
							{
								idlist.remove(b);
							}
						}
						Http45.doPost(urlString+"user/delOK", re);
						System.out.println("-----------数据库已同步----------");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				
			}
            try {
				Thread.sleep(180000);    //休眠3分钟
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}

	}

	/**
	 * 
	 * 初始化服务
	 * 
	 * @param coon
	 */
	private static void initSer(Connection conn) {
		String sql = "select u_id from u_ids";
		PreparedStatement p = JdbcSteps.getPstmt(conn, sql);
		ResultSet res = JdbcSteps.inQuiry(p);
		try {
			while (res.next()) {
				idlist.add(res.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		JdbcSteps.release(null, p, res); // 释放资源

		System.out.println("初始化结果:" + idlist.size() + "条");
		for (String a : idlist) {
			System.out.println(a);
		}
	}

	public static void getMsg(String uid) {
		if (idlist.contains(uid)) {
			System.out.println("开门");
			Http45.doPost(urlString + "user/ioLog", "{\"g_id\":1,\"u_id\":\"" + uid + "\"}");
			return;
		}

		else {
			System.out.println("向云端请求数据");
			try {
				System.out.println("uid: "+uid);
				String reString = Http45.doPost(urlString + "user/isExist", "{\"g_id\":1,\"u_id\":\"" + uid + "\"}");
				JSONObject jsonObject=JSONObject.parseObject(reString);
				if ((int)jsonObject.get("errorCode") == 0) {
					System.out.println("开门");
					idlist.add(uid);
					
					String sql="INSERT INTO u_ids ( u_id ) VALUES (?);";
//					updata(conn, uid,sql);
					
					//在新线程中完成
					myThread thread=new myThread(conn, sql,uid);
					thread.start();
					return;
				}
				else System.out.println("权限不足无法开门");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("网络连接失败");
			}

			
		}
	}
	
	private static int updata(Connection conn,String uid,String sql) {
		PreparedStatement pstmt=JdbcSteps.getPstmt(conn, sql);
		
		try {
			pstmt.setString(1, uid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		int i=JdbcSteps.doSomething(pstmt);
		JdbcSteps.release(null, pstmt, null);  //释放资源
		return i;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		getMsg(uid);
	}
}
