// CvDefPoly.java: To be used in other program files.

// Copied from Section 1.5 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.

// A class that enables the user to define
// a polygon by clicking the mouse.
// Uses: Point2D (discussed below).
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Dimension;
class CvDefPoly extends JPanel { 
   
   float playerx, playery = 0.0F;
   int[] pressDir = new int[]{0,0,0,0};
   int[] pressView = new int[]{0,0};
   float[] vspbuf =  new float[4];
   float[] raybuf =  new float[4];
   float x0, y0, rWidth = 10.0F, rHeight = 10.0F, pixelSize;
   boolean ready = true;
   boolean threeD = false;
   boolean primed = false;
   boolean visuals = false;
   boolean hotel = false;
   int centerX, centerY;
   int cornerX, cornerY;
   int maxX, maxY;
   renderer sprend;
   renderer roomrend;
   visplane vsp;
   visplane ray; 
   float playerangle = (float) (Math.PI*5.0F)/2.0F;
   boolean theBox;
   float sensetivity; 
   boolean cwcTurn = false;
   CvDefPoly() {
	 setFocusable(true);

	System.out.println("oops");	 

   Timer timer  = new Timer(20, new ActionListener() {			//Most important part of the code:
	    @Override							//it makes sure that values are added according to which 
	    public void actionPerformed(ActionEvent e){			//key was pressDir. This makes it very responsive
			// As long as the key is pressDir AND it has not reached its respective boundary.
		
		


            if (pressDir[0] == 1 && playery < rHeight / 2 - (2 * pixelSize) ) {
                playerx += Math.cos(playerangle)/5;
		playery += Math.sin(playerangle)/5; 
            }
            if (pressDir[1] == 1 && playery > -rHeight / 2 + (2 * pixelSize) ) {
                playerx -= Math.cos(playerangle)/5;
		playery -= Math.sin(playerangle)/5; 
            }
	    if (pressDir[2] == 1 && playerx < rWidth / 2 + (2 * pixelSize) - .1F){
                playerx += Math.cos(playerangle - Math.PI/2)/5;
		playery += Math.sin(playerangle - Math.PI/2)/5; 
	
	    }if (pressDir[3] == 1 && playerx > -rWidth / 2 - (2 * pixelSize) + .1F){
		
                playerx -= .1F; 
                playerx += Math.cos(playerangle + Math.PI/2)/5;
		playery += Math.sin(playerangle + Math.PI/2)/5; 
	     }

	    if (cwcTurn){
		    sensetivity = .07F;

	    }else{

		sensetivity = .03F;
	    }

           if (pressView[0] == 1){
		
		if (playerangle > 6*Math.PI - .07){
			playerangle = (float) Math.PI * 2; 
		}else{	
			playerangle += sensetivity;
		}
		}

	   if (pressView[1] == 1){
		
		if (playerangle < Math.PI + .07){
			playerangle = (float) Math.PI * 3;
		}else{
		playerangle -= sensetivity;
		}
	   }
/*
	   if (playerangle > Math.PI*4 || -playerangle < -Math.PI*4){
			playerangle = 0;
	   }
*/
            repaint();
	   }
        });
        timer.start();

	addKeyListener(new KeyAdapter() {

	  @Override
	public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_W){
		pressDir[0] = 1;}	
	if (e.getKeyCode() == KeyEvent.VK_S){
		pressDir[1] = 1;	}
	if (e.getKeyCode() == KeyEvent.VK_D){
		pressDir[2] = 1;	
	}
	if (e.getKeyCode() == KeyEvent.VK_A){ 
		pressDir[3] = 1;	
	}
	if (e.getKeyCode() == KeyEvent.VK_LEFT){ 
		pressView[0] = 1;	
	}
	if (e.getKeyCode() == KeyEvent.VK_RIGHT){ 
		pressView[1] = 1;	
	}
	
	if (e.getKeyCode() == KeyEvent.VK_F){
		if (cwcTurn){
			cwcTurn = false;
		}else{
			cwcTurn = true;
		}
	}

	if (e.getKeyCode() == KeyEvent.VK_M){
		if (threeD){
			threeD = false;
		}else{
			threeD = true;
		}
	}


        if (e.getKeyCode() == KeyEvent.VK_H){
                if (visuals){
                     visuals = false;
                }else{
                        visuals = true;
                }
        }	



        if (e.getKeyCode() == KeyEvent.VK_P){
                if (hotel){
                     hotel = false;
                }else{
                     hotel = true;
                }
        }	



	repaint();
	}
	@Override	
	public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_W){
		pressDir[0] = 0;	
	}
	if (e.getKeyCode() == KeyEvent.VK_S){
		pressDir[1] = 0;	

        }
	if (e.getKeyCode() == KeyEvent.VK_D){
		pressDir[2] = 0;	
	}
	if (e.getKeyCode() == KeyEvent.VK_A){ 
		pressDir[3] = 0;	
	}
	if (e.getKeyCode() == KeyEvent.VK_LEFT){ 
		pressView[0] = 0;	
	}
	if (e.getKeyCode() == KeyEvent.VK_RIGHT){ 
		pressView[1] = 0;	
	}
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}



  	});
   }



//for scaling
   void initgar() {
      Dimension d = this.getSize();
      maxX = d.width - 1;
      maxY = d.height - 1;
      
      pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
      centerX = maxX / 2; centerY = maxY / 2;
      cornerX = (maxX/2)+110; 
      cornerY = (maxY/2)+110;
   }
 //  int nX(float x) normalized the bottom right corner as the origin.
 //  int nY(float y) {return Math.round((9.0F-y)/pixelSize);}
   int nX(float x) {return Math.round(cornerX + x / pixelSize);}
   int nY(float y) {return Math.round(cornerY - y / pixelSize);}
   int iX(float x) {return Math.round(centerX + x / pixelSize);}
   int iY(float y) {return Math.round(centerY - y / pixelSize);}
   float fx(int x) {return (x - centerX) * pixelSize;}
   float fy(int y) {return (centerY - y) * pixelSize;}

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      initgar();
      	roomrend.emeticize();
	sprend.emeticize();
   	vsp.emeticize();	//free memory
	ray.emeticize();
	roomrend = new renderer();
   	sprend = new renderer();
  	vsp = new visplane(5, 5, false);
  	ray = new visplane(5.0F, 5.0F, true);
      
	
      float Xbox = 2.0F;
      float Ybox = 2.0F;
      int Wbox = 10;
      int Hbox = 10;

      int left = iX(-rWidth / 2), right = iX(rWidth / 2), 
          bottom = iY(-rHeight / 2), top = iY(rHeight / 2);
	 System.out.println("\033[H\033[2J");
	 System.out.flush();					//     |  is this a ray?
								 //    V	true if yes false if no
	ray.updateVis(playerx, playery, playerangle, false);
	 
	vsp.updateVis(playerx, playery, playerangle, false); 
	vspbuf = vsp.getEnds();
	
	if (visuals){

	g.setColor(Color.CYAN);
	

	for (int i = 0; i < 100; i++){

				
		g.drawLine(iX(playerx) - 2, iY(playery) - 2, iX(ray.endRays[i].x)-2, iY(ray.endRays[i].y)-2);
		g.drawLine(iX(playerx) - 2, iY(playery) - 2, iX(ray.negRays[i].x)-2, iY(ray.negRays[i].y)-2);
		
		}


	}
	
	if (visuals){
	g.setColor(Color.BLACK);
	g.drawLine(iX(playerx) - 2, iY(playery) - 2, iX(vspbuf[0])-2, iY(vspbuf[1])-2);
	g.drawLine(iX(playerx) - 2, iY(playery) - 2, iX(vspbuf[2])-2, iY(vspbuf[3])-2);
	}
      	
	
	//g.drawRect(left, top, right - left, bottom - top);
	
//	 System.out.println("The player is at "+playerx+","+playery);
      // Show tiny rectangle around first vertex:
      if (threeD){

//	System.out.println("as written");	
	
	Point2D pts = new Point2D(Xbox, Ybox);
	
	Point2D[] curpts = new Point2D[4]; 

	curpts[0] = new Point2D(2,2);
	curpts[1] = new Point2D(2, 1);
	curpts[2] = new Point2D(1, 1);
	curpts[3] = new Point2D(1, 2);

	
	Point2D playerPos = new Point2D(playerx,playery);
	Point2D visEnd = new Point2D(vspbuf[0], vspbuf[1]);
	Point2D visNeg = new Point2D(vspbuf[2], vspbuf[3]);			//remeber area2?
										//check if any box point is in view
	boolean crank = Tools2D.insideTriangle(playerPos,visNeg,visEnd,pts); //the box coords 2,2 on top right
	pts.y -= .1F;								// and 1.9,1.9 on bottom left
	boolean cank  = Tools2D.insideTriangle(playerPos,visNeg,visEnd,pts);
	pts.x -= .1F;
	boolean clank = Tools2D.insideTriangle(playerPos,visNeg,visEnd,pts);
	pts.y += .1F;
	boolean chank = Tools2D.insideTriangle(playerPos,visNeg,visEnd,pts);
	pts.x += .1F;	

	if (crank || cank || clank || chank){
		theBox = true;
//		System.out.println("The BOX!");
	}else{
		theBox = false;
	}
	
	
		
	for (int i = 0; i < 100; i++){
		
		
		if (!hotel){
			
			
			roomrend.updateRenderer(playerx, playery, playerangle, centerX, centerY, ray.endRays[i]);
			g.drawRect(iX(roomrend.truX-10.0F), iY(roomrend.topscr), roomrend.xspan, roomrend.xspan);
			roomrend.updateRenderer(playerx, playery, playerangle, centerX, centerY, ray.negRays[i]);
			g.drawRect(iX(roomrend.truX-10.0F), iY(roomrend.topscr), roomrend.xspan, roomrend.xspan);



		}else{

		if (i > 20){
			roomrend.updateRenderer(playerx, playery, playerangle, centerX, centerY, ray.endRays[i]);
			g.drawRect(iX(roomrend.truX-10.0F), iY(roomrend.topscr), roomrend.xspan, roomrend.xspan);
			roomrend.updateRenderer(playerx, playery, playerangle, centerX, centerY, ray.negRays[i]);
			g.drawRect(iX(roomrend.truX-10.0F), iY(roomrend.topscr), roomrend.xspan, roomrend.xspan);
		}else{

			roomrend.updateRenderer(playerx, playery, playerangle, centerX, centerY, ray.endRays[i]);
			g.drawRect(iX(roomrend.truX-10.0F), iY(roomrend.topscr), roomrend.xspan, roomrend.xspan);
			roomrend.updateRenderer(playerx, playery, playerangle, centerX, centerY, ray.negRays[i]);
			g.drawRect(iX(roomrend.truX-10.0F), iY(roomrend.topscr), roomrend.xspan, roomrend.xspan);
		}
		}
	}






	if(theBox){

		sprend.updateRenderer(playerx, playery, playerangle, centerX, centerY, pts);

		g.setColor(Color.WHITE);

		
		
		g.fillRect(iX(sprend.truX-10.0F), iY(sprend.topscr), sprend.xspan, sprend.xspan);
		g.setColor(Color.BLACK);
		g.drawRect(iX(sprend.truX-10.0F), iY(sprend.topscr), sprend.xspan+1, sprend.xspan+1);

	}

      }else{

      	g.drawRect(iX(playerx) -2 , iY(playery) -2, 4, 4);
      	//g.drawRect(nX(0), nY(0), 10, 10);
      	g.drawRect(iX(Xbox) -2, iY(Ybox) -2, Wbox, Hbox);          
	float prntsin = (float) Math.sin(playerangle);
	float prntcos = (float) Math.cos(playerangle);
								   
//      System.out.println("the angle is "+playerangle+" sin is " +prntsin+" cos is "+prntcos);
  //    	 System.out.println("POS "+vspbuf[0]+" "+vspbuf[1]+"\nNEG "+vspbuf[2]+" "+vspbuf[3]);
 	}
      }

}
