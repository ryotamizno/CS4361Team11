import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

class renderer{

	private Point2D[] newPts = new  Point2D[4];
	private Point2D[] currPts = new Point2D[4];   //start here	
	private float eyeConv = 3.0F;
	private float xbnd = 5;
	private float sprH = 1.0F;
	private float sprW = 1.0F;


	private float floorptH = .1F;
	private float floorptW = .1F;


	private float[] ystep = new float[100];
	private float[] floorspan  = new float[100];
        private float[] nfloorspan = new float[100];
	private static Stack<renderer> ipecac = new Stack<>();

	private float maxMag = 7;
	private float playX;
	private float playY;
	private float xscale; 
	private float offs = 5.001F;
	
//	BufferedImage buffer = new BufferedImage(1080, 980, BufferedImage.TYPE_INT_ARGB);
//	Graphics2D linebuf = buffer.createGraphics();


	private float objZ = 0.0F;
	private float posz = 0.7F;   //in CENTIMETERS!

	public int xspan;
 	public float topscr;
	public float truX;	//for now only worry about renderer(), odds are I am going to get rid of the 
				//other functions
		
	/*
	public renderer(){

	}
*/
	public void updateRenderer(float posx, float posy, float angle, float centerx, float centery, Point2D pts){
		
		//System.out.println("this x "+this.playX+" this y "+this.playX);
		//
							//ripped straight from doom 
							//i cant explain it other than it handles sprite projection
		

	       float tr_x = pts.x - posx;
	       float tr_y = pts.y - posy;
		
	       float tsin = (float) Math.sin(angle);
	       float tcos = (float) Math.cos(angle);
		
       		float gxt  = tr_x * tcos;

		float gyt = -(tr_y * tsin);

		float tz = gxt-gyt;

		float xscale = centerx / tz;

		gxt = -(tr_x * tsin);
		gyt = tr_y * tcos;	

		float tx = -(gxt + gyt);
		

		float x1 = centerx + (tx * xscale);
		
		tx += sprW;

		float x2 = centerx + (tx * xscale);
		
		float gzt = objZ + sprH;
		
		float texmid = gzt - posz;
	
		float sprtopscreen  = centery - (texmid * xscale);

	//	System.out.println("x1 "+x1+" x2 "+x2+" sprtop "+sprtopscreen);
		
		xspan = (int) (x2-x1);	//the variables printed are the ones you should be worrying about
		topscr = sprtopscreen/900;	//xspan determines the box scale 	
		float homoX = x1 -121;		//topscr determines y projection		
		truX  = homoX/50;		//truX determines x projection
	//	System.out.println("xspan "+xspan+" sprtop "+topscr+" homoX "+homoX+" truX "+truX); 
	}





	float distance(Point2D pt){
		return (float) Math.sqrt(Math.pow(playX- pt.x,2) + (float) Math.pow(playY - pt.y, 2)); 
	}

	private Point2D distX(Point2D pt){

		float mag = distance(pt);

		float newY = ( playX / mag ) * 3.0F;
		
		float newMag = (4*mag)/7;
		float stupidvar = (float) (-(Math.pow(newY, 2)) + newMag) * (256/ (newMag*10));
		if (stupidvar < 0){

	  //	System.out.println("YOU FUCKED UP" +stupidvar+" newMag " +newMag+ " mag "+mag);
		}
		float newX = (float) Math.sqrt( (-(Math.pow(newY, 2)) + newMag) * (256/ (newMag*10)) );

		//System.out.println("newX "+newX+"mag "+mag+" "+-(newY*newY)+" "+newMag+" newMag");

	 	return new Point2D(newX - offs, newY - offs);
	}

		
	public static void emeticize(){

         if (!ipecac.isEmpty()) {
             renderer recent = ipecac.pop(); // Remove the most recent instance
             System.out.println("Destroying instance: " + recent);
             // Nullify the reference to help garbage collection
             recent = null;
         } else {
             System.out.println("No instances to destroy.");
         }


         }
	

/*
	public BufferedImage getPlaneBuf(){
	

			
		
		linebuf.setColor(Color.WHITE);  // Choose the color you want to clear with (e.g., white)
    		linebuf.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
		
		for (int i = 0; i < 100; i++){
			
			float y = ystep[i];
			float x1 = floorspan[y]; 
			float x2 = nfloorspan[y];
			linebuf.drawLine(iX(x1), iY(y), iX(x2), iY(y));	

		}	

		return buffer; 

	}
*/
	public Point2D[] getPts(){

	
		for (int i = 0; i < 4; i++){
		
			newPts[i] = distX(currPts[i]);	
			
		
		}
		
			

		return newPts;
	}



}
