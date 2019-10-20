import java.util.HashMap;

public class Market_buy {

	static String order_id;
	static String data;
	@SuppressWarnings("unchecked")
	public static String marketbuy(String coin_name)
	{
		
			Api_Client api = new Api_Client(Static_setting.Connect_key,
				Static_setting.Secret_key);

			HashMap<String, Object> rgParams = new HashMap<String, Object>();
			rgParams.put("currency", coin_name);// ���θ� �ٲٱ�~~~~~~~~~~~~~~~``
			Static_setting.krwcheck="no";

			try {
			    String result = api.callApi_Balnace("/info/balance", rgParams);
			    String result1= api.callApi_Balnace_last("/info/balance", rgParams);
			    double krw=Double.parseDouble(result); // ���� �������ִ� ��
			
			    krw=krw*0.95;
			   
			    double coin_krw=Double.parseDouble(result1); //������
			   
			    /* ������ ������ ���ڸ� ������ �ľ��ϱ� 
			     * 0�� ���� �ľ��ϱ�
			     */
			    int length=(int)(Math.log10(coin_krw)+1);
			    System.out.println(length);
			    double av_coin=(krw/coin_krw);
			    int essence=(int)(Math.log10(av_coin)+1); //������ �ڸ���
			    System.out.println("������ ���� �ڸ����� ="+essence);
			    int decimal_point=7-essence; // �Ҽ����� ���°���� �츱�� ���ϰ�
			    System.out.println("������ ���� �ִ� �Ҽ��� �ڸ����� ="+decimal_point);
			    float units = 0;
			    if(decimal_point==1) { // ���� �ִ� �Ҽ��� �ڸ����� 1�� ���
			    	System.out.println("������:"+av_coin);
			    	units=(float) ((int)(av_coin*10)/10.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==2) { // ���� �ִ� �Ҽ��� �ڸ����� 2�� ���
			    	System.out.println("������:"+av_coin);
			    	units=(float) ((int)(av_coin*100)/100.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==3) { // ���� �ִ� �Ҽ��� �ڸ����� 3�� ���
			    	System.out.println("������:"+av_coin);
			    	units=(float) ((int)(av_coin*1000)/1000.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==4) { // ���� �ִ� �Ҽ��� �ڸ����� 4�� ���
			    	System.out.println("������:"+av_coin);
			    	units=(float) ((int)(av_coin*10000)/10000.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==5) { // ���� �ִ� �Ҽ��� �ڸ����� 5�� ���
			    	System.out.println("������:"+av_coin);
			    	units=(float) ((int)(av_coin*100000)/100000.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==6) { // ���� �ִ� �Ҽ��� �ڸ����� 6�� ���
			    	System.out.println("������:"+av_coin);
			    	units=(float) ((int)(av_coin*1000000)/1000000.0);
			    	System.out.println("������:"+units);
			    }
			    else if(decimal_point==7) { // ���� �ִ� �Ҽ��� �ڸ����� 7�� ���
			    	System.out.println("������:"+av_coin);
			    	units=(float) ((int)(av_coin*10000000)/10000000.0);
			    	System.out.println("������:"+units);
			    }
			    System.out.println("�ֹ� �̸�ŭ�Ѵ�~   "+units);
		   
			    
			    if(av_coin*coin_krw>=100000000)// �ŷ����� ���μ� * ���� ���� �� 1���� ������ �ŷ� ���ϰ��Ѵ�.
			    {
			 
				    System.out.println("�ŷ� ������ �ݾ��� 1���Դϴ�. ����ڲ����� "+av_coin*coin_krw+"�� �Ǿ ����� �Ұ��մϴ�.");
				    Main.result_Jtext.append("�ŷ� ������ �ݾ��� 1���Դϴ�. ����ڲ����� "+av_coin*coin_krw+"�� �Ǿ ����� �Ұ��մϴ�."+"\n");
			    	//�ŷ� ���ϰ� �Ѵ�.
			    }
			    else { // �ŷ����� ���μ� * ���ΰ����� 1���� �ȳ��� ��� api ���డ�� �ϹǷ� �����Ѵ�.
			    	HashMap<String, Object> rgParams2 = new HashMap<String, Object>();
			    	HashMap<String, Object> result2 = new HashMap<String, Object>();
			    	
			    	rgParams2.put("units",units); // ���� ��
			    	rgParams2.put("currency", coin_name);// ���θ� �ٲٱ�~~~~~~~~~~~~~~~``
				    result2 = api.callApi_buy("/trade/market_buy", rgParams2);
				    System.out.println(result2);
				    String result4 = api.callApi_Balnace("/info/balance", rgParams);
				    Static_setting.mykrw=result4;
				    order_id=(String) result2.get("order_id");
				    Static_setting.order_id=order_id;
				    Main.result_Jtext.append(result2+"\n");
			    }

			    
			} catch (Exception e1) {
			    e1.printStackTrace();
			    //���� �ż� �� �� ���µ� �ż��϶���ϸ�????
			    
			}
			return order_id;
	}

}
