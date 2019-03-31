package com.fk.test;
import java.util.List;

import org.junit.Test;

import com.fk.entitry3.Account;
import com.fk.entitry3.Author;
import com.fk.entitry3.Book;
import com.fk.entitry3.Stort;

import xfl.fk.sqldao.SqlControl;
import xfl.fk.sqldao.Transaction;

@SuppressWarnings("all")
public class LuckyTest {

	@Test
	//有参构造建表
	public void test1() {
		 SqlControl sql=SqlControl.getSqlControlAddCTable(1, 4);
	}
	@Test
	//ID操作-删除
	public void test2() {
		//1.获得操作数据库的SqlControl对象
		 SqlControl sql=SqlControl.getSqlControl();
		//2.封装操作信息
		//方式1.ID操作
		 sql.delete(Book.class, 1);//删除book表中主键为 1 的那条记录
		 Book b1=(Book) sql.getOne(Book.class, 1);//查询book表中主键为 1 的那条记录
		 //方式2.对象操作
		 Book book=new Book();
		 book.setBname("朝花夕拾");
		 book.setPrice(43.0);
		 sql.save(book);//添加一本价格为43元书名为‘朝花夕拾’的书
		 //方式3.预编译SQL操作
		 String sqlsrt="UPDATE book SET price=? WHERE bid=?";
		 sql.update(sqlsrt, 34.7,3);//将书本ID为3的那本书的价格改为34.7
	}
	@Test
	//ID操作-查询
	public void test3() {
		 SqlControl sql=SqlControl.getSqlControl();
		 Stort st=(Stort) sql.getOne(Stort.class, 7);
		 System.out.println(st);
	}
	@Test
	//对象操作-增删操作
	public void test5() {
		 SqlControl sql=SqlControl.getSqlControl();
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
		 SqlControl sql=SqlControl.getSqlControl();
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
		SqlControl sql=SqlControl.getSqlControl();
		Author aut=new Author();
		aut.setAutsex("女");
		aut=(Author) sql.getObject(aut);
		System.out.println(aut);
	}
	@Test
	//对象操作-查询多条记录
	public void test8() {
		SqlControl sql=SqlControl.getSqlControl();
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
		SqlControl sql=SqlControl.getSqlControl();
		List<Author> list=(List<Author>)sql.getSortList(Author.class, "autid", 1);//降序
//		List<Author> list=(List<Author>)sql.getSortList(Author.class, "autid", 0);//升序
		System.out.println(list);
	}
	@Test
	//简单模糊查询
	public void test10() {
		SqlControl sql=SqlControl.getSqlControl();
		List<Author> list=(List<Author>)sql.getFuzzyList(Author.class, "autname", "余");//模糊查询姓名带“余”
		System.out.println(list);
	}
	@Test
	//分页查询
	public void test11() {
		SqlControl sql=SqlControl.getSqlControl();
		List<Author> list=(List<Author>)sql.getPagList(new Author(), 0, 3);//每页3条记录
		System.out.println(list);
	}
	@Test
	//预编译SQl-增删改
	public void test12() {
		SqlControl sql=SqlControl.getSqlControl();
		String sql1;
		sql1="INSERT INTO stort(stname) VALUES(?)";
		sql.save(sql1, "国学经典");//增加
//		sql2="DELETE FROM t_book WHERE bname=?";
//		sql.delete(sql2, "888");//删除
//		sql3="UPDATE t_stort SET stname=? WHERE stid=?";
//		sql.update(sql3, "Hello Word",3);//修改
	}
	@Test
	//预编译SQl-查询
	public void test13() {
		SqlControl sql=SqlControl.getSqlControl();
		List<Book> list=(List<Book>)sql.getList(Book.class, "SELECT * FROM t_book WHERE stid=?", 1);//查询类型1下的所有书本
		System.out.println(list);
	}
	//Lucky的事务处理机制
	@Test
	public void test14() {
		SqlControl sql=SqlControl.getSqlControl();
		Transaction tx=sql.openTransaction();//开启事务
		Account zs=(Account) sql.getOne(Account.class, 1);//获得张三的账户信息
		Account ls=(Account) sql.getOne(Account.class, 2);//获得李四的账户信息
		zs.setMoney(zs.getMoney()-1000);//张三余额减少1000
		ls.setMoney(ls.getMoney()+1000);//李四余额增加1000
		try {
//			tx=sql.openTransaction();//开启事务
			//执行更改
			sql.update(zs);
			int i=1/0;//这里会产生异常，我们用这个异常代替例子中的网络故障
			sql.update(ls);
			tx.commit();//提交事务
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();//出现异常就回滚
		}
	}
	//Lucky的逆向工程
	@Test
	public void test15() {
		SqlControl sql=SqlControl.getSqlControlAddCJavaBean("C:/Users/chenjun/Desktop/Lucky/Lucky/测试项目/jack_test/src");
	}
	//Lucky批量操作
	@Test
	public void test16() {
		SqlControl sql=SqlControl.getSqlControl();
		/*预编译SQL批量添加（删除和修改类似，不做赘述）
		Object[][] obj= {{"鲁迅","男"},{"冰心","女"},{"余华","男"},{"三毛","女"}};
		sql.saveBatch("INSERT INTO t_author(autname,autsex) VALUES(?,?)", obj);
		*/
		/*对象方式的批量操作（删除和修改类似，不做赘述）
		Stort s1=new Stort(null,"网络小说");
		Stort s2=new Stort(null,"文学名著");
		Stort s3=new Stort(null,"恐怖悬疑");
		Author a1=new Author(null,"余华","男");
		Author a2=new Author(null,"鲁迅","男");
		Author a3=new Author(null,"三毛","女");
		Book b1=new Book(null,"《骆驼祥子》",34.5,1,2);
		Book b2=new Book(null,"《活着》",44.4,2,1);
		Book b3=new Book(null,"《花落知多少》",55.5,3,3);
		Book b4=new Book(null,"《朝花夕拾》",66.6,3,2);
		sql.saveBatch(s1,s2,s3,a1,a2,a3,b1,b2,b3,b4);//批量向不同的表中添加多条纪录
		*/
	}
}
