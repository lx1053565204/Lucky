package xfl.fk.sqldao;

/**
 * 日志管理类
 * 更具配置信息打印或不打印sql信息
 * @author fk-7075
 *
 */
public class LogInfo {
	
	public void isShowLog(String sql, Object[] obj) {
		if("true".equals(LuckyConfig.getConfig().nameToValue("Log")))
			log(sql,obj);
	}
	
	@Deprecated
	public void isShowLog(String object) {
		if("true".equals(LuckyConfig.getConfig().nameToValue("Log")))
			System.out.println("T-Obj: "+object);
	}
	
	
	private void log(String sql, Object[] obj) {
		System.out.println("SQL:   " + sql);
		if (obj == null)
			System.out.println("Object[]: { }");
		else {
			System.out.print("Object[]: {");
			for (Object o : obj) {
				System.out.print(o + "   ");
			}
			System.out.println("}");
		}
	}
}
