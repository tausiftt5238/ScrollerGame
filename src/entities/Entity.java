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
	protected Shape footing;
	protected boolean collide;
	protected boolean falling;
	
	protected SpriteSheet sh;
	
	public Entity(float x, float y){
		this.x = x;
		this.y = y;
		collide = true;
		falling = true;
	}
	
	public Entity(float x, float y, boolean collide){
		this.x = x;
		this.y = y;
		this.collide = collide;
		this.falling = true;
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
	public boolean gravity(Entity ob){
		return footing.intersects(ob.getHitBox());
	}
	public boolean getFalling(){
		return falling;
	}
	public void setFalling(boolean falling){
		this.falling = falling;
	}
	public float getX(){ return x; }
	public float getY(){ return y; }
}