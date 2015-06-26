package server;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageReceive
{
	private BufferedImage img;

	public ImageReceive()
	{

	}

	public ImageReceive(DataInputStream dis) throws Exception
	{
		img = ImageIO.read(dis);

	}

	public BufferedImage get()
	{
		return img;
	}
}
