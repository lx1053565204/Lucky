package xfl.fk.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.utils.LuckyUtils;

/**
 * 表到类的信息封装器（类名 属性名 属性类型 toString方法）
 * @author fk-7075
 *
 */
public class TableStructure {
	private String tableName;//表名
	private List<String> fields=new ArrayList<String>();//字段名集
	private List<String> types=new ArrayList<String>();//对应的类型集
	private String key;//主键
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public String toString() {
		return "TableStructure [tableName=" + tableName + ", fields=" + fields + ", types=" + types + ", key=" + key
				+ "]";
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 获得tableName表对应的表的结构
	 * @param tableName 表名
	 */
	public TableStructure(String tableName) {
		this.tableName=LuckyUtils.TableToClass(tableName);
		ResultSet rs=LuckyUtils.getResultSet("DESCRIBE "+tableName);
		try {
			while(rs.next()) {
				this.getFields().add(rs.getString("Field"));
				if("PRI".equalsIgnoreCase(rs.getString("Key")))
					this.setKey(rs.getString("Field"));
				this.getTypes().add(LuckyUtils.mysqlToJava(LuckyUtils.getMySqlType(rs.getString("Type"))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获得数据库中所有表的结构
	 * @param tables Tables对象
	 * @return List<TableStructure>
	 */
	public static List<TableStructure> getMoreTableStructure(Tables tables){
		List<TableStructure> list=new ArrayList<TableStructure>();
		for (String t_name : tables.getTablenames()) {
			TableStructure table=new TableStructure(t_name);
			list.add(table);
		}
		return list;
	}
	/**
	 * 得到该表对应类的toString方法的源代码 
	 * @return
	 */
	public String getToString() {
		String tostr="\n\t@Override\n\tpublic String toString() {\n\t\treturn \""+tableName+" [";
		for(int i=0;i<fields.size();i++) {
			if(i!=fields.size()-1) {
				tostr+=fields.get(i)+"=\" + "+fields.get(i)+" + \", ";
			}else{
				tostr+=fields.get(i)+"=\" + "+fields.get(i)+" + \"]\";\n\t}\n}";
			}
		}
		return tostr;
	}
	/**
	 * 无参构造器的源代码
	 * @return
	 */
	public String getConstructor() {
		String ctsrc="\tpublic "+tableName+"(){}";
		return ctsrc;
	}
	/**
	 * 返回包含该表全部属性的有参构造器的源代码
	 * @return
	 */
	public String getParameterConstructor() {
		String pctsrc="\tpublic "+tableName+"(";
		for(int i=0;i<fields.size();i++) {
			if(i!=fields.size()-1)
				pctsrc+=types.get(i)+" "+fields.get(i)+", ";
			else
				pctsrc+=types.get(i)+" "+fields.get(i)+"){\n";
		}
		for(int i=0;i<fields.size();i++) {
			if(i!=fields.size()-1)
				pctsrc+="\t\tthis."+fields.get(i)+"="+fields.get(i)+";\n";
			else
				pctsrc+="\t\tthis."+fields.get(i)+"="+fields.get(i)+";\n\t}";
		}
		return pctsrc;
	}
}
