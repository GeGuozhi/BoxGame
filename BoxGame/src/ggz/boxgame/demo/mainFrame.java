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
	 * renew.重来  
	 */
	JButton buttonrenew,buttonlast,buttonnext,buttonchooseLev;
	//buttonback;//悔一步，back
	mainpanel panel;

	/*
	 * 设置模块名称
	 */
	MenuItem about=new MenuItem("    关于推箱子...");
	mainFrame()
	{
		/*
		 * 设置主页面
		 */
		super("实习项目推箱子");
		setSize(720,720);
		setVisible(true);
		setResizable(false);
		setLocation(300,20);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * set  coler 背景为黑色
		 */
		Container container	=getContentPane();
		container.setLayout(null);
		container.setBackground(Color.black);

		/*
		 * 设置监听器
		 */



		

		
		Menu help=new Menu("    帮助");
		help.add(about);
		about.addActionListener(this);
		MenuBar bar=new MenuBar();
		bar.add(help);
		setMenuBar(bar);                                        
		Label=new JLabel("实习项目推箱子",SwingConstants.CENTER);
		add(Label);

		Label.setBounds(100,20,400,20);
		Label.setForeground(Color.white);
/*
 * 设置按钮
 */
		buttonrenew=new JButton("重来");
		buttonlast=new JButton("上一关");
		buttonnext=new JButton("下一关");
		buttonchooseLev=new JButton("选关");

		add(buttonrenew);add(buttonlast);add(buttonnext);add(buttonchooseLev);
		/*
		 * 设置按钮的位置
		 */
		buttonrenew.setBounds(625,100,80,30);
		buttonrenew.addActionListener(this);
		buttonlast.setBounds(625,150,80,30);
		buttonlast.addActionListener(this);
		buttonnext.setBounds(625,200,80,30);
		buttonnext.addActionListener(this);
		buttonchooseLev.setBounds(625,250,80,30);
		buttonchooseLev.addActionListener(this);
//绘制
		panel=new mainpanel();
		add(panel);
		panel.Tuixiangzi(panel.level);
		panel.requestFocus();
		validate();
	}
	/*
	 *鼠标响应
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
			{panel.level++;JOptionPane.showMessageDialog(this,"本关是第一关");panel.requestFocus();}
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
			{panel.level--;JOptionPane.showMessageDialog(this,"本关已是最后一关");panel.requestFocus();}
			else 
			{
				panel.Tuixiangzi(panel.level);
				panel.requestFocus();
			}
			panel.remove();
		}
		else if(e.getSource()==about)
		{
			JOptionPane.showMessageDialog(this, "推箱子,author:ggz");
		}
		else if(e.getSource()==buttonchooseLev)
		{
			String lel=JOptionPane.showInputDialog(this,"请输入跳转的关卡");
			panel.level=Integer.parseInt(lel);
			panel.remove();
			if(panel.level>panel.maxlevel()||panel.level<1)
			{JOptionPane.showMessageDialog(this, "没有这一关！");panel.requestFocus();}
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