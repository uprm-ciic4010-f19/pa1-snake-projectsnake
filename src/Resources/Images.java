package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {



    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage[] Resume;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static ImageIcon icon;
    public static BufferedImage gameover;
    public static BufferedImage[] Restart;
    
   
    public Images() {

        butstart = new BufferedImage[2];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        Restart = new BufferedImage[2];

        try {
        	Restart[0]=ImageIO.read(getClass().getResourceAsStream("/Buttons/Restart1.png"));
        	Restart[1]=ImageIO.read(getClass().getResourceAsStream("/Buttons/Restart2.png"));
            gameover=ImageIO.read(getClass().getResourceAsStream("/Sheets/Gameover.png"));
            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Title.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Sheets/Pause.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume1.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume2.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Title1.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Title2.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Options1.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Options2.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/start1.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/start2.png"));//hoverbut            
           

           

            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));


        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
