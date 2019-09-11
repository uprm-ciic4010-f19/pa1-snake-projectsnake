package Game.GameStates;
import Main.Handler;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;
import java.awt.*;
import Game.Entities.Dynamic.Player;
/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameoverState extends State {
    private int count = 0;
    private UIManager uiManager;
    public GameoverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
    
        uiManager.addObjects(new UIImageButton(235, (223+(64+16))+(64+16)+20, 128, 64, Images.Resume, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().gameState);
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
        g.setColor(new Color(204,0,204));
        g.drawString("Score: " + Player.score, 210,330);
    }
}