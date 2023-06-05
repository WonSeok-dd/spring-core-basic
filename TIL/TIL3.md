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
    - OrderServiceImpl이 DiscountPolicy(인터페이스)뿐만 아니라 FixDiscountPolicy, RateDiscountPolicy(구현)에 의존
    - _**인터페이스**_ 뿐만 아니라 _**구현**_ 까지 모두 의존
  - ![img3_2.png](file/img3_2.png)
  - ![img3_3.png](file/img3_3.png)