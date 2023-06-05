# 🟢 스프링 핵심 원리 - 기본편

## 📄 Section01 - 객체 지향 설계와 스프링
### ✅ 스프링이란?
- **스프링 부트**
  - 단독으로 실행할 수 있는 스프링 애플리케이션을 쉽게 생성
  - (Tomcat) 같은 웹 서버를 내장해서 별도의 웹 서버를 설치X
    - 빌드 후 서버에 띄우기 까지 가능
  - 손쉬운 빌드 구성을 위한 starter 종속성 제공
    - starter가 라이브러리를 하나 불러오면, 나머지 불러옴
  - 스프링과 3rd party(외부) 라이브러리 자동 구성
  - (메트릭, 상태 확인, 외부 구성) 같은 프로덕션 준비 기능 제공
  - 관례에 의한 간결한 설정


- **스프링 단어 사용 사례**
  - 스프링 DI 컨테이너 기술
  - 스프링 프레임워크
  - 스프링 부트, 스프링 프레임워크 등을 모두 포함한 스프링 생태계


- **스프링 핵심**
  - 객체 지향 언어 특징을 살려내는 프레임워크

### ✅ 좋은 객체 지향 프로그래밍이란?
- **다형성의 본질**
  - 클라이언트를 변경하지 않고, 서버의 구현 기능을 유연하게 변경


- **역할과 구현을 분리**
  - 확장 가능한 설계
  - 클라이언트에 영향을 주지 않는 변경 가능


- **스프링과 객체 지향**
  - 스프링은 **다형성**을 극대화
  - 제어의 역전, 의존관계 주입 -> **다형성**을 활용  
    이는 스프링에서 역할과 구현을 편리

### ✅ 좋은 객체 지향 설계의 원칙(SOLID)
- **SRP(Single responsibility principle)**
  - 한 클래스는 하나의 책임
  - 기준: **변경**, 변경이 있을 때 파급 효과 적을 수록 좋은 것  
    ex) memberRepository -> MemoryMemberRepository, JDBCMemberRepository


- **OCP(Open/Closed principle)**
  - 확장에는 열려있지만, 변경에는 닫힘
  - 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능을 구현  
- **OCP(Open/Closed principle) 문제점**  
  ex) memberRepository -> MemoryMemberRepository, JDBCMemberRepository  
  ex) MemberService -> **클라이언트인, MemberSerivce** 수정이 필요
    - 해결: **스프링 컨테이너**로 해결  
      -> **연관관계를 맺어주는 별도의 조립**, **설정자** 


- **LSP(Liskov substitution principle)**
  - 하위 클래스는 인터페이스 규약, 원칙을 지켜야함  
    ex) 악셀을 밟으면 앞으로 가야한다는 규약


- **ISP(Interface segregation principle)**
  - 특정 클라이언트를 위한 인터페이스 여러개 > 1개 더 나음 
  - 인터페이스가 명확해지고, 대체 가능성 높아짐  
    ex) 자동차 인터페이스 -> 운전 인터페이스, 정비 인터페이스


- **DIP(Dependency inversion principle)**
  - 의존관계 역전 원칙
  - 추상화 의존O(인터페이스 의존O), 구체화 의존X(구현 클래스 의존X)
  - 역할 의존O, 구현 의존X
- **DIP(Dependency inversion principle) 문제점**  
  ex) memberRepository -> MemoryMemberRepository, JDBCMemberRepository  
  ex) MemberService -> **클라이언트인, MemberService** 구현 클래스를 직접 선택
    - 해결: **스프링 컨테이너**로 해결  
      -> **연관관계를 맺어주는 별도의 조립**, **설정자** 


- **❗결국 다형성만으로는 OCP, DIP를 지킬 수 없다.**

### ✅ 객체 지향 설계와 스프링
- 스프링  
  -> 다형성 + OCP,DIP 지원