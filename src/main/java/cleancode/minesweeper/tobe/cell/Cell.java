package cleancode.minesweeper.tobe.cell;

// 객체를 만들 때 getter, setter은 바로 만들지 않고 필요한 순간에만 만드는 편이 좋음
// 특히 setter는 만들지 않는게 좋음 -> 객체의 불변성 보존
public abstract class Cell {

    // 매직 넘버, 매직 스트링
    // - 의미를 갖고 있으나, 상수로 추출되지 않은 숫자, 문자열 등
    // - 상수 추출로 이름을 짓고 의미를 부여함으로써 가독성, 유지보수성 업
    protected static final String FLAG_SIGN = "⚑";

    protected static final String UNCHECKED_SIGN = "□";

    protected boolean isFlagged;
    protected boolean isOpened;

    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public abstract boolean isLandMine();

    public boolean isOpened() {
        return isOpened;
    }

    public abstract boolean hasLandMineCount();

    public abstract String getSign();
}
