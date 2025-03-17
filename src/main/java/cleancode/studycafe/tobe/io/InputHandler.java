package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;

import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner;  // Scanner를 주입받도록 변경

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = scanner.nextLine();

        if ("1".equals(userInput)) {
            return StudyCafePassType.HOURLY;
        }
        if ("2".equals(userInput)) {
            return StudyCafePassType.WEEKLY;
        }
        if ("3".equals(userInput)) {
            return StudyCafePassType.FIXED;
        }
        throw new AppException("잘못된 입력입니다.");
    }

    public StudyCafeSeatPass getSelectPass(List<StudyCafeSeatPass> passes) {
        String userInput = scanner.nextLine();
        int selectedIndex = Integer.parseInt(userInput) - 1;
        return passes.get(selectedIndex);
    }

    public boolean getLockerSelection() {
        String userInput = scanner.nextLine();
        return "1".equals(userInput);
    }

}
