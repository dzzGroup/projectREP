package mavenTest2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlUtil {
	/**
	 * 由于视频的p2~p5的信息时有时不有。这里的话就给p2~p5画图的横坐标，纵坐标都整一遍。 我希望jsp文件调用一下我就可以了，而不需要自己写复杂的代码。
	 * 
	 * @param date2to5
	 * @param val2to5
	 * @return
	 * @throws SQLException
	 */
	public static void setArrayListFrom2To5(Connection Conn, String bvid, ArrayList<ArrayList<MyDate>> date2to5,
			ArrayList<ArrayList<Double>> val2to5) throws SQLException {
		date2to5.clear();
		val2to5.clear();
		for (int i = 2; i <= 5; ++i) {
			ArrayList<MyDate> dateArr = new ArrayList<MyDate>();
			ArrayList<Double> valArr = new ArrayList<Double>();

			Statement stm = Conn.createStatement();
			ResultSet res = stm.executeQuery(
					"select caijishijian,viewing_p" + i + " from " + bvid + " where viewing_p" + i + ">=0");
			while (res.next()) {
				dateArr.add(StrToMyDate(res.getString("caijishijian")));
				valArr.add((double) res.getInt("viewing_p" + i));

			}
			date2to5.add(dateArr);
			val2to5.add(valArr);
		}
		
	}

	// 将爬取得到的一行数据保存到数据库对应的表中。
	/**
	 * 并不是所有的参数都是bilibili生数据，MyDate参数可以在调用这个函数的时候实时生成,代表采集数据的时间。
	 * 
	 * @param dianzan
	 * @param toubi
	 * @param shoucang
	 * @param bofang
	 * @param danmu
	 * @param caijishijian
	 * @param viewing_p1
	 * @param viewing_p2
	 * @param viewing_p3
	 * @param viewing_p4
	 * @param viewing_p5
	 * @param p            视频的分p数
	 * @return
	 */
	public static BilibiliVideoData RawDataToBVD(String dianzan, String toubi, String shoucang, String bofang,
			String danmu, MyDate caijishijian, String viewing_p1, String viewing_p2, String viewing_p3,
			String viewing_p4, String viewing_p5, int p) {
		// 采集到的生数据，转成BVD
		// b站对于过万的数据，采用一位小数+"万"的表达方式
		// 而对于过亿的数据，采用一位小数+"亿"的表达方式
		// 所以，多数生数据都可以通过一个方式转换成int类型的数据。
		// 这个转换方法作为另一个私有方法bilibiliRawDataToInt()。
		int dianzan_ = bilibiliRawDataToInt(dianzan);
		int toubi_ = bilibiliRawDataToInt(toubi);
		int shoucang_ = bilibiliRawDataToInt(shoucang);
		int bofang_ = bilibiliRawDataToInt(bofang);
		int danmu_ = bilibiliRawDataToInt(danmu);
//		int caijishijian_=bilibiliRawDataToInt(caijishijian);
		int viewing_p1_ = bilibiliRawDataToInt(viewing_p1);
		int viewing_p2_ = bilibiliRawDataToInt(viewing_p2);
		int viewing_p3_ = bilibiliRawDataToInt(viewing_p3);
		int viewing_p4_ = bilibiliRawDataToInt(viewing_p4);
		int viewing_p5_ = bilibiliRawDataToInt(viewing_p5);

		return new BilibiliVideoData(caijishijian, dianzan_, toubi_, shoucang_, bofang_, danmu_, viewing_p1_,
				viewing_p2_, viewing_p3_, viewing_p4_, viewing_p5_, p);
	}

	/**
	 * 把bilibili爬取来的数据如"1.1万"转成int类型。
	 * 
	 * @param rawData
	 * @return
	 */
	private static int bilibiliRawDataToInt(String rawData) {
		if (rawData.contains("亿")) {
			return (int) (Double.valueOf(rawData.replace("亿", "")) * 100000000);
		} else if (rawData.contains("万")) {
			return (int) (Double.valueOf(rawData.replace("万", "")) * 10000);
		} else
			return Integer.valueOf(rawData);

	}

	/**
	 * 存储一行记录到bv表中。
	 * 
	 * @param conn
	 * @param bv
	 * @param bvd
	 * @throws SQLException
	 */
	public static void saveToMysql(Connection conn, String bv, BilibiliVideoData bvd) throws SQLException {
		Statement stm = conn.createStatement();
		// 创建表不需要单独创建，在每次存数据的时候，都会查看是否有这个bv号对应的表
		// 如果没有这个表，就会创建, mysql代码如下:
		stm.execute("CREATE TABLE IF NOT EXISTS `" + bv + "`(\r\n" + "   `id` INT UNSIGNED AUTO_INCREMENT,\r\n"
				+ "   `dianzan` INT UNSIGNED NOT NULL,\r\n" + "   `toubi` INT UNSIGNED NOT NULL,\r\n"
				+ "   `shoucang` INT UNSIGNED NOT NULL,\r\n" + "   `bofang` INT UNSIGNED NOT NULL,\r\n"
				+ "   `danmu` INT UNSIGNED NOT NULL,\r\n" + "   `caijishijian` varchar(30) NOT NULL,\r\n"
				+ "   `viewing_p1` INT  NOT NULL,\r\n" + "   `viewing_p2` INT  NOT NULL,\r\n"
				+ "   `viewing_p3` INT  NOT NULL,\r\n" + "   `viewing_p4` INT  NOT NULL,\r\n"
				+ "   `viewing_p5` INT  NOT NULL,\r\n" + "   PRIMARY KEY ( `id` )\r\n" + ") DEFAULT CHARSET=utf8;");
		int dianzan = bvd.getDianzan();
		int toubi = bvd.getToubi();
		int shoucang = bvd.getShoucang();
		int bofang = bvd.getBofangliang();
		int danmu = bvd.getDanmu();
		MyDate caijishijian = bvd.getCaijishijian();
		int viewing_p1 = bvd.getViewing_p1();
		int viewing_p2 = bvd.getViewing_p2();
		int viewing_p3 = bvd.getViewing_p3();
		int viewing_p4 = bvd.getViewing_p4();
		int viewing_p5 = bvd.getViewing_p5();
		// 到这里才开始存数据。
		stm.execute("INSERT INTO " + bv
				+ " (`dianzan`,`toubi`,`shoucang`,`bofang`,`danmu`,`caijishijian`,`viewing_p1`,`viewing_p2`,`viewing_p3`,`viewing_p4`, `viewing_p5`)\r\n"
				+ "                       VALUES\r\n" + "                       (" + dianzan + "," + toubi + ","
				+ shoucang + "," + bofang + "," + danmu + ",'" + myDateToStr(caijishijian) + "'," + viewing_p1 + ","
				+ viewing_p2 + "," + viewing_p3 + "," + viewing_p4 + "," + viewing_p5 + " );");
	}

	/**
	 * 创建一个mysql数据库连接。
	 * 
	 * @param account
	 * @param password
	 * @param userDB
	 * @return
	 * @throws Exception
	 */
	public static java.sql.Connection getConn(String account, String password, String userDB) throws Exception {
		/* Create MySQL Database Connection */
		Class.forName("com.mysql.jdbc.Driver");

		java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + userDB, account,
				password);
		return connect;
	}

	/**
	 * 给定表名和列名，把列中的所有数据按顺序经转换放入ArrayList<Double>的对象并返回。
	 * 
	 * @param conn       mysql连接对象
	 * @param tableName  表名
	 * @param ColumnName 列名
	 * @return 以double为元素的ArrayList
	 * @throws SQLException
	 */
	public static ArrayList<Double> getValArr(java.sql.Connection conn, String tableName, String ColumnName)
			throws SQLException, NumberFormatException {
		Statement stm = conn.createStatement();
		ResultSet res = stm.executeQuery("select * From " + tableName);
		ArrayList<Double> doubleArr = new ArrayList<Double>();
		while (res.next()) {
			String strValue = res.getString(ColumnName);
			// 接下来尝试把得到的str进行类型转换
			double value = Double.valueOf(strValue);
			doubleArr.add(value);
		}
		return doubleArr;
	}

	/**
	 * 给定表名和列名，把列中的所有数据按顺序经转换放入ArrayList<MyDate>的对象并返回。
	 * 
	 * @param conn       mysql连接对象
	 * @param tableName  表名
	 * @param ColumnName 列名
	 * @return 以MyDate为元素的ArrayList
	 * @throws SQLException
	 */

	public static ArrayList<MyDate> getDateArr(java.sql.Connection conn, String tableName, String ColumnName)
			throws SQLException, NumberFormatException {
		Statement stm = conn.createStatement();
		ResultSet res = stm.executeQuery("select * From " + tableName);
		ArrayList<MyDate> dateArr = new ArrayList<MyDate>();
		while (res.next()) {

			String strValue = res.getString(ColumnName);
			// 接下来尝试把得到的str进行类型转换
			MyDate value = StrToMyDate(strValue);
			dateArr.add(value);
		}
		return dateArr;

	}

	/**
	 * 因为MyDate的自己定义的类，他在数据库保存的方式，以及从数据库读取的方式都需要自己定义 不采用序列化,因为序列化保存对数据库管理员不友好。
	 * 为什么这个方法不定义在MyDate类里呢,因为这个MyDate和String的转换只是为数据库存储而服务的。
	 */
	private static MyDate StrToMyDate(String str) throws NumberFormatException {
		String[] sarr = str.split("_");
		int year_ = Integer.valueOf(sarr[0]);
		int month_ = Integer.valueOf(sarr[1]);
		int date_ = Integer.valueOf(sarr[2]);
		int hour_ = Integer.valueOf(sarr[3]);
		int min_ = Integer.valueOf(sarr[4]);
		int sec_ = Integer.valueOf(sarr[5]);
		return new MyDate(year_, month_, date_, hour_, min_, sec_);
	}

	/**
	 * MyDate转String
	 */
	private static String myDateToStr(MyDate md) {

		return md.getYear() + "_" + md.getMonth() + "_" + md.getDate() + "_" + md.getHours() + "_" + md.getMinutes()
				+ "_" + md.getSeconds();
	}
}
