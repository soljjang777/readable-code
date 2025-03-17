package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputHandlerTest {

    @DisplayName("사용자가 이용권 선택 시 1번을 선택하면 시간 이용권이다.")
    @Test
    void getPassTypeSelectingUserAction_Hourly() {
        // given
        Scanner testScanner = new Scanner(new ByteArrayInputStream("1\n".getBytes()));
        InputHandler inputHandler = new InputHandler(testScanner);

        // when
        StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

        // then
        assertThat(result).isEqualTo(StudyCafePassType.HOURLY);
    }

    @DisplayName("사용자가 이용권 선택 시 2번을 선택하면 주단위 이용권이다.")
    @Test
    void getPassTypeSelectingUserAction_Weekly() {
        // given
        Scanner testScanner = new Scanner(new ByteArrayInputStream("2\n".getBytes()));
        InputHandler inputHandler = new InputHandler(testScanner);

        // when
        StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

        // then
        assertThat(result).isEqualTo(StudyCafePassType.WEEKLY);
    }

    @DisplayName("사용자가 이용권 선택 시 3번을 선택하면 1인 고정석이다.")
    @Test
    void getPassTypeSelectingUserAction_Fixed() {
        // given
        Scanner testScanner = new Scanner(new ByteArrayInputStream("3\n".getBytes()));
        InputHandler inputHandler = new InputHandler(testScanner);

        // when
        StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

        // then
        assertThat(result).isEqualTo(StudyCafePassType.FIXED);
    }

    @DisplayName("잘못된 입력 0를 입력시  예외가 발생 한다.(경계값)")
    @Test
    void getPassTypeSelectingUserAction_InvalidInput0() {
        // given
        Scanner testScanner = new Scanner(new ByteArrayInputStream("0\n".getBytes()));
        InputHandler inputHandler = new InputHandler(testScanner);

        // when & then
        assertThatThrownBy(inputHandler::getPassTypeSelectingUserAction)
                .isInstanceOf(AppException.class)
                .hasMessage("잘못된 입력입니다.");
    }

    @DisplayName("잘못된 입력 4를 입력시  예외가 발생 한다.(경계값)")
    @Test
    void getPassTypeSelectingUserAction_InvalidInput4() {
        // given
        Scanner testScanner = new Scanner(new ByteArrayInputStream("4\n".getBytes()));
        InputHandler inputHandler = new InputHandler(testScanner);

        // when & then
        assertThatThrownBy(inputHandler::getPassTypeSelectingUserAction)
                .isInstanceOf(AppException.class)
                .hasMessage("잘못된 입력입니다.");
    }

}
