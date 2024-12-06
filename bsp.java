
class BullshitHaggis {

	int room; 

	int numitems;

	BullshitHaggis left, right;

	public BullshitHaggis(int roomnumber){
		
		this.room = roomnumber;
		this.left  = this.right = null

	}


}



class bsp{

	
	public void MakeBSP(float[] sector, boolean[] secval, int numBounds){
		
		BullshitHaggis bs = new BullshitHaggis(sector[0]);
			
		

		for (int i = 0; i < numbounds; i++){
			
			BullshitHaggis nbs = new BullshitHaggis(sector[i]);
			
			nbs.room = sector[i];
			
			
			if (secval[i]){
					
				
				if (i == 0){
				
					bs.right = nbs;
					
				}			
			
			}else{

			
				if (i == 0){
					bs.left = nbs; 
				}

			}
		}
	

	}




}
