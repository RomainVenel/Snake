package fr.sio.lla.snake.snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.sio.lla.snake.carte.Food2;
import fr.sio.lla.sound.Sound;


/**
 * Le serpent
 * @author Romain Venel
 *
 */
public class Snake2 extends JPanel implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	public final static short WEST = -1;
	public final static short EST = 1;
	public final static short NORTH = -2;
	public final static short SOUTH = 2;

	public final static short PLAY = 0;
	public final static short DEAD = 1;
	public final static short PAUSE = 2;
	
	private Block2 tete;
	private ArrayList<Block2> queue = new ArrayList<Block2>();
	
	private short dir = WEST;
	private short dirAv = WEST;
	
	private Color color;
	private short statut = PLAY;
	
	private Food2 objectif = new Food2((int)(Math.random()*Data.NBRCASEX), (int)(Math.random()*Data.NBRCASEY));
	private int score = 0;
	private JPanel pc = new JPanel();
	
	//Bruitage de miam-miam
    Sound miam = new Sound("C:\\Users\\VENEL\\workspace\\SnakeMulti\\src\\fr\\sio\\lla\\sound\\13173.wav");
    InputStream streamMiam = new ByteArrayInputStream(miam.getSamples());
	
	/**
	 * Créé un nouveau serpent
	 * @param tete le Block représentant sa tête
	 * @param nbrQueu le nombre de Block en plus de sa tete
	 */
	public Snake2(Block2 tete, int nbrQueu){
		
		this.tete = tete;
		color = new Color(0, 0, 250);
		
		for(int i=0;i<nbrQueu;i++)//Ajoute le corps
		{
			if(i==0)
				queue.add(new Block2(tete));
			else queue.add(new Block2(queue.get(i-1)));
			
			queue.get(i).setColor(color);
		}
		
		//Ajouter a la map
		
		Data.MAP.add(tete);
		Data.MAP.add(queue.toArray(new Block2[queue.size()]));
		Data.MAP.add(objectif);
		
		//Ajouter le panel du score a l'ecran
		
		pc.add(new JLabel("Score du bleu: "));
		pc.add(new JLabel(String.valueOf(score)));
	}
	
	/**
	 * Résussite le serpent
	 * @param tete le Block représentant sa tete
	 * @param nbrQueu le nombre de Block en plus de sa tete
	 */
	public void reborn(Block2 tete, int nbrQueu){
		
		this.tete = tete;
		color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		score = 0;
		
		pc.removeAll();
		pc.add(new JLabel("Score du bleu: "));
		pc.add(new JLabel(String.valueOf(score)));
		
		queue.removeAll(queue);
		
		for(int i=0;i<nbrQueu;i++)//Ajouter le corps du serpent
		{
			if(i==0){
				queue.add(new Block2(tete));
			}else{
				queue.add(new Block2(queue.get(i-1)));
			}
			
			queue.get(i).setColor(color);
		}
		
		objectif = new Food2((int)(Math.random()*Data.NBRCASEX), (int)(Math.random()*Data.NBRCASEY));
		
		Data.MAP.add(tete);
		Data.MAP.add(queue.toArray(new Block2[queue.size()]));
		Data.MAP.add(objectif);
		
		dir = WEST;
		dirAv = WEST;
		statut = PLAY;
	}
	
	/**
	 * Ajouter un Block en plus au serpent
	 */
	public void addOne(){
		
		Block2 b = new Block2(queue.get(queue.size()-1));
		b.setColor(color);
		
		queue.add(b);
		Data.MAP.add(b);
		Data.SNAKESPEED2 = Data.SNAKESPEED2 + 0.5;
	}
	
	/**
	 * Faire avancer le serpent d'une case
	 */
	public void avance(){
		
		for(int k=queue.size()-1;k>=0;k--)//Remplacer le Block n-1 par celui n
			queue.get(k).avance();
		
		if(dir+dirAv != 0)//Ne peut pas se diriger dans le sens contraire
		{
			tete.avance(dir);//On fait avancer la tete toujours dans la même direction
			dirAv=dir;
		}
		else
			tete.avance((short) (dirAv));//On fait avancer la tete dans la direction dir
		
		for(int k=0;k<queue.size();k++)
			if(queue.get(k).getLocation().equals(tete.getLocation()))//Si la tete touche un autre Block du serpent
			{
				statut = DEAD;//Bloquer le serpent
				//Dialogue.danger(null, "Ah oui t'es bien nul en fait !");
				
			}
		
		if(tete.posX == objectif.getPosX() && tete.posY == objectif.getPosY())//Si il a atteint un objectif
		{
			
			objectif.moveIt((int)(Math.random()*Data.NBRCASEX), (int)(Math.random()*Data.NBRCASEY));
			this.addOne();
			//miam.play(streamMiam);
			score++;
			((JLabel)pc.getComponent(1)).setText(String.valueOf(score));
			pc.repaint();
		}
	}
	
	/**
	 * Thread run
	 */
	public void run(){
		
		while(true)
		{
			if(statut == PLAY)//Faire avancer le serpent s'il est vivant
			{
				this.avance();
				
				try
				{
					Thread.sleep((long) (1040-10*Data.SNAKESPEED2));
				}
				catch (InterruptedException e){e.printStackTrace();}
			}
		}
	}
	
	public void keyPressed(KeyEvent e){
		
		if(e.getKeyCode()==KeyEvent.VK_Z)
			dir=NORTH;
		else if(e.getKeyCode()==KeyEvent.VK_S)
			dir=SOUTH;
		else if(e.getKeyCode()==KeyEvent.VK_Q)
			dir=WEST;
		else if(e.getKeyCode()==KeyEvent.VK_D)
			dir=EST;
	}
	
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	/**
	 * 
	 * @return les Block formant la queue du serpent
	 */
	public ArrayList<Block2> getQueue(){
		
		return queue;
	}
	
	/**
	 * Snake.DEAD: mort
	 * Snake.PLAY: vivant
	 * Snake.PAUSE: en pause
	 * @return statut du serpent
	 */
	public short getStatut(){
		
		return statut;
	}
	
	/**
	 * Snake.DEAD: mort
	 * Snake.PLAY: vivant
	 * Snake.PAUSE: en pause
	 * @param statut statut à donner au serpent
	 */
	public void setStatut(short statut){
		
		this.statut = statut;
	}
	
	/**
	 * 
	 * @return Panel du score
	 */
	public JPanel getPanel(){
		
		return this.pc;
	}
	
	/**
	 * 
	 * @return le score final
	 */
	public int getScore(){
		
		return this.score;
	}
}