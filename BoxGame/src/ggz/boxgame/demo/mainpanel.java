package ggz.boxgame.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class mainpanel extends JPanel implements KeyListener
{
	/*
	 * 数组中的数字代表的图形
	 * 0背景，1房屋，2绿地，3箱子
	 * 4目的地
	 * 5初始状态或者按下下的状态
	 * 6左
	 * 7右
	 * 8上
	 * 9成功后的箱子
	 */
	int max=20;
	int[][] map,maptmp;
	int manX,manY,boxnum;
	Image[] myImage;
	Readmap Levelmap;
	Readmap Levelmaptmp;
	int len=30;
	public int level=1;
	/*
	 * 建立一个堆栈，先进后出，取消,返回操作。
	 */
//	Stack mystack=new Stack();
	/*
	 * 设置一个image数组储存十个图片，每个对应不同状态
	 */
	mainpanel()
	{	
		setBounds(15,50,600,600);
		setBackground(Color.white);
		addKeyListener(this);
		myImage=new Image[10];
		for(int i=0; i<10; i++)
		{
		    myImage[i] = Toolkit.getDefaultToolkit().getImage("pic\\"+i+".gif");
		}
		
		setVisible(true);
	}

	void Tuixiangzi(int i)
	{
		Levelmap=new Readmap(i);
		Levelmaptmp=new Readmap(i);
		map=Levelmap.getmap();
		manX=Levelmap.getmanX();
		manY=Levelmap.getmanY();
		maptmp=Levelmaptmp.getmap();
		repaint();
	}
	
	/*
	 * 最大关卡
	 */
	int maxlevel(){return max;}

	public void paint(Graphics g)
	{
		for(int i=0; i<20; i++)
			for(int j=0; j<20; j++)
		    {
			    g.drawImage(myImage[map[j][i]],i*len,j*len,this);//一个图片的大小为30*30
			}	
	
		
				g.setColor(new Color(0,0,100));
				g.setFont(new Font("楷体_2312",Font.BOLD,30));
				g.drawString("现在是第",160,38);
				g.drawString(String.valueOf(level),310,38);
				g.drawString("关",350,38);
		
		}	

	

	/*
	 *按键响应
	 */
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_UP){moveup();}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){movedown();}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){moveleft();}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){moveright();}
		if(e.getKeyCode()==KeyEvent.VK_CONTROL){
			JOptionPane.showMessageDialog(this, "恭喜您通过此关卡！！！");
			level++;
			Tuixiangzi(level);
			if(level==max){JOptionPane.showMessageDialog(this, "恭喜您通过最后一关！！！");}
		}
		if(iswin())
		{
			if(level==max){JOptionPane.showMessageDialog(this, "恭喜您通过最后一关！！！");}
			else
			{
				String msg="恭喜您通过第"+level+"关!!!\n是否要进入下一关？";
				int type=JOptionPane.YES_NO_OPTION;
				String title="过关";
				int choice=0;
				choice=JOptionPane.showConfirmDialog(null,msg,title,type);
				if(choice==1)System.exit(0);
				else if(choice==0)
				{
					level++;
					Tuixiangzi(level);
				}
			}

		}
	}

	

	void remove(){
		}
	
	//向上移动实现
	void moveup()
	{
		if(map[manY-1][manX]==2||map[manY-1][manX]==4)//上一块为绿地或者目的地，判断有无障碍
		{
			if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
				map[manY][manX]=4;
			else map[manY][manX]=2;
			map[manY-1][manX]=8;
			repaint();manY--;
		}
		else if(map[manY-1][manX]==3)//判断前面是否有箱子
		{
			if(map[manY-2][manX]==4)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY-1][manX]=8;
				map[manY-2][manX]=9;
				repaint();manY--;
			}
			else if(map[manY-2][manX]==2)//如果上面两个为2即为绿地，移动，重绘
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY-1][manX]=8;
				map[manY-2][manX]=3;
				repaint();manY--;
			}
			else {map[manY][manX]=8;repaint();}
		}
		else if(map[manY-1][manX]==9)//上一个为已经进入目的地的箱子
		{
			if(map[manY-2][manX]==4)//上两格为target，移动，chonghui
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY-1][manX]=8;
				map[manY-2][manX]=9;
				repaint();manY--;
			}
			else if(map[manY-2][manX]==2)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY-1][manX]=8;
				map[manY-2][manX]=3;
				repaint();manY--;
			}
			else {map[manY][manX]=8;repaint();}
		}
		if(map[manY-1][manX]==1)
		{
			map[manY][manX]=8;repaint();
		}
	}
	

//向下移动实现
	void movedown()
	{
		if(map[manY+1][manX]==2||map[manY+1][manX]==4)
		{
			if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
				map[manY][manX]=4;
			else map[manY][manX]=2;
			map[manY+1][manX]=5;
			repaint();manY++;
		}
		else if(map[manY+1][manX]==3)
		{
			if(map[manY+2][manX]==4)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY+1][manX]=5;
				map[manY+2][manX]=9;
				repaint();manY++;
			}
			else if(map[manY+2][manX]==2)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY+1][manX]=5;
				map[manY+2][manX]=3;
				repaint();manY++;
			}
			else {map[manY][manX]=5;repaint();}
		}
		else if(map[manY+1][manX]==9)
		{
			if(map[manY+2][manX]==4)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY+1][manX]=5;
				map[manY+2][manX]=9;
				repaint();manY++;
			}
			else if(map[manY+2][manX]==2)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY+1][manX]=5;
				map[manY+2][manX]=3;
				repaint();manY++;
			}
			else {map[manY][manX]=5;repaint();}
		}
		else if(map[manY+1][manX]==1)
		{
			map[manY][manX]=5;repaint();
		}
	}


//向左移动实现
	void moveleft()
	{
		if(map[manY][manX-1]==2||map[manY][manX-1]==4)
		{
			if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
				map[manY][manX]=4;
			else map[manY][manX]=2;
			map[manY][manX-1]=6;			
			repaint();manX--;
		}
		else if(map[manY][manX-1]==3)
		{
			if(map[manY][manX-2]==4)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY][manX-1]=6;
				map[manY][manX-2]=9;
				repaint();manX--;
			}
			else if(map[manY][manX-2]==2)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY][manX-1]=6;
				map[manY][manX-2]=3;
				repaint();manX--;
			}
			else {map[manY][manX]=6;repaint();}
		}
		else if(map[manY][manX-1]==9)
		{
			if(map[manY][manX-2]==4)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY][manX-1]=6;
				map[manY][manX-2]=9;
				repaint();manX--;
			}
			else if(map[manY][manX-2]==2)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY][manX-1]=6;
				map[manY][manX-2]=3;
				repaint();manX--;
			}
			else {map[manY][manX]=6;repaint();}
		}
		else if(map[manY][manX-1]==1)
		{
			map[manY][manX]=6;repaint();
		}
	}


//向右移动实现
	void moveright()
	{
		if(map[manY][manX+1]==2||map[manY][manX+1]==4)
		{			
			if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
				map[manY][manX]=4;
			else map[manY][manX]=2;
			map[manY][manX+1]=7;			
			repaint();manX++;
		}
		else if(map[manY][manX+1]==3)
		{
			if(map[manY][manX+2]==4)
			{
				if(maptmp[manY][manX]==4)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY][manX+1]=7;
				map[manY][manX+2]=9;
				repaint();manX++;
			}
			else if(map[manY][manX+2]==2)
			{
				if(maptmp[manY][manX]==4)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY][manX+1]=7;
				map[manY][manX+2]=3;
				repaint();manX++;
			}
			else {map[manY][manX]=7;repaint();}
		}
		else if(map[manY][manX+1]==9)
		{
			if(map[manY][manX+2]==4)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY][manX+1]=7;
				map[manY][manX+2]=9;
				repaint();manX++;
			}
			else if(map[manY][manX+2]==2)
			{
				if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
					map[manY][manX]=4;
				else map[manY][manX]=2;
				map[manY][manX+1]=7;
				map[manY][manX+2]=3;
				repaint();manX++;
			}
			else {map[manY][manX]=7;repaint();}
		}
		else if(map[manY][manX+1]==1)
		{
			map[manY][manX]=7;repaint();
		}
	}


//判断输赢
	boolean iswin()
	{
		
		boolean num=false;
		out:for(int i=0; i<20; i++)
			for(int j=0; j<20; j++)
		{
			if(maptmp[i][j]==4||maptmp[i][j]==9)
				if(map[i][j]==9)
					num=true;
			    else {num=false;break out;}
		}
		return num;
	}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
}

