package start;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import client.*;
import server.*;


public class RDSharing extends JPanel
{
	JFrame frame;
	JPanel pan1, pan2, pan3, pan4;
	JLabel lip, lid, lch;
	JTextField tfname;
	JPasswordField tfpass;
	JRadioButton rbclient, rbserver;
	JButton bsubmit, breset;
	protected static String strname, strpass;

	public RDSharing()
	{
		frame = new JFrame();
		pan1 = new JPanel(new GridLayout(2,2));
		pan2 = new JPanel(new GridLayout(1,3));
		pan3 = new JPanel(new GridLayout(1,2));
		pan4 = new JPanel(new GridLayout(3,1));

		lip = new JLabel("Enter Your Comp name: ");
		lid = new JLabel("Enter the pass-code to use: ");
		lch = new JLabel("Choice: ");
		tfname = new JTextField(20);
		tfpass = new JPasswordField(20);
		rbclient = new JRadioButton("Client", false);
		rbserver = new JRadioButton("Server", false);
		bsubmit = new JButton("Submit");
		breset = new JButton("Reset");

		ButtonGroup bg = new ButtonGroup();
		bg.add(rbclient);
		bg.add(rbserver);

		pan1.add(lip);
		pan1.add(tfname);
		pan1.add(lid);
		pan1.add(tfpass);

		pan2.add(lch);
		pan2.add(rbclient);
		pan2.add(rbserver);

		pan3.add(bsubmit);
		pan3.add(breset);

		pan4.add(pan1);
		pan4.add(pan2);
		pan4.add(pan3);

		frame.setTitle("Remote Desktop Sharing");
		frame.add(pan4);
		frame.setSize(500,150);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ButtonHandler bh = new ButtonHandler();
		bsubmit.addActionListener(bh);
		breset.addActionListener(bh);
	}

	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getSource() == bsubmit)
			{
				if(rbclient.isSelected())
				{
					if(tfname.getText().equals("") || tfpass.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please fill all the details.....", "Error!!!",JOptionPane.PLAIN_MESSAGE);
					else
					{
						strname = tfname.getText();
						strpass = tfpass.getText();
						frame.dispose();
						new RDSClient();
					}
				}
				if(rbserver.isSelected())
				{
					if(tfname.getText().equals("") || tfpass.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please fill all the details.....", "Error!!!",JOptionPane.PLAIN_MESSAGE);
					else
					{
						strname = tfname.getText();
						strpass = tfpass.getText();
						frame.dispose();
						new RDSServer();
					}
				}
				if(!rbclient.isSelected() && !rbserver.isSelected())
				{
					JOptionPane.showMessageDialog(frame, "Please fill all the details.....", "Error!!!",JOptionPane.PLAIN_MESSAGE);
				}
			}

			if(ae.getSource() == breset)
			{
				tfname.setText("");
				tfpass.setText("");
				rbclient.setSelected(false);
				rbclient.setSelected(false);
			}
		}
	}

	public static void main(String args[])
	{
		new RDSharing();
	}
}

class RDSClient extends JPanel
{
	JFrame frame;
	JPanel pan1, pan2, pan3;
	JLabel lip, lport;
	JTextField tfip, tfport;
	JButton bconnect, bexit;
	JMenuBar mbmain;
	JMenu mnufile, mnuabt;
	JMenuItem minew, miexit, miabt;
	String strip;
	int port;

	public RDSClient()
	{
		frame = new JFrame();

		pan1 = new JPanel(new GridLayout(2,2));
		pan2 = new JPanel(new GridLayout(1,2));
		pan3 = new JPanel();

		lip = new JLabel("IP Address: ");
		lport = new JLabel("Port Number: ");
		tfip = new JTextField(20);
		tfport = new JTextField(20);
		bconnect = new JButton("Connect");
		bexit = new JButton("Exit");

		mbmain = new JMenuBar();
		mnufile = new JMenu("File");
		mnuabt = new JMenu("About");
		minew = new JMenuItem("New");
		miexit = new JMenuItem("Exit");
		miabt = new JMenuItem("About");

		mnuabt.add(miabt);
		mnufile.add(minew);
		mnufile.add(miexit);
		mbmain.add(mnufile);
		mbmain.add(mnuabt);

		pan1.add(lip);
		pan1.add(tfip);
		pan1.add(lport);
		pan1.add(tfport);

		pan2.add(bconnect);
		pan2.add(bexit);

		pan3.add(pan1);
		pan3.add(pan2);

		frame.setTitle("Remote Desktop Sharing: Client Mode");
		frame.setJMenuBar(mbmain);
		frame.add(pan3);
		frame.setSize(600,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MBHandler mbh = new MBHandler();
		bconnect.addActionListener(mbh);
		bexit.addActionListener(mbh);
		miabt.addActionListener(mbh);
		minew.addActionListener(mbh);
		miexit.addActionListener(mbh);
	}

	class MBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			try
			{
				if(ae.getSource() == bconnect)
				{
					if(tfip.getText().equals("") || tfport.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please fill all the details.....", "Error!!!",JOptionPane.PLAIN_MESSAGE);

					else
					{
						strip = tfip.getText();
						port = Integer.parseInt(tfport.getText());

						if(SValidate.validatePwd(strip, RDSharing.strname, RDSharing.strpass, port))
							new Client(strip,RDSharing.strname,RDSharing.strpass,port);
						else
							JOptionPane.showMessageDialog(null, "Invalid Password", "Error: ",JOptionPane.ERROR_MESSAGE);
					}
				}

				if(ae.getSource() == miabt)
				{
					new RDSAbout();
				}

				if(ae.getSource() == minew)
				{
					new RDSNew();
				}

				if(ae.getSource() == miexit)
				{
					System.exit(0);
				}

				if(ae.getSource() == bexit)
				{
					System.exit(0);
				}
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

class RDSNew extends JFrame
{
	JPanel pan1, pan2, pan3;
	JLabel lch;
	JRadioButton rbclient, rbserver;
	JButton bsubmit, bcancel;

	RDSNew()
	{
		pan1 = new JPanel();
		pan2 = new JPanel();
		pan3 = new JPanel();

		lch = new JLabel("Choice: ");
		rbclient = new JRadioButton("Client", false);
		rbserver = new JRadioButton("Server", false);
		bsubmit = new JButton("Submit");
		bcancel = new JButton("Cancel");

		ButtonGroup bg = new ButtonGroup();
		bg.add(rbclient);
		bg.add(rbserver);

		pan1.add(lch);
		pan1.add(rbclient);
		pan1.add(rbserver);

		pan2.add(bsubmit);
		pan2.add(bcancel);

		pan3.setLayout(new BorderLayout());
		pan3.add(pan1, BorderLayout.CENTER);
		pan3.add(pan2, BorderLayout.PAGE_END);

		setTitle("New Connection");
		setSize(300,100);
		add(pan3);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ButtonHandler bh = new ButtonHandler();
		bsubmit.addActionListener(bh);
		bcancel.addActionListener(bh);
	}

	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			try
			{
				if(ae.getSource() == bsubmit)
				{
					if(rbclient.isSelected())
					{
						dispose();
						new RDSClient();
					}
					if(rbserver.isSelected())
					{
						dispose();
						new RDSServer();
					}
					if(!rbclient.isSelected() && !rbserver.isSelected())
						JOptionPane.showMessageDialog(null, "Please fill all the details.....", "Error!!!",JOptionPane.PLAIN_MESSAGE);
				}
				if(ae.getSource() == bcancel)
				{
					dispose();
				}
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
//				System.out.println(ex);
			}		
		}
	}
}

class RDSAbout extends JFrame
{
	MyPanel pan1;
	JPanel pan2,pan3;
	JButton bclose;

	public RDSAbout()
	{
		bclose = new JButton("Close");

		pan1 = new MyPanel();

		pan2 = new JPanel();
		pan2.setLayout(new GridLayout(1,5));
		pan2.add(new JLabel(""));
		pan2.add(new JLabel(""));
		pan2.add(bclose);
		pan2.add(new JLabel(""));
		pan2.add(new JLabel(""));

		pan3 = new JPanel();
		pan3.setLayout(new BorderLayout());
		pan3.add(pan1, BorderLayout.CENTER);
		pan3.add(pan2, BorderLayout.PAGE_END);

		setTitle("About");
		setSize(400,300);
		add(pan3);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bclose.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					dispose();
				}
			}
		);
	}
}

class MyPanel extends JPanel
{
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setFont(new Font("Arial",Font.BOLD,15));

		String str[] = new String[13];

		str[0] = "This is a small & light-weight open source";
		str[1] = "software for Remote Desktop Troubleshooting";
		str[2] = "";
		str[3] = "Version :          1.0";
		str[4] = "";
		str[5] = "Created by:   Sandip Khot & Karan Rathod";
		str[6] = "";
		str[7] = "In case of any queries or suggestions please";
		str[8] = "refer help or contact:";
		str[9] = "                 sandip.khot21@gmail.com";
		str[10] = "                 rathodkaran07@gmail.com";
		str[11] = "";
		str[12] = "";

		FontMetrics fm = g.getFontMetrics();
		int h = fm.getHeight();
		int y= 20;

		for(int i=0; i<13; i++, y+=h)
		{
			g.drawString(str[i],10,y);
		}
	}
}

class RDSServer extends JPanel
{
	JFrame frame;
	JPanel pan1, pan2, pan3;
	JLabel lport;
	JTextField tfport;
	JButton bstart, bexit;
	JMenuBar mbmain;
	JMenu mnufile, mnuabt;
	JMenuItem minew, miexit, miabt;
	int port;

	public RDSServer()
	{
		frame = new JFrame();

		pan1 = new JPanel();
		pan2 = new JPanel();
		pan3 = new JPanel();

		lport = new JLabel("Port Number: ");
		tfport = new JTextField(20);
		bstart = new JButton("Start");
		bexit = new JButton("Exit");

		mbmain = new JMenuBar();
		mnufile = new JMenu("File");
		mnuabt = new JMenu("About");
		minew = new JMenuItem("New");
		miexit = new JMenuItem("Exit");
		miabt = new JMenuItem("About");

		mnuabt.add(miabt);
		mnufile.add(minew);
		mnufile.add(miexit);
		mbmain.add(mnufile);
		mbmain.add(mnuabt);

		pan1.setLayout(new GridLayout(1,2));
		pan1.add(lport);
		pan1.add(tfport);
		pan2.setLayout(new GridLayout(1,2));
		pan2.add(bstart);
		pan2.add(bexit);
		pan3.add(pan1);
		pan3.add(pan2);

		frame.setTitle("Remote Desktop Sharing: Client Mode");
		frame.setJMenuBar(mbmain);
		frame.add(pan3);
		frame.setSize(500,150);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MBHandler mbh = new MBHandler();
		bstart.addActionListener(mbh);
		bexit.addActionListener(mbh);
		miabt.addActionListener(mbh);
		minew.addActionListener(mbh);
		miexit.addActionListener(mbh);
	}

	class MBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			try
			{
				if(ae.getSource() == bstart)
				{
					if(tfport.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please fill all the details.....", "Error!!!",JOptionPane.PLAIN_MESSAGE);
					else
					{
						port = Integer.parseInt(tfport.getText());
						if(CValidate.validatePwd(RDSharing.strname,RDSharing.strpass,port))
							new Server(RDSharing.strname,RDSharing.strpass,port);
						else
							JOptionPane.showMessageDialog(null, Server.cname+": Incorrect Password!!!", "Error: ",JOptionPane.ERROR_MESSAGE);
					}
				}

				if(ae.getSource() == miexit)
				{
					System.exit(0);
				}

				if(ae.getSource() == bexit)
				{
					System.exit(0);
				}

				if(ae.getSource() == miabt)
				{
					new RDSAbout();
				}

				if(ae.getSource() == minew)
				{
					new RDSNew();
				}

			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
//				System.out.println(ex);				
			}
		}
	}
}
