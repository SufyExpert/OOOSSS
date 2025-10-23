import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinExample 
{
	public static void main(String[] args) {
		int data[] = new int[100];
		Random rand = new Random();
		for (int i = 0; i < data.length; i++)
			data[i] = rand.nextInt(100);
		System.out.println("Original Data");
		for (int i = 0; i < data.length; i++)
		{
			if (i %10 == 0 )
				System.out.println("");
			System.out.printf("%2d ", data[i]);
		}
		IncrementTask iTask = new IncrementTask(data, 0, data.length);
		ForkJoinPool Pool = ForkJoinPool.commonPool();
		Pool.invoke(iTask);
		System.out.println("\nModified Data");
		for (int i = 0; i < data.length; i++)
		{
			if (i %10 == 0 )
				System.out.println("");
			System.out.printf("%2d ", data[i]);
		}		
	}
}
class IncrementTask extends RecursiveAction {
   final int[] array; final int lo, hi;
   final int THRESHOLD = 10;
   IncrementTask(int[] array, int lo, int hi) {
     this.array = array; this.lo = lo; this.hi = hi;
   }
   protected void compute() {
     if (hi - lo < THRESHOLD) {
       for (int i = lo; i < hi; ++i)
         array[i]++;
     }
     else {
       int mid = (lo + hi) >>> 1;
       invokeAll(new IncrementTask(array, lo, mid),
                 new IncrementTask(array, mid, hi));
     }
   }
 }