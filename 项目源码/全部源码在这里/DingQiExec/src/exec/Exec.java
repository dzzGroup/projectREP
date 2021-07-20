package exec;


import java.util.ArrayList;
import java.util.Scanner;

import mavenTest2.*;
public class Exec {
/**
 * 每隔一段时间，执行这个main方法,进行数据的爬取，以及数据的保存。
 * 请在同目录下新建一个ap.txt,里面写两行，第一行是mysql用户名，第二行是mysql密码,第三行是数据库名
*还请在同目录下新建一个bvids.txt,里面写你要统计的bvids.
*数据库名是web,请不要更改。
 * @param args
 * @throws Exception 
 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	exec();
	}
	
	private static void exec() throws Exception {
		ArrayList<String> aparr=new ArrayList<String>();
		ArrayList<String> bvidarr=new ArrayList<String>();
		aparr=MyUtilForBilibili.getStrArrInTxt("ap.txt");
		bvidarr=MyUtilForBilibili.getStrArrInTxt("bvids.txt");
		java.sql.Connection Conn=SqlUtil.getConn(aparr.get(0),aparr.get(1), "web");
		for(String bvid:bvidarr) {
				String[] res1=MyUtilForBilibili.getVideoData(bvid);//除了当前观看人数之外的。
				
				ArrayList<String> cids=MyUtilForBilibili.getCidsbyBvid(bvid);
				int p=cids.size();
				if(p>5)p=5;
				int[] res2=MyUtilForBilibili.getViewingNums(bvid, cids, p);//当前观看人数。(实际上你也找不出超过一万的。)
				
				BilibiliVideoData bvd=SqlUtil.RawDataToBVD(res1[2], res1[3], res1[4], res1[0], res1[1], MyDate.getCurrentDate(), String.valueOf(res2[0]), String.valueOf(res2[1]), String.valueOf(res2[2]), String.valueOf(res2[3]), String.valueOf(res2[4]), p);
				//上面这个方法已经把生数据进行了处理，得到了bvd类型的数据。
				SqlUtil.saveToMysql(Conn, bvid, bvd);
				Thread.sleep(5000);	//每5秒对下一条视频进行爬取
		}
	
		
//		ArrayList<MyDate> dateArr=SqlUtil.getDateArr(Conn, "BV1pv411n7WS", "caijishijian");
//		ArrayList<Double> dataArr=SqlUtil.getValArr(Conn, "BV1pv411n7WS", "dianzan");
//		JFreeChartUtil.JFreeChart_(dateArr, dataArr, "date","点赞数", "标题", "图片.jpg");
	
	}
	
}
