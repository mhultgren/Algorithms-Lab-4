/**
 * Enables multithreading with summation
 * 
 * @author mhultgren
 * @since 11-08-2019
 */

public class ThreadSum extends Thread {
	
	long sum, lowerN, upperN;
	
	public ThreadSum(long lowerN, long upperN) {
		this.lowerN = lowerN;
		this.upperN = upperN;
		this.sum = 0;
	}
	
	@Override
	public void run() {
		// exclusive upper bound
		long count = lowerN;
		while (count < upperN) {
			sum += count;
			count++;
		}
	}
} 