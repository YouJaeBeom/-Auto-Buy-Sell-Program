import java.util.HashMap;

public class Auto_cancel {
	public static void auto_cancel(String order_id,String type,String currency)
	{
			String cancel="";
			Api_Client api = new Api_Client(Static_setting.Connect_key,
				Static_setting.Secret_key);

			HashMap<String, Object> rgParams = new HashMap<String, Object>();
			rgParams.put("order_id", order_id);// 오더 번호
			rgParams.put("type", type);// 
			rgParams.put("currency", currency);// 코인명
			Static_setting.krwcheck="no";
			try {
				HashMap<String, Object> result = api.callApi_order_check("/trade/cancel", rgParams);
			
				if(result.containsValue("0000")) 
				{
					Main.result_Jtext.append(currency+"/"+type+" 취소완료\n");
					Static_setting.comOrpla="can";
					 String result4 = api.callApi_Balnace("/info/balance", rgParams);
					    Static_setting.mykrw=result4;
				
				}
				else 
				{
					Main.result_Jtext.append(currency+"/"+type+" 취소미완료\n");
				}


			    
			} catch (Exception e1) {
			    e1.printStackTrace();
			}
	
	}
}
