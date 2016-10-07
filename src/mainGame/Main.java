package mainGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.Base;


public class Main extends StateBasedGame {

	private static String name = "MyGame";
	
	public static int previousRoom = -1;
	
	public static final int base = 0;
	
	public static boolean transition = false;
	
	public static int width = 640;
	public static int height = 480;
	
	public static void main(String args[]){
		AppGameContainer apc;
		try{
			apc = new AppGameContainer(new Main(name));
			apc.setDisplayMode(width, height, false);
			apc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
	
	public Main(String name) throws SlickException{
		super(name);
		
		this.addState(new Base());

	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(base).init(gc, this);

		this.enterState(base);
	}

}
