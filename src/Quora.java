import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Quora’s mission is to share and grow the world’s knowledge. A vast amount of the knowledge that would be valuable to many people is currently only available to a few - either locked in people’s heads, or only accessible to select
 * groups. We want to connect the people who have knowledge to the people who need it, to bring together people with
 * different perspectives so they can understand each other better, and to empower everyone to share their knowledge for
 * the benefit of the rest of the world.
 * There’s a lot of content on Quora and we need a way of recommending the top stories for a user. Recommender
 * systems drive many features at Quora, but in this problem we’ll focus on Digest recommendation. Quora Digests are
 * emails which we send to users containing top recommended stories based on their interests.
 * Note: This is a fictitious representation of our Digest recommender system.
 * There are n stories (S1, . . . , Sn) and m users (U1, . . . , Um). Each story is created by a single user. A user can follow
 * both other users and other stories.
 * The Digest recommendation score for story Sk to user Ui
 * is defined as follows:
 * • If Ui created or follows Sk, the score is −1.
 * • Otherwise, the score is
 * Xm
 * j=1
 * a(Ui
 * , Uj ) × b(Uj , Sk)
 * where
 * a(Ui
 * , Uj ) =
 * 
 * 
 * 
 * 0 if i = j
 * otherwise, 3 if Ui follows Uj
 * otherwise, 2 if Ui follows stories created by Uj
 * otherwise, 1 if Ui follows stories followed by Uj
 * otherwise, 0
 * b(Uj , Sk) =
 * 
 * 
 * 
 * 2 if Uj created Sk
 * otherwise, 1 if Uj follows Sk
 * otherwise, 0
 * Given the stories, users, and their associations, find the top three recommended Digest stories for each user.
 * Input
 * Your program will receive input from standard input.
 * The first line contains two space-separated positive integers n and m, representing the number of stories and the number of users.
 * The following n lines each contain a single integer. The k-th line contains an integer j indicating that Uj created Sk.
 * After that, there will be two integers p and q, representing the number of follows between users and the number of
 * story follows.
 * The next p lines each contain two integers i and j representing that user Ui follows user Uj .
 * The final q lines each contain two integers i and k representing that user Ui follows story Sk.
 * Output
 * Your program should write to standard output.
 * Print m lines. The i-th line should contain three integers representing the recommended stories for Ui
 * . To select the
 * stories to output, sort all the stories in non-increasing order by recommendation score, and in increasing order by story
 * index among stories with the same score. Then, print the indexes of the first 3 stories in that order.
 * Constraints
 * • 5 ≤ n, m ≤ 200
 * • 0 ≤ p ≤ n
 * 2 − n
 * • 0 ≤ q ≤ nm
 * • It is guaranteed that there will be at least 3 stories to recommend for each user.
 * • There will be no duplication on follow relations.
 * • When Ui created Sk, it is guaranteed that Ui does not follow Sk.
 * Scoring
 * There are 25 test cases, each worth 4 points. Your submission score will be the sum of the points you get from each
 * test case you pass.
 * Sample Input 1 Sample Output 1
 * 7 5
 * 1
 * 1
 * 2
 * 3
 * 4
 * 5
 * 5
 * 2 4
 * 1 2
 * 4 5
 * 1 7
 * 5 2
 * 3 3
 * 4 1
 * <p>
 * Outputs
 * 3 6 4
 * 1 2 4
 * 1 2 5
 * 7 2 6
 * 1 3
 */

public class Quora {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] created_by = new int[N + 1];
        for (int i = 1; i <= N; i++) created_by[i] = sc.nextInt();

        int P = sc.nextInt();
        int Q = sc.nextInt();

        boolean[][] follows_user = new boolean[M + 1][M + 1];
        for (int i = 1; i <= P; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            follows_user[a][b] = true;
        }
        boolean[][] follows_story = new boolean[M + 1][N + 1];
        for (int i = 1; i <= Q; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            follows_story[a][b] = true;
        }

        boolean[][] follows_users_story = new boolean[M + 1][M + 1];
        boolean[][] follows_same_story = new boolean[M + 1][M + 1];
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++)
                if (follows_story[i][j]) {
                    follows_users_story[i][created_by[j]] = true;
                    for (int k = 1; k <= M; k++) if (follows_story[k][j]) follows_same_story[i][k] = true;
                }
        }

        for (int i = 1; i <= M; i++) {
            ArrayList<Pair> scores = new ArrayList<Pair>();
            for (int j = 1; j <= N; j++) {
                int score = 0;
                if (created_by[j] == i || follows_story[i][j]) score = -1;
                else {
                    for (int k = 1; k <= M; k++) {
                        int a = 0, b = 0;

                        if (i == k) a = 0;
                        else if (follows_user[i][k]) a = 3;
                        else if (follows_users_story[i][k]) a = 2;
                        else if (follows_same_story[i][k]) a = 1;
                        else a = 0;

                        if (created_by[j] == k) b = 2;
                        else if (follows_story[k][j]) b = 1;
                        else b = 0;

                        score += a * b;
                    }
                }
                scores.add(new Pair(score, j));
            }
            Collections.sort(scores);
            for (int j = 0; j < 3; j++) {
                System.out.print(scores.get(j).b);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

class Pair implements Comparable<Pair> {
    int a, b;

    public Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int compareTo(Pair rhs) {
        if (this.a != rhs.a) return rhs.a - this.a;
        return this.b - rhs.b;
    }
}
