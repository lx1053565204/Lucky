package xfl.fk.sqldao;

import java.util.ResourceBundle;

import xfl.fk.table.TableNum;

/**
 * 读取配置文件的类(DL)
 * 
 * @author fk-7075
 *
 */
public class LuckyConfig {

	private ResourceBundle rb = ResourceBundle.getBundle("lucky");
	private static LuckyConfig cfg = null;

	private LuckyConfig() {
	};

	public static LuckyConfig getConfig() {
		if (cfg == null)
			cfg = new LuckyConfig();
		return cfg;
	}

	/**
	 * 读取配置文件中的ID配置信息
	 * @param name
	 * @return
	 */
	public String nameToValueId(String name) {
		try {
			String value = rb.getString(name + ".id");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return "err";
		}
	}

	/**
	 * 读取配置文件中的普通的配置信息
	 * @param name
	 * @return
	 */
	public String nameToValue(String name) {
		try {
			String value = rb.getString(name);
			return value;
		} catch (Exception e) {
			return "err";
		}
	}
	/**
	 * 读取配置文件中关于表字段长度的配置信息
	 * @param name
	 * @return
	 */
	public String fieldLength(String name) {
		try {
			return rb.getString(name);
		}catch(Exception e){
			return "35";
		}
	}
	/**
	 * 返回数据由库名和其中表的张数所组成的对象
	 * @return
	 */
	public TableNum getTableNum() {
		SqlControl sq = new SqlControl();
		TableNum tn = new TableNum();
		String sql = nameToValue("jdbc.url");
		int state = sql.lastIndexOf("/")+1;
		int end = sql.length();
		tn = (TableNum) sq.getList(TableNum.class,
				"SELECT COUNT(*) tables, table_schema FROM information_schema.TABLES   WHERE table_schema =?;",
				sql.substring(state, end)).get(0);
		tn.setTable_schema(sql.substring(state, end));
		return tn;
	}

}
