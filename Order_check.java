import java.util.HashMap;

//import Start.Serial_Reader_Writer;

public class Order_check {
	public  void order_check(String order_id,String type,String currency)
	{
			String order_check="";
			Api_Client api = new Api_Client(Static_setting.Connect_key,
				Static_setting.Secret_key);

			HashMap<String, Object> rgParams = new HashMap<String, Object>();
			rgParams.put("order_id", order_id);// 오더 번호
			rgParams.put("type", type);// 오더 번호
			rgParams.put("currency", currency);// 오더 번호
			//Main.result_Jtext.append(order_id+"\n");
			/*매수 매도시 나오는 오더 아이디를 가지고 
			 * 거래 중인지 완료 했는지 확인한다.*/
			

			try {
				
				HashMap<String, Object> result = api.callApi_order_check("/info/orders", rgParams);
				if(result==null)
				{
					//Main.result_Jtext.append("조회가 안됨\n");
					order_check="null";
				}
				System.out.println(result+"\n");
			   //Main.result_Jtext.append(result+"\n");
				if(result.containsValue("5600"))
				{
					//Main.result_Jtext.append(currency+"/"+type+" 완료\n");
					
					Static_setting.coin_name=currency;
					Static_setting.type=type;
					Static_setting.comOrpla="com";
				}
				else if(result.containsValue("0000")) 
				{
					//Main.result_Jtext.append(currency+"/"+type+" 미완료\n");
					
					Static_setting.coin_name=currency;
					Static_setting.type=type;
					Static_setting.comOrpla="pla";
				}


			    
			} catch (Exception e1) {
			    e1.printStackTrace();
			    //(new Thread(new Serial_Reader_Writer(in,out))).start();
			}
		
	}
}
