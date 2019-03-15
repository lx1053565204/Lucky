package com.fk.test;
import java.util.List;

import org.junit.Test;

import com.fk.entitry.Account;
import com.fk.entitry.Author;
import com.fk.entitry.Book;
import com.fk.entitry.Stort;

import xfl.fk.sqldao.SqlControl;
import xfl.fk.sqldao.Transaction;

@SuppressWarnings("all")
public class LuckyTest {

	@Test
	//�вι��콨��
	public void test1() {
		 SqlControl sql=SqlControl.getSqlControl(1, 5);
	}
	@Test
	//ID����-ɾ��
	public void test2() {
		 SqlControl sql=SqlControl.getSqlControl();
		 sql.delete(Book.class,5);
	}
	@Test
	//ID����-��ѯ
	public void test3() {
		 SqlControl sql=SqlControl.getSqlControl();
		 Stort st=(Stort) sql.getOne(Stort.class, 2);
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
		sql1="INSERT INTO t_stort(stname) VALUES(?)";
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
}
