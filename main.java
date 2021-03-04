import java.io.*;

class Main {


    static final int maxElephantsNumber = 1000000;


    static int[] elephantsWeights = new int[maxElephantsNumber];
    static int[] baseElephantOrder = new int[maxElephantsNumber];
    static int[] perm = new int[maxElephantsNumber];
    static boolean[] checked = new boolean[maxElephantsNumber];

    static long minElephantWeight = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        System.out.println(checkMinimumStrengthNeeded(args[0]));
    }

    public static int checkMinimumStrengthNeeded(String filename) throws IOException {
        BufferedReader objReader = new BufferedReader(new FileReader(filename));

        int numberOfElephants = Integer.parseInt(objReader.readLine());
        String[] weightsStr = objReader.readLine().split(" ");
        String[] baseOrderStr = objReader.readLine().split(" ");
        String[] targetOrderStr = objReader.readLine().split(" ");
        for (int i = 0; i < numberOfElephants; i++){
            elephantsWeights[i] = Integer.parseInt(weightsStr[i]);
            minElephantWeight = Math.min(minElephantWeight, elephantsWeights[i]);

            baseElephantOrder[i] = Integer.parseInt(baseOrderStr[i]) - 1;

            perm[Integer.parseInt(targetOrderStr[i]) - 1] = baseElephantOrder[i];
        }

        long minStrengthNeeded = 0;
        for (int i = 0; i < numberOfElephants; i++)
            if (!checked[i])
            {
                long min_cycle_weight = Long.MAX_VALUE;
                long cycle_weights_sum = 0;
                int cur = i;
                int cycle_length = 0;
                do {
                    min_cycle_weight = Math.min(min_cycle_weight, elephantsWeights[cur]);
                    cycle_weights_sum += elephantsWeights[cur];
                    cur = perm[cur];
                    checked[cur] = true;
                    cycle_length++;
                } while (cur != i);
                minStrengthNeeded += Math.min(
                        cycle_weights_sum+(cycle_length-2)*min_cycle_weight,
                        cycle_weights_sum+min_cycle_weight+(cycle_length+1)*minElephantWeight
                );
            }
        return (int) minStrengthNeeded;
    }
}