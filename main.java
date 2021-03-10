// This task solved also with Python and JavaScript you can find on my github -> github.com/Lok3rs/RecrutationTask
import java.util.Scanner;

class Main {


    static final int maxElephantsNumber = 1000000;


    static int[] elephantsWeights = new int[maxElephantsNumber];
    static int[] baseElephantOrder = new int[maxElephantsNumber];
    static int[] perm = new int[maxElephantsNumber];
    static boolean[] checked = new boolean[maxElephantsNumber];

    static long minElephantWeight = Long.MAX_VALUE;

    public static void main(String[] args) {
        System.out.println(checkMinimumStrengthNeeded());
    }

    public static long checkMinimumStrengthNeeded() {
        Scanner objReader = new Scanner(System.in);

        int numberOfElephants = Integer.parseInt(objReader.nextLine());
        String[] weightsStr = objReader.nextLine().split(" ");
        String[] baseOrderStr = objReader.nextLine().split(" ");
        String[] targetOrderStr = objReader.nextLine().split(" ");
        for (int i = 0; i < numberOfElephants; i++) {
            elephantsWeights[i] = Integer.parseInt(weightsStr[i]);
            minElephantWeight = Math.min(minElephantWeight, elephantsWeights[i]);

            baseElephantOrder[i] = Integer.parseInt(baseOrderStr[i]) - 1;

            perm[Integer.parseInt(targetOrderStr[i]) - 1] = baseElephantOrder[i];
        }

        long minStrengthNeeded = 0;
        for (int i = 0; i < numberOfElephants; i++)
            if (!checked[i]) {
                long minCycleWeight = Long.MAX_VALUE;
                long cycleWeightsSum = 0;
                int cur = i;
                int cycleLength = 0;
                do {
                    minCycleWeight = Math.min(minCycleWeight, elephantsWeights[cur]);
                    cycleWeightsSum += elephantsWeights[cur];
                    cur = perm[cur];
                    checked[cur] = true;
                    cycleLength++;
                } while (cur != i);
                minStrengthNeeded += Math.min(
                        cycleWeightsSum + (cycleLength - 2) * minCycleWeight,
                        cycleWeightsSum + minCycleWeight + (cycleLength + 1) * minElephantWeight
                );
            }
        return minStrengthNeeded;
    }
}
