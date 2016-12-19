package entities.hostile;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EnemyFactory {
	private SpriteSheet sh_enemy1;
	
	public EnemyFactory(){
		try{
			sh_enemy1 = new SpriteSheet("enemy1.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
	
	public Enemy getEnemy(int x, int y, int color){
		if(color == 0xFFFF0F0F) return new DummyEnemy((x << 6),(y << 6),true, sh_enemy1);
		else if(color == 0xFFFF0000) return new DummyEnemy((x << 6),(y << 6),false, sh_enemy1);
		
		return null;
	}
}
