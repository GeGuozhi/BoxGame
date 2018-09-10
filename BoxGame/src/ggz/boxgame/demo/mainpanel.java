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
	 * �����е����ִ����ͼ��
	 * 0������1���ݣ�2�̵أ�3����
	 * 4Ŀ�ĵ�
	 * 5��ʼ״̬���߰����µ�״̬
	 * 6��
	 * 7��
	 * 8��
	 * 9�ɹ��������
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
	 * ����һ����ջ���Ƚ������ȡ��,���ز�����
	 */
//	Stack mystack=new Stack();
	/*
	 * ����һ��image���鴢��ʮ��ͼƬ��ÿ����Ӧ��ͬ״̬
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
	 * ���ؿ�
	 */
	int maxlevel(){return max;}

	public void paint(Graphics g)
	{
		for(int i=0; i<20; i++)
			for(int j=0; j<20; j++)
		    {
			    g.drawImage(myImage[map[j][i]],i*len,j*len,this);//һ��ͼƬ�Ĵ�СΪ30*30
			}	
	
		
				g.setColor(new Color(0,0,100));
				g.setFont(new Font("����_2312",Font.BOLD,30));
				g.drawString("�����ǵ�",160,38);
				g.drawString(String.valueOf(level),310,38);
				g.drawString("��",350,38);
		
		}	

	

	/*
	 *������Ӧ
	 */
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_UP){moveup();}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){movedown();}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){moveleft();}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){moveright();}
		if(e.getKeyCode()==KeyEvent.VK_CONTROL){
			JOptionPane.showMessageDialog(this, "��ϲ��ͨ���˹ؿ�������");
			level++;
			Tuixiangzi(level);
			if(level==max){JOptionPane.showMessageDialog(this, "��ϲ��ͨ�����һ�أ�����");}
		}
		if(iswin())
		{
			if(level==max){JOptionPane.showMessageDialog(this, "��ϲ��ͨ�����һ�أ�����");}
			else
			{
				String msg="��ϲ��ͨ����"+level+"��!!!\n�Ƿ�Ҫ������һ�أ�";
				int type=JOptionPane.YES_NO_OPTION;
				String title="����";
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
	
	//�����ƶ�ʵ��
	void moveup()
	{
		if(map[manY-1][manX]==2||map[manY-1][manX]==4)//��һ��Ϊ�̵ػ���Ŀ�ĵأ��ж������ϰ�
		{
			if(maptmp[manY][manX]==4||maptmp[manY][manX]==9)
				map[manY][manX]=4;
			else map[manY][manX]=2;
			map[manY-1][manX]=8;
			repaint();manY--;
		}
		else if(map[manY-1][manX]==3)//�ж�ǰ���Ƿ�������
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
			else if(map[manY-2][manX]==2)//�����������Ϊ2��Ϊ�̵أ��ƶ����ػ�
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
		else if(map[manY-1][manX]==9)//��һ��Ϊ�Ѿ�����Ŀ�ĵص�����
		{
			if(map[manY-2][manX]==4)//������Ϊtarget���ƶ���chonghui
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
	

//�����ƶ�ʵ��
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


//�����ƶ�ʵ��
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


//�����ƶ�ʵ��
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


//�ж���Ӯ
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

