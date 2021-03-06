package xfl.fk.table;

import java.util.ArrayList;
import java.util.List;

import xfl.fk.sqldao.LuckyConfig;
import xfl.fk.utils.LuckyUtils;

/**
 * 得到关于某个属性的源代码（属性声明，get/set方法的源码）
 * @author fk-7075
 *
 */
public class JavaFieldGetSet {
	private String field;
	private String getField;
	private String setField;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getGetField() {
		return getField;
	}
	public void setGetField(String getField) {
		this.getField = getField;
	}
	public String getSetField() {
		return setField;
	}
	public void setSetField(String setField) {
		this.setField = setField;
	}
	public void to_String() {
		System.out.println(field);
		System.out.println(getField);
		System.out.println(setField);
	}
	
	public JavaFieldGetSet(String field,String type) {
		this.field="\tprivate "+type+" "+field+";\n";
		this.getField="\tpublic "+type+" get"+LuckyUtils.TableToClass(field)+"(){\n\t\treturn this."+field+";\n\t}";
		this.setField="\tpublic void set"+LuckyUtils.TableToClass(field)+"("+type+" "+field+"){\n\t\tthis."+field+"="+field+";\n\t}";
	}
	
	/**
	 * 给出TableStructure对应表的类的源代码
	 * @param ts TableStructure对象
	 * @return 对应表的java源代码
	 */
	public static GetJavaSrc getOneJavaSrc(TableStructure ts){
		GetJavaSrc javasrc=new GetJavaSrc();
		List<JavaFieldGetSet> list=new ArrayList<JavaFieldGetSet>();
		javasrc.setClassName(ts.getTableName());
		javasrc.setPack("package "+LuckyConfig.getConfig().nameToValue("Package")+";");
		javasrc.setImpor("import java.util.Date;\nimport java.sql.*;\nimport java.util.*;\nimport xfl.fk.annotation.Lucky;");
		javasrc.setToString(ts.getToString());
		javasrc.setConstructor(ts.getConstructor());
		javasrc.setParameterConstructor(ts.getParameterConstructor());
		String src="@SuppressWarnings(\"all\")\n@Lucky(id=\""+ts.getKey()+"\")\npublic class "+ts.getTableName()+"{\n";
		for(int i=0;i<ts.getFields().size();i++) {
			JavaFieldGetSet jf=new JavaFieldGetSet(ts.getFields().get(i), ts.getTypes().get(i));
			list.add(jf);
		}
		for (JavaFieldGetSet jf : list) {
			src+=jf.getField()+"";
		}
		src+="\n"+ts.getConstructor()+"\n\n"+ts.getParameterConstructor()+"\n";
		for (JavaFieldGetSet jf : list) {
			src+="\n"+jf.getGetField()+"\n";
			src+=jf.getSetField()+"\n";
		}
		javasrc.setJavaSrc(src);
		return javasrc;
	}
	/**
	 * 得到数据库中所有表对应的java源码对象GetJavaSrc的集合
	 * @return
	 */
	public static List<GetJavaSrc> getMoreJavaSrc(){
		List<GetJavaSrc> javasrclist=new ArrayList<GetJavaSrc>();
		List<TableStructure> list=TableStructure.getMoreTableStructure(new Tables());
		for (TableStructure tableStructure : list) {
			GetJavaSrc java=JavaFieldGetSet.getOneJavaSrc(tableStructure);
			javasrclist.add(java);
		}
		return javasrclist;
	}

}
