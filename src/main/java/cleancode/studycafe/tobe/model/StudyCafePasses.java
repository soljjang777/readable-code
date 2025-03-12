package cleancode.studycafe.tobe.model;

import java.util.List;

public class StudyCafePasses {

    // 1급 컬렉션으로 묶어서 관련 메소드는 여기서 정리 가능
    private final List<StudyCafePass> passes;

    public StudyCafePasses(List<StudyCafePass> passes) {
        this.passes = passes;
    }

    public static StudyCafePasses of(List<StudyCafePass> passes) {
        return new StudyCafePasses(passes);
    }

    public List<StudyCafePass> findPassBy(StudyCafePassType studyCafePassType) {
        return passes.stream()
                // studyCafePass.getPassType() == studyCafePassType 으로 객체에게 데이터를 강탈하지말고
                // 조회 메서드를 만들어서 메세지로 물어보자!
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }
}
