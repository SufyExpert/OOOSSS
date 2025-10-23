import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class MMWithThreadExecService 
{
	
	static int[][] sMat = new int[3][3]; // source matrix
	static int[][] rMat = new int[3][3]; // squared matrix
	
	public static void main (String[] arg)
	{
		Random rand = new Random();

		for (int i = 0; i < sMat.length; i++)
			for (int j = 0; j < sMat[i].length; j++)
				sMat[i][j]=rand.nextInt(10);

		System.out.println("Source matrix is ");
		for (int i = 0; i < sMat.length; i++) 
		{
			for (int j = 0; j < sMat[i].length; j++)
				System.out.print(sMat[i][j]+" ");
			System.out.println();	
		}
		
		MatrixThread[] tArray = new MatrixThread[3];
		for ( int i = 0; i < tArray.length; ++i)
			tArray[i] = new MatrixThread(sMat, rMat, i);

		ExecutorService  eS = Executors.newCachedThreadPool();
		for ( int i = 0; i < tArray.length; ++i)
			eS.execute(tArray[i]);
		eS.shutdown();
        try {
				eS.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		  e.printStackTrace();
		}
		System.out.println("The result of MxM is ");
		System.out.println();
		for ( int i = 0; i < tArray.length; ++i )
		{
			for ( int j = 0; j < tArray.length; ++j)
				System.out.printf ("%3d ", rMat[i][j]);
			System.out.println();
		}
	}
}