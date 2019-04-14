package com.fk.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fk.pojo.User;
import com.fk.service.TestService;

import xfl.fk.annotation.Autowired;
import xfl.fk.annotation.Controller;
import xfl.fk.annotation.Download;
import xfl.fk.annotation.RequestMapping;
import xfl.fk.annotation.RequestParam;
import xfl.fk.annotation.SendRed;
import xfl.fk.annotation.Upload;

@Controller
@RequestMapping("/jack")
public class TestController {
	@Autowired("request")
	private HttpServletRequest request;
	
	@Autowired("response")
	private HttpServletResponse response;
	
	@Autowired("TestService")
	private TestService service;
	
	/**
	 * 首页-user列表
	 * @return
	 */
	@RequestMapping("/userList")
	public String showUsers() {
		List<User> list=service.getUser();
		request.setAttribute("userlist", list);
		return "/userList.jsp";
	}
	/**
	 * 登录
	 * @param name 用户名
	 * @param pass 密码
	 * @return
	 */
	@SendRed//重定向
	@RequestMapping("/login")
	public String login(String name,String pass) {
		User u=new User();
		u.setUname(name);u.setPassword(pass);
		if(service.login(u)) {
			request.getSession().setAttribute("User", u);
			return "userList";
		}else
			return "../Login.jsp";
	}
	/**
	 * 添加用户
	 * @return
	 */
	@RequestMapping("/add")
	@Upload(name="photo",filePath="lucy/upload")
	public String add(@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("sex")String sex,
			@RequestParam(fileName="fk")String filename) {
		String fileName="lucy/upload/"+filename;
		User u=new User(null,username,sex,password,fileName);
		service.add(u);
		return "userList";
	}
	/**
	 * 通过ID找到一名用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/get")
	public String get(String id) {
		User u=service.get(Integer.parseInt(id));
		request.setAttribute("user", u);
		if("男".equals(u.getSsex()))
			request.setAttribute("info_s", "1");
		else
			request.setAttribute("info_s", "2");
		return "/UserUpdate.jsp";
	}
	
	/**
	 * 删除一名用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/del")
	public String del(String id){
		User user=service.get(Integer.parseInt(id));
		String path=request.getServletContext().getRealPath(user.getPrint());
		System.out.println(path);
		File file=new File(path);
		if(file.exists())
			file.delete();
		service.del(Integer.parseInt(id));
		return "userList";
	}
	/**
	 * 更新用户信息
	 * @param uid
	 * @param username
	 * @param password
	 * @param sex
	 * @param filename
	 * @return
	 */
	@RequestMapping("/upd")
	@Upload(name="photo",filePath="lucy/upload")
	public String upd(
			@RequestParam("uid")String uid,
			@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("sex")String sex,
			@RequestParam(fileName="fk")String filename){
		User u=service.get(Integer.parseInt(uid));
		String path=request.getServletContext().getRealPath(u.getPrint());
		System.out.println(filename);
		File file=new File(path);
		if(filename!=null) {
		if(file.exists())
			file.delete();
		u.setPrint("lucy/upload/"+filename);u.setSsex(sex);u.setPassword(password);u.setUname(username);
		}else {
			u.setSsex(sex);u.setPassword(password);u.setUname(username);
		}
		service.upd(u);
		return "userList";
	}
	/**
	 * 用户检索功能
	 * @param username
	 * @param sex
	 * @return
	 */
	@RequestMapping("/search")
	public String search(String username,String sex) {
		String obj="%"+username+"%";
		request.setAttribute("info_u", username);
		if(sex=="") {
			String sqlsrt="SELECT * FROM user WHERE uname LIKE ?";
			request.setAttribute("userlist", service.search(sqlsrt, obj));
		}else {
			if("男".equals(sex))
				request.setAttribute("info_s", "1");
			else
				request.setAttribute("info_s", "2");
			String sqlsrt="SELECT * FROM user WHERE uname LIKE ? AND ssex=?";
			request.setAttribute("userlist", service.search(sqlsrt, obj,sex));
		}
		return "/userList.jsp";
	}
	/**
	 * 文件上传测试
	 * @param fileName
	 * @param fk
	 * @param xfl
	 * @return
	 */
	@RequestMapping("/upload")
	@Upload(name="myfile",filePath="lucy/upload")
	public String upload(
			@RequestParam(fileName="h") String fileName,
			@RequestParam("name") String fk,
			@RequestParam("age") String xfl
			) {
		User u=new User();
		u.setUname(fk);
		u.setPassword(xfl);
		request.setAttribute("user", u);
		request.setAttribute("por", "lucy/upload/"+fileName);
		System.out.println("lucy/upload/"+fileName);
		return "../a.jsp";
	}
	
	@RequestMapping("/download")
	@Download(name="file",filePath="")
	public void download() {
		
	}

}
