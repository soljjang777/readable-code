package cleancode.studycafe.tobe.model.pass;

import java.util.List;

public class StudyCafeSeatPasses {

    // 1급 컬렉션으로 묶어서 관련 메소드는 여기서 정리 가능
    private final List<StudyCafeSeatPass> passes;

    public StudyCafeSeatPasses(List<StudyCafeSeatPass> passes) {
        this.passes = passes;
    }

    public static StudyCafeSeatPasses of(List<StudyCafeSeatPass> passes) {
        return new StudyCafeSeatPasses(passes);
    }

    public List<StudyCafeSeatPass> findPassBy(StudyCafePassType studyCafePassType) {
        return passes.stream()
                // studyCafePass.getPassType() == studyCafePassType 으로 객체에게 데이터를 강탈하지말고
                // 조회 메서드를 만들어서 메세지로 물어보자!
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }
}
