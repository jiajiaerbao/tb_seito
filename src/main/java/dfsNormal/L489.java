package dfsNormal;

import java.util.Arrays;
import java.util.HashSet;

public class L489 {
    /*
    * 问题点 1: targetDir = (dir + d) % DIRECTIONS.length;
    * 问题点 2: Math.abs(targetDir - dir) == 2
    * 问题点 3: getDirIndex(targetDir + 2);
    * 问题点 4: if (!initialMove)
    * 问题点 5: new Cell(cur.i + DIRECTIONS[targetDir][0], cur.j + DIRECTIONS[targetDir][1]);
    * 问题点 6:
    * 问题点 7:
    * */
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private static class Cell {
        public int i, j;
        public Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }
        @Override
        public int hashCode() {
            return i * 31 + j;
        }
        @Override
        public boolean equals(Object o) {
            if (o instanceof Cell) {
                Cell that = (Cell) o;
                return this.i == that.i && this.j == that.j;
            } else {
                return false;
            }
        }
    }

    public void cleanRoom(Robot robot) {
        dfs(robot, new Cell(0, 0), 0, new HashSet<Cell>(), true);
    }

    private void dfs(Robot robot, Cell cur, int dir, HashSet<Cell> visited, boolean initialMove) {
        robot.clean();
        int curDir = dir;
        for (int d = 0; d < DIRECTIONS.length; d++) {
            int targetDir = (dir + d) % DIRECTIONS.length;
            if (!initialMove && Math.abs(targetDir - dir) == 2) {
                continue;
            }
            turn(robot, curDir, targetDir);
            Cell nextCell = new Cell(cur.i + DIRECTIONS[targetDir][0], cur.j + DIRECTIONS[targetDir][1]);
            if (visited.add(nextCell) && robot.move()) {
                dfs(robot, nextCell, targetDir, visited, false);
                curDir = getDirIndex(targetDir + 2);
            } else {
                curDir = targetDir;
            }
        }
        if (!initialMove) {
            //move back
            turn(robot, curDir, getDirIndex(dir + 2));
            robot.move();
        }
    }
    private void turn(Robot robot, int curDir, int targetDir) {
        if (curDir == targetDir) {
            return;
        } else if (getDirIndex(curDir + 1) == getDirIndex(targetDir)) {
            robot.turnRight();
        } else if (getDirIndex(curDir - 1) == getDirIndex(targetDir)) {
            robot.turnLeft();
        } else if (getDirIndex(curDir + 2) == getDirIndex(targetDir)) {
            robot.turnRight();
            robot.turnRight();
        } else {
            throw new IllegalArgumentException();
        }
    }
    private int getDirIndex(int dir) {
        return (dir + DIRECTIONS.length) % DIRECTIONS.length;
    }

}

interface Robot {
    // Returns true if the cell in front is open and robot moves into the cell.
    // Returns false if the cell in front is blocked and robot stays in the current cell.
    public boolean move();

    // Robot will stay in the same cell after calling turnLeft/turnRight.
    // Each turn will be 90 degrees.
    public void turnLeft();

    public void turnRight();

    // Clean the current cell.
    public void clean();
}

class RobotImpl implements Robot {
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int i, j, dir;
    private int room[][];
    private int row, col;
    public RobotImpl(int room[][], int i, int j, int dir) {
        this.room = room;
        this.i = i;
        this.j = j;
        this.dir = dir;

        this.row = room.length;
        this.col = room[0].length;
    }
    @Override
    public boolean move() {
        int ii = i + DIRECTIONS[dir][0], jj = j + DIRECTIONS[dir][1];
        if (ii < 0 || ii >= row || jj < 0 || jj >= col || room[ii][jj] == 0) {
            return false;
        } else {
            i = ii;
            j = jj;
            return true;
        }
    }
    @Override
    public void turnLeft() {
        dir = (dir + 3) % 4;
    }
    @Override
    public void turnRight() {
        dir = (dir + 1) % 4;
    }
    @Override
    public void clean() {
        room[i][j] = 2;
    }
    public void print() {
        for (int[] r : room) {
            System.out.println(Arrays.toString(r));
        }
    }
}
