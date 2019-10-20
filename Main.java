import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import com.sun.prism.paint.Color;

public class Main extends JFrame{
	
	JButton Enr_btn ;
	JButton con_btn ;
	JButton discon_btn ;
	public static JTextArea result_Jtext;
	public Main()
	{
		//제목 설정
		Dimension dim = new Dimension(400,150);
		JFrame frame =new JFrame("제목은 여기에!!");
		frame.setLocation(200,400);
		frame.setPreferredSize(dim);
		
	
		// 세팅 판넬
		JPanel setting_panel =new JPanel(); // key 2개 입력 칸과 com port선택창 추가
		setting_panel.setLayout(new BoxLayout(setting_panel,BoxLayout.X_AXIS));
		setting_panel.add(new JLabel("Connect_Key"));
		final JTextField Connect_Key=new JTextField(20);
		setting_panel.add(Connect_Key);
		setting_panel.add(new JLabel("Secret_Key"));
		final JTextField Secret_Key=new JTextField(20);
		setting_panel.add(Secret_Key);
		setting_panel.add(new JLabel("COM_Port"));
		final JTextField COM_Port=new JTextField(20);
		setting_panel.add(COM_Port);
		/*String[] listEntries= {"COM1","COM2","COM3","COM4","COM5","COM6"};
		JList com_port=new JList(listEntries);
		JScrollPane scroller=new JScrollPane(com_port);
		com_port.setVisibleRowCount(3);
		com_port.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		setting_panel.add(scroller);*/
		
		
		// 버튼 판넬
		JPanel btn_panel =new JPanel(); // 등록 키 및 시작 정지 버튼 누르기
		btn_panel.setLayout(new BoxLayout(btn_panel,BoxLayout.X_AXIS));
		Enr_btn = new JButton("등록 하기");
		 con_btn = new JButton("연결하기");
		 discon_btn = new JButton("연결해제");
		 Enr_btn.setBackground(SystemColor.LIGHT_GRAY);
		 con_btn.setBackground(SystemColor.LIGHT_GRAY);
		 discon_btn.setBackground(SystemColor.LIGHT_GRAY);
		btn_panel.add(Enr_btn);
		btn_panel.add(con_btn);
		btn_panel.add(discon_btn);
	
		//결과 창 판넬
		JPanel result_panel =new JPanel(); // key 2개 입력 칸과 com port선택창 추가
		result_panel.setLayout(new BoxLayout(result_panel,BoxLayout.X_AXIS));
		result_panel.add(new JLabel("Connect Key"));
		result_Jtext=new JTextArea(10,20);
		JScrollPane scroller=new JScrollPane(result_Jtext);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		result_panel.add(scroller);
		
		
		
		
		JPanel total_panel =new JPanel();
		total_panel.setLayout(new BoxLayout(total_panel,BoxLayout.Y_AXIS));
		total_panel.add(setting_panel);
		total_panel.add(btn_panel);
		total_panel.add(result_panel);
		
		frame.add(total_panel,BorderLayout.CENTER);
		
		//final String coin_name="TMTG";
		frame.pack();
		Start start= new Start();
		//등록하기 
		Enr_btn.addActionListener(new ActionListener(){ //익명클래스로 리스너 작성
			public void actionPerformed(ActionEvent e){
				//Enr_btn.setBackground(SystemColor.control);
				Enr_btn.setBackground(SystemColor.gray);
				Enr_btn.setText("등록 완료");
				//String api_key=Jtext.getText();
				String Connect_key="";
				String Secret_key="";
				String com_port="";
				if(Connect_Key.getText().isEmpty()||Secret_Key.getText().isEmpty()||COM_Port.getText().isEmpty())
				{ // 어떤것이 입력이 재대로 안됨
					result_Jtext.append("입력이 완벽하지 않습니다."+"\n");
				}
				else { // 제대로 입력이됨
					 Static_setting.Connect_key=Connect_Key.getText();
					 Static_setting.Secret_key=Secret_Key.getText();
					 Static_setting.com_port=COM_Port.getText();
					 result_Jtext.append("나의 Connect_key : "+Static_setting.Connect_key+"\n");
					 result_Jtext.append("나의 Secret_key : "+Static_setting.Secret_key+"\n");
					 result_Jtext.append("나의 com_port : "+Static_setting.com_port+"\n");
					 result_Jtext.setCaretPosition(result_Jtext.getDocument().getLength());  // 맨아래로 스크롤한다.

				}
				
				 
				}
		});
		
		/*연결하기 버튼 클릭시 
		 * 시리얼 포트 연결하여 시리얼 값받고 보내서 자동 매수 매도 실행하도록 이동하게
		 * 시리얼 포트 클래스 생성해서 그기로 이동하면 될듯 */
		//연결하기 //사는거 매수 
		con_btn.addActionListener(new ActionListener(){ //익명클래스로 리스너 작성
			public void actionPerformed(ActionEvent e){
			//String api_key=Jtext.getText();
				con_btn.setBackground(SystemColor.gray);
				con_btn.setText("연결 완료");
				Start start= new Start();
				try {
					start.connect(Static_setting.com_port);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
			}
		});
		
		/*연결 해제 버튼 클릭시
		 * api키를 null로 세팅하여 사용 못하도록 다시 세팅할 수 있도록 유도*/
		//연결 해제 //파는거 매도
		discon_btn.addActionListener(new ActionListener(){ //익명클래스로 리스너 작성
			public void actionPerformed(ActionEvent e){
				Enr_btn.setBackground(SystemColor.LIGHT_GRAY);
				Enr_btn.setText("등록 하기");
				con_btn.setBackground(SystemColor.LIGHT_GRAY);
				con_btn.setText("연결하기");
				Static_setting.Connect_key=null;
				 Static_setting.Secret_key=null;
				 Static_setting.com_port=null;
				 result_Jtext.append("연결이 해제되었습니다.\n"+"재시도시 다시 키를 등록해주세요.\n");
				 result_Jtext.setCaretPosition(result_Jtext.getDocument().getLength());  // 맨아래로 스크롤한다.
				 System.exit(0);
			}
		});
		
		//프레임 보이도록 설정
		frame.setVisible(true);
		
		//X버튼 눌렀을 때 닫히도록 설정
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	

	public static void setText(String uploadstr) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyy년-MM월-dd일-HH:mm:ss").format(date));
		result_Jtext.append(today+"에 업로드 = "+uploadstr+"\n");
		result_Jtext.setCaretPosition(result_Jtext.getDocument().getLength());  // 맨아래로 스크롤한다.
	}



	public static void main(String[] args)
	{
		
		//실행
		new Main();
	}
	
    
		
    
}

