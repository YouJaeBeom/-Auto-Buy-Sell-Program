import java.util.HashMap;

public class Mykrw_check {


	@SuppressWarnings("unchecked")
	public static void krwcheck()
	{
		
			Api_Client api = new Api_Client(Static_setting.Connect_key,
				Static_setting.Secret_key);

			HashMap<String, Object> rgParams = new HashMap<String, Object>();
			Static_setting.krwcheck="yes";
		

			try {
			    String result = api.callApi_Balnace("/info/balance", rgParams);
			    Static_setting.mykrw=result;
//			    String[] krw= {"0","0","0","0","0","0","0","0","0","0","0"};
//			    String[] mykrw=result.split("");
//			    int mykrw_length1=mykrw.length;
//			    int mykrw_length2=mykrw.length;
//			    for(int i=0; i<mykrw_length1;i++)
//			    {
//			    	krw[i]=mykrw[mykrw_length1-i-1];
//			    }
//			    String status="";
//			    String type="";
//			    String coin="";
//			    for(int i=0;i<3;i++)
//			    {
//			    	status+=krw[2-i];
//			    }
//			    for(int i=0;i<3;i++)
//			    {
//			    	type+=krw[5-i];
//			    }
//			    for(int i=0;i<5;i++)
//			    {
//			    	coin+=krw[10-i];
//			    }
//			    Static_setting.comOrpla=status;
//			    Static_setting.type=type;
//			    Static_setting.coin_name=coin;
//			    System.out.println("@@@@@"+status+"\n");
//			    System.out.println("@@@@@"+type+"\n");
//			    System.out.println("@@@@@"+coin+"\n");
//			    System.out.println("@@@@@"+result+"\n");
			   
			
			    
			    
			} catch (Exception e1) {
			    e1.printStackTrace();
			    //만약 매수 할 수 업는데 매수하라고하면????
			    
			}
			
	}

}
