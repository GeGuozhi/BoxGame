package ggz.boxgame.demo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

class mainFrame extends JFrame implements ActionListener,ItemListener
{
	JLabel Label;

	/*
	 * renew.����  
	 */
	JButton buttonrenew,buttonlast,buttonnext,buttonchooseLev;
	//buttonback;//��һ����back
	mainpanel panel;

	/*
	 * ����ģ������
	 */
	MenuItem about=new MenuItem("    ����������...");
	mainFrame()
	{
		/*
		 * ������ҳ��
		 */
		super("ʵϰ��Ŀ������");
		setSize(720,720);
		setVisible(true);
		setResizable(false);
		setLocation(300,20);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * set  coler ����Ϊ��ɫ
		 */
		Container container	=getContentPane();
		container.setLayout(null);
		container.setBackground(Color.black);

		/*
		 * ���ü�����
		 */



		

		
		Menu help=new Menu("    ����");
		help.add(about);
		about.addActionListener(this);
		MenuBar bar=new MenuBar();
		bar.add(help);
		setMenuBar(bar);                                        
		Label=new JLabel("ʵϰ��Ŀ������",SwingConstants.CENTER);
		add(Label);

		Label.setBounds(100,20,400,20);
		Label.setForeground(Color.white);
/*
 * ���ð�ť
 */
		buttonrenew=new JButton("����");
		buttonlast=new JButton("��һ��");
		buttonnext=new JButton("��һ��");
		buttonchooseLev=new JButton("ѡ��");

		add(buttonrenew);add(buttonlast);add(buttonnext);add(buttonchooseLev);
		/*
		 * ���ð�ť��λ��
		 */
		buttonrenew.setBounds(625,100,80,30);
		buttonrenew.addActionListener(this);
		buttonlast.setBounds(625,150,80,30);
		buttonlast.addActionListener(this);
		buttonnext.setBounds(625,200,80,30);
		buttonnext.addActionListener(this);
		buttonchooseLev.setBounds(625,250,80,30);
		buttonchooseLev.addActionListener(this);
//����
		panel=new mainpanel();
		add(panel);
		panel.Tuixiangzi(panel.level);
		panel.requestFocus();
		validate();
	}
	/*
	 *�����Ӧ
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==buttonrenew)
		{
			panel.Tuixiangzi(panel.level);
			panel.requestFocus();
			panel.remove();
		}
		else if(e.getSource()==buttonlast)
		{
			panel.level--;
			if(panel.level<1)
			{panel.level++;JOptionPane.showMessageDialog(this,"�����ǵ�һ��");panel.requestFocus();}
			else 
			{
				panel.Tuixiangzi(panel.level);
				panel.requestFocus();
			}
			panel.remove();
		}
		else if(e.getSource()==buttonnext)
		{
			panel.level++;
			if(panel.level>panel.maxlevel())
			{panel.level--;JOptionPane.showMessageDialog(this,"�����������һ��");panel.requestFocus();}
			else 
			{
				panel.Tuixiangzi(panel.level);
				panel.requestFocus();
			}
			panel.remove();
		}
		else if(e.getSource()==about)
		{
			JOptionPane.showMessageDialog(this, "������,author:ggz");
		}
		else if(e.getSource()==buttonchooseLev)
		{
			String lel=JOptionPane.showInputDialog(this,"��������ת�Ĺؿ�");
			panel.level=Integer.parseInt(lel);
			panel.remove();
			if(panel.level>panel.maxlevel()||panel.level<1)
			{JOptionPane.showMessageDialog(this, "û����һ�أ�");panel.requestFocus();}
			else
				{
				panel.Tuixiangzi(panel.level);
				panel.requestFocus();
				}
		}
	}
		



	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}