# 스터디 카페 프로젝트 테스트 기록

## [test] StudyCafeLockerPasses 클래스 테스트 코드 작성 - a1f4706

- **목표**: StudyCafeLockerPasses 클래스에 대한 테스트 코드를 작성하여, 특정 기간 타입에 맞는 락커 패스를 반환하는지 검증.
- **주요 내용**: `findLockerPassBy` 메서드가 같은 기간 타입의 락커 패스를 올바르게 반환하는지 테스트했습니다.

## [test] StudyCafePassOrder 클래스 테스트 코드 작성 - cdc45ee

- **목표**: StudyCafePassOrder 클래스에 대한 테스트 코드를 작성하여, 이용권의 할인 금액과 총 금액을 올바르게 계산하는지 검증.
- **주요 내용**:
    - 할인율이 적용된 경우 올바른 할인 금액을 반환하는지 테스트.
    - 이용권의 총 금액이 올바르게 계산되는지 테스트.

## [test] InputHandler의 getPassTypeSelectingUserAction 테스트 추가 - f8f91bc

- **목표**: InputHandler 클래스의 `getPassTypeSelectingUserAction` 메서드에 대한 테스트를 추가하여, 사용자가 선택한 입력 값에 따라 올바른 StudyCafePassType이 반환되는지 검증.
- **주요 내용**:
    - 사용자가 입력한 값(1~3)에 따라 올바른 `StudyCafePassType`이 반환되는지 테스트.
    - 잘못된 입력 시 `AppException` 예외가 발생하는지 테스트.
    - 테스트 가능성을 높이기 위해 `Scanner`를 주입받도록 개선하여 테스트 환경을 개선.

---

위의 테스트 코드들은 각 클래스 및 메서드의 정상 동작을 보장하고, 예상되는 동작에 대한 검증을 포함하고 있습니다. 각 커밋은 주요 기능의 동작을 점검하기 위한 테스트를 추가한 내용을 포함하고 있습니다.

