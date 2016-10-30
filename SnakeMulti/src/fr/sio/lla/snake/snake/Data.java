package fr.sio.lla.snake.snake;

import fr.sio.lla.snake.carte.Map;

/**
 * Class pour stocker donnée
 * @author Romain Venel
 *
 */
public class Data{
	
	public static short CASESIZE = 25;
	public static short SNAKESIZE = 3;
	
	public static short NBRCASEX = 20;
	public static short NBRCASEY = 20;
	public static double SNAKESPEED1 = 80;
	public static double SNAKESPEED2 = 80;
	
	public static Map MAP = new Map(Data.CASESIZE*Data.NBRCASEX, Data.CASESIZE*Data.NBRCASEY);
	
	public static void reborn(){
		
		MAP = new Map(Data.CASESIZE*Data.NBRCASEX, Data.CASESIZE*Data.NBRCASEY);
	}
}
