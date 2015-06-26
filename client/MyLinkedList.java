package client;

import java.util.LinkedList;
import javax.swing.JOptionPane;

public class MyLinkedList extends LinkedList
{
	private boolean available;

	public MyLinkedList()
	{
		super();
	}

	public synchronized boolean add(Object obj)
	{
		if(available == true)
		{
			try
			{
				wait();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
			}
		}

		available = super.add(obj);
//		System.out.println("Object added to LL");
		notifyAll();
		return available;
	}

	public synchronized Object removeFirst()
	{
		if(available == false)
		{
			try
			{
				wait();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex, "Error: ",JOptionPane.ERROR_MESSAGE);
			}
		}

		available = false;
		notifyAll();
		return super.removeFirst();
	}
}
