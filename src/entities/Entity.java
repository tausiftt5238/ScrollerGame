package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

public abstract class Entity {
	
	protected float x;
	protected float y;
	
	protected Animation anim;
	protected Image sprite;
	
	protected Shape hitBox;
	protected boolean collide;
	
	protected SpriteSheet sh;
	
	public Entity(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Entity(float x, float y, boolean collide){
		this.x = x;
		this.y = y;
		this.collide = collide;
	}
	
	public Entity(float x, float y, Image sprite, boolean collide){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.collide = collide;
	}
	public abstract void update(float x, float y);
	public abstract void render(float x, float y);
	protected abstract void setHitBox();
	public abstract void render(Graphics g);
	
	public Shape getHitBox(){
		return hitBox;
	}
	public boolean collision(Entity ob){
		if(collide)
			return hitBox.intersects(ob.getHitBox());
		else return false;
	}
	public void getSpriteSheet(SpriteSheet sh){
		this.sh = sh;
	}
	public void getSprite(Image sprite){
		this.sprite = sprite;
	}
	public float getX(){ return x; }
	public float getY(){ return y; }
}