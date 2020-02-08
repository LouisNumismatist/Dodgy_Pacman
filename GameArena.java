import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

public class GameArena extends JFrame implements Runnable, KeyListener
{
	private int arenaWidth;
	private int arenaHeight;

	private boolean exiting = false; 

	private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean space = false;

	private boolean rendered = false;

	public GameArena(int width, int height)
	{
		this.setTitle("Play Dodgy Pacman!");
		this.setSize(width, height);
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);		
	
		Thread t = new Thread(this);
		t.start();

		this.addKeyListener(this);
	}

	public void run() {
		try {
			while (true) {
				this.repaint();
				Thread.sleep(10);
			}
		} catch (InterruptedException iex) {}
	}

	public void setSize(int width, int height)
	{
		this.arenaWidth = width;
		this.arenaHeight = height;

		super.setSize(arenaWidth + this.getInsets().left + this.getInsets().right, arenaHeight + this.getInsets().top + this.getInsets().bottom);
	}	

	public void exit()
	{
		this.exiting = true;
	}

	public void paint (Graphics gr)
	{
		if (!rendered)
		{
			this.setSize(arenaWidth, arenaHeight);
			rendered = true;
		}

		Graphics2D window = (Graphics2D) gr;
		BufferedImage i = new BufferedImage(arenaWidth, arenaHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = i.createGraphics();
		
		window.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		window.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		synchronized (this)
		{
			if (!this.exiting)
			{
				g.clearRect(0,0, arenaWidth, arenaHeight);

				for(Rectangle b : rectangles)
				{
					g.setColor(this.getColourFromString(b.colour));
					g.fillRect(b.xPos, b.yPos, b.width, b.height);
				}
			}
					
			window.drawImage(i, this.getInsets().left, this.getInsets().top, this);
		}
	}

	private Color getColourFromString(String col)
	{
		Color colour = Color.WHITE;
		col = col.toUpperCase();

		if (col.equals("BLACK"))
			colour = Color.BLACK;	

		if (col.equals("BLUE"))
			colour = Color.BLUE;	

		if (col.equals("CYAN"))
			colour = Color.CYAN;	

		if (col.equals("DARKGREY"))
			colour = Color.DARK_GRAY;	

		if (col.equals("GREY"))
			colour = Color.GRAY;	

		if (col.equals("GREEN"))
			colour = Color.GREEN;	

		if (col.equals("LIGHTGREY"))
			colour = Color.LIGHT_GRAY;	
				
		if (col.equals("MAGENTA"))
			colour = Color.MAGENTA;	

		if (col.equals("ORANGE"))
			colour = Color.ORANGE;	

		if (col.equals("PINK"))
			colour = Color.PINK;	

		if (col.equals("RED"))
			colour = Color.RED;	
		
		if (col.equals("WHITE"))
			colour = Color.WHITE;	

		if (col.equals("YELLOW"))
			colour = Color.YELLOW;	

		return colour;
	}

	public void addRectangle(Rectangle r)
	{

		synchronized (this)
		{
			if (rectangles.size() > 100000)
			{
				System.out.println("\n\n");
				System.out.println(" ********************************************************* ");
				System.out.println(" ***** Only 100000 Objects Supported per Game Arena! ***** ");
				System.out.println(" ********************************************************* ");
				System.out.println("\n");
				System.out.println("-- Joe\n\n");

				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			}
			else
			{
				rectangles.add(r);
			}
		}
	}

	public void removeRectangle(Rectangle r)
	{
		synchronized (this)
		{
			rectangles.remove(r);
		}
	}

	public void pause()
	{
		try { Thread.sleep(20); }
		catch (Exception e) {};
	}

 	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP)
			up = true;		
		if (code == KeyEvent.VK_DOWN)
			down = true;		
		if (code == KeyEvent.VK_LEFT)
			left = true;		
		if (code == KeyEvent.VK_RIGHT)
			right = true;	
		if (code == KeyEvent.VK_SPACE)
			space = true;		
	}
 	
	public void keyReleased(KeyEvent e) 
	{
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP)
			up = false;		
		if (code == KeyEvent.VK_DOWN)
			down = false;		
		if (code == KeyEvent.VK_LEFT)
			left = false;		
		if (code == KeyEvent.VK_RIGHT)
			right = false;		
		if (code == KeyEvent.VK_SPACE)
			space = false;		
	}

 	public void keyTyped(KeyEvent e) { }

	public int getArenaWidth() { return arenaWidth; }

	public int getArenaHeight() { return arenaHeight; }

	public boolean upPressed() { return up; }

	public boolean downPressed() { return down; }

	public boolean leftPressed() { return left; }

	public boolean rightPressed() { return right; }

	public boolean spacePressed() { return space; }

	
}
