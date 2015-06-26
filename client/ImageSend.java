package client;

import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class ImageSend
{
	BufferedImage img;

	public ImageSend(Robot robot)
	{
		img = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	}

	public void execute(DataOutputStream dos) throws Exception
	{
		ImageIO.write(img, "bmp", dos);
		Thread.sleep(20);
//		System.out.println("Image Sent");
		dos.flush();
	}
}
