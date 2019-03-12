package xfl.fk.sqldao;

import xfl.fk.annotation.AnnotationCgf;

@SuppressWarnings("all")
public class PropAnnotCombination {
	private AnnotationCgf ann=AnnotationCgf.getAnnCfg();
	private LuckyConfig lucy=LuckyConfig.getConfig();
	
	/**
	  *  获得class对应表的主键名（如果配置文件和注解中都没有配置返回null）
	 * @param clzz
	 * @return
	 */
	public String tableID(Class clzz) {
		if(ann.getId(clzz)!=null)
			return ann.getId(clzz);
		else if(!("err".equals(lucy.nameToValueId(ann.getTableName(clzz)))))
			return lucy.nameToValueId(ann.getTableName(clzz));
		else
			return null;
		
	}

}
