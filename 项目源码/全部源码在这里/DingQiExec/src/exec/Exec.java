package exec;


import java.util.ArrayList;
import java.util.Scanner;

import mavenTest2.*;
public class Exec {
/**
 * ÿ��һ��ʱ�䣬ִ�����main����,�������ݵ���ȡ���Լ����ݵı��档
 * ����ͬĿ¼���½�һ��ap.txt,����д���У���һ����mysql�û������ڶ�����mysql����,�����������ݿ���
*������ͬĿ¼���½�һ��bvids.txt,����д��Ҫͳ�Ƶ�bvids.
*���ݿ�����web,�벻Ҫ���ġ�
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
				String[] res1=MyUtilForBilibili.getVideoData(bvid);//���˵�ǰ�ۿ�����֮��ġ�
				
				ArrayList<String> cids=MyUtilForBilibili.getCidsbyBvid(bvid);
				int p=cids.size();
				if(p>5)p=5;
				int[] res2=MyUtilForBilibili.getViewingNums(bvid, cids, p);//��ǰ�ۿ�������(ʵ������Ҳ�Ҳ�������һ��ġ�)
				
				BilibiliVideoData bvd=SqlUtil.RawDataToBVD(res1[2], res1[3], res1[4], res1[0], res1[1], MyDate.getCurrentDate(), String.valueOf(res2[0]), String.valueOf(res2[1]), String.valueOf(res2[2]), String.valueOf(res2[3]), String.valueOf(res2[4]), p);
				//������������Ѿ��������ݽ����˴����õ���bvd���͵����ݡ�
				SqlUtil.saveToMysql(Conn, bvid, bvd);
				Thread.sleep(5000);	//ÿ5�����һ����Ƶ������ȡ
		}
	
		
//		ArrayList<MyDate> dateArr=SqlUtil.getDateArr(Conn, "BV1pv411n7WS", "caijishijian");
//		ArrayList<Double> dataArr=SqlUtil.getValArr(Conn, "BV1pv411n7WS", "dianzan");
//		JFreeChartUtil.JFreeChart_(dateArr, dataArr, "date","������", "����", "ͼƬ.jpg");
	
	}
	
}
