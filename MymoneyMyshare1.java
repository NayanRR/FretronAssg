import java.util.*;

public class MymoneyMyshare1 {
    public static void main(String[] args) {
        List<Integer> apples = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int totalWeight = 0;

        while (true) {
            System.out.print("Enter apple weight in gram (-1 to stop): ");
            int weight = scanner.nextInt();
            if (weight == -1) break;
            apples.add(weight);
            totalWeight += weight;
        }

        apples.sort(Collections.reverseOrder()); // Sort apples in descending order

        int ramTarget = (int) (totalWeight * 0.5);
        int shamTarget = (int) (totalWeight * 0.3);
        int rahimTarget = totalWeight - ramTarget - shamTarget; // Ensure exact total

        int[] distribution = new int[apples.size()];
        Arrays.fill(distribution, -1);

        if (distributeApples(apples, 0, ramTarget, shamTarget, rahimTarget, distribution, new int[3])) {
            System.out.println("Distribution Result:");
            printDistribution(apples, distribution);
        } else {
            System.out.println("Perfect distribution not possible.");
            System.out.println("Remaining apples: " + apples);
        }

        scanner.close();
    }

    private static boolean distributeApples(List<Integer> apples, int index, int ramTarget, int shamTarget, int rahimTarget, int[] distribution, int[] currentSums) {
        if (index == apples.size()) {
            return currentSums[0] == ramTarget && currentSums[1] == shamTarget && currentSums[2] == rahimTarget;
        }

        // Tryig to assignthe currnt apple to each person
        for (int person = 0; person < 3; person++) {
            int target = (person == 0) ? ramTarget : (person == 1) ? shamTarget : rahimTarget;
             // Check if adding this apple doesn't exceed the person's target
            if (currentSums[person] + apples.get(index) <= target) {
                 // Assign apple to this person
                distribution[index] = person;
                currentSums[person] += apples.get(index);
                 //Going  to the next apple
                if (distributeApples(apples, index + 1, ramTarget, shamTarget, rahimTarget, distribution, currentSums)) {
                    return true;
                }
                 // Backtracking to try all the options and remove the curent one
                currentSums[person] -= apples.get(index);
            }
        }

          // No valid assignment found for this apple   
        distribution[index] = -1;
        return false;
    }

    private static void printDistribution(List<Integer> apples, int[] distribution) {
        List<Integer>[] result = new List[]{new ArrayList<>(), new ArrayList<>(), new ArrayList<>()};
        for (int i = 0; i < distribution.length; i++) {
            if (distribution[i] != -1) {
                result[distribution[i]].add(apples.get(i));
            }
        }
        System.out.println("Ram: " + result[0]);
        System.out.println("Sham: " + result[1]);
        System.out.println("Rahim: " + result[2]);
    }

    //Time complexity is  O(3^n)
    // At each level of recursion, we're making 3 choices.
    // The depth of the recursion tree is n (the number of apples).
    // This results in a total of 3 * 3 * 3 * ... (n times) = 3^n possible paths in the recursion tree.
}

