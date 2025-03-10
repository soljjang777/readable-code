package cleancode.minesweeper.tobe.minesweeper.board.cell;

// 상속 구조는 결합도가 높은 구조다 하위 모듈이 상위 모듈을 참조하는 형태라서 결합도가 높은 상태이다, 캡슐화도 깨진상태(부모의 값을 알고 있으닌깐)
// 부모의 변경도 자식들에게 영향이 감
// 그래서 인터페이스 구현(조합)시 구조 유연도가 높아짐
public interface Cell {

    boolean isLandMine();

    boolean hasLandMineCount();

    CellSnapshot getSnapshot();

    void flag();

    void open();

    boolean isChecked();

    boolean isOpened();
}
