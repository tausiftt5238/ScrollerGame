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
	Animation standingRight, standingLeft;
	Animation jumpingLeft, jumpingRight;
	
	public Player(float x, float y){		
		super(x,y);
		x = 640/2;
		y = 480/2;
		
		movingLeft = new Animation(); movingLeft.setAutoUpdate(true);
		movingRight = new Animation(); movingRight.setAutoUpdate(true);
		
		standingLeft = new Animation();
		standingRight = new Animation();
		
		jumpingLeft = new Animation();
		jumpingRight = new Animation();
		
		try{
			sh = new SpriteSheet("megaman.png",64, 64);
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
				movingRight.addFrame(sh.getSprite(col, row), 100);
				movingLeft.addFrame(sh.getSprite(col, row).getFlippedCopy(true, false), 100);
			}
		}
		standingRight.addFrame(sh.getSprite(0, 0), 100);
		standingLeft.addFrame(sh.getSprite(0, 0).getFlippedCopy(true, false),100);
		
		jumpingRight.addFrame(sh.getSprite(1, 0), 100);
		jumpingLeft.addFrame(sh.getSprite(1, 0).getFlippedCopy(true, false),100);
		
		anim = standingRight;
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
		else if(direction.equals("standing")){
			if(this.direction.equals("left")) anim = standingLeft;
			else anim = standingRight;
		}
		if(falling){
			if(this.direction.equals("left")) anim = jumpingLeft;
			else anim = jumpingRight;
		}
	}

	@Override
	public void render(float x, float y) {
		anim.draw(this.x, this.y);
	}
	public void render(Graphics g){
		g.draw(hitBox);
		g.draw(footing);
	}
	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,32,32);
		hitBox.setLocation(x+20, y+17);
		
		footing = new Rectangle(0,0,32,2);
		footing.setLocation(x+20, y+17+31);
	}
	public String getDirection(){
		return direction;
	}
	public int animationNumber(){
		return anim.getFrame();
	}
}
