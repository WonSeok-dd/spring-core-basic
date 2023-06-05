# 🟢 스프링 핵심 원리 - 기본편

## 📄 Section03 - 스프링 핵심 원리 이해2 - 객체 지향 원리 적용
### ✅ 새로운 할인 정책 설계, 개발 및 실행, 테스트
- 도메인 설계
  - ![img3_1.png](file/img3_1.png)


- 도메인 설계의 문제점
  - 역할과 구현 분리 -> O
  - 다형성 활용, 인터페이스 및 구현 분리 -> O
  - OCP 원칙(다른 할인정책으로 변경) -> X
    - 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 줌
  - DIP 원칙 -> X
    - OrderServiceImpl이 DiscountPolicy(추상)뿐만 아니라 FixDiscountPolicy, RateDiscountPolicy(구현)에 의존
    - _**추상**_ 뿐만 아니라 _**구현**_ 까지 모두 의존
  - ![img3_2.png](file/img3_2.png)
  - ![img3_3.png](file/img3_3.png)

### ✅ 관심사의 분리
- AppConfig
  - 애플리케이션의 전체 작동 방식을 구성(config)하기 위해<br>***구현 객체***를 ***생성하고 연결***하는 책임을 가지는 클래스
  - 구현 객체 생성, 생성자를 통해 주입(의존관계 주입, 의존성 주입, DI=Dependency Injection)
    - MemberServiceImpl -> MemoryMemberRepository
    - OrderServiceImpl -> MemoryMemberRepository, RateDiscountPolicy
  - OCP 원칙 -> O
    - 할인 정책을 변경하면, 클라이언트 코드(OrderServiceImpl)영향X
    - 할인 정책을 변경하면, AppConfig 영향O
  - DIP 원칙 -> O
    - MemberServiceImpl은 MemberRepository(추상)에 의존
    - OrderServiceImpl은 MemberRepository, DiscountPolicy(추상)에 의존
  - 관심사의 분리 -> O
    - 구현 객체를 ***생성하고 연결하는 역할***과 ***실행하는 역할*** 분리