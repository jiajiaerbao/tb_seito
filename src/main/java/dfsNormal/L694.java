package dfsNormal;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class L694 {
    class Solution {
        public int numDistinctIslands(int[][] grid) {
            //corner case
            if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
                return 0;
            }
            Set<String> set = new HashSet<>();

            int row = grid.length;
            int col = grid[0].length;

            boolean[][] visited = new boolean[row][col];

            int[][] directionsInt = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            Character[] directionsChar = {'U', 'R', 'D', 'L', 'X'};

            Character startChar = 'S';
            Character endChar = 'N';

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        StringBuilder path = new StringBuilder();
                        path.append(startChar);
                        dfs(grid, i, j, visited, path, directionsInt, directionsChar);
                        path.append(endChar);
                        set.add(path.toString());
                    }
                }
            }
            return set.size();
        }

        private void dfs(int[][] grid, int idxI, int idxJ, boolean[][] visited, StringBuilder path,
                         int[][] directionsInt, Character[] directionsChar) {
            int row = grid.length;
            int col = grid[0].length;
            if (idxI < 0 || idxI >= row ||
                    idxJ < 0 || idxJ >= col ||
                    grid[idxI][idxJ] == 0 || visited[idxI][idxJ]) {
                return;
            }
            visited[idxI][idxJ] = true;
            for (int i = 0; i < directionsInt.length; i++) {
                int nextI = idxI + directionsInt[i][0];
                int nextJ = idxJ + directionsInt[i][1];
                if (nextI >= 0 && nextI < grid.length &&
                        nextJ >= 0 && nextJ < grid[0].length &&
                        grid[nextI][nextJ] == 1 && !visited[nextI][nextJ]) {
                    path.append(directionsChar[i]);
                    dfs(grid, nextI, nextJ, visited, path, directionsInt, directionsChar);
                    path.append(directionsChar[4]);
                }
            }
        }
    }

    public static void main(String args[]) {
        L694 l694 = new L694();
        //Solution solution = new Solution();
        int[][] input = {{1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 1, 0, 1, 1}};
        /*System.out.println(l694.numDistinctIslands(input));*/
    }
}
