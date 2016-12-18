package entities.projectile;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class Patronous extends PlayerProjectile{
	
	public Patronous(float x, float y, String direction) {
		super(x, y, direction);
		try{
			sh = new SpriteSheet("projectiles.png",128, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		
		alive = 100;
		loadSprite();
	}
	@Override
	protected void loadSprite(){
		anim = new Animation(); anim.setAutoUpdate(true);
		for(int i = 0 ; i < 5 ; i++){
			if(direction.equals("right")) anim.addFrame(sh.getSprite(i, 1), 150);
			else anim.addFrame(sh.getSprite(i, 1).getFlippedCopy(true, false), 150);
		}
	}
	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,128,64);
		hitBox.setLocation(x,y);	
	}
	@Override
	public void render(float x, float y) {
		anim.draw(this.x + x , this.y + y);	
	}
	public void action(LinkedList<Entity> e, int delta){
		if(direction.equals("left")) this.x -= delta/2;
		else this.x += delta/2;
	}
	
	@Override
	public String toString(){
		return "patronus";
	}
}
