package src.visu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import src.univ.CelestialBody;

/**
 * Group 22
 * A wrapper class that holds a display of celestial bodies in orbit, and provides
 * user input control. 
 * @author L.Debnath
 * @date 14 Mar 21
 */
public class Visualiser extends JFrame implements KeyListener, MouseWheelListener, MouseMotionListener, ActionListener, ChangeListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private Point mousePoint = new Point();
	private int frame_width = screen.width;
	private int frame_height = screen.height - 50;
	
	private boolean play = false;
	private boolean reverse = false;
	private int timeInterval = 10;
	private Timer timer;
			
	private Canvas canvas;
	private JButton[] btn;
	private JButton playBtn;
	private JButton stopBtn;
	private JButton revBtn;
	private JSlider timeSlider;

	public Visualiser(CelestialBody[][] U)
	{
		setTitle("Desmond");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(frame_width, frame_height);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(true);
		
		// Add planet buttons
		BufferedImage icon = null;
		JPanel rPanel = new JPanel();
		GridLayout rLayout = new GridLayout(0,1);
		rLayout.setHgap(5);
		rLayout.setVgap(5);
		rPanel.setLayout(rLayout);
		rPanel.setBackground(Color.BLACK);
		btn = new JButton[U.length];
		for(int i = 0; i < 11; i++)
		{
			Icon btnIcon = null;
			String path = U[i][0].icon;
			try 
			{
				icon = ImageIO.read(new File(path));
				btnIcon = new ImageIcon(path);
			}
			catch (IOException e) { System.out.println("Planet Icon Image not found");}
			btn[i] = new JButton(btnIcon);
			btn[i].setBorder(BorderFactory.createEmptyBorder());
			btn[i].setBackground(Color.BLACK);
			btn[i].setSize(50, 50);
			btn[i].setMaximumSize(btn[i].getSize());
			btn[i].addActionListener(this);
			rPanel.add(btn[i]);
		}
		this.add(rPanel, BorderLayout.EAST);
		rPanel.setVisible(true);
		
		
		// Create play button
		Icon playIcon = null;
		try 
		{
			FileSystem fileSystem = FileSystems.getDefault();
			String path = fileSystem.getPath("").toAbsolutePath().toString();
			path = path.concat("/src/main/java/src/misc/playIcon.png");
			icon = ImageIO.read(new File(path));
			playIcon = new ImageIcon(icon);
		} 
		catch (IOException e) { System.out.println("Play Icon Image not found");}
		playBtn = new JButton();
		playBtn.setBorder(BorderFactory.createEmptyBorder());
		playBtn.setBackground(Color.BLACK);
		playBtn.setIcon(playIcon);;
		playBtn.addActionListener(this);
		playBtn.setSize(50, 50);
		playBtn.setMaximumSize(playBtn.getSize());
		
		// Create stop button
		Icon stopIcon = null;
		try 
		{
			FileSystem fileSystem = FileSystems.getDefault();
			String path = fileSystem.getPath("").toAbsolutePath().toString();
			path = path.concat("/src/main/java/src/misc/stopIcon.png");
			icon = ImageIO.read(new File(path));
			stopIcon = new ImageIcon(icon);
		} 
		catch (IOException e) { System.out.println("Stop Button Image not found");}
		stopBtn = new JButton(stopIcon);
		stopBtn.setBorder(BorderFactory.createEmptyBorder());
		stopBtn.setBackground(Color.BLACK);
		stopBtn.addActionListener(this);
		stopBtn.setSize(50, 50);
		stopBtn.setMaximumSize(stopBtn.getSize());
		
		// Create reverse button
		Icon revIcon = null;
		try 
		{
			FileSystem fileSystem = FileSystems.getDefault();
			String path = fileSystem.getPath("").toAbsolutePath().toString();
			path = path.concat("/src/main/java/src/misc/revIcon.png");
			icon = ImageIO.read(new File(path));
			revIcon = new ImageIcon(icon);
		} 
		catch (IOException e) { System.out.println("Reverse Icon Image not found");}
		revBtn = new JButton(revIcon);
		revBtn.setBorder(BorderFactory.createEmptyBorder());
		revBtn.setBackground(Color.BLACK);
		revBtn.addActionListener(this);
		revBtn.setSize(50, 50);
		revBtn.setMaximumSize(revBtn.getSize());
		
		// Create time slider
		timeSlider = new JSlider(0, U[0].length);
		timeSlider.setBorder(BorderFactory.createEmptyBorder());
		timeSlider.setBackground(Color.BLACK);
		timeSlider.setValue(0);
		timeSlider.addChangeListener(this);
		
		// Create the bottom panel and add components
		JPanel bPanel = new JPanel();
		bPanel.setLayout((new BoxLayout(bPanel, BoxLayout.X_AXIS))); 
		bPanel.setBackground(Color.BLACK);
		bPanel.add(revBtn);
		bPanel.add(stopBtn);
		bPanel.add(playBtn);
		bPanel.add(timeSlider);
		this.add(bPanel, BorderLayout.SOUTH);
		bPanel.setVisible(true);
		
		// Add the canvas and listeners
		int canvasX = (int) screen.getWidth();
		int canvasY = (int) screen.getHeight();
		canvas = new Canvas(U, new Dimension(canvasX, canvasY));
		canvas.addMouseListener(this);
		canvas.addMouseWheelListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addKeyListener(this);
		this.addKeyListener(this);
		this.add(canvas, BorderLayout.CENTER);
		setVisible(true);
		
		// Set timing interval
		ActionListener timeEvent = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
			    if(play && !reverse)
			    {
			  	  canvas.incrementTime(timeInterval);
			  	  timeSlider.setValue(canvas.getTime());
			    }
			    else if(play && reverse)
			    {
			    	canvas.incrementTime(-timeInterval);
				  	timeSlider.setValue(canvas.getTime());
			    }		    
			}
		};
		timer = new Timer(1, timeEvent);
		timer.start();
	}
		
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		canvas.zoom(e.getPreciseWheelRotation(), e.getLocationOnScreen());
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		mousePoint = e.getPoint();
		System.out.println("MP x: " + e.getX());
		System.out.println("MP y: " + e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{	
		canvas.follow(-1);					// Turn off following
		double dX = frame_width/2;
		dX = e.getX() - mousePoint.getX();
		System.out.println("x: " + dX);
		double dY = frame_height/2;
		dY = e.getY() - mousePoint.getY(); 
		System.out.println("y: " + dY);
        mousePoint = e.getPoint();
		canvas.move(dX,dY);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == playBtn && play)
			play = false;
		else if(e.getSource() == playBtn && !play)
			play = true;
		else if(e.getSource() == stopBtn)
			play = false;
		else if(e.getSource() == revBtn && reverse)
		{
			reverse = false;
			play = false;
		}
		else if(e.getSource() == revBtn)
		{
			reverse = true;
			play = true;
		}
		for(int i = 0; i < btn.length; i++)
		{
			if(e.getSource() == btn[i])
			{			
				canvas.follow(i);
				break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{ 
		if(e.getButton() == 3)
		{
			canvas.follow(-1);
			canvas.generalView();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		canvas.setTime(timeSlider.getValue());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
		
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
