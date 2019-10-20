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
			rgParams.put("currency", coin_name);// 코인명 바꾸기~~~~~~~~~~~~~~~``
			Static_setting.krwcheck="no";

			try {
			    String result = api.callApi_Balnace("/info/balance", rgParams);
			    String result1= api.callApi_Balnace_last("/info/balance", rgParams);
			    double krw=Double.parseDouble(result); // 내가 가지고있는 돈
			
			    krw=krw*0.95;
			   
			    double coin_krw=Double.parseDouble(result1); //지정가
			   
			    /* 코인의 정수가 몇자리 수인지 파악하기 
			     * 0의 갯수 파악하기
			     */
			    int length=(int)(Math.log10(coin_krw)+1);
			    System.out.println(length);
			    double av_coin=(krw/coin_krw);
			    int essence=(int)(Math.log10(av_coin)+1); //정수의 자리수
			    System.out.println("코인의 정수 자리수는 ="+essence);
			    int decimal_point=7-essence; // 소수점의 몇번째까지 살릴지 정하고
			    System.out.println("코인의 쓸수 있는 소수점 자리수는 ="+decimal_point);
			    float units = 0;
			    if(decimal_point==1) { // 쓸수 있는 소수점 자리수가 1일 경우
			    	System.out.println("수정전:"+av_coin);
			    	units=(float) ((int)(av_coin*10)/10.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==2) { // 쓸수 있는 소수점 자리수가 2일 경우
			    	System.out.println("수정전:"+av_coin);
			    	units=(float) ((int)(av_coin*100)/100.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==3) { // 쓸수 있는 소수점 자리수가 3일 경우
			    	System.out.println("수정전:"+av_coin);
			    	units=(float) ((int)(av_coin*1000)/1000.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==4) { // 쓸수 있는 소수점 자리수가 4일 경우
			    	System.out.println("수정전:"+av_coin);
			    	units=(float) ((int)(av_coin*10000)/10000.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==5) { // 쓸수 있는 소수점 자리수가 5일 경우
			    	System.out.println("수정전:"+av_coin);
			    	units=(float) ((int)(av_coin*100000)/100000.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==6) { // 쓸수 있는 소수점 자리수가 6일 경우
			    	System.out.println("수정전:"+av_coin);
			    	units=(float) ((int)(av_coin*1000000)/1000000.0);
			    	System.out.println("수정후:"+units);
			    }
			    else if(decimal_point==7) { // 쓸수 있는 소수점 자리수가 7일 경우
			    	System.out.println("수정전:"+av_coin);
			    	units=(float) ((int)(av_coin*10000000)/10000000.0);
			    	System.out.println("수정후:"+units);
			    }
			    System.out.println("주문 이만큼한다~   "+units);
		   
			    
			    if(av_coin*coin_krw>=100000000)// 거래가능 코인수 * 코인 가격 이 1억이 넘으면 거래 못하게한다.
			    {
			 
				    System.out.println("거래 가능한 금액은 1억입니다. 사용자께서는 "+av_coin*coin_krw+"이 되어서 사용이 불가합니다.");
				    Main.result_Jtext.append("거래 가능한 금액은 1억입니다. 사용자께서는 "+av_coin*coin_krw+"이 되어서 사용이 불가합니다."+"\n");
			    	//거래 못하게 한다.
			    }
			    else { // 거래가능 코인수 * 코인가격이 1억이 안넘을 경우 api 실행가능 하므로 실행한다.
			    	HashMap<String, Object> rgParams2 = new HashMap<String, Object>();
			    	HashMap<String, Object> result2 = new HashMap<String, Object>();
			    	
			    	rgParams2.put("units",units); // 코인 수
			    	rgParams2.put("currency", coin_name);// 코인명 바꾸기~~~~~~~~~~~~~~~``
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
			    //만약 매수 할 수 업는데 매수하라고하면????
			    
			}
			return order_id;
	}

}
