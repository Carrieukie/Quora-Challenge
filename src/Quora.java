import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
