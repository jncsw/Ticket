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
		names.put("CARD_STATUS_NOT_VERIFY", "待审核");
		names.put("CARD_STATUS_VERIFY_OK", "通过审核");
		names.put("CARD_STATUS_USER_DELETE", "已删除");
		names.put("CARD_STATUS_DISPATCH", "在公众平台投放过");
	}
}





