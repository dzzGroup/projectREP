package mavenTest2;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class JFreeChartUtil {
	private final static int quyangshu=15;
	/**
	 * 先对x,y的数据进行等间隔取样。取样数为quyangshu个
	 * @param dateArr
	 * @param dataArr
	 * @param xlabel
	 * @param ylabel
	 * @param title
	 * @param savePath
	 * @throws Exception
	 */
	public static void JFreeChart_(ArrayList<MyDate> dateArr,ArrayList<Double> dataArr,String xlabel,String ylabel,String title,String savePath) throws Exception {
		int xSize=dateArr.size();
		int ySize=dataArr.size();
		if(xSize!=ySize) {
			throw new Exception("xy长度不符");
			
		}
		/**
		 * 对x进行等间隔取样
		 */
		ArrayList<MyDate> newDateArr=new ArrayList<MyDate>();
		if(xSize>=quyangshu) {
			int dx=xSize/quyangshu;
			for(int i=0;i<xSize;i=i+dx) {
				newDateArr.add(dateArr.get(i));
			}
		
		}else {
			newDateArr=dateArr;
		}
		/**
		 * 对y进行等间隔取样
		 */
		ArrayList<Double> newDataArr=new ArrayList<Double>();
		if(ySize>=quyangshu) {
			int dy=ySize/quyangshu;
			for(int i=0;i<ySize;i=i+dy) {
				newDataArr.add(dataArr.get(i));
			}
		
		}else {
			newDataArr=dataArr;
		}
		JFreeChartTest(newDateArr, newDataArr, xlabel, ylabel, title, savePath);
	}
/**
 * 这个方法不能被外界直接调用，因为他的x,y没有个数限制。
 * 写的一个调用JFreeChart包进行绘图的简易方法。
 * 
 * @param dateArr 时间序列(对应x)。一定要是等间隔的时间序列，绘制出来的每个折点也是沿x轴等间隔排列的。
 * @param dataArr 数据序列(对应y)。
 * @param xlabel 	x标
 * @param ylabel	y标
 * @param title		图片标题
 * @param savePath 图片保存路径
 * @throws Exception
 */
	private static void JFreeChartTest(ArrayList<MyDate> dateArr,ArrayList<Double> dataArr,String xlabel,String ylabel,String title,String savePath) throws Exception {
//		ArrayList<Double> dataArr=new ArrayList<Double>();//y值数据集
//		ArrayList<MyDate> dateArr=new ArrayList<MyDate>();//x值(时间)数据集
		int xlen=dateArr.size();
		int ylen=dataArr.size();
		int len=xlen>ylen?ylen:xlen;//最终绘制点个数以x,y最小长度为准
		DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
		for(int i=0;i<len;++i) {
			line_chart_dataset.addValue(dataArr.get(i),ylabel,dateArr.get(i));
			
		}
	
		
		
		//创建LineChartObject,准备写入文件。
		JFreeChart lineChartObject = ChartFactory.createLineChart(title, xlabel,ylabel, line_chart_dataset,
				PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot catplot = lineChartObject.getCategoryPlot();
		Font f=new Font("宋体", Font.PLAIN, 20);
		NumberAxis na=(NumberAxis) catplot.getRangeAxis();
		na.setLabelFont(f);
//		na.setfon
		na.setAutoRangeIncludesZero(false);
		catplot.setRangeAxis(na);
		lineChartObject.getTitle().setFont(f);
		lineChartObject.getLegend().setItemFont(f);
		int width = 300+150*len; /* Width of the image 与采样点个数有关系*/
		int height = 500; /* Height of the image */
		
		File lineChart = new File(savePath);//不进行路径检测，如果不存在相应目录，则会报错。
		
		ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);// 原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/jfreechart/jfreechart_line_chart.html

	}
}
