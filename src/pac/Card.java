package pac;

import java.sql.Timestamp;
import java.util.Hashtable;

public class Card {
	
	public String id;
	public String title;
	public String sub_title;
	public String logo_url;
	public String brand_name;
	public String color;
	public String notice;
	public String description;
	public String status;
	public String quantity;
	public String total_quantity;
	public String create_time;
	public Timestamp begin_timestamp;
	public Timestamp end_timestamp;
	public String qrcode_url;
	
	public String user;
	public String password;
	
	public static Hashtable<String, String> names;
	static{
		names=new Hashtable<String, String>();
		names.put("CARD_STATUS_NOT_VERIFY", "�����");
		names.put("CARD_STATUS_VERIFY_OK", "ͨ�����");
		names.put("CARD_STATUS_USER_DELETE", "��ɾ��");
		names.put("CARD_STATUS_DISPATCH", "�ڹ���ƽ̨Ͷ�Ź�");
	}
}





