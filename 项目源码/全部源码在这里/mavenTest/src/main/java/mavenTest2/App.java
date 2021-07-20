package mavenTest2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.awt.Font;
import java.io.*;
import java.util.Date;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;

import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class App {
	private static String videoSavePath = "D:\\视频\\";
	private static String savePath = "D:\\图片保存\\";
	private static String headerPath = "D:\\视频\\headers.txt";

	public static void main(String[] args) throws Exception {
//		test3("你好");

//System.out.println("你好".getBytes().length);
//		JFreeChartTest();
		test2();
	}
	public static void test2() {
		ArrayList<String> strarr=MyUtilForBilibili.getCidsbyBvid("BV1Rx411876f");
		for(String s:strarr) {
			System.out.println(s);
		}
		System.out.println(strarr.size());
		
	}
	
	

	public static void test() throws IOException {
//		String ffmpegpath="D:\\ffmpeg\\bin\\ffmpeg";
//Utils.downLoadAVandCombine(ffmpegpath,"https://www.bilibili.com/video/BV1Rx411876f?p=12", videoSavePath, "首页视频测试",true);

//		String bvurl = "https://www.bilibili.com/video/BV1cE411u7RA";
//		ArrayList<String> a = MyUtilForBilibili.getAllPinfoOfVideo(MyUtilForBilibili.getDocByUrl(bvurl));
//		for (String s : a) {
//			System.out.println(a);
//		}
	}

	/**
	 * 搜索功能
	 */
	public static void test3(String keyWord) {
		Document doc = MyUtilForBilibili.getDocByUrlAndHeaders("https://search.bilibili.com/video?keyword=" + keyWord,
				null);

		Elements elements = doc.select("[class=video-item matrix]");

		for (Element e : elements) {
			System.out.println(e.selectFirst("a").attr("title"));// 视频标题
			System.out.println(e.selectFirst("[class=so-icon watch-num]").text());
			System.out.println(e.selectFirst("[class=so-icon hide]").text());
			System.out.println(e.selectFirst("[class=so-icon time]").text());
			System.out.println(e.selectFirst("[class=so-icon]").text());
			System.out.println(e.selectFirst("[class=so-imgTag_rb]").text());// 视频时长
			System.out.println();
//			System.out.println("https:"+e.selectFirst("a").attr("href"));//视频地址
		}

//		System.out.println(getPage(doc));
	}

	/**
	 * 把王者荣耀的所有英雄和图片(小图和大图)
	 * 
	 * @throws IOException
	 */
	public static void test1() throws IOException {
		String url = "https://pvp.qq.com/web201605/herolist.shtml";
		String headUrl = "https://pvp.qq.com/web201605/";// 这是要补在相对路径前面的
		Connection con = Jsoup.connect(url);
		Document doc = con.get();
		Element e = doc.selectFirst("[class=herolist clearfix]");// 第一步，找到class=herolist clearfix的元素

		Elements e2s = e.select("a");// e中有a标签的元素。
		for (Element ele : e2s) {
			Element ele2 = ele.selectFirst("img");
			String heroName = ele.text();// 英雄名称
			MyUtilForBilibili.savePic("https:" + ele2.attr("src"), savePath + heroName + "_S.jpg");// 保存头像图片
			getSubInfo(headUrl + ele.attr("href"), heroName);// 对每个子链接进行这个方法。
		}
	}

	public static void getSubInfo(String httpUrl, String heroName) throws IOException {
		Connection con = Jsoup.connect(httpUrl);
		Document doc = con.get();
		Element element1 = doc.selectFirst("[class=zk-con1 zk-con]");// 大图的链接所在的元素。
		String attrStyle = element1.attr("style");// 大图的链接是在元素的style属性里。

		int startIndex = attrStyle.indexOf("'//") + 3;// style属性并不完全是大图链接，左右有一些不需要的东西
		int endIndex = attrStyle.lastIndexOf("'");
		String bigPicUrl = "https://" + attrStyle.substring(startIndex, endIndex);// 截取到大图链接。
		// 开始下载并保存
		MyUtilForBilibili.savePic(bigPicUrl, savePath + heroName + "_L.jpg");
		// System.out.println("https://"+latterUrl);//这是大图路径

		Elements paragraphs = doc.selectFirst("[class=pop-bd]").select("p");
		String story = "";// 保存英雄的故事。
		for (Element pe : paragraphs) {
			// 一个英雄的故事分为多段。
			// 每次循环对应一段。pe.text()就是一段话的内容
			story += ("\t" + pe.text() + "\r\n");// 一段对应一个tab和一个换行
		}
		MyUtilForBilibili.saveString(story, savePath + heroName + "_Story.txt");

	}

	public static void downWithHeaders(String httpUrl, String title, Map<String, String> map) {
		Connection con = Jsoup.connect(httpUrl);
		Document doc = null;
		con.headers(map);
		con.maxBodySize(20000000);// 20MB
		con.ignoreContentType(true);
		try {
			doc = con.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//			System.out.println(doc.html().substring(0,100));
		try {
			FileOutputStream fos = new FileOutputStream(videoSavePath + title + ".mp4");
			fos.write(doc.text().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<String> getUrlsFromBiliBiliUrl(String bilibiliurl) {
		ArrayList<String> sarr = new ArrayList<String>();

		Connection con = Jsoup.connect(bilibiliurl);
		con.header("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3314.0 Safari/537.36 SE 2.X MetaSr 1.0");
		Document doc = null;

		try {
			doc = con.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(doc.html());
		Elements es = doc.getElementsByTag("script");
//	String s=doc.html();
//	s.
//	System.out.println(s);
		String jsstr = es.get(4).html().replace("window.__playinfo__=", "");
		JSONObject jsonObject = JSONObject.parseObject(jsstr);
		System.out.println(jsonObject.toJSONString());
		String a = jsonObject.getJSONObject("data").getString("url");
		System.out.println(a);
		return null;
	}

	/**
	 * 方法描述: 下载无水印视频方法
	 *
	 * @param httpUrl
	 * @param title
	 * @author tarzan
	 * @date 2020年08月04日 10:34:09
	 */
	public static void downVideo(String httpUrl, String title) {
		String fileAddress = videoSavePath + title + ".ts";
		int byteRead;
		try {
			URL url = new URL(httpUrl);

			// 获取链接
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("Referer", "https://www.bilibili.com/");

			// 输入流
			InputStream inStream = conn.getInputStream();
			// 封装一个保存文件的路径对象
			File fileSavePath = new File(fileAddress);
			// 注:如果保存文件夹不存在,那么则创建该文件夹
			File fileParent = fileSavePath.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			// 写入文件
			FileOutputStream fs = new FileOutputStream(fileSavePath);
			byte[] buffer = new byte[1024];
			while ((byteRead = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteRead);
			}
			inStream.close();
			fs.close();
			System.out.println("\n-----视频保存路径-----\n" + fileSavePath.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
//           log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
//           log.error(e.getMessage());
		}
	}

}
