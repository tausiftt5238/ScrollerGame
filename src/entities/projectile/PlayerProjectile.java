package entities.projectile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class PlayerProjectile extends Entity{
	
	protected Image moving;
	protected Image stationary;
	protected int alive;
	
	public PlayerProjectile(float x, float y, String direction) {
		super(x, y);
		this.direction = direction;
		try{
			sh = new SpriteSheet("projectiles.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		if(direction.equals("right")) moving = sh.getSprite(0, 0);
		else moving = sh.getSprite(0, 0).getFlippedCopy(true, false);
		stationary = sh.getSprite(1, 0);
		alive = (5 << 2);
		setHitBox();
	}

	@Override
	public void update(float x, float y) {
		//System.out.println(x + " " + y);
		if(direction.equals("left")) this.x -= 5;
		else this.x += 5;
		hitBox.setLocation(this.x + x, this.y + y);
	}

	@Override
	public void render(float x, float y) {
		moving.draw(this.x + x , this.y + y);	
	}

	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,64,64);
		hitBox.setLocation(x,y);		
	}

	@Override
	public void render(Graphics g) {
		g.draw(hitBox);		
	}
	public boolean isAlive(){
		alive--;
		if(alive < 0) return false;
		else return true;
	}
}
