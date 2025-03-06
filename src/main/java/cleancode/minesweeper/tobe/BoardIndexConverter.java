package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COL = 'a' ;

    public int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow, rowSize);
    }

    public int getSelectedColIndex(String cellInput, int colSize) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol, colSize);
    }

    private int convertRowFrom(String cellInputRow, int rowSize) {
        // 한줄이여도 메서드로 만듬 -> 코드의 양을 줄이는 목적이 아니라 추상화 목적
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        // 예외가 가능한 부부은 예상해서 예외 처리해주기
        if (rowIndex < 0 || rowIndex > rowSize) {
            throw new GameException("잘못된 입력입니다.");
        }

        return rowIndex;
    }

    private int convertColFrom(char cellInputCol, int colSize) {
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL;
        if (colIndex < 0 || colIndex > colSize) {
            // 잘못된 인수의 값을 던지는것 보다 예외를 던져주자
            // 예외에는 어떠한 메세지를 넣어서 같이 던져주자
            throw new GameException("잘못된 입력입니다");
        }

        return colIndex;
    }
}
