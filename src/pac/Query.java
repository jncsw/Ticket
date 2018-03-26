package pac;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;

import tools.Download;
import tools.MD5;
import tools.Password;
import tools.PostSth;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Query {

	public static int cardNum = -1;
	public static int pageNum = -1;
	public static Hashtable hashlist;
	public static Hashtable cardlist;
	public static Hashtable cardlistByPage;

	static {
		init();
	}

	public static void init() {
		hashlist = new Hashtable();//按页数分的卡券id列表
		cardlistByPage= new Hashtable();//按页数分的卡券对象列表
		cardlist = new Hashtable();//完整卡券信息 对象
		

		try {
			cardNum = getCardList();
		} catch (Exception e) {
		}
		pageNum = getPageNum();
	}

	public static int getPageNum() {
		int num = cardNum / 5;
		if (cardNum % 5 != 0)
			num++;
		return num;
	}

	public static int getCardList() throws Exception{

		int cardNum = 0;
		ArrayList<String> cardList = new ArrayList<String>();

		while (true) {

			JSONObject jObject = new JSONObject();
			jObject.put("offset", 0);
			jObject.put("count", 50);
			JSONArray ja = new JSONArray();
			ja.add("CARD_STATUS_NOT_VERIFY");
			ja.add("CARD_STATUS_VERIFY_FAIL");
			ja.add("CARD_STATUS_VERIFY_OK");
			ja.add("CARD_STATUS_DISPATCH");
			jObject.put("status_list", ja);

			String strURL= "https://api.weixin.qq.com/card/batchget?access_token="
						+ Do.getToken();
				String returnString = PostSth.postJson(strURL,
						jObject.toString());
				JSONObject returnJsonObject = JSONObject
						.fromObject(returnString);
				JSONArray array = returnJsonObject.getJSONArray("card_id_list");
				ArrayList<String> oneOfCardList = (ArrayList<String>) JSONArray.toList(array);
				for (String card_id:oneOfCardList) {
					cardList.add(card_id);
				}
				cardNum += oneOfCardList.size();
				if (oneOfCardList.size() < 50)
					break;

		}

		SettleList(cardList);
		Password.setPassword(cardList);

		return cardNum;
	}

	private static void SettleList(ArrayList<String> cardList) {
		int i = 0;
		ArrayList<String> pageOfCardList = new ArrayList<String>();
		
		for (String card_id : cardList) {
			if(i%5==0&&i!=0){
				hashlist.put(i/5-1, pageOfCardList);
				pageOfCardList=new ArrayList<String>();
			}
			pageOfCardList.add(card_id);
			i++;
		}
		hashlist.put(i/5, pageOfCardList);
	}

	private static ArrayList<String> getCardList(int page) {
		ArrayList<String> pageOfCardList=(ArrayList<String>) hashlist.get(page);
		if(pageOfCardList==null){
			try {
				getCardList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			pageOfCardList=new ArrayList<String>();
		}
		return pageOfCardList;
	}

	public static ArrayList<Card> getPageOfCard(int page) throws Exception{
		ArrayList<Card> cards = (ArrayList<Card>) cardlistByPage.get(page);
		if (cards != null)
			return cards;
		cards = new ArrayList<Card>();
		for (String card_id : Query.getCardList(page)) {
			Card card = Query.getCard(card_id);
			if (card == null)
				continue;
			cards.add(card);
		}
		cardlistByPage.put(page, cards);
		return cards;
	}

	public static Card getCard(String card_id) throws Exception{
		Card card = (Card) cardlist.get(card_id);
		if (card != null) {
			return card;
		}

		JSONObject jObject = new JSONObject();
		jObject.put("card_id", card_id);

		String strURL ="https://api.weixin.qq.com/card/get?access_token="+ Do.getToken();
		String cardsString = PostSth.postJson(strURL, jObject.toString());
		JSONObject cardJsonObject = JSONObject.fromObject(cardsString);
		if (!cardJsonObject.get("errcode").toString().equals("0")) {
			return null;
		}
		JSONObject jo1 = JSONObject.fromObject(cardJsonObject.get("card"));

		JSONObject jo2;
		JSONObject jo3;
		try {
			jo2 = JSONObject.fromObject(jo1.get("gift"));
			jo3 = JSONObject.fromObject(jo2.get("base_info"));
		} catch (Exception e) {
			jo2 = JSONObject.fromObject(jo1.get("groupon"));
			jo3 = JSONObject.fromObject(jo2.get("base_info"));
		}

		card = new Card();
		card.id = jo3.get("id").toString();
		card.title = jo3.get("title").toString();
		card.sub_title = jo3.get("sub_title").toString();
		card.logo_url = Download.picture(jo3.get("logo_url").toString(),
				card.id);
		card.brand_name = jo3.get("brand_name").toString();
		card.color = jo3.get("color").toString();
		card.notice = jo3.get("notice").toString();
		card.description = jo3.get("description").toString();
		card.status = jo3.get("status").toString();
		card.create_time = jo3.get("create_time").toString();

		JSONObject jo4 = JSONObject.fromObject(jo3.get("date_info"));
		card.begin_timestamp = new Timestamp(Long.parseLong(jo4.get(
				"begin_timestamp").toString()
				+ "000"));
		card.end_timestamp = new Timestamp(Long.parseLong(jo4.get(
				"end_timestamp").toString()
				+ "000"));

		JSONObject jo5 = JSONObject.fromObject(jo3.get("sku"));
		card.quantity = jo5.get("quantity").toString();
		card.total_quantity = jo5.get("total_quantity").toString();
		
		card.qrcode_url=getQRCode(card_id);
		
		card.user=MD5.MD5(card_id).substring(0,5);
		card.password=Password.getPassword2(card.user);
				
		cardlist.put(card_id, card);
		
		return card;
	}
	public static String getQRCode(String card_id)throws Exception{
		JSONObject card = new JSONObject();
		card.put("card_id", card_id);
		
		JSONObject action_info = new JSONObject();
		action_info.put("card", card);
		
		JSONObject jObject = new JSONObject();
		jObject.put("action_info", action_info);
		jObject.put("action_name", "QR_CARD");
		
		try {
			JSONObject returnJsonObject = JSONObject.fromObject(PostSth.postJson("https://api.weixin.qq.com/card/qrcode/create?access_token="+Do.getToken(), jObject.toString()));
			if(!(Integer.parseInt(""+ returnJsonObject.get("errcode"))==0)){
				//System.out.println(Integer.parseInt(""+ returnJsonObject.get("errcode"))==0);
				return "";
			}

			return returnJsonObject.get("show_qrcode_url").toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		
	}
}
