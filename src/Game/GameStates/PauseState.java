package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;
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
public class PauseState extends State {

    private int count = 0;
    private UIManager uiManager;
  //Res.music
    private InputStream audioFile;
    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip audioClip;

    public PauseState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(320, 170, 128, 64, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
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
        }));

        uiManager.addObjects(new UIImageButton(360,250, 128, 64, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
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
        }));

        uiManager.addObjects(new UIImageButton(400, 340, 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
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
        g.drawImage(Images.Pause,0,0,600,600,null);
        uiManager.Render(g);

    }
}
