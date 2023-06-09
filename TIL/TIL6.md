# 🟢 스프링 핵심 원리 - 기본편

## 📄 Section06 컴포넌트 스캔
### ✅ 컴포넌트 스캔(`@ComponentScan`)
- 컴포넌트 스캔(`@ComponentScan`)
  - 설정 정보( `@Bean`/`<bean/>`)가 없어도 **_자동으로 스프링 빈을 등록_**
  - `@Component` 붙은 클래스를 **_자동으로 스프링 빈을 등록_**
    - `excludeFilters` 설정<br/> 
      -> `@Configuration` 붙은 클래스 제거(`@Configuration`에 `@Component`가 등록되있음)<br/> 
      -> 즉 AppConfig 의 스프링 빈, 테스트 코드의 `@Configuration`붙은 클래스 제외
  - `@Bean` 으로 등록된 클래스가 없음

<br/>

### ✅ 자동 의존관계 주입(`@Autowired`)
- 자동 의존관계 주입(`@Autowired`)
    - 의존관계 명시 없어도 **_자동으로 스프링 빈 의존관계 주입_**


- ![img6_1.png](file/img6_1.png)
  - 스프링 빈의 이름: 맨 앞글자만 소문자
  - 스프링 빈의 이름 설정: `@Component("memberServiceImpl)`<br><br>
- ![img6_2.png](file/img6_2.png)
- ![img6_3.png](file/img6_3.png)
  - 자동 의존 관계 주입 전략: `Type`이 같은 스프링 빈을 찾음(만약 `Type`이 같은 스프링 빈 여러개면 충돌)