package entities.hostile;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entities.Entity;

public class DummyEnemy extends Enemy{

	public DummyEnemy(float x, float y) {
		super(x, y);
		standingLeft = new Animation();
		standingRight = new Animation();
		
		offsetX = 20;
		offsetY = 17;
		
		try{
			sh = new SpriteSheet("enemy1.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		
		loadAnimation();
		
		setHitBox();
	}
	@Override
	public void loadAnimation(){

		standingRight.addFrame(sh.getSprite(0, 0), 100);
		standingLeft.addFrame(sh.getSprite(0, 0).getFlippedCopy(true, false),100);
		
		anim = standingLeft;
		direction = "left";
	}
	public void action(LinkedList<Entity> e){
		boolean collision = false;
		for(Entity x : e){
			if(x.collision(this)){
				collision = true;
				break;
			}
		}
		if(!collision && !falling && !damageStatus) x--;
		updateHitBox();
	}

}
