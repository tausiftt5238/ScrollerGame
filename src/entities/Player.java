package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Player extends Entity{
	public static Player player = new Player(640/2,480/2);
	
	String direction;
	Animation movingLeft, movingRight;
	
	public Player(float x, float y){		
		super(x,y);
		x = 640/2;
		y = 480/2;
		
		movingLeft = new Animation(); movingLeft.setAutoUpdate(true);
		movingRight = new Animation(); movingRight.setAutoUpdate(true);	
		
		try{
			sh = new SpriteSheet("/res/megaman.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		
		loadAnimation();
		
		setHitBox();
	}
	
	public void loadAnimation(){
		int row = 1;
		for(int col = 0 ; col < 10 ; col++){
			if(row == 1){
				
				movingRight.addFrame(sh.getSprite(col, row), 200);
				if(col == 1){
					movingRight.addFrame(sh.getSprite(0, row), 200);
				}
			}
		}
		anim = movingRight;
		direction = "right";
	}
	
	public void update(float x, float y){ }
	
	public void update(String direction) {
		if(direction.equals("left")){
			anim = movingLeft;
			this.direction = "left";
		}
		else if(direction.equals("right")){
			anim = movingRight;
			this.direction = "right";
		}			
	}

	@Override
	public void render(float x, float y) {
		anim.draw(this.x, this.y);
	}
	public void render(Graphics g){
		g.draw(hitBox);
	}
	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,38,80);
		hitBox.setLocation(x+5, y);
	}
	public String getDirection(){
		return direction;
	}
}
