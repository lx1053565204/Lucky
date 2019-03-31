package xfl.fk.table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import xfl.fk.utils.LuckyManager;

/**
 * �������ݿ��еı�ת��Ϊ��Ӧ��JavaBean
 * @author fk-7075
 *
 */
public class TableToJava {
	/**
	 * ����JavaBean(���÷�ʽurl,�����Խ�������)
	 */
	public static void generateJavaSrc() {
		List<GetJavaSrc> src=JavaFieldGetSet.getMoreJavaSrc();
		String srcpath=LuckyManager.getPropCfg().getSrcPath();
		String packpath=LuckyManager.getPropCfg().getPackages().replaceAll("\\.", "/");
		File file=new File(srcpath+"/"+packpath);
		if(!file.exists())
			file.mkdirs();
		BufferedWriter bw=null;
		for (GetJavaSrc getJavaSrc : src) {
			try {
			bw=new BufferedWriter(new FileWriter(new File(srcpath+"/"+packpath+"/"+getJavaSrc.getClassName()+".java")));
			System.out.println(srcpath+"/"+packpath+"/"+getJavaSrc.getClassName()+".java");
				bw.write(getJavaSrc.getPack());
				bw.write("\n");
				bw.write("\n");
				bw.write(getJavaSrc.getImpor());
				bw.write("\n");
				bw.write("\n");
				bw.write(getJavaSrc.getJavaSrc());
				bw.write(getJavaSrc.getToString());
				bw.close();
			} catch (IOException e) {
				System.err.println("xflfk:���򹤳�����JavaBeanʧ�ܣ�������ص����ã�\"SrcPath\" And \"Package\"!");
				e.printStackTrace();
			}
		}
	}
	/**
	 * ����JavaBean(�ֶ�����url,���Խ�������)
	 * @param srcPath src�ļ��еľ���·��
	 */
	public static void generateJavaSrc(String srcPath) {
		List<GetJavaSrc> src=JavaFieldGetSet.getMoreJavaSrc();
		String packpath=LuckyManager.getPropCfg().getPackages().replaceAll("\\.", "/");
		String url=srcPath+"/"+packpath;
		File file=new File(url);
		if(!file.exists())
			file.mkdirs();
		BufferedWriter bw=null;
		for (GetJavaSrc getJavaSrc : src) {
			try {
				bw=new BufferedWriter(new FileWriter(new File(url+"/"+getJavaSrc.getClassName()+".java")));
				System.out.println(url+"/"+getJavaSrc.getClassName()+".java");
				bw.write(getJavaSrc.getPack());
				bw.write("\n");
				bw.write("\n");
				bw.write(getJavaSrc.getImpor());
				bw.write("\n");
				bw.write("\n");
				bw.write(getJavaSrc.getJavaSrc());
				bw.write(getJavaSrc.getToString());
				bw.close();
			} catch (IOException e) {
				System.err.println("xflfk:���򹤳�����JavaBeanʧ�ܣ�������ص����ã�\"Package\"!");
				e.printStackTrace();
			}
		}
	}
}
