package mavenTest2;
/**
 * 创建这个类的目的有两个:
 * 1.方便时间的加减运算
 * 2.重写toString方法，给JFreeChart当横坐标。
 * @author Administrator
 *
 */
public class MyDate extends java.util.Date{

	/**
	 * constructor method
	 * 注意这里的year是Date所说的year,即从1900到此时的年数
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param min
	 * @param sec
	 */
	@SuppressWarnings("deprecation")
	public MyDate(int year,int month,int date,int hour,int min,int sec) {
		super(year,month,date,hour,min,sec);
	}
	public MyDate() {
		// TODO Auto-generated constructor stub
		super();
	}
	public static MyDate getCurrentDate() {
		return new MyDate();
	}
	public String toString() {
		return getYear()-100+"/"+getMonth()+"/"+getDate()+" "+getHours()+":"+getMinutes();
	}
	
}
