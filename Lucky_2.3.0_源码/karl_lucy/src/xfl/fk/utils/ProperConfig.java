package xfl.fk.utils;

import xfl.fk.sqldao.LuckyConfig;

/**
 * lucky.properties内容的包装类
 * @author fk-7075
 *
 */
public class ProperConfig {
	private String driver;//jdbc.driver
	private String url;//jdbc.url
	private String username;//jdbc.username
	private String password;//jdbc.password
	private boolean log;//控制台信息
	private boolean cache;//查询缓存
	private Integer fieldlength;//建表时字段的长度
	private String packages;//逆向工程创建的类的所在的包
	private String srcPath;//逆向工程创建类时需要知道的src文件夹的绝对路径
	private Integer poolmin;//连接池最小链接数量
	private Integer poolmax;//连接池最大链接数量
	private static ProperConfig prop;
	

	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public Integer getPoolmin() {
		return poolmin;
	}
	public void setPoolmin(Integer poolmin) {
		this.poolmin = poolmin;
	}
	public Integer getPoolmax() {
		return poolmax;
	}
	public void setPoolmax(Integer poolmax) {
		this.poolmax = poolmax;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLog() {
		return log;
	}
	public void setLog(boolean log) {
		this.log = log;
	}
	public boolean isCache() {
		return cache;
	}
	public void setCache(boolean cache) {
		this.cache = cache;
	}
	public Integer getFieldlength() {
		return fieldlength;
	}
	public void setFieldlength(Integer fieldlength) {
		this.fieldlength = fieldlength;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	private ProperConfig() {
		LuckyConfig lucy=LuckyConfig.getConfig();
		this.driver=lucy.nameToValue("jdbc.driver");
		this.url=lucy.nameToValue("jdbc.url");
		this.username=lucy.nameToValue("jdbc.username");
		this.password=lucy.nameToValue("jdbc.password");
		if("true".equals(lucy.nameToValue("Log")))
			this.log=true;
		else
			this.log=false;
		if("true".equals(lucy.nameToValue("Cache")))
			this.cache=true;
		else
			this.cache=false;
		this.fieldlength=Integer.parseInt(lucy.fieldLength("FieldLength"));
		if("err".equals(lucy.nameToValue("Package")))
			this.packages=null;
		else
			this.packages=lucy.nameToValue("Package");
		if("err".equals(lucy.nameToValue("SrcPath")))
			this.srcPath=null;
		else
			this.srcPath=lucy.nameToValue("SrcPath");
		if("err".equals(lucy.nameToValue("Pool_Min")))
			this.poolmin=10;
		else
			this.poolmin=Integer.parseInt(lucy.nameToValue("Pool_Min"));
		if("err".equals(lucy.nameToValue("Pool_Max")))
			this.poolmax=100;
		else	
			this.poolmax=Integer.parseInt(lucy.nameToValue("Pool_Max"));
	}
	public static ProperConfig getPropCfg() {
		if(prop==null)
			prop=new ProperConfig();
		return prop;
	}
}
