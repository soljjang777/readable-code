package cleancode.studycafe.tobe.provider;

import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;

public interface LockerPassProvider {

    // SeatPassFileReadr.java를 상위레벨의 추상화해서 LockerPassProvider를 만듬
    // 결국 FileReader 방법이 아닌
    // 구글 시트에서 읽는 방법으로 변경되어도
    // 추상화된 Provider의 스펙만 맞춰준다면
    // 구현체가 어떤것이든
    // LockerPassProvider을 사용하고 있는 StudyCafePassMachine 쪽의 로직은 변경이 되지않는다 -> OCP를 지키는 로직이됨
    StudyCafeLockerPasses getLockerPasses();
}
