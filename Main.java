/**
 * Tests methods to provide summation,
 * uses multithreading of multiples sizes
 * and measures the runtime for differing
 * amounts of threads.
 * 
 * Done for Algorithms Analysis (COMP 317)
 * 
 * @author mhultgren
 * @since 11-08-2019
 */

import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		int R = 1;
		long sum, count, N;
		long start, totalTime, averageTime;
		
		// initialize array with large value in order to test at multiple values of R
		ThreadSum[] threads = new ThreadSum[2048];
		Arrays.fill(threads, null);
		
		// SINGLE FOR-LOOP
		System.out.println("---METHOD ONE---");
		
		sum = 0;
		count = 1;
		averageTime = 0;
		totalTime = 0;
		
		N = 100000001;
		
		// find summation through while-loop
		// find average runtime over 100 times
		for (int i=0; i<100; i++) {
			sum = 0;
			count = 1;
			
			start = System.nanoTime();
			System.out.print(i + " ");
			while (count < N) {
				sum += count;
				count++;
			} totalTime = System.nanoTime() - start;
			
			averageTime += totalTime;
			
		} averageTime /= 100;
		
		System.out.println("\nN: " + (N - 1));
		System.out.println("R: " + R);
		System.out.println("Sum: " + sum);
		System.out.println("Time elapsed: " + averageTime + " ns");
		
		// SINGLE THREAD
		System.out.println("\n\n---METHOD TWO---");
		
		averageTime = 0;
		
		// find average runtime for use of one thread
		for (int i=0; i<100; i++) {
			System.out.print(i + " ");

			ThreadSum t1 = new ThreadSum(1, 100000001);
			
			start = System.nanoTime();
			t1.start();
			t1.join();
			
			totalTime = System.nanoTime() - start;
			averageTime += totalTime;
		}
		
		averageTime /= 100;
		
		System.out.println("\nN: " + (N - 1));
		System.out.println("R: " + R);
		System.out.println("Sum: " + sum);
		System.out.println("Time elapsed: " + averageTime + " ns");
		
		// TWO THREADS
		System.out.println("\n\n--METHOD THREE--");
		
		callDivide(N, 2, threads);
		
		System.out.println("\n\n---METHOD FOUR--");
		
		callDivide(N, 3, threads);
		
		callDivide(N, 4, threads);
		
		callDivide(N, 5, threads);
		
		callDivide(N, 6, threads);
		
		callDivide(N, 7, threads);
		
		callDivide(N, 8, threads);
		
		System.out.println("\n\n---METHOD FIVE--");
		
		callDivide(N, 16, threads);
		
		callDivide(N, 64, threads);
		
		callDivide(N, 256, threads);
		
		callDivide(N, 1024, threads);
	}
	
	public static void callDivide(long N, int R, ThreadSum[] threads) throws InterruptedException {
		
		/**
		 * This method is responsible for calling the threadSumDivide() method
		 * as well as timing the method.
		 * 
		 * Takes same parameters as ThreadSumDivide() in order to call the method
		 */
		
		long start, sum, totalTime, averageTime;
		
		sum = 0;
		totalTime = 0;
		averageTime = 0;
		
		// runs threadSumDivide 100 times in order to find an average runtime
		
		for (int i=0; i<100; i++) {
			start = System.nanoTime();
			
			sum = threadSumDivide(N, R, threads);
			
			totalTime = System.nanoTime() - start;
			averageTime += totalTime;
		}
		
		averageTime /= 100;
		
		// outputs pertinent information after running
		
		System.out.println("\nN: " + (N - 1));
		System.out.println("R: " + R);
		System.out.println("Sum: " + (sum));
		System.out.println("Time elapsed: " + averageTime + " ns");
		
	}
	
	public static long threadSumDivide(long N, int R, ThreadSum[] threads) throws InterruptedException {
		
		/**
		 * References the ThreadSum class, in file ThreadSum.java
		 * 
		 * @param N the given number which will be used in the summation process
		 * @param R the amount of threads which will be run, all equal sized parts of N
		 * @param threads an array of threads which will be used to retrieve sums later
		 */
		
		long sum = 0;
		
		// divide N into R equal sized threads to be summed concurrently
		for (int i=0; i<R; i++) {
			threads[i] = new ThreadSum(i*N/R, (i+1)*N/R);
		}
		
		// threads are started one at a time, and joined after each has begun
		
		for (int i=0; i<R; i++) {
			threads[i].start();
		}
		
		for (int i=0; i<R; i++) {
			threads[i].join();
		}
		
		// go through each thread and add the partial sum to a returned value
		
		for (int i=0; i<R; i++) {
			sum += threads[i].sum;
		} return sum;
	}

}