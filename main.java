import java.io.*;

class Main {


    static final int maxElephantsNumber = 1000000;


    static int[] elephantsWeights = new int[maxElephantsNumber];
    static int[] baseElephantOrder = new int[maxElephantsNumber];
    static int[] perm = new int[maxElephantsNumber];
    static boolean[] checked = new boolean[maxElephantsNumber];

    static long minElephantWeight = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        try {
            System.out.println(checkMinimumStrengthNeeded(args[0]));
        } catch (FileNotFoundException e) {
            System.out.printf("There is no file '%s'", args[0]);
        } catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid data format in file");
        }
    }

    public static long checkMinimumStrengthNeeded(String filename) throws IOException {
        BufferedReader objReader = new BufferedReader(new FileReader(filename));

        int numberOfElephants = Integer.parseInt(objReader.readLine());
        String[] weightsStr = objReader.readLine().split(" ");
        String[] baseOrderStr = objReader.readLine().split(" ");
        String[] targetOrderStr = objReader.readLine().split(" ");
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