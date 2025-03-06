package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
    }

    public void flag(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.flag();

    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        return cell.isLandMine();
    }

    public boolean isAllCellChecked() {
        // depth 줄이기
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                // cell.equals 은 cell에서 Null이 있을 가능성이 있는곳에서 호출중
//                .noneMatch(cell -> cell.equals(CLOSED_CELL_SIGN));
                // 이미 Null이 아닌값 CLOSED_CELL_SIGN으로 부터 비교하면 안전해짐 => NPE 발생 가능성이 낮아짐
//                .noneMatch(CLOSED_CELL_SIGN::equals);
                // cell에서 바로 get 하지말고 객체안에 메소드 만들어서 사용하기
//                .noneMatch(cell -> cell.isClosed());
                .allMatch(Cell::isChecked);
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize =getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = new EmptyCell();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMinerow = new Random().nextInt(rowSize);
            LandMineCell landMineCell = new LandMineCell();
            board[landMinerow][landMineCol] = landMineCell;
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                // 부정연산자를 제거 할 수 있으면 베스트
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearByLandMines(row, col);
                if (count == 0) {
                    continue;
                }
                NumberCell numberCell = new NumberCell(count);
                board[row][col] = numberCell;
            }
        }
    }

    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    private int countNearByLandMines(int row, int col) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < 10 && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.open();
    }

    public void openSurroundedCells(int row, int col) {
        // 코드가 글 읽듯이 읽어지게 리펙토링됨
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }
        if (isOpenedCell(row, col)) { // 보드(셀)이 열려있어? 리턴해
            return;
        }
        if (isLandMineCell(row, col)) { // 지뢰 셀이면 리턴해
            return;
        }

        open(row, col); // 앞에 조건에 안걸렸으면 아직 안열린 셀이닌깐 오픈해

        if (doesCellHaveLandMineCount(row, col)) { // 열고나서 숫자셀이야? 그럼 더이상 재귀돌지말고 멈춰
//            BOARD[row][col] = Cell.ofNearByLandMineCount(NEARBY_LAND_MIND_COUNTS[row][col]);
            return;
        }

        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

}
