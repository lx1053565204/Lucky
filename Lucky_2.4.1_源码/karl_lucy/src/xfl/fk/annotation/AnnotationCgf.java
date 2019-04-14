package xfl.fk.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ȡע����Ϣ�ࣨDL��
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class AnnotationCgf {
	private static AnnotationCgf cfg = null;

	private AnnotationCgf() {
	};

	public static AnnotationCgf getAnnCfg() {
		if (cfg == null)
			cfg = new AnnotationCgf();
		return cfg;
	}

	/**
	 * �������(id)ע��
	 * @param clzz Ҫ���������Class����
	 * @return ע���ϵ�id
	 */
	public String getId(Class clzz) {
		Annotation annotations = clzz.getAnnotation(Lucky.class);
		if (annotations != null) {
			if ("".equals(((Lucky) annotations).id()))
				return null;
			else
				return ((Lucky) annotations).id();
		} else {
			return null;
		}
	}
	/**
	 * ��ȡ����ע��(���ڱ���ע���򷵻�ע����������򷵻�class����)
	 * @param clzz Ҫ���������Class����
	 * @return ע���ϵı���
	 */
	public String getTableName(Class clzz) {
		Annotation annotations = clzz.getAnnotation(Lucky.class);
		if(annotations!=null) {
			if("".equals(((Lucky) annotations).table()))
				return clzz.getSimpleName();
			else
				return ((Lucky) annotations).table();
		}else {
			return clzz.getSimpleName();
		}
	}

	/**
	 * ������ע��
	 * @param clzz Ҫ���������Class����
	 * @return ע���ϵ��������
	 */
	public String[] getkey(Class clzz) {
		Annotation annotations = clzz.getAnnotation(Lucky.class);
		if (annotations != null) {
			if ("".equals(((Lucky) annotations).key()[0]))
				return null;
			else
				return ((Lucky) annotations).key();
		} else
			return null;
	}

	/**
	 * ��������Ӧ���ȫ·��URLע��
	 * @param clzz Ҫ���������Class����
	 * @return ע���ϵ�url����
	 */
	private String[] geturl(Class clzz) {
		Annotation annotations = clzz.getAnnotation(Lucky.class);
		if (annotations != null) {
			if ("".equals(((Lucky) annotations).url()[0])) {
				return null;
			} else {
				return ((Lucky) annotations).url();
			}
		} else
			return null;
	}

	/**
	 * ��ȡurlע���Ӧ��Class����
	 * @param clzz Ҫ���������Class����
	 * @return �õ�ע��url�����ж�Ӧ�����Class
	 */
	public Class[] getMainTables(Class clzz) {
		String[] url = geturl(clzz);
		Class[] cs = null;
		if (url == null)
			return null;
		else {
			try {
				cs = new Class[url.length];
				for (int i = 0; i < url.length; i++) {
					cs[i] = Class.forName(url[i]);
				}
			} catch (ClassNotFoundException e) {
				System.err.println("xflfk:ע��url�Ҳ���");
				e.printStackTrace();
			}
			return cs;
		}
	}
	/**
	 * ����ע����ϢΪĳ�ű����ü���ɾ�����
	 * @param clzz Ҫ���������Class����
	 * @return �����й����������SQl���
	 */
	public String isCascadeDel(Class clzz) {
		Annotation annotation = clzz.getAnnotation(Cascade.class);
		if (annotation != null) {
			if (((Cascade) annotation).delete())
				return " ON DELETE CASCADE";
			else
				return "";
		} else
			return "";
	}
	/**
	 * ��ע����ϢΪĳ�ű����ü������µ����
	 * @param clzz Ҫ���������Class����
	 * @return �����й����������SQl���
	 */
	public String isCascadeUpd(Class clzz) {
		Annotation annotation = clzz.getAnnotation(Cascade.class);
		if(annotation!=null) {
		if (((Cascade) annotation).update())
			return " ON UPDATE CASCADE";
		else
			return "";
		}else
			return "";
	}
	/**
	 * �����Ƿ��Զ�����
	 * @param clzz
	 * @return
	 */
	public String isAuto(Class clzz) {
		Annotation annotation = clzz.getAnnotation(Lucky.class);
		if(annotation!=null) {
			if(((Lucky) annotation).auto())
				return "AUTO_INCREMENT";
			else
				return "";
		}else
			return "";
	}
}
