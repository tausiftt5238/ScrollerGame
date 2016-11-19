package entities.inanimate;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class InanimateFactory {
	
	SpriteSheet terrain;
	
	public InanimateFactory(){
		try {
			terrain = new SpriteSheet("Terrain.png",64,64);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Inanimate getInanimateObject(int co_x, int co_y, int color){
		if(color == 0xFF3a4646)
			return new Inanimate((co_x<<6),(co_y<<6),terrain.getSprite(1, 1), true);
		
		return null;
	}
}
