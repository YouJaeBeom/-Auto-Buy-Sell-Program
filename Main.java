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
		//���� ����
		Dimension dim = new Dimension(400,150);
		JFrame frame =new JFrame("������ ���⿡!!");
		frame.setLocation(200,400);
		frame.setPreferredSize(dim);
		
	
		// ���� �ǳ�
		JPanel setting_panel =new JPanel(); // key 2�� �Է� ĭ�� com port����â �߰�
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
		
		
		// ��ư �ǳ�
		JPanel btn_panel =new JPanel(); // ��� Ű �� ���� ���� ��ư ������
		btn_panel.setLayout(new BoxLayout(btn_panel,BoxLayout.X_AXIS));
		Enr_btn = new JButton("��� �ϱ�");
		 con_btn = new JButton("�����ϱ�");
		 discon_btn = new JButton("��������");
		 Enr_btn.setBackground(SystemColor.LIGHT_GRAY);
		 con_btn.setBackground(SystemColor.LIGHT_GRAY);
		 discon_btn.setBackground(SystemColor.LIGHT_GRAY);
		btn_panel.add(Enr_btn);
		btn_panel.add(con_btn);
		btn_panel.add(discon_btn);
	
		//��� â �ǳ�
		JPanel result_panel =new JPanel(); // key 2�� �Է� ĭ�� com port����â �߰�
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
		//����ϱ� 
		Enr_btn.addActionListener(new ActionListener(){ //�͸�Ŭ������ ������ �ۼ�
			public void actionPerformed(ActionEvent e){
				//Enr_btn.setBackground(SystemColor.control);
				Enr_btn.setBackground(SystemColor.gray);
				Enr_btn.setText("��� �Ϸ�");
				//String api_key=Jtext.getText();
				String Connect_key="";
				String Secret_key="";
				String com_port="";
				if(Connect_Key.getText().isEmpty()||Secret_Key.getText().isEmpty()||COM_Port.getText().isEmpty())
				{ // ����� �Է��� ���� �ȵ�
					result_Jtext.append("�Է��� �Ϻ����� �ʽ��ϴ�."+"\n");
				}
				else { // ����� �Է��̵�
					 Static_setting.Connect_key=Connect_Key.getText();
					 Static_setting.Secret_key=Secret_Key.getText();
					 Static_setting.com_port=COM_Port.getText();
					 result_Jtext.append("���� Connect_key : "+Static_setting.Connect_key+"\n");
					 result_Jtext.append("���� Secret_key : "+Static_setting.Secret_key+"\n");
					 result_Jtext.append("���� com_port : "+Static_setting.com_port+"\n");
					 result_Jtext.setCaretPosition(result_Jtext.getDocument().getLength());  // �ǾƷ��� ��ũ���Ѵ�.

				}
				
				 
				}
		});
		
		/*�����ϱ� ��ư Ŭ���� 
		 * �ø��� ��Ʈ �����Ͽ� �ø��� ���ް� ������ �ڵ� �ż� �ŵ� �����ϵ��� �̵��ϰ�
		 * �ø��� ��Ʈ Ŭ���� �����ؼ� �ױ�� �̵��ϸ� �ɵ� */
		//�����ϱ� //��°� �ż� 
		con_btn.addActionListener(new ActionListener(){ //�͸�Ŭ������ ������ �ۼ�
			public void actionPerformed(ActionEvent e){
			//String api_key=Jtext.getText();
				con_btn.setBackground(SystemColor.gray);
				con_btn.setText("���� �Ϸ�");
				Start start= new Start();
				try {
					start.connect(Static_setting.com_port);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
			}
		});
		
		/*���� ���� ��ư Ŭ����
		 * apiŰ�� null�� �����Ͽ� ��� ���ϵ��� �ٽ� ������ �� �ֵ��� ����*/
		//���� ���� //�Ĵ°� �ŵ�
		discon_btn.addActionListener(new ActionListener(){ //�͸�Ŭ������ ������ �ۼ�
			public void actionPerformed(ActionEvent e){
				Enr_btn.setBackground(SystemColor.LIGHT_GRAY);
				Enr_btn.setText("��� �ϱ�");
				con_btn.setBackground(SystemColor.LIGHT_GRAY);
				con_btn.setText("�����ϱ�");
				Static_setting.Connect_key=null;
				 Static_setting.Secret_key=null;
				 Static_setting.com_port=null;
				 result_Jtext.append("������ �����Ǿ����ϴ�.\n"+"��õ��� �ٽ� Ű�� ������ּ���.\n");
				 result_Jtext.setCaretPosition(result_Jtext.getDocument().getLength());  // �ǾƷ��� ��ũ���Ѵ�.
				 System.exit(0);
			}
		});
		
		//������ ���̵��� ����
		frame.setVisible(true);
		
		//X��ư ������ �� �������� ����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	

	public static void setText(String uploadstr) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyy��-MM��-dd��-HH:mm:ss").format(date));
		result_Jtext.append(today+"�� ���ε� = "+uploadstr+"\n");
		result_Jtext.setCaretPosition(result_Jtext.getDocument().getLength());  // �ǾƷ��� ��ũ���Ѵ�.
	}



	public static void main(String[] args)
	{
		
		//����
		new Main();
	}
	
    
		
    
}

