package ggz.boxgame.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Readmap
{
	private int level,mx,my;
	//mapΪ20*20������
	private int[][] mymap=new int[20][20];
	FileReader r;
	BufferedReader bufferedReader;String bb="";
	int[] x;int c=0;
    Readmap(int k)
	{
		level=k;
		String s;
		try
		{
			//��ȡmaps�ļ��µĹؿ���level+.mapΪ���ֵ��ļ�
			File file=new File("maps\\"+level+".map");
			r=new FileReader(file);
			bufferedReader=new BufferedReader(r);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		try
		{
			while ((s=bufferedReader.readLine())!=null)
			{
				bb=bb+s;
			}
		}
		catch (IOException g)
		{
			System.out.println("��ȡʧ��");
		}
		//���ַ���ת�����ֽ�����
		byte[] d=bb.getBytes();
		int len=bb.length();
		int[] x=new int[len];
		for(int i=0;i<bb.length();i++)x[i]=d[i]-48;//��ASCIIת��������
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<20;j++)
		    {
				mymap[i][j]=x[c];
		        if(mymap[i][j]==5)
		        {
					mx=j;my=i;
		        }
		        c++;
		    }
	    }
	}
	int[][] getmap(){return mymap;}//mymap��ȡ�ĵ�ͼ����
	int getmanX(){return mx;}
	int getmanY(){return my;}
}