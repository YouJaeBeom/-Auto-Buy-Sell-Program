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
			rgParams.put("currency", coin_name);// 코인명 바꾸기~~~~~~~~~~~~~~~``
			Static_setting.krwcheck="no";
		
			try {
				
				/*매도할때는 내가 가지고 있는 코인수를 다 파악해서 7자리 만든후 시장가로 매도한다
				 * 그래서 문제는 생기지 않지만 
				 * 가격이 은근 많이 싸게 해서 올려서 손해 발생할지도 몰라유*/
			    String balance = api.callApi_Account("/info/account", rgParams);
			    double coin_count=Double.parseDouble(balance); //내가 가진 코인 갯수 파악하기
		
			    
			    /* 코인의 정수가 몇자리 수인지 파악하기 
			     * 0의 갯수 파악하기
			     */
			    int essence=(int)(Math.log10(coin_count)+1); //정수의 자리수
			    System.out.println("코인의 정수 자리수는 ="+essence);
			    int decimal_point=7-essence; // 소수점의 몇번째까지 살릴지 정하고
			    System.out.println("코인의 쓸수 있는 소수점 자리수는 ="+decimal_point);
			    
			    float units = 0;
			    if(decimal_point==1) { // 쓸수 있는 소수점 자리수가 1일 경우
			    	System.out.println("수정전:"+coin_count);
			    	units=(float) ((int)(coin_count*10)/10.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==2) { // 쓸수 있는 소수점 자리수가 2일 경우
			    	System.out.println("수정전:"+coin_count);
			    	units=(float) ((int)(coin_count*100)/100.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==3) { // 쓸수 있는 소수점 자리수가 3일 경우
			    	System.out.println("수정전:"+coin_count);
			    	units=(float) ((int)(coin_count*1000)/1000.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==4) { // 쓸수 있는 소수점 자리수가 4일 경우
			    	System.out.println("수정전:"+coin_count);
			    	units=(float) ((int)(coin_count*10000)/10000.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==5) { // 쓸수 있는 소수점 자리수가 5일 경우
			    	System.out.println("수정전:"+coin_count);
			    	units=(float) ((int)(coin_count*100000)/100000.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==6) { // 쓸수 있는 소수점 자리수가 6일 경우
			    	System.out.println("수정전:"+coin_count);
			    	units=(float) ((int)(coin_count*1000000)/1000000.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==7) { // 쓸수 있는 소수점 자리수가 7일 경우
			    	System.out.println("수정전:"+coin_count);
			    	units=(float) ((int)(coin_count*10000000)/10000000.0);
			    	System.out.println("수정후:"+units);
			    }
			    
			    /* 시장가 * 내가 가지고 있는 코인의 수가 1억이 넘어가는지 파악해서 만약 넘어 갈 경우 거래 안되도록
			     * 추가 해줘야하는데 시장가 가져오는방법 생각해봐야한다.
			     * 일단은 바로 매도 가능하도록 해놈
			     * 이유는 DB컴퓨터에서 어느정도 계산이 가능하기 때문이다.
			     */
			    HashMap<String, Object> result = new HashMap<String, Object>();
			    System.out.println("주문 이만큼한다~   "+units);
			    HashMap<String, Object> rgParams1 = new HashMap<String, Object>();
			    /* 여기서 코인의 자리수 파악하여 몇개 살지 정하고 소수점 몇개살릴지 정해야한ㄷ.*/
			    rgParams1.put("units", units);
			    
			    rgParams1.put("currency", coin_name);// 코인명 바꾸기~~~~~~~~~~~~~~~``
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
