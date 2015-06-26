package client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MouseReceive
{
	private MouseEvent me;
	private int mouseButton;
	private int clicks;
	private int x;
	private int y;
	private String par[] = null;

	public MouseReceive(ObjectInputStream ois) throws Exception
	{
//		System.out.println("In Mouse Receive constructor....");
		Object obj = ois.readObject();
		if(obj instanceof MouseEvent)
		{
			MouseEvent me = (MouseEvent) obj;
			if(me != null)
			{
				mouseButton = me.getModifiers();
				clicks = me.getClickCount();

				Point to = me.getPoint();
				x = (int)to.getX();
				y = (int)to.getY();
			}			
		}
	}

	public void execute(Robot robot) throws Exception
	{
//		System.out.println("In Mouse Receive execute....");
		if (clicks>0)
		{
			for(int i=0; i < clicks; i++)
			{
//				System.out.println("In Mouse Clicked execute....");
				robot.mousePress(mouseButton);
				robot.mouseRelease(mouseButton);
			}
		}
		else
		{
//			System.out.println("In Mouse Moved execute....");
			robot.mouseMove(x,y);
		}
	}
}
