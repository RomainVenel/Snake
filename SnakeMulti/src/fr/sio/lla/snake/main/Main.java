package fr.sio.lla.snake.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import fr.sio.lla.dialogue.Dialogue;
import fr.sio.lla.snake.snake.*;
import fr.sio.lla.sound.Sound;


/**
 * Class Main du Snake
 * @author Romain Venel
 *
 */
public class Main extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private final Main before = this;
	private static final String version = "0.1";
	
	private Block1 tete1;
	private Block2 tete2;
	private Snake1 serpent1;
	private Snake2 serpent2;
	
	private JPanel mapPanel1 = new JPanel();
	private JPanel mapPanel2 = new JPanel();
	
	public static void main(String[] args){
		
		new Main();
	}
	
	/**
	 * Constructeur pour construire la fenetre du jeu
	 */
	public Main(){
		
		//Musique de fond
		Sound fond = new Sound("C:\\Users\\VENEL\\workspace\\SnakeMulti\\src\\fr\\sio\\lla\\sound\\Requiem_for_a_dream_Soundtrack.wav");
	    InputStream streamFond = new ByteArrayInputStream(fond.getSamples());
		
		this.setTitle("Snake !");
		this.setLayout(new BorderLayout());
		
		//MENU !
		
			JMenuBar mb = new JMenuBar();
				JMenu jeu = new JMenu("Jeu");
					JMenuItem start = new JMenuItem("Start");
					JMenuItem pause = new JMenuItem("Pause");
				JMenu info = new JMenu("?");
					JMenuItem aPropos = new JMenuItem("A propos");
			
			jeu.setMnemonic('J');
			pause.setMnemonic('P');
			aPropos.setMnemonic('H');
			
			start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
			start.addActionListener(new Option(this));
			
			jeu.add(start);
			
			pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
			pause.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0){
					
					serpent1.setStatut(Snake1.PAUSE);
					Dialogue.info(before, "Jeu en pause !");
					serpent1.setStatut(Snake1.PLAY);
					
					serpent2.setStatut(Snake2.PAUSE);
					Dialogue.info(before, "Jeu en pause !");
					serpent2.setStatut(Snake2.PLAY);
				}
			});
			
			jeu.add(pause);
			
			aPropos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			aPropos.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0){
					
					Dialogue.info(before, "<html>Créé par: Romain Venel<br />Version: "+version+"");
				}
			});
			
			info.add(aPropos);
			
			mb.add(jeu);
			mb.add(info);
			
			this.getContentPane().add(mb, BorderLayout.NORTH);
		
		//SNAKE !
			
			//Map Joueur 1
			mapPanel1.setLayout(new FlowLayout());
			mapPanel1.add(Data.MAP, BorderLayout.CENTER);
			this.getContentPane().add(mapPanel1);
		
			tete1 = new Block1((Data.NBRCASEX-6)/2, (Data.NBRCASEY-1)/2);
			tete1.setColor(new Color((int)(Math.random()*127), (int)(Math.random()*127), (int)(Math.random()*127)));
			serpent1 = new Snake1(tete1, Data.SNAKESIZE);
			
			new Thread(serpent1).start();
			this.addKeyListener(serpent1);
			
			//Map Joueur 2
			mapPanel2.setLayout(new FlowLayout());
			mapPanel2.add(Data.MAP, BorderLayout.CENTER);
			this.getContentPane().add(mapPanel2);
		
			tete2 = new Block2((Data.NBRCASEX-1)/2, (Data.NBRCASEY-1)/2);
			tete2.setColor(new Color((int)(Math.random()*127), (int)(Math.random()*127), (int)(Math.random()*127)));
			serpent2 = new Snake2(tete2, Data.SNAKESIZE);
			
			new Thread(serpent2).start();
			this.addKeyListener(serpent2);
			
		//COMPTEUR
			
			this.add(serpent1.getPanel(), BorderLayout.WEST);
			this.add(serpent2.getPanel(), BorderLayout.EAST);
			
		this.pack();
		this.setLocationRelativeTo(null);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//ON INCRUSTE LE SON PUTAIN !
		fond.play(streamFond);
	}
	
	/**
	 * Rejouer et rétablir la fenetre
	 */
	public void replay(){
		
		Data.MAP.removeAll();
		Data.MAP.repaint();
		
		mapPanel1.removeAll();
		mapPanel1.repaint();
		
		mapPanel2.removeAll();
		mapPanel2.repaint();
		
		this.remove(mapPanel1);
		mapPanel1.remove(Data.MAP);
		this.remove(mapPanel2);
		mapPanel2.remove(Data.MAP);
		Data.reborn();
		
		mapPanel1.add(Data.MAP, BorderLayout.CENTER);
		mapPanel2.add(Data.MAP, BorderLayout.CENTER);
		this.getContentPane().add(mapPanel1);
		this.getContentPane().add(mapPanel2);
		
		tete1.moveIt((Data.NBRCASEX-1)/2, (Data.NBRCASEY-1)/2);
		tete2.moveIt((Data.NBRCASEX-1)/2, (Data.NBRCASEY-1)/2);
		tete1.setColor(new Color((int)(Math.random()*127), (int)(Math.random()*127), (int)(Math.random()*127)));
		tete2.setColor(new Color((int)(Math.random()*127), (int)(Math.random()*127), (int)(Math.random()*127)));
		serpent1.reborn(tete1, Data.SNAKESIZE);
		serpent2.reborn(tete2, Data.SNAKESIZE);

		this.pack();
		this.setLocationRelativeTo(null);
	}
}