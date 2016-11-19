package entities.hostile;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class Enemy extends Entity{
	
	String direction;
	
	Animation standingRight, standingLeft;
	
	public Enemy(float x, float y) {
		super(x, y);
		
		standingLeft = new Animation();
		standingRight = new Animation();
		
		try{
			sh = new SpriteSheet("enemy1.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		
		loadAnimation();
		
		setHitBox();
	}
	
	public void loadAnimation(){
		
		standingRight.addFrame(sh.getSprite(0, 0), 100);
		standingLeft.addFrame(sh.getSprite(0, 0).getFlippedCopy(true, false),100);
		
		anim = standingLeft;
		direction = "left";
	}

	@Override
	public void update(float x, float y) {
		if(falling)	this.y++;
		hitBox.setLocation(this.x + x + 20, this.y + y + 17);
		footing.setLocation(this.x + x + 20, this.y + y + 17 + 32);
	}

	@Override
	public void render(float x, float y) {
		anim.draw(this.x + x, this.y + y);
	}

	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,32,32);
		hitBox.setLocation(x+20, y+17);
		
		footing = new Rectangle(0,0,32,1);
		footing.setLocation(x+20, y+17+32);
	}

	@Override
	public void render(Graphics g) {
		g.draw(hitBox);
		g.draw(footing);
		g.drawString(falling + " ", 500, 80);
	}
	
	public void gravityFall(LinkedList<Entity> e){
		boolean tempFall = false;
		for(Entity z : e){
			tempFall |= gravity(z);
		}
		this.setFalling(!tempFall);
	}
}
