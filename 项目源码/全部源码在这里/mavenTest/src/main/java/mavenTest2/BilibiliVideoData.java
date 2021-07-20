package mavenTest2;

public class BilibiliVideoData {
private MyDate caijishijian;
private int dianzan;
private int toubi;
private int shoucang;
private int bofangliang;
private int danmu;
private int viewing_p1;//采集的是网页端观看人数。不包括移动端。
private int viewing_p2;//p2~p5未必存在。不存在置为-1。
private int viewing_p3;
private int viewing_p4;
private int viewing_p5;

public BilibiliVideoData(MyDate caijishijian, int dianzan, int toubi, int shoucang, int bofangliang,int danmu, int viewing_p1,
		int viewing_p2, int viewing_p3, int viewing_p4, int viewing_p5, int p_num) {
	super();
	this.caijishijian = caijishijian;
	this.dianzan = dianzan;
	this.toubi = toubi;
	this.shoucang = shoucang;
	this.bofangliang = bofangliang;
	this.viewing_p1 = viewing_p1;
	this.danmu=danmu;
	this.viewing_p2 = viewing_p2;
	this.viewing_p3 = viewing_p3;
	this.viewing_p4 = viewing_p4;
	this.viewing_p5 = viewing_p5;
	this.p_num = p_num;
}
public int getDanmu() {
	return danmu;
}
public void setDanmu(int danmu) {
	this.danmu = danmu;
}
public MyDate getCaijishijian() {
	return caijishijian;
}
public void setCaijishijian(MyDate caijishijian) {
	this.caijishijian = caijishijian;
}
public int getDianzan() {
	return dianzan;
}
public void setDianzan(int dianzan) {
	this.dianzan = dianzan;
}
public int getToubi() {
	return toubi;
}
public void setToubi(int toubi) {
	this.toubi = toubi;
}
public int getShoucang() {
	return shoucang;
}
public void setShoucang(int shoucang) {
	this.shoucang = shoucang;
}
public int getBofangliang() {
	return bofangliang;
}
public void setBofangliang(int bofangliang) {
	this.bofangliang = bofangliang;
}
public int getViewing_p1() {
	return viewing_p1;
}
public void setViewing_p1(int viewing_p1) {
	this.viewing_p1 = viewing_p1;
}
public int getViewing_p2() {
	return viewing_p2;
}
public void setViewing_p2(int viewing_p2) {
	this.viewing_p2 = viewing_p2;
}
public int getViewing_p3() {
	return viewing_p3;
}
public void setViewing_p3(int viewing_p3) {
	this.viewing_p3 = viewing_p3;
}
public int getViewing_p4() {
	return viewing_p4;
}
public void setViewing_p4(int viewing_p4) {
	this.viewing_p4 = viewing_p4;
}
public int getViewing_p5() {
	return viewing_p5;
}
public void setViewing_p5(int viewing_p5) {
	this.viewing_p5 = viewing_p5;
}
public int getP_num() {
	return p_num;
}
public void setP_num(int p_num) {
	this.p_num = p_num;
}
private int p_num;//1~5 这里最选5个p,我怕数据采集太多,bilibili把我ban了。


}
