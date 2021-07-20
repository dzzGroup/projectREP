

<%@page import="mavenTest2.VideoInfo"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="mavenTest2.MyUtilForBilibili"%>



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="Css.css" />
<script type="text/javascript" src="myScript.js"></script>
</head>
<body >
	<%
	boolean OK = true;
	request.setCharacterEncoding("UTF-8");
	String bvurl = request.getParameter("bvurl");
	String p = request.getParameter("p");
	if(bvurl==null){
		out.print("不好意思，页面没找到。");
		return;
	}
	String ffmpegPath = "/usr/local/ffmpeg/bin/ffmpeg";
	String downloadPath = "/dzz/web/tomcat/webapps/ROOT/downloads/";
	int beginIndex = bvurl.indexOf("BV");
	int endIndex = bvurl.indexOf("?");
	String bvid =null;
	if(endIndex<0){
		bvid=bvurl.substring(beginIndex);
	}else{
		bvid= bvurl.substring(beginIndex, endIndex);
	}

	//int p_num = 0;
	
	//String code=request.getParameter("code");
	Document doc = MyUtilForBilibili.getDocByUrl(bvurl);
	int total_p_num = MyUtilForBilibili.getVideoPageNum(doc);
	if (total_p_num == 1) {
		
		MyUtilForBilibili.downLoadAVandCombineWithPNum(ffmpegPath, bvurl, downloadPath, bvid, false, 1);
		Thread.sleep(1000);
		out.print("<a href=http://152.136.107.79/downloads/"+bvid+".mp4"+" download>视频获取完毕,点我下载</a>");
		
	} else if (p == null) {
		out.print("<p>检测到有视频有多个分p,请选择下载</p>");
		for (int i = 0; i < total_p_num; ++i) {
	%>
	<button
		onclick="window.open('http://152.136.107.79/test2.jsp?bvurl=<%=bvurl%>&p=<%=i + 1%>')"
		style='display: inline-block; font: italic bold 20px/40px arial, sans-serif;border-radius:50%;'><%=i + 1%></button>

	<%
	}
		
	}
	else {
			try {
				int p_num=Integer.valueOf(p);
				MyUtilForBilibili.downLoadAVandCombineWithPNum(ffmpegPath, bvurl, downloadPath, bvid, false, p_num);
				Thread.sleep(1000);

				out.print("<a href=http://152.136.107.79/downloads/"+bvid+".mp4"+" download>视频获取完毕,点我下载</a>");
			}
			catch(NumberFormatException e){
				
			}
		}
	%>







</body>
</html>