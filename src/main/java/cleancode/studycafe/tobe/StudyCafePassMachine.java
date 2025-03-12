package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            // * 파라미터로 Optional을 전달하는건 안티패턴
            // Optional<StudyCafeLockerPass>을 파라미터로 전달해서 받는 메소드 입장에서는
            // Optional이 null일 수도 있고
            // Optional안에 있는 StudyCafeLockerPass이 null일 수도 있고 아닐 수도 있는
            // 모든 케이스를 검사해야됨

            optionalLockerPass.ifPresentOrElse(
                    lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
            );

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidates = findPassCandidateBy(studyCafePassType);

        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private List<StudyCafePass> findPassCandidateBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();

        return allPasses.stream()
                // studyCafePass.getPassType() == studyCafePassType 으로 객체에게 데이터를 강탈하지말고
                // 조회 메서드를 만들어서 메세지로 물어보자!
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }

    // Optional을 사용함으로서 null이 있을 수도 있고 없을 수도 있어라는 명시 -> NPE 방지
    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        // 고정 좌석 타입이 아닌가? -> 낮은 추상화 레벨 -> doesNotFixedType()
        // 사물함 옵션을 사용할 수 있는 타입이 아닌가?
        //  -> 사용자가 고른 이용권이 사물함 옵션을 사용할 수 있는지 없는지가 더 맞는 관심사
        //  -> 높은 추상화 레벨 -> cannotUseLocker()
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        // 1. 이용권 타입과 이용기간에 따른 락커이용권 후보들을 추출
        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        // 2. 락커 이용권 후보들이 존재하면
        if (lockerPassCandidate != null) {
            // 3. 사용자에게 락커 사용할건지 물어보기 (출력)
            outputHandler.askLockerPass(lockerPassCandidate);
            // 4. 사용한다고 선택 락커 이용권 리턴(입력)
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass pass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
            .filter(pass::isSameDurationType)
            .findFirst()
            .orElse(null);
    }
}
