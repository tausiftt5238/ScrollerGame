package states;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Entity;
import entities.Player;
import entities.inanimate.Inanimate;

public class Base extends BasicGameState{
	
	SpriteSheet terrain;
	
	BufferedImage terrainMap;
	
	private int state;
	
	protected float x;
	protected float y;
	
	private String map;
	
	float speed = 0.3f;
	
	LinkedList <Entity> entityList;
	
	public Base(int state, String map){
		super();
		x = -(0 << 6);
		y = -(0 << 6);
		this.state = state;
		this.map = map;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		terrain = new SpriteSheet("Terrain.png",64,64);
		
		entityList = new LinkedList <Entity>();
		
		loadTerrain();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		drawEntity(g);
		Player.player.render(x, y);
		Player.player.render(g);
		g.drawString(Player.player.animationNumber() + " ", 500, 400);
		g.drawString(x + " " + y, 500, 0);
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		float change = delta * speed;

		boolean tempFall = false;
		for(Entity z: entityList){
			tempFall = tempFall | Player.player.gravity(z);
		}
		Player.player.setFalling(!tempFall);

		if(Player.player.getFalling()){
			for(int i = 0 ; i < 7 ; i++){
				boolean done = false;
				for(Entity z: entityList){
					if(Player.player.gravity(z)){
						done = true;
						break;
					}
				}
				if(done) break;
				y--;
				for(Entity z: entityList) z.update(x, y);
			}
		}
		
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
				break;
			}
		}
		for(Entity z : entityList) z.update(x, y);
		
	}

	@Override
	public int getID() {
		
		return state;
	}
	
	private void loadTerrain(){
		try {
			terrainMap = ImageIO.read(getClass().getResourceAsStream(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i < terrainMap.getWidth(); i++){
			for(int j = 0 ; j < terrainMap.getHeight(); j++){
				if(terrainMap.getRGB(i, j) == 0xFF3a4646){
					Inanimate rock = new Inanimate((i<<6),(j<<6),terrain.getSprite(0, 2), true);
					entityList.add(rock);
				}
			}
		}
	}
	private void drawEntity(Graphics g){
		for(Entity z : entityList){
			z.render(x, y);
			z.render(g);
		}
	}

}
