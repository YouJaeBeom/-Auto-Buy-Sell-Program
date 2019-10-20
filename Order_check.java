import java.util.HashMap;

//import Start.Serial_Reader_Writer;

public class Order_check {
	public  void order_check(String order_id,String type,String currency)
	{
			String order_check="";
			Api_Client api = new Api_Client(Static_setting.Connect_key,
				Static_setting.Secret_key);

			HashMap<String, Object> rgParams = new HashMap<String, Object>();
			rgParams.put("order_id", order_id);// ���� ��ȣ
			rgParams.put("type", type);// ���� ��ȣ
			rgParams.put("currency", currency);// ���� ��ȣ
			//Main.result_Jtext.append(order_id+"\n");
			/*�ż� �ŵ��� ������ ���� ���̵� ������ 
			 * �ŷ� ������ �Ϸ� �ߴ��� Ȯ���Ѵ�.*/
			

			try {
				
				HashMap<String, Object> result = api.callApi_order_check("/info/orders", rgParams);
				if(result==null)
				{
					//Main.result_Jtext.append("��ȸ�� �ȵ�\n");
					order_check="null";
				}
				System.out.println(result+"\n");
			   //Main.result_Jtext.append(result+"\n");
				if(result.containsValue("5600"))
				{
					//Main.result_Jtext.append(currency+"/"+type+" �Ϸ�\n");
					
					Static_setting.coin_name=currency;
					Static_setting.type=type;
					Static_setting.comOrpla="com";
				}
				else if(result.containsValue("0000")) 
				{
					//Main.result_Jtext.append(currency+"/"+type+" �̿Ϸ�\n");
					
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
