package tools;

import java.util.ArrayList;
import java.util.Hashtable;

public class Password {
	static Hashtable hashlist;
	static Hashtable hashlist2;
	static Hashtable hashlist3;
	
	private final static String[][] pw={
		{"***","***"},
		{"**","**"},
		{"*","*"},
	};
	static{
		hashlist=new Hashtable();
		hashlist2=new Hashtable();
		hashlist3=new Hashtable();
		
		for(String[] str : pw){
			hashlist.put(str[0], str[1]);
		}
	}
	public static String getPassword(String id) {
		try {
			String pw=hashlist.get(id).toString();
			return pw;
		} catch (Exception e) {
			return "";
		}
	}
	public static String getPassword2(String id) {
		try {
			String pw=hashlist2.get(id).toString();
			return pw;
		} catch (Exception e) {
			return "";
		}
	}
	public static void setPassword(ArrayList<String> cards) {
		for (String card_id:cards) {
			String user=MD5.MD5(card_id).substring(0,5);
			String password=MD5.MD5(card_id+RandomString.randomString()).substring(0,8);
			hashlist2.put(user, password);
			hashlist3.put(user, card_id);
		}
	}
	public static String getCardId(String user){
		return hashlist3.get(user).toString();
	}
	public static void main(String[] args) {
		System.out.println(Password.getPassword("***"));
	}
}
