package fr.sio.lla.snake.carte;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.sio.lla.snake.snake.Data;


/**
 * Nourriture pour le serpent
 * @author Romain Venel
 *
 */
public class Food1 extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	protected int posX;
	protected int posY;
	
	private Color color = new Color(250, 0, 0);
	
	/**
	 * Créé un nouvel  objectif pour le serpent
	 * @param posX position horizontal de l'objectif
	 * @param posY position vertical de l'objectif
	 */
	public Food1(int posX, int posY){
		
		this.setSize(new Dimension(Data.CASESIZE, Data.CASESIZE));
		this.setLocation(posX*Data.CASESIZE, posY*Data.CASESIZE);
		
		this.posX = posX;
		this.posY = posY;
	}
	
	/**
	 * Bouger l'objectif
	 * @param posX position horizontal de l'objectif
	 * @param posY position vertical de l'objectif
	 */
	public void moveIt(int posX, int posY){
		
		
		this.posX = posX;
		this.posY = posY;
		
		this.setLocation(posX*Data.CASESIZE, posY*Data.CASESIZE);
	}
	
	public void paintComponent(Graphics g){
		
		g.setColor(Color.BLACK);
		g.drawOval(Data.CASESIZE/6, Data.CASESIZE/6, Data.CASESIZE-(Data.CASESIZE/3)-1, Data.CASESIZE-(Data.CASESIZE/3)-1);
		
		g.setColor(color);
		g.fillOval(Data.CASESIZE/6+1, Data.CASESIZE/6+1, Data.CASESIZE-(Data.CASESIZE/3)-3, Data.CASESIZE-(Data.CASESIZE/3)-3);
	}
	
	/**
	 * @return position horizontal de l'objectif
	 */
	public int getPosX(){
		
		return posX;
	}
	
	/**
	 * @return position vertical de l'objectif
	 */
	public int getPosY(){
		
		return posY;
	}
}
