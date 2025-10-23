import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FA23_BCS_161 {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Please provide number_of_Players initial_balance cut_Off_Value zakat_on_off");
            System.exit(1);
        }

        int numPlayers = Integer.parseInt(args[0]);
        int initialBalance = Integer.parseInt(args[1]);
        int cutOffValue = Integer.parseInt(args[2]);
        int zakat = Integer.parseInt(args[3]);
        int rounds = 0;

        // Initialize balances
        int[] balances = new int[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            balances[i] = initialBalance;
        }

        // Random generator
        Random rand = new Random();

        // Get number of cores and create fixed thread pool
        int numCores = Runtime.getRuntime().availableProcessors();
        System.out.println("The machine that I am running on has " + numCores + " cores");
        ExecutorService executor = Executors.newFixedThreadPool(numCores);

        // Game loop
        while (numPlayers > 1) {
            rounds++;
            System.out.println("This is round# " + rounds);

            // List to track tasks
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < numPlayers; i++) {
                if ((balances[i] < cutOffValue) && (zakat == 0)) {
                    continue; // Skip eliminated players in non-zakat mode
                }

                // Find a valid opponent
                int opponent;
                do {
                    opponent = rand.nextInt(numPlayers);
                } while (opponent == i || balances[opponent] < cutOffValue);

                // Create task for this pair
                final int myI = i;
                final int myOpp = opponent;
                Runnable task = () -> {
                    Thread currentThread = Thread.currentThread();
                    String threadName = currentThread.getName();
                    long time = System.currentTimeMillis();
                    System.out.println("Thread " + threadName + " is going to start game at " + time);

                    // Bet logic
                    int betAmount = Math.min(balances[myI], balances[myOpp]) / 5;
                    boolean coinFlip = rand.nextBoolean();
                    if (coinFlip) {
                        balances[myI] += betAmount;
                        balances[myOpp] -= betAmount;
                    } else {
                        balances[myI] -= betAmount;
                        balances[myOpp] += betAmount;
                    }

                    System.out.println("Bet result : player " + myI + " has " + balances[myI] + " vs " + myOpp + " has " + balances[myOpp]);
                };

                // Submit task and add to tracking
                futures.add(executor.submit(task));

                // Delay between submissions
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Wait for all bets to finish
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Zakat or elimination
            if (zakat == 1) {
                int rich = 0, max = 0, poor = 0;
                boolean foundPoor = false;
                for (int j = 0; j < numPlayers; j++) {
                    if (balances[j] <= cutOffValue) {
                        foundPoor = true;
                        poor = j;
                        break;
                    }
                }
                if (foundPoor) {
                    for (int j = 0; j < numPlayers; j++) {
                        if (balances[j] > max) {
                            max = balances[j];
                            rich = j;
                        }
                    }
                    int calcZakat = (int) (balances[rich] * 0.025);
                    balances[poor] += calcZakat;
                    balances[rich] -= calcZakat;
                    System.out.println("Zakat = " + calcZakat + " was transferred from Player " + rich + " to " + poor);
                }
            } else {
                int numRemainingPlayers = 0;
                for (int j = 0; j < numPlayers; j++) {
                    if (balances[j] >= cutOffValue) {
                        balances[numRemainingPlayers] = balances[j];
                        numRemainingPlayers++;
                    } else {
                        System.out.println("Player " + j + " eliminated with balance " + balances[j]);
                    }
                }
                numPlayers = numRemainingPlayers;
            }
        }

        // Cleanup
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Game finished after %d rounds\n", rounds);
    }
}