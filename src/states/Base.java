package states;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Entity;
import entities.Player;
import entities.hostile.DummyEnemy;
import entities.inanimate.Inanimate;
import entities.inanimate.InanimateFactory;
import entities.projectile.Patronous;
import entities.projectile.PlayerProjectile;

public class Base extends BasicGameState{
	
	BufferedImage terrainMap;
	
	private int state;
	
	protected float x;
	protected float y;
	
	private String map;
	
	private int jump;
	private int damage;
	private int shooting;
	private int patronum;
	
	private final int jumpLimit = 24;
	private final int damageLengthLimit = 24;
	private final int shootingLimit = 24;
	private final int patronumLimit = 100;
	
	private Image background;
	
	float speed = 0.3f;
	
	LinkedList <Entity> entityList;
	LinkedList <Entity> enemyList;
	LinkedList <Entity> playerProjectileList;
	
	InanimateFactory infac;
	
	public Base(int state, String map){
		super();
		x = -(0 << 6) + 640/2;		//21
		y = -(0 << 6) + 480/2;		//19
		this.state = state;
		this.map = map;
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		infac = new InanimateFactory();		
		
		entityList = new LinkedList <Entity>();
		enemyList = new LinkedList <Entity> ();
		playerProjectileList = new LinkedList <Entity> ();
		
		DummyEnemy enemy = new DummyEnemy((25 << 6), (15 << 6));
		
		enemyList.add(enemy);
		
		loadTerrain();
		
		try{
			background = new Image("background.png");
		}catch(SlickException e){
			System.out.println("didn't work out");
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(0,0);
		drawProjectile(g);
		drawEntity(g);
		drawEnemy(g);
		
		Player.player.render(x, y);
		//Player.player.render(g);
		
		g.drawString(Player.player.animationNumber() + " ", 500, 400);
		g.drawString(x + " " + y, 500, 0);
		g.drawString(jump + "", 500, 20);
		g.drawString(Player.player.getFalling() + " ", 500, 40);
		g.drawString(shooting + " ", 500, 60);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		gravity();
		//Enemy on the move
		/*for(Entity z: enemyList){
			z.action(entityList, (int)(delta * speed));
		}*/
		enemyCheck(delta);
		
		float change = delta * speed;

		float tempX = x ;

		//Input start
		if(input.isKeyDown(Input.KEY_LEFT) && !Player.player.getDamageStatus()){
			Player.player.update("left");
			x += (int)change;
		}
		
		else if(input.isKeyDown(Input.KEY_RIGHT) && !Player.player.getDamageStatus()){
			Player.player.update("right");
			x -= (int)change;
		}
		else {
			Player.player.update("standing");
		}
		
		if(input.isKeyPressed(Input.KEY_X) 
				&& !Player.player.getFalling() && !Player.player.getDamageStatus()){
			jump = jumpLimit;
			
		}
		if(input.isKeyPressed(Input.KEY_Z)
				&& !Player.player.getDamageStatus() && shooting < 5){
			shooting = shootingLimit;
			if(Player.player.getDirection().equals("right"))
				playerProjectileList.add(new PlayerProjectile(-x + 640/2 + 45,-y + 480/2 + 8,Player.player.getDirection()));
			else
				playerProjectileList.add(new PlayerProjectile(-x + 640/2 - 45,-y + 480/2 + 8,Player.player.getDirection()));
			Player.player.setShooting(true);
		}
		
		if(input.isKeyPressed(Input.KEY_C)
				&& !Player.player.getDamageStatus() && shooting < 10 && patronum <= 0){
			shooting = shootingLimit;
			if(Player.player.getDirection().equals("right"))
				playerProjectileList.add(new Patronous(-x + 640/2 + 60,-y + 480/2 + 8,Player.player.getDirection()));
			else
				playerProjectileList.add(new Patronous(-x + 640/2 - 90,-y + 480/2 + 8,Player.player.getDirection()));
			Player.player.setShooting(true);
			patronum = patronumLimit;
			
		}
		//Input ends
		
		
		//Checking jump
		if(jump > 0){
			jump--;
			y += 12;
		}
		
		//Checking shooting
		if(shooting > 0){
			shooting--;
		}else{
			Player.player.setShooting(false);
		}
		
		if(patronum > 0) patronum--;
		//Checking damage
		if(damage > 0){
			damage--;
			if(Player.player.getDirection().equals("left")){
				x -= (int) change/2;
			}
			else if(Player.player.getDirection().equals("right")){
				x += (int) change/2;
			}
			Player.player.damage(true);
		} else Player.player.damage(false);
		
		//Collision checking start
		for(Entity z : entityList) z.update(x, y);
		for(Entity z: enemyList) z.update(x, y);
		for(Entity z: playerProjectileList) z.update(x, y);
		
		//Collision with Non-hostile
		for(Entity z: entityList){
			if(z.collision(Player.player)){
				x = tempX;
				break;
			}
		}
		//Collision with hostile
		if(!Player.player.getDamageStatus())
		for(Entity z: enemyList){
			if(z.collision(Player.player) && !z.damageStatus()){
				damage = damageLengthLimit;
				break;
			}
		}
		
		for(Entity z : entityList) z.update(x, y);
		for(Entity z : enemyList) z.update(x, y);
		for(Entity z : playerProjectileList) z.update(x, y);
		//Collision checking ends
		
		//Check if projectile is done
		projectileCheck(delta);
				
		
	}
	
	private void enemyCheck(int delta){
		Iterator<Entity> it = enemyList.iterator();
		while(it.hasNext()){
			Entity p = it.next();
			if(!p.isAlive()){
				it.remove();
			}
			p.action(entityList, (int)(delta * speed));
		}
	}
	
	private void projectileCheck(int delta){
		Iterator<Entity> it = playerProjectileList.iterator();
		while(it.hasNext()){
			Entity p = it.next();
			if(!p.isAlive()){
				it.remove();
			}
			p.action(enemyList, (int)(2*delta*speed));
		}
	}
	
	private void gravity(){
		//Drop the enemies
		for(Entity z: enemyList){
			z.gravityFall(entityList, x , y);
		}
		
		//drop the player
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
				for(Entity z : entityList) z.update(x, y);
				for(Entity z : enemyList) z.update(x, y);
			}
		}
		else if(!Player.player.getFalling()){
			jump = 0;
		}
		
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
				Inanimate inanim = infac.getInanimateObject(i, j, terrainMap.getRGB(i, j));
				if(inanim != null)
					entityList.add(inanim);
			}
		}
	}
	private void drawEntity(Graphics g){
		for(Entity z : entityList){
			z.render(x, y);
			//z.render(g);
		}
	}
	private void drawEnemy(Graphics g){
		for(Entity z: enemyList){
			z.render(x,y);
			//z.render(g);
		}
	}
	private void drawProjectile(Graphics g){
		for(Entity z: playerProjectileList){
			z.render(x,y);
			//z.render(g);
		}
	}

}
