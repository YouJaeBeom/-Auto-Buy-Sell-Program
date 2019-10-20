import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Start {


	
	public void connect(String portName) throws Exception {
		 System.out.println("클래스안으로 ");
	        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
	        
	        if (portIdentifier.isCurrentlyOwned()) {
	            System.out.println("Error: Port is currently in use");
	        } else {
	            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

	            if (commPort instanceof SerialPort) {
	                SerialPort serialPort = (SerialPort) commPort;
	                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
	                        SerialPort.PARITY_NONE);

	                InputStream in = serialPort.getInputStream();
	                OutputStream out = serialPort.getOutputStream();

	                (new Thread(new Serial_Writer(out))).start();
	                (new Thread(new Serial_Reader(in))).start();
	                //(new Thread(new SerialWriter(out))).start();

	                
	            } else {
	                System.out.println("Error: Only serial ports are handled by this example.");
	            }
	        }
	    }	

	
	/*
	 * 시리얼 읽는곳
	 */
	public static class Serial_Reader implements Runnable 
	{
			InputStream in ;
			public Serial_Reader(InputStream in) {
				// TODO Auto-generated constructor stub
				this.in=in;
			}

			@Override
			public void run() {
				// TODO Auto-generated method stub
				byte[] buffer = new byte[1024];
				int len = -1;
				String status;
				Str_process str_process =new Str_process();
				try {
					while(true) 
					{ // 계속해서 돌아가는중......
						String str="";
						status="<<00000/000/000"; //17byte; 기본적으로
						while ((len = this.in.read(buffer)) > -1)
						{ //읽어드리다가.
							
							if(len>=1)
							{
								str+=new String(buffer, 0, len);
								if(str.contains("start")||str.contains("cancel"))
								{
									String[] split_str=str.split("/");
									/*
									 * split_str[0] = coin_name
									 * split_str[1] = price
									 * split_str[2] = bid or ask
									 * split_str[3] = start or cancel
									 */
									String coin_name=split_str[0];
									String price =split_str[1];
								
									if(str.contains("bid")&&str.contains("start"))
									{
										//매수시작
										Main.setText(str);
										str_process.auto(coin_name,"bid",0,price);
										
									}
									if(str.contains("ask")&&str.contains("start"))
									{
										//매도시작
										Main.setText(str);
										str_process.auto(coin_name,"ask",0,price);	
									}
									if(str.contains("bid")&&str.contains("cancel"))
									{
										//매수취소
										Main.setText(str);
										str_process.cancel(coin_name,"bid",0,price);
									}
									if(str.contains("ask")&&str.contains("cancel"))
									{
										//매도취소
										Main.setText(str);
										str_process.cancel(coin_name,"ask",0,price);
									}
								}
							}
							else if(len==0)
							{
								str="";
							}
						    
						}
						
					}
				
					
					} catch (IOException e) {
					e.printStackTrace();
					run();
				}
			}
		
	}
	
	/*
	 * 시리얼 쓰는곳
	 */
	public static class Serial_Writer implements Runnable 
	{
			
			OutputStream out;
			public Serial_Writer(OutputStream out) {
				this.out = out;
			}
			
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					int l=0;
					while(true)
					{
						l++;
						
						if(l==30) {
							
						Api_Client api = new Api_Client(Static_setting.Connect_key,
								Static_setting.Secret_key);
						HashMap<String, Object> rgParams = new HashMap<String, Object>();
							 String result4 = api.callApi_Balnace("/info/balance", rgParams);
							    Static_setting.mykrw=result4;
							    l=0;
						}
						if(Static_setting.krwcheck.equals("no")) {
						int coinname_len = 0;
						int coinname_len1 = 0;
						int type11_len = 0;
						int type11_len1 = 0;
						int status_len = 0;
						int status_len1 = 0;
						int mykrw_len = 0;
						int mykrw_len1 = 0;
						String[] check_list1=Static_setting.coin_name.split("");
						String[] coinname= {"<","<","0","0","0","0","0"};
						String[] check_list2=Static_setting.type.split("");
						String[] type11= {"/","/","0","0","0"};
						String[] check_list3=Static_setting.comOrpla.split("");
						String[] status1= {"/","/","0","0","0"};
						String[] check_list4=Static_setting.mykrw.split("");
						String[] krw1= {"/","/","0","0","0","0","0","0","0","0","0"};
						//코인명 비트화 할때........
						if(Static_setting.coin_name==""&&Static_setting.type==""&&Static_setting.comOrpla==""&&Static_setting.mykrw=="")
						{
							coinname_len = 0;
							coinname_len1 = 0;
							type11_len = 0;
							type11_len1 = 0;
							status_len = 0;
						    status_len1 = 0;
						     mykrw_len = 0;
							 mykrw_len1 = 0;
						}
						else {
						check_list1=Static_setting.coin_name.split("");
						
						 coinname_len=check_list1.length;
						
						 coinname_len1=check_list1.length;
						for(int i=6;i>6-coinname_len;i--)
						{
							coinname[i]=check_list1[coinname_len1-1];
							coinname_len1--;
						}
						
						//type 비트화 할떄...........
						
						check_list2=Static_setting.type.split("");
						
						 type11_len=check_list2.length;
						
						 type11_len1=check_list2.length;
						for(int i=4;i>4-type11_len;i--)
						{
							type11[i]=check_list2[type11_len1-1];
							type11_len1--;
						}
						
						//상태 비트화 할때...........
						check_list3=Static_setting.comOrpla.split("");
						
						 status_len=check_list3.length;
						 status_len1=check_list3.length;
						for(int i=4;i>4-status_len;i--)
						{
							status1[i]=check_list3[status_len1-1];
							status_len1--;
						}
						
						// 현재원화를 비트화
						check_list4=Static_setting.mykrw.split("");
						
						 mykrw_len=check_list4.length;
						 mykrw_len1=check_list4.length;
						for(int i=10;i>10-mykrw_len;i--)
						{
							krw1[i]=check_list4[mykrw_len1-1];
							mykrw_len1--;
						}
						}
						//비트 통합
						ArrayList list =new ArrayList(Arrays.asList(coinname));
						list.addAll(Arrays.asList(type11));
						list.addAll(Arrays.asList(status1));
						list.addAll(Arrays.asList(krw1));
						Object[] all=list.toArray();
						for(int y=0; y<list.size();y++)
						{
							out.write(((String) list.get(y)).getBytes());
						}
						out.write("\n".getBytes());
						Thread.sleep(100);
						}
						
					}
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
					run();
				}
				
			}
	}
}
