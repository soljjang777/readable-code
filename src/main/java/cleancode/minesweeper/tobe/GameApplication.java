package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.minesweeper.Minesweeper;
import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.Advanced;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.Beginner;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.VeryBeginner;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameConfig gameConfig = new GameConfig(
                new Advanced(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );

        Minesweeper minesweeper = new Minesweeper(gameConfig);
        minesweeper.initialize();
        minesweeper.run();
    }

    /**
     *  DIP (Dependency Inversion Principle) - 상위 모듈(고수준)은 하위 모듈(저수준)에 의존하지 않고, 추상화(인터페이스, 추상 클래스)에 의존해야 함.
     *
     *  DI (Dependency Injection) - "3" ( 스프링에서는 제3자 즉 스프링컨텍스트가 두 객체간의 의존성을 맺어주고 주입을 해준다)
     *
     *  IoC (Inversion of Control) - 프로그램의 제어권(객체의 생명주기 컨트롤)을 스프링에서 즉 IoC 컨테이너가 제어함
     * */

}
