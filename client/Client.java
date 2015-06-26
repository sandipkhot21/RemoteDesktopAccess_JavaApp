package client;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class Client extends Thread
{
	private final DataOutputStream dos;
	private final ObjectInputStream ois;
	private final Robot robot;
	private final Socket socket;
	private ImageSend imgs;
	private MouseReceive mrcv;
	private MyLinkedList jobs = new MyLinkedList();

	public Client(String serverip, String cname, String strpass, int port) throws Exception
	{
		socket = new Socket(serverip, port);
		robot = new Robot();
		dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

		this.start();
		createIoThread();
	}

	private void createIoThread()
	{
		Thread ioThread = new Thread("io")
		{
			public void run()
			{
				while(true)
				{
					try
					{
						Thread.sleep(50);
						Object obj = jobs.removeFirst();
						if(obj instanceof ImageSend)
						{
//							System.out.println("Image Sent.....");
							ImageSend imgs = (ImageSend) obj;
							imgs.execute(dos);
						}
						else if(obj instanceof MouseReceive)
						{
							MouseReceive mrcv = (MouseReceive) obj;
							mrcv.execute(robot);
//							System.out.println("Mouse Event exec......");				
						}
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
						System.exit(0);
//						System.out.println("Error in Thread: " + ex);
					}
				}
			}
		};

		ioThread.start();
	}

	public void run()
	{
		try
		{
			while(true)
			{
				jobs.add(new ImageSend(robot));
				jobs.add(new MouseReceive(ois));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
//			System.out.println("Error in Thread: " + ex);
		}
	}
}
