package Game.GameStates;
import Main.Handler;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;
import java.awt.*;
import Game.Entities.Dynamic.Player;
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
 * Created by AlexVR on 7/1/2018.
 */
public class GameoverState extends State {
	private int count = 0;
	private UIManager uiManager;

	//music
	private InputStream audioFile;
	private AudioInputStream audioStream;
	private AudioFormat format;
	private DataLine.Info info;
	private Clip audioClip;

	public GameoverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		uiManager.addObjects(new UIImageButton(235, (223+(64+16))+(64+16)+20, 128, 64, Images.Restart, new ClickListlener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUimanager(null);
				handler.getGame().reStart();
				State.setState(handler.getGame().gameState);
				 try {

	                    audioFile = getClass().getResourceAsStream("/music/Snakesound.wav");
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
		}));

	}
	@Override
	public void tick() {
		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();
		count++;
		if( count>=30){
			count=30;
		}
		if(handler.getKeyManager().pbutt && count>=30){
			count=0;
			State.setState(handler.getGame().gameState);
		}
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Images.gameover,0,0,600,630,null);
		uiManager.Render(g);
		g.setFont(new Font("Impact",Font.PLAIN,50));
		g.setColor(Color.green);
		g.drawString("Score: " + Player.score, 210,330);
	}
}