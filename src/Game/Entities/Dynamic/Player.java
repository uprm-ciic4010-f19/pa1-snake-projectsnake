package Game.Entities.Dynamic;

import Main.Handler;
import java.lang.Math;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import Game.GameStates.State;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	public int lenght;
	public boolean justAte;
	private Handler handler;

	public int xCoord;
	public int yCoord;

	public int moveCounter;
	public static int score;
	public int i;
	public String direction;//is your first name one?
	//music
	private InputStream audioFile;
	private AudioInputStream audioStream;
	private AudioFormat format;
	private DataLine.Info info;
	private Clip audioClip;

	public Player(Handler handler){
		this.handler = handler;
		xCoord = 0;
		yCoord = 0;
		moveCounter = 0;
		score=0;
		direction= "Right";
		justAte = false;
		lenght= 1;
		i=1;

	}

	public void tick(){
		moveCounter++;
		if (moveCounter>=i){
			checkCollisionAndMove();
			moveCounter = -2;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
			if(direction != "Down") //Prevent Backtracking
				direction="Up";
		}
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
			if(direction != "Up")  //Prevent Backtracking
				direction="Down";
			
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
			if(direction != "Right") //Prevent Backtracking
				direction="Left";

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
			if(direction != "Left") //Prevent Backtracking
				direction="Right";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
			lenght++;  //Add tail "N" is pressed
			handler.getWorld().body.addFirst(new Tail (xCoord,yCoord, handler));

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
			i++;        //Decrease speed "-"
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
			i--;       //Increase speed "+"

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			State.setState(handler.getGame().pauseState);
		}
	}


	public void checkCollisionAndMove(){
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int i= 0;
		int x = xCoord;
		int y = yCoord;
		switch (direction){
		case "Left":
			if(xCoord==0){
				xCoord = 59;
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				xCoord = 0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				yCoord = 59;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				yCoord = 0;
			}else{
				yCoord++;
			}
			break;
		}
		handler.getWorld().playerLocation[xCoord][yCoord]=true;


		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}
		//self collision 
		for (i=0; i< handler.getWorld().body.size();i++) {
			if((xCoord==handler.getWorld().body.get(i).x)&&(yCoord==handler.getWorld().body.get(i).y)) {
				try {

		            audioFile = getClass().getResourceAsStream("/music/Gameover.wav");
		            audioStream = AudioSystem.getAudioInputStream(audioFile);
		            format = audioStream.getFormat();
		            info = new DataLine.Info(Clip.class, format);
		            audioClip = (Clip) AudioSystem.getLine(info);
		            audioClip.open(audioStream);
		            audioClip.start();


		        } catch (UnsupportedAudioFileException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (LineUnavailableException e) {
		            e.printStackTrace();
		        }
				State.setState(handler.getGame().gameoverState);
				
			}
		}

	}

	public void render(Graphics g,Boolean[][] playeLocation){
		Random r = new Random();
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
				g.setColor(Color.GREEN);

				if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize); g.setFont(new Font("Impact",Font.PLAIN,15));
							g.setColor(Color.green);
							g.drawString("Score: " + score, 0,15);
				}

			}
		}


	}

	public void Eat(){
		lenght++;
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

					}
				}

			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}

			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		handler.getWorld().body.addLast(tail);
		handler.getWorld().playerLocation[tail.x][tail.y] = true;
		score += (int) Math.sqrt(2*score +1);
		i = i - 3;
		 try {

	            audioFile = getClass().getResourceAsStream("/music/Eating.wav");
	            audioStream = AudioSystem.getAudioInputStream(audioFile);
	            format = audioStream.getFormat();
	            info = new DataLine.Info(Clip.class, format);
	            audioClip = (Clip) AudioSystem.getLine(info);
	            audioClip.open(audioStream);
	            audioClip.start();


	        } catch (UnsupportedAudioFileException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (LineUnavailableException e) {
	            e.printStackTrace();
	        }
	}

	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;


			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}
