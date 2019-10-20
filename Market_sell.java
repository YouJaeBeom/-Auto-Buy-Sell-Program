import java.util.HashMap;

public class Market_sell {

	static String order_id;
	static String data;
	public static String marketsell(String coin_name)
	{

			//String api_key=Jtext.getText();
			Api_Client api = new Api_Client(Static_setting.Connect_key,
				Static_setting.Secret_key);
		
			HashMap<String, Object> rgParams = new HashMap<String, Object>();
			rgParams.put("currency", coin_name);// ���θ� �ٲٱ�~~~~~~~~~~~~~~~``
			Static_setting.krwcheck="no";
		
			try {
				
				/*�ŵ��Ҷ��� ���� ������ �ִ� ���μ��� �� �ľ��ؼ� 7�ڸ� ������ ���尡�� �ŵ��Ѵ�
				 * �׷��� ������ ������ ������ 
				 * ������ ���� ���� �ΰ� �ؼ� �÷��� ���� �߻������� ������*/
			    String balance = api.callApi_Account("/info/account", rgParams);
			    double coin_count=Double.parseDouble(balance); //���� ���� ���� ���� �ľ��ϱ�
		
			    
			    /* ������ ������ ���ڸ� ������ �ľ��ϱ� 
			     * 0�� ���� �ľ��ϱ�
			     */
			    int essence=(int)(Math.log10(coin_count)+1); //������ �ڸ���
			    System.out.println("������ ���� �ڸ����� ="+essence);
			    int decimal_point=7-essence; // �Ҽ����� ���°���� �츱�� ���ϰ�
			    System.out.println("������ ���� �ִ� �Ҽ��� �ڸ����� ="+decimal_point);
			    
			    float units = 0;
			    if(decimal_point==1) { // ���� �ִ� �Ҽ��� �ڸ����� 1�� ���
			    	System.out.println("������:"+coin_count);
			    	units=(float) ((int)(coin_count*10)/10.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==2) { // ���� �ִ� �Ҽ��� �ڸ����� 2�� ���
			    	System.out.println("������:"+coin_count);
			    	units=(float) ((int)(coin_count*100)/100.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==3) { // ���� �ִ� �Ҽ��� �ڸ����� 3�� ���
			    	System.out.println("������:"+coin_count);
			    	units=(float) ((int)(coin_count*1000)/1000.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==4) { // ���� �ִ� �Ҽ��� �ڸ����� 4�� ���
			    	System.out.println("������:"+coin_count);
			    	units=(float) ((int)(coin_count*10000)/10000.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==5) { // ���� �ִ� �Ҽ��� �ڸ����� 5�� ���
			    	System.out.println("������:"+coin_count);
			    	units=(float) ((int)(coin_count*100000)/100000.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==6) { // ���� �ִ� �Ҽ��� �ڸ����� 6�� ���
			    	System.out.println("������:"+coin_count);
			    	units=(float) ((int)(coin_count*1000000)/1000000.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==7) { // ���� �ִ� �Ҽ��� �ڸ����� 7�� ���
			    	System.out.println("������:"+coin_count);
			    	units=(float) ((int)(coin_count*10000000)/10000000.0);
			    	System.out.println("������:"+units);
			    }
			    
			    /* ���尡 * ���� ������ �ִ� ������ ���� 1���� �Ѿ���� �ľ��ؼ� ���� �Ѿ� �� ��� �ŷ� �ȵǵ���
			     * �߰� ������ϴµ� ���尡 �������¹�� �����غ����Ѵ�.
			     * �ϴ��� �ٷ� �ŵ� �����ϵ��� �س�
			     * ������ DB��ǻ�Ϳ��� ������� ����� �����ϱ� �����̴�.
			     */
			    HashMap<String, Object> result = new HashMap<String, Object>();
			    System.out.println("�ֹ� �̸�ŭ�Ѵ�~   "+units);
			    HashMap<String, Object> rgParams1 = new HashMap<String, Object>();
			    /* ���⼭ ������ �ڸ��� �ľ��Ͽ� � ���� ���ϰ� �Ҽ��� ��츱�� ���ؾ��Ѥ�.*/
			    rgParams1.put("units", units);
			    
			    rgParams1.put("currency", coin_name);// ���θ� �ٲٱ�~~~~~~~~~~~~~~~``
			    result = api.callApi_sell("/trade/market_sell", rgParams1);
			    System.out.println(result);
			    String result4 = api.callApi_Balnace("/info/balance", rgParams);
			    Static_setting.mykrw=result4;
			    order_id=(String) result.get("order_id");
			    if(!order_id.isEmpty()) {
			    	Static_setting.order_id=order_id;
			    }
			    Main.result_Jtext.append(result+"\n");
			    
			} catch (Exception e1) {
			    e1.printStackTrace();
			}
			return order_id;
	}
	

}
