

<%@page import="mavenTest2.JFreeChartUtil"%>
<%@page import="mavenTest2.SqlUtil"%>
<%@page import="mavenTest2.MyDate"%>
<%@page import="mavenTest2.VideoInfo"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="mavenTest2.MyUtilForBilibili"%>



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>

<%
String bvid = request.getParameter("bvid");
boolean OK = true;
int p=1;

String picName=null;
if (bvid == null) {
	OK = false;
} else if (!MyUtilForBilibili.getStrArrInTxt("/dzz/web/autoRun/bvids.txt").contains(bvid)) {//不在服务器的统计视频表内。
	OK = false;
} else {
	//BV1pv411n7WS

	
	
	picName = bvid + "_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 100);

	Connection Conn = SqlUtil.getConn("user", "123456", "web");
	ArrayList<MyDate> dateArr = SqlUtil.getDateArr(Conn, bvid, "caijishijian");
	ArrayList<Double> dianzanArr = SqlUtil.getValArr(Conn, bvid, "dianzan");
	ArrayList<Double> toubiArr = SqlUtil.getValArr(Conn, bvid, "toubi");
	ArrayList<Double> shoucangArr = SqlUtil.getValArr(Conn, bvid, "shoucang");
	ArrayList<Double> bofangArr = SqlUtil.getValArr(Conn, bvid, "bofang");
	ArrayList<Double> danmuArr = SqlUtil.getValArr(Conn, bvid, "danmu");
	ArrayList<Double> p1Arr = SqlUtil.getValArr(Conn, bvid, "viewing_p1");
	ArrayList<ArrayList<Double>> val2to5 = new ArrayList<ArrayList<Double>>();//p2~p5
	ArrayList<ArrayList<MyDate>> date2to5 = new ArrayList<ArrayList<MyDate>>();//p2~p5
	SqlUtil.setArrayListFrom2To5(Conn, bvid, date2to5, val2to5);

	JFreeChartUtil.JFreeChart_(dateArr, bofangArr, "date", "播放数", "播放数折线图", "/dzz/web/tomcat/webapps/ROOT/imgs/" + picName + "bofang.jpg");
	JFreeChartUtil.JFreeChart_(dateArr, danmuArr, "date", "弹幕数", "弹幕数折线图", "/dzz/web/tomcat/webapps/ROOT/imgs/" + picName + "danmu.jpg");
	JFreeChartUtil.JFreeChart_(dateArr, dianzanArr, "date", "点赞数", "点赞数折线图", "/dzz/web/tomcat/webapps/ROOT/imgs/" + picName + "dianzan.jpg");
	JFreeChartUtil.JFreeChart_(dateArr, toubiArr, "date", "投币数", "投币数折线图", "/dzz/web/tomcat/webapps/ROOT/imgs/" + picName + "toubi.jpg");
	JFreeChartUtil.JFreeChart_(dateArr, shoucangArr, "date", "收藏数", "收藏数折线图", "/dzz/web/tomcat/webapps/ROOT/imgs/" + picName + "shoucang.jpg");

	JFreeChartUtil.JFreeChart_(dateArr, p1Arr, "date", "p1当时观看人数", "p1观看人数折线图", "/dzz/web/tomcat/webapps/ROOT/imgs/" + picName + "p1.jpg");
	
	for (int i = 2; i <= 5; ++i) {
		if (date2to5.get(i - 2).size() != 0) {
	JFreeChartUtil.JFreeChart_(date2to5.get(i - 2), val2to5.get(i - 2), "date", "p" + i + "当时观看人数",
			"p" + i + "观看人数折线图", "/dzz/web/tomcat/webapps/ROOT/imgs/" + picName + "p" + i+".jpg");
	p++;
		} else
	break;
	}

}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="Css.css" />
<script type="text/javascript" src="myScript.js"></script>
</head>
<body>

<%
if(OK){
%>

<div>
<img src="//152.136.107.79/imgs/<%=picName+"bofang"+".jpg" %>">
</div>
<HR align=center width=300 color=#987cb9 SIZE=1>
<div>
<img src="//152.136.107.79/imgs/<%=picName+"danmu"+".jpg" %>">
</div>
<HR align=center width=300 color=#987cb9 SIZE=1>
<div>
<img src="//152.136.107.79/imgs/<%=picName+"dianzan"+".jpg" %>">
</div>
<HR align=center width=300 color=#987cb9 SIZE=1>
<div>
<img src="//152.136.107.79/imgs/<%=picName+"toubi"+".jpg" %>">
</div>
<HR align=center width=300 color=#987cb9 SIZE=1>
<div>
<img src="//152.136.107.79/imgs/<%=picName+"shoucang"+".jpg" %>">
</div>


<% for(int i=1;i<=p;++i){%>
<HR align=center width=300 color=#987cb9 SIZE=1>
<div>
<img src="//152.136.107.79/imgs/<%=picName+"p"+i+".jpg" %>">
</div>
<% }%>



<%
}
else {
	out.print("data not found.");
}
%>



</body>
</html>