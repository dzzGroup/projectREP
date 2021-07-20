package mavenTest2;
/**
 * 存储着一个视频的基本信息
 * @author Administrator
 *
 */
public class VideoInfo {

private String title=null;
private String watch_num;//播放量
private String danmu_num;//弹幕个数
private String time;//发布时间
private String up_name;//up主名字
private String	duration;//视频时长
private String bv_url;//bv类型的地址

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getWatch_num() {
	return watch_num;
}

public void setWatch_num(String watch_num) {
	this.watch_num = watch_num;
}

public String getDanmu_num() {
	return danmu_num;
}

public void setDanmu_num(String danmu_num) {
	this.danmu_num = danmu_num;
}

public String getTime() {
	return time;
}

public void setTime(String time) {
	this.time = time;
}

public String getUp_name() {
	return up_name;
}

public void setUp_name(String up_name) {
	this.up_name = up_name;
}

public String getDuration() {
	return duration;
}

public void setDuration(String duration) {
	this.duration = duration;
}

public String getBv_url() {
	return bv_url;
}

public void setBv_url(String bv_url) {
	this.bv_url = bv_url;
}

public VideoInfo(String title, String watch_num, String danmu_num, String time, String up_name, String duration,
		String bv_url) {
	super();
	this.title = title;
	this.watch_num = watch_num;
	this.danmu_num = danmu_num;
	this.time = time;
	this.up_name = up_name;
	this.duration = duration;
	this.bv_url = bv_url;
}

}
