package states;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Player;
import entities.Entity;

public class Base extends BasicGameState{
	
	private int state;
	
	protected float x;
	protected float y;
	
	float speed = 0.2f;
	
	LinkedList <Entity> entityList;
	
	public Base(int state){
		super();
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		entityList = new LinkedList <Entity>();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Player.player.render(x, y);
		Player.player.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
Input input = gc.getInput();
		
		float change = delta * speed;
		float tempX = x , tempY = y;
	
		if(input.isKeyDown(Input.KEY_LEFT)){
			Player.player.update("left");
			x += (int)change;
		}
		
		else if(input.isKeyDown(Input.KEY_RIGHT)){
			Player.player.update("right");
			x -= (int)change;
		}
		
		else {
			Player.player.update("standing");
		}
		
		for(Entity z : entityList) z.update(x, y);
		
		for(Entity z: entityList){
			if(z.collision(Player.player)){
				x = tempX;
				y = tempY;
			}
		}
		
	}

	@Override
	public int getID() {
		
		return state;
	}

}
