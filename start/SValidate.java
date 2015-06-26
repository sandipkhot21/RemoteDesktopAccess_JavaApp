package start;

import java.io.*;
import java.net.*;
import client.*;

public class SValidate
{
	protected static boolean validatePwd(String serverip, String cname, String strpass, int port) throws Exception
	{
		Socket s = new Socket(serverip, 21687);

		OutputStream os = s.getOutputStream();
		DataOutputStream dout = new DataOutputStream(os);

		InputStream is = s.getInputStream();
		DataInputStream dis = new DataInputStream(is);

		dout.writeUTF(cname);
		dout.writeUTF(strpass);

		String conf = dis.readUTF();

		if(conf.equals("1"))
		{
			String sname = dis.readUTF();
			dis.close();
			dout.close();
			s.close();
			return true;
		}
		else
		{
			dis.close();
			dout.close();
			s.close();
			return false;
		}
	}
}
