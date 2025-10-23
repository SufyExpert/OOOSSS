import java.util.Random;

public class MatrixSquare {

	static int[][] sMat = new int[3][3]; // source matrix
	static int[][] rMat = new int[3][3]; // squared matrix

	public static void main(String [] args)
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
		
		MatrixThread thread1 = new MatrixThread(sMat, rMat, 0);
		MatrixThread thread2 = new MatrixThread(sMat, rMat, 1);
		MatrixThread thread3 = new MatrixThread(sMat, rMat, 2);
		
		Thread th1 = new Thread(thread1);
		Thread th2 = new Thread(thread2);
		Thread th3 = new Thread(thread3);		
		try
		{	   
			th1.start();
			th2.start();
			th3.start();

			th1.join();
			th2.join();
			th3.join();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		System.out.println("\n\nResult:");
		for (int i = 0; i < rMat.length; i++) 
		{
			for (int j = 0; j < rMat[i].length; j++)
				System.out.print(rMat[i][j]+" ");
			System.out.println();
		}
  }
}