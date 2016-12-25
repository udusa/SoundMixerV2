package core;

public class Algorithm {
	
	private double[] SNR_levels;
	private int index;
	public Algorithm(double[] SNR_levels){
		this.SNR_levels = SNR_levels;
		index = SNR_levels.length/2;
	}
	
	public double getHigherSNR(){
		return SNR_levels[inc()];
	}
	
	public double getLowerSNR(){
		return SNR_levels[dec()];
	}
	
	private int inc(){
		if(index+1 < SNR_levels.length){
			index++;
		}
		return index;
	}
	private int dec(){
		if(index-1 > 0){
			index--;
		}
		return index;
	}
	public double getCurrentSNR(){
		return SNR_levels[index];
	}

}
