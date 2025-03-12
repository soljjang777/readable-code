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
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
    }

    // Optional을 사용함으로서 null이 있을 수도 있고 없을 수도 있어라는 명시 -> NPE 방지
    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        // 스터디 카페 이용권 타입이 고정이 아니면 null 반환
        if (selectedPass.getPassType() != StudyCafePassType.FIXED) {
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

    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
            .filter(lockerPass ->
                lockerPass.getPassType() == selectedPass.getPassType()
                    && lockerPass.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);
    }
}
