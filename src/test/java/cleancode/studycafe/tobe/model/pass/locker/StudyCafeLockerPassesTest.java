package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static cleancode.studycafe.tobe.model.pass.StudyCafePassType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudyCafeLockerPassesTest {

    @DisplayName("같은 기간 타입의 StudyCafeLockerPass를 찾을 수 있다")
    @Test
    void shouldFindLockerPassByMatchingDurationType() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(FIXED,4,250000,0.1);
        StudyCafeLockerPass lockerPass1 = StudyCafeLockerPass.of(FIXED,4,10000);
        StudyCafeLockerPass lockerPass2 = StudyCafeLockerPass.of(FIXED,12,30000);
        StudyCafeLockerPasses lockerPasses = new StudyCafeLockerPasses(List.of(lockerPass1, lockerPass2));

        // when
        Optional<StudyCafeLockerPass> foundLockerPass = lockerPasses.findLockerPassBy(seatPass);

        // then
        assertThat(foundLockerPass).isPresent();
        assertThat(foundLockerPass.get()).isEqualTo(lockerPass1);
    }

    @DisplayName("기간 타입이 맞지 않으면 StudyCafeLockerPass를 찾을 수 없다")
    @Test
    void shouldNotFindLockerPassWhenDurationTypeDoesNotMatch() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(FIXED,7,700000,0.15);
        StudyCafeLockerPass lockerPass1 = StudyCafeLockerPass.of(FIXED,4,10000);
        StudyCafeLockerPass lockerPass2 = StudyCafeLockerPass.of(FIXED,12,30000);
        StudyCafeLockerPasses lockerPasses = new StudyCafeLockerPasses(List.of(lockerPass1, lockerPass2));

        // when
        Optional<StudyCafeLockerPass> foundLockerPass = lockerPasses.findLockerPassBy(seatPass);

        // then
        assertThat(foundLockerPass).isEmpty();
    }

}
