package Game.Entities.Static;

import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;
    private static boolean Applestate = true;
    public int xCoord;
    public int yCoord;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
     
    public static void Goodapple(boolean state)
    {
    	Applestate = state;
    }


    public static boolean isGood()
    {
    	return Applestate;
    }
    }



