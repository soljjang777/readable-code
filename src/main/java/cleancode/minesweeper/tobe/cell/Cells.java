package cleancode.minesweeper.tobe.cell;

import java.util.Arrays;
import java.util.List;

public class Cells {
    //  1급 컬렉션으로 생성 : 생성 조건은 필드가 반드시 1개
    private final List<Cell> cells;

    public Cells(List<Cell> cells) {
        this.cells = cells;
    }

    public static Cells of(List<Cell> cells) {
        return new Cells(cells);
    }

    public static Cells from(Cell[][] cells) {
        List<Cell> cellList = Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .toList();
        return of(cellList);
    }

    public boolean isAllChecked() {
        return cells.stream().allMatch(Cell::isChecked);
    }
}
