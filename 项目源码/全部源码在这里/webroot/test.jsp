

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
<body>




	<%
	request.setCharacterEncoding("UTF-8");
	String keyWord = request.getParameter("search_key_word");
	String page_num = request.getParameter("page_num");
	boolean formatable = true;
	int page_Num = 1;
	try {
		page_Num = Integer.valueOf(page_num);
	} catch (NumberFormatException e) {
		//不能转换就默认是1
		formatable = false;
	}
	String searchText = null;
	if (keyWord == null || keyWord.isEmpty()) {
		searchText = "";
	} else {
		searchText = keyWord;
	}
	%><!-- 搜索栏 -->
	<div class="container">

		<form action="" class="parent">

			<input value="<%=searchText%>" type="text" id="1" class="search"
				placeholder="搜索"
				onkeydown="if(event.keyCode==13) {toUrl('test.jsp?search_key_word='+$('#1').val());}">


		</form>

	</div>
	<%
	if (keyWord != null && !keyWord.isEmpty()) {

		Document doc = MyUtilForBilibili.getDocByKeyWord(keyWord);
		int totalPageNum = MyUtilForBilibili.getPage(doc);
	%>
	<button
		onclick="window.location.href='http://152.136.107.79/test.jsp?search_key_word=<%=keyWord%>&page_num=<%=(1)%>'"
		style='display: inline-block; font: italic bold 20px/30px arial, sans-serif;'>第一页</button>
	<button
		onclick="window.location.href='http://152.136.107.79/test.jsp?search_key_word=<%=keyWord%>&page_num=<%=(page_Num - 1 < 1 ? 1 : page_Num - 1)%>'"
		style='display: inline-block; font: italic bold 20px/30px arial, sans-serif;'>上一页</button>
	<span>当前页数<%=page_Num%>/总页数<%=totalPageNum%></span>
	<button
		onclick="window.location.href='http://152.136.107.79/test.jsp?search_key_word=<%=keyWord%>&page_num=<%=(page_Num + 1 > totalPageNum ? totalPageNum : page_Num + 1)%>'"
		style='display: inline-block; font: italic bold 20px/30px arial, sans-serif;'>下一页</button>
	<button
		onclick="window.location.href='http://152.136.107.79/test.jsp?search_key_word=<%=keyWord%>&page_num=<%=(totalPageNum)%>'"
		style='display: inline-block; font: italic bold 20px/30px arial, sans-serif;'>最后一页</button>

	<%
	if (page_num == null || formatable == false) {//如果没有输入page_num参数，或page_num不可转换,就是普通(无页数)的搜索功能。

		ArrayList<VideoInfo> viarr = MyUtilForBilibili.getVideoInfos(doc);//获取页面视频信息
		if (totalPageNum == 0) {
			out.print(" 很抱歉,没有搜索结果 ");
		} else {
			for (int i = 0; i < viarr.size(); ++i) {
	%>
	<div style='margin: 50px;'>
		<div style='display: inline-block;'>
			标题:<%=viarr.get(i).getTitle()%></div>
		<div style='display: inline-block;'>
			发布时间:<%=viarr.get(i).getTime()%></div>
		<div style='display: inline-block;'>
			up主:<%=viarr.get(i).getUp_name()%></div>
		<div style='display: inline-block;'>
			播放量:<%=viarr.get(i).getWatch_num()%></div>
		<div style='display: inline-block;'>
			弹幕数:<%=viarr.get(i).getDanmu_num()%></div>
		<div style='display: inline-block;'>
			总时长:<%=viarr.get(i).getDuration()%></div>

		<button onclick="window.open($('#a<%=i%>').attr('href'))"
			style='display: inline-block; font: italic bold 20px/30px arial, sans-serif;'>在线播放</button>
		<button
			onclick="window.open('http://152.136.107.79/test2.jsp?bvurl='+$('#a<%=i%>').attr('href'))"
			style='display: inline-block; font: italic bold 20px/30px arial, sans-serif;'>视频下载</button>

		<%
		String bvurl = viarr.get(i).getBv_url();
		bvurl = bvurl.split("\\?", 2)[0];
		%>
		<a style='display: inline-block' id='a<%=i%>' href="<%=bvurl%>"
			target="_blank"></a>
	</div>
	<%
	}
	}

	} else {//如果输入了page_num参数，

	//int pageNum = Integer.valueOf(page_num);//把String类型的page_num转成Int类型的pageNum
	Document doc2 = MyUtilForBilibili.getDocByKeyWordAndPageNum(keyWord, page_Num);
	ArrayList<VideoInfo> viarr = MyUtilForBilibili.getVideoInfos(doc2);
	//TODO3 也是一个一个从上到下显示。
	for (int i = 0; i < viarr.size(); ++i) {
	%>
	<div style='margin: 50px;'>
		<div style='display: inline-block;'>
			标题:<%=viarr.get(i).getTitle()%></div>
		<div style='display: inline-block;'>
			发布时间:<%=viarr.get(i).getTime()%></div>
		<div style='display: inline-block;'>
			up主:<%=viarr.get(i).getUp_name()%></div>
		<div style='display: inline-block;'>
			播放量:<%=viarr.get(i).getWatch_num()%></div>
		<div style='display: inline-block;'>
			弹幕数:<%=viarr.get(i).getDanmu_num()%></div>

		<button onclick="window.open($('#a<%=i%>').attr('href'))"
			style='display: inline-block; font: italic bold 20px/30px arial, sans-serif;'>在线播放</button>
		<button
			onclick="window.open('http://152.136.107.79/test2.jsp?bvurl='+$('#a<%=i%>').attr('href'))"
			style='display: inline-block; font: italic bold 20px/30px arial, sans-serif;'>视频下载</button>

		<%
		String bvurl = viarr.get(i).getBv_url();
		bvurl = bvurl.split("\\?", 2)[0];
		%>
		<a style='display: inline-block' id='a<%=i%>' href="<%=bvurl%>"
			target="_blank"></a>
	</div>
	<%
	}

	}

	} else {//这就是刚进来的页面

	}
	%>



</body>
</html>