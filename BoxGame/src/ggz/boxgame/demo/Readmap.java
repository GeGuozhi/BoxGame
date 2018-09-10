package ggz.boxgame.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Readmap
{
	private int level,mx,my;
	//map为20*20的数组
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
			//读取maps文件下的关卡，level+.map为名字的文件
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
			System.out.println("读取失败");
		}
		//将字符串转化成字节数组
		byte[] d=bb.getBytes();
		int len=bb.length();
		int[] x=new int[len];
		for(int i=0;i<bb.length();i++)x[i]=d[i]-48;//将ASCII转化成数字
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
	int[][] getmap(){return mymap;}//mymap读取的地图数组
	int getmanX(){return mx;}
	int getmanY(){return my;}
}