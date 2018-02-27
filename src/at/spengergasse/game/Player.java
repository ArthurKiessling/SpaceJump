/**
 * 
 */
package at.spengergasse.game;
import  javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * @author Arthur Kiessling
 *
 */
public class Player{
	private static final String PLAYER_IMAGE_LOC ="img/Hero-icon.png";
    private static Image playerImage;
    public static Node player;

   private  boolean up;
   private boolean goEast;
   private	 boolean goWest;
   private boolean running;
   private int jump;
   private  float multi=1;
   public int life=5;
	
    public  void move(Group root) throws Exception {
        playerImage = new Image(PLAYER_IMAGE_LOC);
        player = new ImageView(playerImage);
        Physiks.moveHeroTo(0,0,player);
        root.getChildren().addAll(player);
    }		
    
    public void rightStart() {
		goEast  = true;
	}

    public boolean leftStart() {
		return goWest  = true;
	}
    public  void leftStop() {
		goWest  = false;
	}
    public void rightStop() {
		goEast  = false;
	}

    public  void runningStart() {
  		running = true;
  	}

      public  void runningStop() {
  		running  = false;
  	}
     public  void jumpStart() {
    	 up=true;
     }
     public void jumpStop() {
    	 up=false;
     }
    public void handle(long now) {
        float dx = 0, dy=0, x=4;
        if(Physiks.checkBoundsUp(player))jump=0;
        if(jump>0&&jump<80&&!Physiks.checkBoundsUp(player)) {dy-=9*multi;jump++;multi-=0.012;if(jump==72) {jump=0;multi=1;}  }
         if (goEast&&!Physiks.checkBoundsRight(player))  dx += 3;
         if (goWest&&!Physiks.checkBoundsLeft(player))  dx -= 3;
         if(up&&jump==0&&Physiks.checkBoundsDown(player)) {dy-=9;jump++;}
         if(Physiks.checkBoundsDown(player)) {x=(float) 0.6;if(multi<0.9) {multi=1;}}
         if (running) { dx *= 2;}
         if(player.getBoundsInParent().getMinY()>Game.H-player.getBoundsInParent().getHeight()-4) {player.relocate(200, 0); life--;}
           Physiks.moveHeroBy(dx,dy+x,player);
          }
    
    public void Scene() {
    Game.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case LEFT:  leftStart(); break;
                case RIGHT: rightStart(); break;
                case SPACE: jumpStart(); break;
                case SHIFT: runningStart( ); break;
            }
        }
    });
    Game.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case LEFT:  leftStop(); break;
                case RIGHT: rightStop(); break;
                case SPACE: jumpStop(); break;
                case SHIFT: runningStop(); break;
            }
        }
    });
    }



    

}
