package src.visu;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class PlanetButton extends  JButton
{
	private static final long serialVersionUID = 1L;
	private int number;
	
	public PlanetButton(Icon icon, int number)
	{
		this.number = number;
		this.setIcon(icon);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(Color.BLACK);
		this.setSize(50, 50);
		this.setMaximumSize(this.getSize());
	}
	
	public int getNumber()
	{
		return number;
	}
}
