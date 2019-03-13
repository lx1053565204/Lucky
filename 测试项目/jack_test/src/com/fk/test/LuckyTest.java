package com.fk.test;
import java.util.List;
import org.junit.Test;
import com.fk.entitry.Author;
import com.fk.entitry.Book;
import com.fk.entitry.Stort;

import xfl.fk.sqldao.SqlControl;

@SuppressWarnings("all")
public class LuckyTest {

	@Test
	//有参构造建表
	public void test1() {
		 SqlControl sql=new SqlControl(1,5);
	}
	@Test
	//ID操作-删除
	public void test2() {
		 SqlControl sql=new SqlControl();
		 sql.delete(Book.class,5);
	}
	@Test
	//ID操作-查询
	public void test3() {
		 SqlControl sql=new SqlControl();
		 Stort st=(Stort) sql.getOne(Stort.class, 2);
		 System.out.println(st);
	}
	@Test
	//对象操作-增删操作
	public void test5() {
		 SqlControl sql=new SqlControl();
		 /*删除操作
		 Book b1=new Book();
		 b1.setBname("666");
		 b1.setPrice(666.0);
		 sql.delete(b1);
		 */
		 //增加操作
		 Book b2=new Book();
		 b2.setBname("书名1");
		 b2.setPrice(45.7);
		 b2.setStid(1);//绑定类型
		 b2.setAutid(1);//绑定作者
		 sql.save(b2);
	}
	@Test
	//对象操作-修改
	public void test6() {
		 SqlControl sql=new SqlControl();
		 System.out.println(sql.getOne(Stort.class, 1));
		 Stort st=new Stort();
//		 st.setStid(1);//如果不设置ID就会报错
		 st.setStname("著名小说");
		 sql.update(st);
		 System.out.println(sql.getOne(Stort.class, 1));
	}
	@Test
	//对象操作-查询单条记录
	public void test7() {
		SqlControl sql=new SqlControl();
		Author aut=new Author();
		aut.setAutsex("女");
		aut=(Author) sql.getObject(aut);
		System.out.println(aut);
	}
	@Test
	//对象操作-查询多条记录
	public void test8() {
		SqlControl sql=new SqlControl();
//		List<Author> list=(List<Author>) sql.getList(new Author());//查询所有记录
//		System.out.println(list);
		Author aut=new Author();
		aut.setAutsex("男");
		List<Author> list=(List<Author>) sql.getList(aut);//查询所有男性作者
		System.out.println(list);
	}
	@Test
	//排序查询
	public void test9() {
		SqlControl sql=new SqlControl();
		List<Author> list=(List<Author>)sql.getSortList(Author.class, "autid", 1);//降序
//		List<Author> list=(List<Author>)sql.getSortList(Author.class, "autid", 0);//升序
		System.out.println(list);
	}
	@Test
	//简单模糊查询
	public void test10() {
		SqlControl sql=new SqlControl();
		List<Author> list=(List<Author>)sql.getFuzzyList(Author.class, "autname", "余");//模糊查询姓名带“余”
		System.out.println(list);
	}
	@Test
	//分页查询
	public void test11() {
		SqlControl sql=new SqlControl();
		List<Author> list=(List<Author>)sql.getPagList(new Author(), 0, 3);//每页3条记录
		System.out.println(list);
	}
	@Test
	//预编译SQl-增删改
	public void test12() {
		SqlControl sql=new SqlControl();
		String sql1;
		sql1="INSERT INTO t_stort(stname) VALUES(?)";
		sql.save(sql1, "国学经典");//增加
//		sql2="DELETE FROM t_book WHERE bname=?";
//		sql.delete(sql2, "888");//删除
//		sql3="UPDATE t_stort SET stname=? WHERE stid=?";
//		sql.update(sql3, "Hello Word",3);//修改
	}
	@Test
	//预编译SQl-查询
	public void test13() {
		SqlControl sql=new SqlControl();
		List<Book> list=(List<Book>)sql.getList(Book.class, "SELECT * FROM t_book WHERE stid=?", 1);//查询类型1下的所有书本
		System.out.println(list);
	}
}
