package entities.sfx;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SFXFactory {
	SpriteSheet sh;
	
	public SFXFactory(){

		try{
			sh = new SpriteSheet("projectiles.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}

	}
	
	public SFX getSFX(int x, int y,String type, String color){
		if(type.equals("particle")){
			return new SFX(x,y,color,sh);
		}
		return null;
	}
}
