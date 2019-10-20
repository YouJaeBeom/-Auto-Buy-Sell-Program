import java.io.IOException;

public class Str_process {
	
	public void auto(String coin_name,String type,int count,String price)  {
		// TODO Auto-generated method stub
		String status="";
		if(type.contains("bid")&&isNumber(price)) {
			
			Auto_buy auto_buy=new Auto_buy();
			String order_id=auto_buy.autobuy(coin_name,price);
			System.out.println(price+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			//Static_setting.order_id=order_id;
			Order_check order_check1=new Order_check();
			order_check1.order_check(order_id, type, coin_name);
		}//if(type.contains("bid"))
		
		else if(type.contains("ask")&&isNumber(price)) {
			Auto_sell auto_sell=new Auto_sell();
			String order_id=auto_sell.autosell(coin_name,price);
			System.out.println(price+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			//Static_setting.order_id=order_id;
			Order_check order_check1=new Order_check();
			order_check1.order_check(order_id, type, coin_name);
		}//if(type.contains("ask"))
		else if(type.contains("bid")&&price.contains("mar")) 
		{
			Market_buy market_buy=new Market_buy();
			String order_id=market_buy.marketbuy(coin_name);
			//Static_setting.order_id=order_id;
			Order_check order_check1=new Order_check();
			order_check1.order_check(order_id, type, coin_name);
		}
		else if(type.contains("ask")&&price.contains("mar")) 
		{
			Market_sell market_sell=new Market_sell();
			String order_id=market_sell.marketsell(coin_name);
			//Static_setting.order_id=order_id;
			Order_check order_check1=new Order_check();
			order_check1.order_check(order_id, type, coin_name);
		}
		
	}
	 public static boolean isNumber(String str){
	        boolean result = false;
	         
	         
	        try{
	            Double.parseDouble(str) ;
	            result = true ;
	        }catch(Exception e){}
	         
	         
	        return result ;
	    }



	/*
	 * 캔슬 요청시
	 */
	public void cancel (String coin_name,String type,int count,String price)
	{
		
			Auto_cancel auto_cancel=new Auto_cancel();
			auto_cancel.auto_cancel(Static_setting.order_id, type, coin_name); // 취소한다.
			
	}
}
