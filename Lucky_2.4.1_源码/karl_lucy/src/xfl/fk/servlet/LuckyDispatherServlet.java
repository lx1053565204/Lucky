package xfl.fk.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import xfl.fk.annotation.Autowired;
import xfl.fk.annotation.Controller;
import xfl.fk.annotation.Dao;
import xfl.fk.annotation.Download;
import xfl.fk.annotation.Upload;
import xfl.fk.annotation.RequestMapping;
import xfl.fk.annotation.RequestParam;
import xfl.fk.annotation.Service;
import xfl.fk.utils.LuckyManager;

@MultipartConfig
@SuppressWarnings("all")
public class LuckyDispatherServlet extends HttpServlet {
	private List<String> classNames = new ArrayList<String>();
	private Map<String, Object> beans = new HashMap<String, Object>();
	private Map<String, Object> handerMaps = new HashMap<String, Object>();

	public void init(ServletConfig config) {
		// 1.ɨ��
		String[] pack = LuckyManager.getPropCfg().getScans();
		for (String str : pack) {
			doScan(str);
		}
		// 2.ʵ����
		doInstance();
		// 3.ע�����
		doAutowired();
		// 4.Url��Controller������ӳ���ϵ
		UrlHanding();
		System.out.println("xflfk_ Lucky��ɨ����:" + classNames);
		System.out.println("xflfk_ Lucky�Զ������Ķ���:" + beans);
		System.out.println("xflfk_ Lucky����ע��ɹ���");
		System.out.println("xflfk_ Lucky��֪��urlӳ���ϵ��" + handerMaps);
	}

	private void UrlHanding() {
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Object instance = entry.getValue();
			Class<?> clzz = instance.getClass();
			if (clzz.isAnnotationPresent(Controller.class)) {
				RequestMapping co = clzz.getAnnotation(RequestMapping.class);
				String classPath = co.value();
				Method[] methods = clzz.getDeclaredMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(RequestMapping.class)) {
						RequestMapping rp = method.getAnnotation(RequestMapping.class);
						String methodPath = rp.value();
						handerMaps.put(classPath + methodPath, method);
					} else {
						continue;
					}
				}
			}
		}

	}

	/**
	 * Ϊ��@Autowired��ע������ע�����
	 */
	private void doAutowired() {
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Object instance = entry.getValue();
			Class<?> clzz = instance.getClass();
			Field[] fields = clzz.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Autowired.class)) {
					Autowired auto = field.getAnnotation(Autowired.class);
					Object obj = beans.get(auto.value());
					field.setAccessible(true);
					try {
						field.set(instance, obj);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					continue;
				}
			}
		}

	}

	/**
	 * ʵ����Controller Service Dao �����������iocMap�����н��й��� iocMap<LuckyController,new
	 * LuckyContriller()>
	 */
	private void doInstance() {
		for (String str : classNames) {
			String classStr = str.replace(".class", "");
			try {
				Class<?> clzz = Class.forName(classStr);
				if (clzz.isAnnotationPresent(Controller.class)) {
					Object instanse1 = clzz.newInstance();
					RequestMapping rp = clzz.getAnnotation(RequestMapping.class);
					String key1 = rp.value();
					beans.put(key1, instanse1);
				} else if (clzz.isAnnotationPresent(Service.class)) {
					Object instanse2 = clzz.newInstance();
					String key2 = clzz.getSimpleName();
					beans.put(key2, instanse2);
				} else if (clzz.isAnnotationPresent(Dao.class)) {
					Object instanse3 = clzz.newInstance();
					String key3 = clzz.getSimpleName();
					beans.put(key3, instanse3);
				} else {
					continue;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * ɨ���ض��İ�
	 */
	private void doScan(String basePackage) {
		URL url = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
		File files = new File(url.getFile());
		String[] filesStr = files.list();// ��ø��ļ����µ������ļ����ļ��еľ���·��
		for (String path : filesStr) {// ����
			File file = new File(url.getFile() + path);
			if (file.isDirectory()) {// �жϸ��ļ��Ƿ����ļ���
				doScan(basePackage + "." + path);// ���ļ��������
			} else {
				classNames.add(basePackage + "." + file.getName());// ���ļ����ļ��ľ���·���ӵ�classNames ��
			}
		}

	}

	/**
	 * ע��ʵ���ļ��ϴ�
	 * @param request
	 * @param response
	 * @param method
	 * @return �ϴ�����ļ���
	 * @throws IOException
	 * @throws ServletException
	 */
	private static String upload(HttpServletRequest request,HttpServletResponse response,Method method) throws IOException, ServletException {
		Upload lf=method.getAnnotation(Upload.class);
		String filena=lf.name();
		String savePath=lf.filePath();
        Part part = request.getPart(filena);
        String disposition = part.getHeader("Content-Disposition");
        if(disposition.indexOf(".")!=-1) {
        //����ļ���׺��
        String suffix = disposition.substring(disposition.lastIndexOf("."),disposition.length()-1);
          //���������һ��32���ַ���
        long time= new Date().getTime();
        String filename=time+suffix;
//        String filename = UUID.randomUUID()+suffix;
          //��ȡ�ϴ����ļ���
        InputStream is = part.getInputStream();
        //��̬��ȡ��������·��
        String serverpath = request.getServletContext().getRealPath(savePath);
        File file=new File(serverpath);
        if(!file.isDirectory()) {
        	file.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(serverpath+"/"+filename);
        byte[] bty = new byte[1024];
        int length =0;
        while((length=is.read(bty))!=-1){
            fos.write(bty,0,length);
        }
        fos.close();
        is.close();
		 return filename;
        }else {
        	
        	return null;
        }
        
	}
	/**
	 * �����ļ�����
	 * @param request
	 * @param response
	 * @param method
	 * @throws IOException
	 */
	private static void download(HttpServletRequest request,HttpServletResponse response,Method method) throws IOException {
		Download dl=method.getAnnotation(Download.class);
		String fileName=dl.name();
		String filePath=dl.filePath();
		String file = request.getParameter(fileName); //�ͻ��˴��ݵ���Ҫ���ص��ļ���
	    String path = request.getServletContext().getRealPath(filePath)+file; //Ĭ����Ϊ�ļ��ڵ�ǰ��Ŀ�ĸ�Ŀ¼
	    System.out.println(path);
	    FileInputStream fis = new FileInputStream(path);
	    response.setCharacterEncoding("utf-8");
	    response.setHeader("Content-Disposition", "attachment; filename="+file);
	    ServletOutputStream out = response.getOutputStream();
	    byte[] bt = new byte[1024];
	    int length = 0;
	    while((length=fis.read(bt))!=-1){
	        out.write(bt,0,length);
	    }
	     out.close();
	}
	/**
	 * ����ִ�з����Ĳ�����ù���
	 * @param request
	 * @param response
	 * @param method
	 * @return �����б�
	 * @throws IOException
	 * @throws ServletException
	 */
	private static Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method) throws IOException, ServletException {
		Class<?>[] paramClzzs = method.getParameterTypes();
		boolean islucy=method.isAnnotationPresent(Upload.class);
		Parameter[] parameters = method.getParameters();
		Object[] args = new Object[paramClzzs.length];
		int args_i = 0;
		if (!isExistRequestParam(method)) {
			for (int p = 0; p < paramClzzs.length; p++) {
					args[args_i++] = request.getParameter(parameters[p].getName());
			}
		} else {
			Annotation[][] paramAns = method.getParameterAnnotations();
			for (Annotation[] annotations : paramAns) {
				for (Annotation annotation : annotations) {
					if (annotation instanceof RequestParam) {
						RequestParam rp = (RequestParam) annotation;
						if (request.getParameter(rp.value()) != null)
							args[args_i++] = request.getParameter(rp.value());
						if(islucy) {
							if(!"".equals(rp.fileName()))
								args[args_i++]=upload(request,response,method);
						}
					}
				}
			}
		}
		return args;
	}

	private static boolean isExistRequestParam(Method method) {
		int i = 0;
		Annotation[][] paramAns = method.getParameterAnnotations();
		for (Annotation[] annotations : paramAns) {
			for (Annotation annotation : annotations) {
				if (annotation instanceof RequestParam) {
					i++;
				}
			}
		}
		return !(i == 0);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf8");
		resp.setCharacterEncoding("utf8");
		resp.setHeader("Content-Type", "text/html;charset=utf-8");
		String uri = req.getRequestURI();
		String context = req.getContextPath();
		String path = uri.replace(context, "");
		Method method = (Method) handerMaps.get(path);
		Object obj = beans.get("/" + path.split("/")[1]);
		Class<?> cl=obj.getClass();
		Field[] fields=cl.getDeclaredFields();
		for (Field field : fields) {
			if(field.isAnnotationPresent(Autowired.class)) {
				Autowired aw=field.getAnnotation(Autowired.class);
				String val=aw.value();
				if("request".equals(val)) {
					field.setAccessible(true);
					try {
						field.set(obj, req);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if("response".equals(val)) {
					field.setAccessible(true);
					try {
						field.set(obj, resp);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		Object[] args;
		if(!method.isAnnotationPresent(Download.class)) {
			args = hand(req, resp, method);
		}else {
			args=null;
			download(req, resp, method);
		}
		try {
			Object obj1 = method.invoke(obj, args);
			if (obj1 != null) {
				if (method.isAnnotationPresent(xfl.fk.annotation.SendRed.class)) {
					resp.sendRedirect(obj1.toString());
				} else {
					req.getRequestDispatcher(obj1.toString()).forward(req, resp);
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
