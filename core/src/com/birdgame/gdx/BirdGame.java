package com.birdgame.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Random;

public class BirdGame extends ApplicationAdapter {
	SpriteBatch batch;
	//Texture is way to import an image assets in to our game
	Texture background;

	//for our character
	//whenever we want to use different images of a character in a game to give the illusion that our character is running
	//we will use a texture array
	Texture[] man;
	//int manState; will handle the animation of our character based on the Texture array position of our man
	int manState;
	//pausing our app fora short amount of time because the animation is way to fast and its gonna drain hell out of the device's battery energy way too fast
	int pause = 0;

	//**now importing adreno openCL physX engine**
	//the float gravity will decide at what speed our character is gonna fall
	float gravity = 0.2f;
	float velocity = 0;
	//int manY = man's y position
	int manY = 0;
	//int manX = man's X position
	int manX = 0;
	//setting up a geometric shape for our man character
	Rectangle manRectangle;

	//adding a firing plane
	Texture[] manfire;
	int manState2;
	Rectangle manRectangle2;
	int pause2;
	Texture manDead;

	//it will hold a bunch of bomb objects that are gonna be appearing on the screen
	//creating an arrayList that will hold the coordinates of our bomb
	ArrayList<Integer> bombX = new ArrayList<>();
	ArrayList<Integer> bombY = new ArrayList<>();
	//ArrayList of type Rectangle allows us add a shape to our objects on the screen
	ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>();
	Texture bomb;
	//bombCount  =  proper spacing between the bomb
	//it will allow us to show a bomb after every 100 loop of screen render
	int bombCount;
	Random random2;

	/**-------BIRDS THAT WILL MOVE NOT ONLY IN X AXIS BUT ALSO IN Y AXIS---------**/
	//it will hold a bunch of bomb2 objects that are gonna be appearing on the screen
	//creating an arrayList that will hold the coordinates of our bomb
	ArrayList<Integer> bombX2 = new ArrayList<>();
	ArrayList<Integer> bombY2 = new ArrayList<>();
	//ArrayList of type Rectangle allows us add a shape to our objects on the screen
	ArrayList<Rectangle> bombRectangles2 = new ArrayList<Rectangle>();
	Texture bomb2;
	//bombCount  =  proper spacing between the bomb
	//it will allow us to show a bomb after every 100 loop of screen render
	int bombCount2;
	Random random3;

	//int score will keep track of our score
	int score =0;
	//now to display our score onto our screen
	BitmapFont Font;

	//setting up a bitmap for play button
	BitmapFont Play;
	ArrayList <Integer> playY = new ArrayList<>();
	ArrayList <Integer> playX = new ArrayList<>();

	//gameState is responsible for ending the game when we collide with the bomb
	//its also responsible for keeping track of our state of the game
	int gameState = 0;

	//it will hold the high score in the game made by the user
	int HighScore = 0;
	//setting up the BitmapFont for highScore
	BitmapFont highScore;
	ArrayList <Integer> highScoreY = new ArrayList<>();
	ArrayList <Integer> highScoreX = new ArrayList<>();
	private static Preferences prefs;//its responsible to store the high score in the game
	String HS;

	//creating a shooting sound object
	private Sound sound;
	int soundState;
	int pause3;
	//creating a crash sound object
	private Sound crash;

	//creating a enemy escaped sound
	private Sound enemyEscaped;

	//Adding bullets into the game
	//it will hold a bunch of bomb objects that are gonna be appearing on the screen
	//creating an arrayList that will hold the coordinates of our bomb
	ArrayList<Integer> bulletX = new ArrayList<>();
	ArrayList<Integer> bulletY = new ArrayList<>();
	//ArrayList of type Rectangle allows us add a shape to our objects on the screen
	ArrayList<Rectangle> bulletRectangles = new ArrayList<Rectangle>();
	Texture bullet;
	//it will allow us to show a bullet after every 100 loop of screen render
	int bulletCount;

	//adding a transparent line so that we can detect its collision with the bombs
	Texture line;
	Rectangle lineR;

	@Override
	public void create () {
		batch = new SpriteBatch();

		background = new Texture("background.png");

		//inintializing line texture
		line = new Texture("line.png");

		//setting up our character in our game
		man = new Texture[4];
		//filling up the Texture array will hold our character's images
		man[0] = new Texture("fly1.png");
		man[1] = new Texture("fly2.png");

		//adding firing plane
		manfire = new Texture[5];
		manfire[0] = new Texture("shoot1.png");
		manfire[1] = new Texture("shoot2.png");
		manfire[2] = new Texture("shoot3.png");
		manfire[3] = new Texture("shoot4.png");
		manfire[4] = new Texture("shoot5.png");

		//for our character
		//setting up our manY
		manY = Gdx.graphics.getHeight()/2;
        //dead plane after crash
		manDead = new Texture("dead.png");
		//bullet fired from the plane
		bullet = new Texture("bullet.png");

		//setting up our bombs
		bomb= new Texture("bird0.png");
		random2 = new Random();

		/**---SETTING UP BOMB DIRECTION IN X AND Y MOVENMENT  -------------**/
		bomb2 = new Texture("bug.png");
		random3 = new Random();

		//setting our BitmapFont so that we can show the score onto our screen
		Font = new BitmapFont();
		//setting up the color on our BitmapFont
		Font.setColor(Color.WHITE);
		//setting up the size for our Bitmap
		//xy = 10
		Font.getData().setScale(10);

		//setting our BitmapFont so that we can show the play button onto our screen
		Play = new BitmapFont();
		//setting up the color on our BitmapFont
		Play.setColor(Color.GREEN);
		//setting up the size for our Bitmap
		//xy = 10
		Play.getData().setScale(7);

		//setting up high score bitmap Font on our screen
		highScore = new BitmapFont();
		highScore.setColor(Color.WHITE);
		highScore.getData().setScale(4);

		//initializing sound object
		sound = Gdx.audio.newSound(Gdx.files.internal("shoot.mp3"));
		//initializing sound crash object
		crash = Gdx.audio.newSound(Gdx.files.internal("crash.mp3"));
		//initializing enemyEscaped sound
		enemyEscaped = Gdx.audio.newSound(Gdx.files.internal("enemyescaped.mp3"));
	}

	//method that deals with showing multiple bomb on our screen randomely
	public void makebomb(){
		//float height = height at which the bomb will appear on the screen (randomly)
		//random.nextFloat() = it will give us randome numbers between 0 and 1
		//Gdx.graphics.getHeight() = will give us the height of the screen
		//float height = random2.nextFloat() * max height at which a bomb can appear randomly;
		float height = random2.nextFloat() * 800;
		//now updating the coordinates of the bomb in the ArrayList that holds the coin Coordinates on the screen
		bombY.add((int)height);
		//make sure that your bomb appears off of the screen and the X coordinate of the coins will all be the same
		bombX.add(Gdx.graphics.getWidth());
	}

	public void makebomb2(){
		//float height = height at which the bomb will appear on the screen (randomly)
		//random.nextFloat() = it will give us randome numbers between 0 and 1
		//Gdx.graphics.getHeight() = will give us the height of the screen
		//float height = random2.nextFloat() * max height at which a bomb can appear randomly;
		float height = random3.nextFloat() * 800;
		//now updating the coordinates of the bomb in the ArrayList that holds the coin Coordinates on the screen
		bombY2.add((int)height);
		//make sure that your bomb appears off of the screen and the X coordinate of the coins will all be the same
		bombX2.add(Gdx.graphics.getWidth());
	}

	public void makeBullet(){
		bulletY.add(manY);
		int bulletX2 = 400;
		bulletX.add(bulletX2);
	}

	//this function handles how the assets show up on the screen
	//this function gets called constantly in a loop
	@Override
	public void render () {

		//calculation of man's X position on the screen
		manX = 100;

		//inorder to start things we need to say batch.begin();
		batch.begin();

		//to draw something on the game screen
		/*
		batch.draw(Image that you want to draw, starting position of the image in x and y coordinates,height and width of the image);
        Gdx.graphics.getWidth() = tells the image to match the width of the devices screen
        Gdx.graphics.getHeight() = tells the image to match the height of the devices screen
		 */
		//setting up our game background
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		//setting up the line Texture
		batch.draw(line,0,0,1,Gdx.graphics.getHeight());
		//setting up rectangle on the line
		lineR = new Rectangle(0, 0, 1, Gdx.graphics.getHeight());

		//checking the state in which our game is
		//if our gameState is equal to 1 then the game will behave normally
		//if our gameState =0 then we are at the starting position
		//if our gameState = 2 then game over
		if(gameState == 1) {
			//setting up our bombs
			if (bombCount < 100) {
				bombCount++;
			} else {
				bombCount = 0;
				//making a coin after every 100 frames rendered
				makebomb();
			}

			//setting up our bombs2
			if (bombCount2 < 300) {
				bombCount2++;
			} else {
				bombCount2 = 0;
				//making a coin after every 100 frames rendered
				makebomb2();
			}

			bombRectangles.clear();
			//using a for loop to draw our bombs on the screen
			for (int i = 0; i < bombX.size(); i++) {
				batch.draw(bomb, bombX.get(i), bombY.get(i));
				//inorder to move the coin in the x directrion from right to left we need to update the X coordinate of the bomb
				//updating the x coordinate of our bomb
				//bombX.set(i, bombX.get(i)-4);
				//bombX.set(); ->updates the x coordinates of a bomb
				//current position = i
				//bombX.get(i)-4 = moves bombs from right to left the speed can be controlled by changing the number four
				//higher the number faster the bomb moves on the screen
				bombX.set(i, bombX.get(i) - 10);

				//coinRectangles.add(new Rectangle(coinX.get(i), coinY.get(i), coin.getWidth(),coin.getHeight())); = now adding the coin physX onto our coins
				//new Rectangle(coordinates of the screen, size of the coin on the screen) = it will set the Rectangle on to our coins
				//coinX.get(i), coinY.get(i) = telling the rectangle to appear where the coin appear on the screen
				//coin.getWidth(),coin.getHeight() = the size of the rectangle should be equal to the size of the coin
				bombRectangles.add(new Rectangle(bombX.get(i), bombY.get(i), bomb.getWidth(), bomb.getHeight()));
			}

			/**SETTING UP BOMB 2 RECTANGLES AND DRAWING BOMB 2-----------**/
			bombRectangles2.clear();
			//using a for loop to draw our bombs on the screen
			for (int i = 0; i < bombX2.size(); i++) {
				batch.draw(bomb2, bombX2.get(i), bombY2.get(i));
				//inorder to move the coin in the x directrion from right to left we need to update the X coordinate of the bomb
				//updating the x coordinate of our bomb
				//bombX.set(i, bombX.get(i)-4);
				//bombX.set(); ->updates the x coordinates of a bomb
				//current position = i
				//bombX.get(i)-4 = moves bombs from right to left the speed can be controlled by changing the number four
				//higher the number faster the bomb moves on the screen
				bombX2.set(i, bombX2.get(i) - 20);

				//coinRectangles.add(new Rectangle(coinX.get(i), coinY.get(i), coin.getWidth(),coin.getHeight())); = now adding the coin physX onto our coins
				//new Rectangle(coordinates of the screen, size of the coin on the screen) = it will set the Rectangle on to our coins
				//coinX.get(i), coinY.get(i) = telling the rectangle to appear where the coin appear on the screen
				//coin.getWidth(),coin.getHeight() = the size of the rectangle should be equal to the size of the coin
				bombRectangles2.add(new Rectangle(bombX2.get(i), bombY2.get(i), bomb2.getWidth(), bomb2.getHeight()));
			}

			//here we want to keep track of the character on what position our character man is in the game screen
			//setting up the pause function in our app so that we can slow our games animation to a manageble speed
			if (pause < 5) {
				pause++;
			} else {
				//here we will update our character's running animation
				pause = 0;
				//we have to have a way to draw our character so that we can show our character is running
				if (manState < 1) {
					//updating our manState
					manState++;
				} else {
					//as soon as it becomes 3 we will set manState to be zero again to keep the running animation of our character in a loop
					manState = 0;
				}
			}
			if (pause2 < 1) {
				pause2++;
			} else {
				pause2 = 0;
				//for firing plane
				if (manState2 < 4) {
					manState2++;
				} else {
					manState2 = 0;
				}
			}

			//calculating our character's velocity
			//velocity of our character in falling state is = current velocity + gravity;
			velocity = velocity + gravity;
			manY -= velocity;
			//we will set our character to remain on the ground and stop his decent once he has reached the ground
			if (manY <= 3) {
				manY = 3;
			} else if (manY >= 750) {
				manY = 750; //it will prevent our character from going out of our screen
			}

			//**so in order to do that we are going to apply graphical mathematical model of adreno gpu using open cl graphics library**
			//we are going to write the code related to our character after we've drawn the main background because we want to draw our character on top of the main background and not under it
        /*
        if we want our man to show in the centre of the screen then we have to do the math like the below:-
        1. figure out the man's x and y positions
        2. take the height/2 and width/2
        4. it will look something like this:-position on the screen of our character-> x: Gdx.graphics.getWidth()/2, y: Gdx.graphics.getHeight()/2
        5. subtracting the half of  width of the man from the x: Gdx.graphics.getWidth()/2 (man's position) - man[0].getHeight()/2 (width of the man's image itself)
         */
			batch.draw(man[manState], manX, manY);
			//setting up a geometric shape for our character
			manRectangle = new Rectangle(manX, manY, man[manState].getWidth(), man[manState].getHeight());

			//setting up the code so that our character can jump when the user touches him
			//Gdx.input.justTouched() = it will handle all the touch input from the user in our game
			//adding accelerometer support
			//getting values from accelerometer
			int Yaxis = (int) Gdx.input.getAccelerometerX();
			Gdx.app.log("Y", Float.toString(Yaxis));
			if (Yaxis < 6) {
				//velocity = -10; -> will make our character go up
				velocity = -8;
			} else {
				// will make our charatcer go down
				velocity = 8;
			}

			//checking condition for shooting guns
			if (Gdx.input.isTouched()) {
				//drawing firing plane after the normal plane
				batch.draw(manfire[manState2], manX, manY);
				//setting up a geometric shape for our character
				manRectangle2 = new Rectangle(manX, manY, manfire[manState2].getWidth(), manfire[manState2].getHeight());

				//playing shoot.mp3 when the screen is touched
				if (pause3 < 7) { //delaying sound play
					pause3++;
				} else {
					pause3 = 0;
					//for firing plane
					if (soundState < 1) {
						soundState++;
					} else {
						soundState = 0;
						long id = sound.play(Float.parseFloat("1.0f"));
						sound.setPitch(id, 5);
						sound.setLooping(id, false);
					}
					//making bullets
					makeBullet();
				}
			}

			//setting up our bullets
			bulletRectangles.clear();
			//using a for loop to draw our bullets on the screen
			for (int i = 0; i < bulletX.size(); i++) {
				batch.draw(bullet, bulletX.get(i), bulletY.get(i));
				//inorder to move the coin in the x directrion from right to left we need to update the X coordinate of the bomb
				//updating the x coordinate of our bomb
				//bombX.set(i, bombX.get(i)-4);
				//bombX.set(); ->updates the x coordinates of a bomb
				//current position = i
				//bombX.get(i)-4 = moves bombs from right to left the speed can be controlled by changing the number four
				//higher the number faster the bomb moves on the screen
				bulletX.set(i, bulletX.get(i) + 30);

				//coinRectangles.add(new Rectangle(coinX.get(i), coinY.get(i), coin.getWidth(),coin.getHeight())); = now adding the coin physX onto our coins
				//new Rectangle(coordinates of the screen, size of the coin on the screen) = it will set the Rectangle on to our coins
				//coinX.get(i), coinY.get(i) = telling the rectangle to appear where the coin appear on the screen
				//coin.getWidth(),coin.getHeight() = the size of the rectangle should be equal to the size of the coin
				bulletRectangles.add(new Rectangle(bulletX.get(i), bulletY.get(i), bullet.getWidth(), bullet.getHeight()));
				Gdx.app.log("bulletRec", "bullet rectangles = " + bulletRectangles.get(i));
			}

			//after we've updated our score now we have to draw the score on our screen
			//drawing a Bitmap on the screen is a little bit different than drawing your game assets on the screen
			Font.draw(batch, String.valueOf(score), 100, 1000);

			//now setting up the collision mechanism for birds after everything is drawn
			//using an if statement to check whether our character is colliding with the bomb or not
			//for lop to get to all of our bomb
			for (int i = 0; i < bombRectangles.size(); i++) {
				//Intersector.overlaps() it checks whether the two images that have the designated polygons assigned to them have collided or not
				if (Intersector.overlaps(manRectangle, bombRectangles.get(i))) {
					//so lets go ahead and log some information here
					Gdx.app.log("Bomb", "Collision with bomb");

					//getting rid of the bomb after the collision with the bomb of our character
					bombRectangles.remove(i);
					bombX.remove(i);
					bombY.remove(i);

					//playing crash sound when the plane is collided with the bird
					long id = crash.play(Float.parseFloat("1.0f"));
					crash.setLooping(id, false);

					//updating our gameState
					gameState = 2;
					break;
				}
			}

				/** -------SETTING UP COLLISION MECHANISM FOR BOMB2-----**/
				//now setting up the collision mechanism for birds after everything is drawn
				//using an if statement to check whether our character is colliding with the bomb or not
				//for lop to get to all of our bomb
				for (int j = 0; j < bombRectangles2.size(); j++) {
					//Intersector.overlaps() it checks whether the two images that have the designated polygons assigned to them have collided or not
					if (Intersector.overlaps(manRectangle, bombRectangles2.get(j))) {
						//so lets go ahead and log some information here
						Gdx.app.log("Bomb2", "Collision with bomb2");

						//getting rid of the bomb after the collision with the bomb of our character
						bombRectangles2.remove(j);
						bombX2.remove(j);
						bombY2.remove(j);

						//playing crash sound when the plane is collided with the bird
						long id = crash.play(Float.parseFloat("1.0f"));
						crash.setLooping(id, false);

						//updating our gameState
						gameState = 2;
						break;
					}
				}

				//checking the collision between the bullets and the birds
				int bul;
				for (int b = 0; b < bombRectangles.size(); b++) {
					for (bul = 0; bul < bulletRectangles.size(); bul++) {
						if (Intersector.overlaps(bulletRectangles.get(bul), bombRectangles.get(b))) {
							//getting rid of the bomb after the collision with the bomb of our character
							bombRectangles.remove(b);
							bombX.remove(b);
							bombY.remove(b);
							bulletRectangles.remove(bul);
							bulletX.remove(bul);
							bulletY.remove(bul);
							score++;
							if (score > HighScore) {
								HighScore = score;
							}
							gameState = 1;
							break;
						}
					}
				}
					//checking the collision between the bullets and the bugs (bomb2)
					int bul2;
					for (int b2 = 0; b2 < bombRectangles2.size(); b2++) {
						for (bul2 = 0; bul2 < bulletRectangles.size(); bul2++) {
							if (Intersector.overlaps(bulletRectangles.get(bul2), bombRectangles2.get(b2))) {
								//getting rid of the bomb after the collision with the bomb of our character
								bombRectangles2.remove(b2);
								bombX2.remove(b2);
								bombY2.remove(b2);
								bulletRectangles.remove(bul2);
								bulletX.remove(bul2);
								bulletY.remove(bul2);
								score++;
								if (score > HighScore) {
									HighScore = score;
								}
								gameState = 1;
								break;
							}
						}
					}

					//setting up collisions for our bomb1
			for (int j = 0; j < bombRectangles.size(); j++) {
				//Intersector.overlaps() it checks whether the two images that have the designated polygons assigned to them have collided or not
				if (Intersector.overlaps(lineR, bombRectangles.get(j))) {
					//so lets go ahead and log some information here
					Gdx.app.log("Bomb", "Collision with bomb2");

					//getting rid of the bomb after the collision with the bomb of our character
					bombRectangles.remove(j);
					bombX.remove(j);
					bombY.remove(j);

					//playing enemy Escaped sound when the plane is collided with the bird
					long id = enemyEscaped.play(Float.parseFloat("1.0f"));
					enemyEscaped.setLooping(id, false);

					//updating our gameState
					gameState = 2;
					break;
				}
			}

					//setting up collision between our bomb2 and our line
			for (int j = 0; j < bombRectangles2.size(); j++) {
				//Intersector.overlaps() it checks whether the two images that have the designated polygons assigned to them have collided or not
				if (Intersector.overlaps(lineR, bombRectangles2.get(j))) {
					//so lets go ahead and log some information here
					Gdx.app.log("Bomb2", "Collision with bomb2");

					//getting rid of the bomb after the collision with the bomb of our character
					bombRectangles2.remove(j);
					bombX2.remove(j);
					bombY2.remove(j);

					//playing enemy Escaped sound when the plane is collided with the bird
					long id = enemyEscaped.play(Float.parseFloat("1.0f"));
					enemyEscaped.setLooping(id, false);

					//updating our gameState
					gameState = 2;
					break;
				}
			}


		}
		else if(gameState == 0)
		{
			//waiting to start
			score = 0;
			//getting rid of the bombs
			for(int i=0;i<bombRectangles.size();i++)
			{
				//getting rid of the bomb after the collision with the bomb of our character
				bombRectangles.remove(i);
				bombX.remove(i);
				bombY.remove(i);
			}
			//drawing a Bitmap on the screen is a little bit different than drawing your game assets on the screen
			String PLAY = "START THE GAME";
			playY.clear();
			playX.clear();
			//now updating the coordinates of the START THE GAME in the ArrayList that holds the coin Coordinates on the screen
			playY.add(Gdx.graphics.getHeight()/2);
			playX.add(1000);
			Play.draw(batch,String.valueOf(PLAY),playX.get(0),playY.get(0));
			savedHighscore();
			if(Gdx.input.justTouched()){
				gameState = 1;
				//removing the BitmapFont from the screen when user touches the screen
				playX.remove(0);
				playY.remove(0);

				//removing highscore from the screen
				highScoreX.remove(0);
				highScoreY.remove(0);
				score = 0;
			}
		}
		else if(gameState == 2){
			//game Over
			//drawing a Bitmap on the screen is a little bit different than drawing your game assets on the screen
			playX.clear();
			playY.clear();
			String PLAY = "Game Over";
			Play.setColor(Color.RED);
			//now updating the coordinates of the coins in the ArrayList that holds the coin Coordinates on the screen
			playY.add(Gdx.graphics.getHeight()/2);
			playX.add(1400);
			Play.draw(batch,String.valueOf(PLAY),playX.get(0),playY.get(0));

			//drawing manDead
			batch.draw(manDead,manX, manY);

			//clearing everything and resetting the game all over again
			velocity = 0;
			bombX.clear();
			bombY.clear();
			bombRectangles.clear();
			bombCount = 0;
			bombX2.clear();
			bombY2.clear();
			bombRectangles2.clear();
			bombCount2 = 0;

			//now showing highScore in the game
			savedHighscore();
			if(Gdx.input.justTouched()){
				gameState = 1;
				//removing the BitmapFont from the screen when user touches the screen
				playX.remove(0);
				playY.remove(0);

				//removing highscore from the screen
				highScoreX.remove(0);
				highScoreY.remove(0);
				score = 0;
			}
		}

		batch.end();
	}

	//saving the high score inside the Preferences permanent memory block
	public void savedHighscore(){

		prefs = Gdx.app.getPreferences("highScoreGame");// We store the value 10 with the key of "highScore"
		prefs.putInteger("HSC", HighScore);
		prefs.flush(); // This saves the preferences file.

		highScoreX.clear();
		highScoreY.clear();
		highScoreX.add(1400);
		highScoreY.add(100);
		HS = "HIGH SCORE = "+ prefs.getInteger("HSC");
		highScore.draw(batch,String.valueOf(HS),highScoreX.get(0),highScoreY.get(0));
		Gdx.app.log("saved",Integer.toString(prefs.getInteger("HSC")));
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
