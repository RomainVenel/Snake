package fr.sio.lla.snake.carte;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Carte sur laquelle bouge le serpent
 * @author Romain Venel
 *
 */
public class Map extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private int sizeX;
	private int sizeY;
	
	/**
	 * Créé une carte
	 * @param sizeX taille X en pixel de la carte
	 * @param sizeY taille Y en pixel de la carte
	 */
	public Map(int sizeX, int sizeY){
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.setSize(sizeX, sizeY);
		this.setPreferredSize(new Dimension(sizeX, sizeY));
		this.setLayout(null);
	}
	
	public void paintComponent(Graphics g){
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.sizeX, this.sizeY);
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.sizeX-1, this.sizeY-1);
	}
	
	/**
	 * @see #add(Component)
	 * @param array
	 */
	public void add(Component[] array){
		
		for(Component o : array)
			this.add(o);
	}
	
	/**
	 * @see #remove(Component)
	 * @param array
	 */
	public void remove(Component[] array){
		
		for(Component o : array)
			this.remove(o);
	}
}
