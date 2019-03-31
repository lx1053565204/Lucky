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
	//�вι��콨��
	public void test1() {
		 SqlControl sql=SqlControl.getSqlControlAddCTable(1, 4);
	}
	@Test
	//ID����-ɾ��
	public void test2() {
		//1.��ò������ݿ��SqlControl����
		 SqlControl sql=SqlControl.getSqlControl();
		//2.��װ������Ϣ
		//��ʽ1.ID����
		 sql.delete(Book.class, 1);//ɾ��book��������Ϊ 1 ��������¼
		 Book b1=(Book) sql.getOne(Book.class, 1);//��ѯbook��������Ϊ 1 ��������¼
		 //��ʽ2.�������
		 Book book=new Book();
		 book.setBname("����Ϧʰ");
		 book.setPrice(43.0);
		 sql.save(book);//���һ���۸�Ϊ43Ԫ����Ϊ������Ϧʰ������
		 //��ʽ3.Ԥ����SQL����
		 String sqlsrt="UPDATE book SET price=? WHERE bid=?";
		 sql.update(sqlsrt, 34.7,3);//���鱾IDΪ3���Ǳ���ļ۸��Ϊ34.7
	}
	@Test
	//ID����-��ѯ
	public void test3() {
		 SqlControl sql=SqlControl.getSqlControl();
		 Stort st=(Stort) sql.getOne(Stort.class, 7);
		 System.out.println(st);
	}
	@Test
	//�������-��ɾ����
	public void test5() {
		 SqlControl sql=SqlControl.getSqlControl();
		 /*ɾ������
		 Book b1=new Book();
		 b1.setBname("666");
		 b1.setPrice(666.0);
		 sql.delete(b1);
		 */
		 //���Ӳ���
		 Book b2=new Book();
		 b2.setBname("����1");
		 b2.setPrice(45.7);
		 b2.setStid(1);//������
		 b2.setAutid(1);//������
		 sql.save(b2);
	}
	@Test
	//�������-�޸�
	public void test6() {
		 SqlControl sql=SqlControl.getSqlControl();
		 System.out.println(sql.getOne(Stort.class, 1));
		 Stort st=new Stort();
//		 st.setStid(1);//���������ID�ͻᱨ��
		 st.setStname("����С˵");
		 sql.update(st);
		 System.out.println(sql.getOne(Stort.class, 1));
	}
	@Test
	//�������-��ѯ������¼
	public void test7() {
		SqlControl sql=SqlControl.getSqlControl();
		Author aut=new Author();
		aut.setAutsex("Ů");
		aut=(Author) sql.getObject(aut);
		System.out.println(aut);
	}
	@Test
	//�������-��ѯ������¼
	public void test8() {
		SqlControl sql=SqlControl.getSqlControl();
//		List<Author> list=(List<Author>) sql.getList(new Author());//��ѯ���м�¼
//		System.out.println(list);
		Author aut=new Author();
		aut.setAutsex("��");
		List<Author> list=(List<Author>) sql.getList(aut);//��ѯ������������
		System.out.println(list);
	}
	@Test
	//�����ѯ
	public void test9() {
		SqlControl sql=SqlControl.getSqlControl();
		List<Author> list=(List<Author>)sql.getSortList(Author.class, "autid", 1);//����
//		List<Author> list=(List<Author>)sql.getSortList(Author.class, "autid", 0);//����
		System.out.println(list);
	}
	@Test
	//��ģ����ѯ
	public void test10() {
		SqlControl sql=SqlControl.getSqlControl();
		List<Author> list=(List<Author>)sql.getFuzzyList(Author.class, "autname", "��");//ģ����ѯ���������ࡱ
		System.out.println(list);
	}
	@Test
	//��ҳ��ѯ
	public void test11() {
		SqlControl sql=SqlControl.getSqlControl();
		List<Author> list=(List<Author>)sql.getPagList(new Author(), 0, 3);//ÿҳ3����¼
		System.out.println(list);
	}
	@Test
	//Ԥ����SQl-��ɾ��
	public void test12() {
		SqlControl sql=SqlControl.getSqlControl();
		String sql1;
		sql1="INSERT INTO stort(stname) VALUES(?)";
		sql.save(sql1, "��ѧ����");//����
//		sql2="DELETE FROM t_book WHERE bname=?";
//		sql.delete(sql2, "888");//ɾ��
//		sql3="UPDATE t_stort SET stname=? WHERE stid=?";
//		sql.update(sql3, "Hello Word",3);//�޸�
	}
	@Test
	//Ԥ����SQl-��ѯ
	public void test13() {
		SqlControl sql=SqlControl.getSqlControl();
		List<Book> list=(List<Book>)sql.getList(Book.class, "SELECT * FROM t_book WHERE stid=?", 1);//��ѯ����1�µ������鱾
		System.out.println(list);
	}
	//Lucky�����������
	@Test
	public void test14() {
		SqlControl sql=SqlControl.getSqlControl();
		Transaction tx=sql.openTransaction();//��������
		Account zs=(Account) sql.getOne(Account.class, 1);//����������˻���Ϣ
		Account ls=(Account) sql.getOne(Account.class, 2);//������ĵ��˻���Ϣ
		zs.setMoney(zs.getMoney()-1000);//����������1000
		ls.setMoney(ls.getMoney()+1000);//�����������1000
		try {
//			tx=sql.openTransaction();//��������
			//ִ�и���
			sql.update(zs);
			int i=1/0;//���������쳣������������쳣���������е��������
			sql.update(ls);
			tx.commit();//�ύ����
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();//�����쳣�ͻع�
		}
	}
	//Lucky�����򹤳�
	@Test
	public void test15() {
		SqlControl sql=SqlControl.getSqlControlAddCJavaBean("C:/Users/chenjun/Desktop/Lucky/Lucky/������Ŀ/jack_test/src");
	}
	//Lucky��������
	@Test
	public void test16() {
		SqlControl sql=SqlControl.getSqlControl();
		/*Ԥ����SQL������ӣ�ɾ�����޸����ƣ�����׸����
		Object[][] obj= {{"³Ѹ","��"},{"����","Ů"},{"�໪","��"},{"��ë","Ů"}};
		sql.saveBatch("INSERT INTO t_author(autname,autsex) VALUES(?,?)", obj);
		*/
		/*����ʽ������������ɾ�����޸����ƣ�����׸����
		Stort s1=new Stort(null,"����С˵");
		Stort s2=new Stort(null,"��ѧ����");
		Stort s3=new Stort(null,"�ֲ�����");
		Author a1=new Author(null,"�໪","��");
		Author a2=new Author(null,"³Ѹ","��");
		Author a3=new Author(null,"��ë","Ů");
		Book b1=new Book(null,"���������ӡ�",34.5,1,2);
		Book b2=new Book(null,"�����š�",44.4,2,1);
		Book b3=new Book(null,"������֪���١�",55.5,3,3);
		Book b4=new Book(null,"������Ϧʰ��",66.6,3,2);
		sql.saveBatch(s1,s2,s3,a1,a2,a3,b1,b2,b3,b4);//������ͬ�ı�����Ӷ�����¼
		*/
	}
}
