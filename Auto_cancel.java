import java.util.HashMap;

public class Auto_cancel {
	public static void auto_cancel(String order_id,String type,String currency)
	{
			String cancel="";
			Api_Client api = new Api_Client(Static_setting.Connect_key,
				Static_setting.Secret_key);

			HashMap<String, Object> rgParams = new HashMap<String, Object>();
			rgParams.put("order_id", order_id);// ���� ��ȣ
			rgParams.put("type", type);// 
			rgParams.put("currency", currency);// ���θ�
			Static_setting.krwcheck="no";
			try {
				HashMap<String, Object> result = api.callApi_order_check("/trade/cancel", rgParams);
			
				if(result.containsValue("0000")) 
				{
					Main.result_Jtext.append(currency+"/"+type+" ��ҿϷ�\n");
					Static_setting.comOrpla="can";
					 String result4 = api.callApi_Balnace("/info/balance", rgParams);
					    Static_setting.mykrw=result4;
				
				}
				else 
				{
					Main.result_Jtext.append(currency+"/"+type+" ��ҹ̿Ϸ�\n");
				}


			    
			} catch (Exception e1) {
			    e1.printStackTrace();
			}
	
	}
}
