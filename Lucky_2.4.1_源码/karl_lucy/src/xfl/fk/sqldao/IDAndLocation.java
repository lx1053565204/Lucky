package xfl.fk.sqldao;

public class IDAndLocation {
	private Object id = null;
	private int location;
	private ClassUtils util = new ClassUtils();

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	/**
	 * 找到ClassInfo对象中values中的ID的值和位置
	 * @param info
	 *  ClassInfo对象
	 */
	IDAndLocation(ClassInfo info) {
		for (int i = 0; i < info.getNames().size(); i++) {
			if (util.getID(info.getClzz()).equals(info.getNames().get(i))) {
				this.id = info.getValues().get(i);
				this.location = i;
			}
		}
		if (this.id == null) {
			try {
				throw new Exception("没有找到ID属性，无法完成更新操作！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
