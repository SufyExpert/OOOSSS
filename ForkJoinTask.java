import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTask 
{
	public static void main(String[] args) {
		int data[] = new int[100];
		Random rand = new Random();
		for (int i = 0; i < data.length; i++)
			data[i] = rand.nextInt(100);
		System.out.println("Searh minimum from following values");
		for (int i = 0; i < data.length; i++)
		{
			if (i %10 == 0 )
				System.out.println("");
			System.out.printf("%2d ", data[i]);
		}
		MinTask iTask = new MinTask(data, 0, data.length);
		ForkJoinPool Pool = ForkJoinPool.commonPool();
		int result = Pool.invoke(iTask);
		System.out.printf("\nResult is %d \n", result);
	}
}
class MinTask extends RecursiveTask<Integer> {
   final int[] array; final int lo, hi;
   final int THRESHOLD = 10;
   MinTask(int[] array, int lo, int hi) {
     this.array = array; this.lo = lo; this.hi = hi;
   }
   protected Integer compute() {
	   int min=array[lo];
     if (hi - lo < THRESHOLD) {
		min = array[lo];
       for (int i = lo; i < hi; ++i)
         if (array[i] < min )
			 min = array[i];
		return min;
	}
     else {
       int mid = (lo + hi) >>> 1;
	   MinTask leftTask = new MinTask(array, lo, mid);
	   MinTask rightTask = new MinTask(array, mid, hi);
       invokeAll(leftTask, rightTask);
		return Math.min(leftTask.join(), rightTask.join());
     }
	
   }
 }