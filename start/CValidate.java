package start;

import java.io.*;
import java.net.*;
import server.*;

public class CValidate
{
	protected static boolean validatePwd(String sname, String strpass, int port) throws Exception
	{
		String strpwd;

		ServerSocket ss = new ServerSocket(21687);
		Socket s = ss.accept();

		OutputStream os = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);

		InputStream is = s.getInputStream();
		DataInputStream dis = new DataInputStream(is);

		Server.cname = dis.readUTF();
		strpwd = dis.readUTF();


		if(strpwd.equals(strpass))
		{
			dos.writeUTF("1");
			dos.writeUTF(sname);
			dis.close();
			dos.close();
			s.close();
			return true;
		}
		else
		{
			dos.writeUTF("0");
			dis.close();
			dos.close();
			s.close();
			return false;
		}
	}
}
