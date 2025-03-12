package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.order.StudyCafePassOder;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafePasses;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler iOHandler = new StudyCafeIOHandler();

    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            iOHandler.showWelcomeMessage();
            iOHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);
            StudyCafePassOder passOrder = StudyCafePassOder.of(
                    selectedPass,
                    optionalLockerPass.orElse(null)
            );

            // * 파라미터로 Optional을 전달하는건 안티패턴
            // Optional<StudyCafeLockerPass>을 파라미터로 전달해서 받는 메소드 입장에서는
            // Optional이 null일 수도 있고
            // Optional안에 있는 StudyCafeLockerPass이 null일 수도 있고 아닐 수도 있는
            // 모든 케이스를 검사해야됨
            iOHandler.showPassOrderSummary(passOrder);

        } catch (AppException e) {
            iOHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            iOHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeSeatPass selectPass() {
        StudyCafePassType passType = iOHandler.askPassTypeSelection();
        List<StudyCafeSeatPass> passCandidates = findPassCandidateBy(passType);

        return iOHandler.askPassSelecting(passCandidates);
    }

    private List<StudyCafeSeatPass> findPassCandidateBy(StudyCafePassType studyCafePassType) {
        StudyCafePasses allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.findPassBy(studyCafePassType);
    }

    // Optional을 사용함으로서 null이 있을 수도 있고 없을 수도 있어라는 명시 -> NPE 방지
    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {
        // 고정 좌석 타입이 아닌가? -> 낮은 추상화 레벨 -> doesNotFixedType()
        // 사물함 옵션을 사용할 수 있는 타입이 아닌가?
        //  -> 사용자가 고른 이용권이 사물함 옵션을 사용할 수 있는지 없는지가 더 맞는 관심사
        //  -> 높은 추상화 레벨 -> cannotUseLocker()
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        // 1. 이용권 타입과 이용기간에 따른 락커이용권 후보들을 추출
        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        // 2. 락커 이용권 후보들이 존재하면
        if (lockerPassCandidate.isPresent()) {
            StudyCafeLockerPass lockerPass = lockerPassCandidate.get();

            boolean isLockerSelected = iOHandler.askLockerPass(lockerPass);
            if (isLockerSelected) {
                return Optional.of(lockerPass);
            }
        }

        return Optional.empty();
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidateBy(StudyCafeSeatPass pass) {
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.findLockerPassBy(pass);
    }
}
