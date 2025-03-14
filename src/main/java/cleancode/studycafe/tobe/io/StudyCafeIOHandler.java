package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.order.StudyCafePassOder;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;

import java.util.List;
// Input, Output은 같이 움직임으로 일련의 객체로 만들어서 사용
// 사용자와의 인터렉션을 객체하나로 통합
public class StudyCafeIOHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    public void showWelcomeMessage() {
        outputHandler.showWelcomeMessage();

    }

    public void showAnnouncement() {
        outputHandler.showAnnouncement();
    }

    public void showPassOrderSummary(StudyCafePassOder passOder) {
        outputHandler.showPassOrderSummary(passOder);
    }

    public void showSimpleMessage(String message) {
        outputHandler.showSimpleMessage(message);
    }

    public StudyCafePassType askPassTypeSelection() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    public StudyCafeSeatPass askPassSelecting(List<StudyCafeSeatPass> passCandidates) {
        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    public boolean askLockerPass(StudyCafeLockerPass lockerPassCandidate) {
        // 3. 사용자에게 락커 사용할건지 물어보기 (출력)
        outputHandler.askLockerPass(lockerPassCandidate);
        // 4. 사용한다고 선택 락커 이용권 리턴(입력)
        return inputHandler.getLockerSelection();
    }
}
