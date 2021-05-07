package src.visu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import src.misc.ResourceLoader;
import src.peng.Vector3d;
import src.univ.CelestialBody;

/**
 * @author L.Debnath
 * @date 14 Mar 21
 */
public class Visualiser extends JFrame implements MouseMotionListener, ActionListener, ChangeListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private Point mousePoint = new Point();
	private int frame_width = screen.width;
	private int frame_height = screen.height - 50;
	
	private boolean play = false;
	private boolean reverse = false;
	private int timeInterval = 20;
	private Timer timer;
			
	private Canvas canvas;
	private JButton[] planetBtn;
	private JSlider timeSlider;

	public Visualiser(CelestialBody[][] U)
	{
		System.out.print("Starting visualisation ...");
		setTitle("Desmond");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(frame_width, frame_height);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(true);
		
		// Add planet buttons
		JPanel rPanel = new JPanel();
		GridLayout rLayout = new GridLayout(0,1);
		rLayout.setHgap(5);
		rLayout.setVgap(5);
		rPanel.setLayout(rLayout);
		rPanel.setBackground(Color.BLACK);
		planetBtn = new PlanetButton[U.length];
		for(int i = 0; i < 11; i++)
		{
			Icon btnIcon = ResourceLoader.getIcon(U[i][0].icon);
			planetBtn[i] = new PlanetButton(btnIcon, i);
			planetBtn[i].addActionListener(this);
			rPanel.add(planetBtn[i]);
		}
		this.add(rPanel, BorderLayout.EAST);
		rPanel.setVisible(true);
		
		JButton playBtn = createButton("playIcon.png");
		playBtn.addActionListener(e -> playFwd());
		
		JButton stopBtn = createButton("stopIcon.png");
		stopBtn.addActionListener(e -> stop());
				
		JButton revBtn = createButton("revIcon.png");
		revBtn.addActionListener(e -> playRev());

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
		canvas.addMouseWheelListener(e -> mouseWheelMoved(e));
		canvas.addMouseMotionListener(this);

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
		System.out.println(" Done");
	}
	
	public void addTrajectories(ArrayList<Vector3d[]> trajectories)
	{
		canvas.addTrajectories(trajectories);
	}
	
	public void addTrajectory(Vector3d[] trajectory)
	{
		canvas.addTrajectory(trajectory);
	}
	
	private JButton createButton(String iconName)
	{
		Icon icon = ResourceLoader.getIcon(iconName);
		JButton btn = new JButton();
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setBackground(Color.BLACK);
		btn.setIcon(icon);
		btn.setSize(50, 50);
		btn.setMaximumSize(btn.getSize());
		return btn;
	}
	
	private void stop()
	{
		play = false;
	}

	private void playFwd()
	{
		reverse = false;
		play = true;
	}

	private void playRev()
	{
		reverse = true;
		play = true;
	}

	private void mouseWheelMoved(MouseWheelEvent e) 
	{
		canvas.zoom(e.getPreciseWheelRotation(), e.getLocationOnScreen());
	}
	
	public void mousePressed(MouseEvent e) 
	{
		mousePoint = e.getPoint();
	}

	public void mouseDragged(MouseEvent e) 
	{	
		canvas.follow(-1);					// Turn off following
		double dX = frame_width/2;
		dX = e.getX() - mousePoint.getX();
		double dY = frame_height/2;
		dY = e.getY() - mousePoint.getY(); 
        mousePoint = e.getPoint();
		canvas.move(dX,dY);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for(int i = 0; i < planetBtn.length; i++)
		{
			if(e.getSource() == planetBtn[i])
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

	@Override public void mouseMoved(MouseEvent arg0) {}
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}

}
