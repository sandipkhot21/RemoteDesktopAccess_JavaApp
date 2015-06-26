package server;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;


public class Server extends Thread
{
	private final DataInputStream dis;
	private final ObjectOutputStream oos;
	private final Robot robot;
	private final Socket socket;
	public static String cname;
	private BufferedImage img;
	private ImageReceive imgr = new ImageReceive();
	private MouseEvent e;
	private MyLinkedList jobs = new MyLinkedList();
	private MyPanel pan;
	private JFrame frame;



	private volatile boolean running = true;

	public Server(String sname, String strpass, int port) throws Exception
	{
		ServerSocket ss = new ServerSocket(port);
		socket = ss.accept();
		robot = new Robot();
		dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		oos = new ObjectOutputStream(socket.getOutputStream());

		setupUI();

		createIoThread();
	}

	private void createIoThread()
	{
		Thread ioThread = new Thread("io")
		{
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(50);
						Object obj = jobs.removeFirst();
						if(obj instanceof ImageReceive)
						{
							imgr=(ImageReceive) obj;
							img = imgr.get();
//							System.out.println("Received screenshot of " + studentName);
							pan.repaint();
						}
						else if(obj instanceof MouseEvent)
						{
							oos.writeObject(obj);
							oos.flush();
//							System.out.println("MouseEvent sent!!!");
						}
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
//						System.out.println("Exception occurred: " + ex);
					}
				}
			}
		};

		ioThread.start();
	}

	class MyPanel extends JPanel
	{
		public MyPanel()
		{
			super();
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			try
			{
				g.drawImage(img,0,0,getWidth(),getHeight(),this);
//				System.out.println("Image Drawn on Panel");
//				System.out.println(img);
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
//				System.out.println("Error in Paint Component: " + ex);
			}
		}
	}

	private void setupUI() throws Exception
	{
		frame = new JFrame();
		new ImageReceive(dis);
//		System.out.println("Inside UI Setup!!!");
		img = imgr.get();
		frame.setTitle("Screen from " + cname);
		pan = new MyPanel();
		frame.add(pan);

		pan.addMouseListener
		(
			new MouseAdapter()
			{
				public void mouseClicked(MouseEvent me)
				{
					e = me;
//					System.out.println("Mouse Click Event....");
					if(running)
					{
						try
						{
							jobs.add(e);
							jobs.add(new ImageReceive(dis));
						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						Toolkit.getDefaultToolkit().beep();
					}
				}
			}
		);

		pan.addMouseMotionListener
		(
			new MouseAdapter()
			{
				public void mouseMoved(MouseEvent me)
				{
					e = me;
//					System.out.println("Mouse Move Event....");
					if(running)
					{
						try
						{
							jobs.add(e);
							jobs.add(new ImageReceive(dis));
						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						Toolkit.getDefaultToolkit().beep();
					}
				}
			}
		);

		frame.setSize(800, 900);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void run()
	{
		try
		{
			while(true)
			{
				Thread.sleep(100);
				jobs.add(new ImageReceive(dis));
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
//			System.out.println("Error in Thread: " + ex);
		}
	}
}
