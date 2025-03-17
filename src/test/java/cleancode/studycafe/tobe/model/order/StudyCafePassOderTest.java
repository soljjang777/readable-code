package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cleancode.studycafe.tobe.model.pass.StudyCafePassType.FIXED;
import static cleancode.studycafe.tobe.model.pass.StudyCafePassType.HOURLY;
import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassOderTest {

    @DisplayName("스터디카페 좌석 이용권 할인률이 있으면 할인금액을 반환한다")
    @Test
    void shouldReturnDiscountPriceWhenDiscountRateIsApplied() {
        //given
        StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(FIXED, 4, 250000, 0.1);
        StudyCafePassOder studyCafePassOder = StudyCafePassOder.of(studyCafeSeatPass, null);

        //when
        int discountPrice = studyCafePassOder.getDiscountPrice();

        //then
        assertThat(discountPrice).isEqualTo(25000);
    }

    @DisplayName("스터디카페 좌석 이용권 할인률이 0.0이면 0을 반환한다")
    @Test
    void shouldReturnZeroWhenDiscountRateIsZero() {
        //given
        StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(HOURLY, 2, 4000, 0.0);
        StudyCafePassOder studyCafePassOder = StudyCafePassOder.of(studyCafeSeatPass, null);

        //when
        int discountPrice = studyCafePassOder.getDiscountPrice();

        //then
        assertThat(discountPrice).isEqualTo(0);
    }

    @DisplayName("이용권 주문 시 총 금액을 반환한다 (고정석, 락커사용)")
    @Test
    void returnTotalPrice() {
        //given
        StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(FIXED, 4, 250000, 0.1);
        StudyCafeLockerPass studyCafeLockerPass = StudyCafeLockerPass.of(FIXED, 4, 10000);
        StudyCafePassOder studyCafePassOder = StudyCafePassOder.of(studyCafeSeatPass, studyCafeLockerPass);

        //when
        int totalPrice = studyCafePassOder.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(235000);
    }

}
