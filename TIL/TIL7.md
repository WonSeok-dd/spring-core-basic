# 🟢 스프링 핵심 원리 - 기본편

## 📄 Section07 의존관계 자동 주입
### ✅ 의존관계 주입 - 다양한 방법
- 의존관계 주입 방법
  - 생성자 주입
  - 수정자 주입(setter 주입)
  - 필드 주입
  - 일반 메소드 주입


- 의존관계 주입 - 생성자 주입
  - 생성자 호출 시, 1번 호출 가능
    - `OrderServiceImpl` **_생성 시 의존관계 주입_** (빈 등록하면서 의존관계 주입)
    - **_불변, 필수 의존관계_**
  - `@Autowired` 생략 -> 생성자가 1개면 가능
  - ```java
    @Component
    public class OrderServiceImpl implements OrderService {
        private final MemberRepository memberRepository;
        private final DiscountPolicy discountPolicy;
        
        @Autowired
        public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy){
            this.memberRepository = memberRepository;
            this.discountPolicy = discountPolicy;
        }
    }
    ```
    

- 의존관계 주입 - 수정자 주입(setter 주입)
  - setter 호출 시, 여러 번 호출 가능
    - `OrderServiceImpl` **_생성 후 의존관계 주입_** (빈 등록 후 의존관계 주입)
    - **_변경 가능성, 선택 의존관계_**
  - `@Autowired(required=false)` -> 의존관계 주입 대상 없어도 동작 가능
  - ```java
    @Component
    public class OrderServiceImpl implements OrderService {
        private MemberRepository memberRepository;
        private DiscountPolicy discountPolicy;
        
        @Autowired
        public MemberRepository setMemberRepository(MemberRepository memberRepository){
            this.memberRepository = memberRepository;
        }   
        
        @Autowired
        public DiscountPolicy setDiscountPolicy(DiscountPolicy discountPolicy){
            this.discountPolicy = discountPolicy;
        }
    }
    ```
    

- 의존관계 주입 - 필드 주입
  - 필드에 바로 주입
    - 변경이 불가하기 때문에, 테스트 하기에 어렵
      - 스프링 컨테이너가 작동해야 되기 때문에, 테스트 할때 `new` 로 테스트 하기에 어렵
      - 해결) 수정자(setter) 생성
    - 사용권장 X
      - 사용예시1) 애플리케이션의 실제 코드와 관계 없는 테스트 코드
        ```java
          @SpringBootTest
          class CoreApplicationTests{
          
            @Autowired OrderService orderService;
            
            @Test
            void contextLoads(){
             //테스트 코드 작성
            } 
          }      
        ```
      - 사용예시2) 스프링 설정을 목적으로 하는 `@Configuration` 파일
        ```java
        @Configuration
        @ComponentScan
        public class AutoAppConfig {
            @Autowired MemeberRepository memeberRepository;
            @Autowired DiscountPolicy discountPolicy;
            
            @Bean
            OrderService orderService() {
                return new OrderServiceImpl(memeberRepository, discountPolicy);
            }   
        }
        ```
  - ```java
    @Component
    public class OrderServiceImpl implements OrderService {
        @Autowired private MemberRepository memberRepository;
        @Autowired private DiscountPolicy discountPolicy;
        
        public MemberRepository setMemberRepository(MemberRepository memberRepository){
            this.memberRepository = memberRepository;
        }   
        
        public DiscountPolicy setDiscountPolicy(DiscountPolicy discountPolicy){
            this.discountPolicy = discountPolicy;
        }
    }   
    ```


- 의존관계 주입 - 일반 메소드 주입
  - 일반 메소드에 주입
  - 의존관계 주입 - 수정자 주입(setter 주입)와 유사

<br/>

### ✅ 의존관계 주입 - 옵션 처리
- 옵션 처리 종류
  - `@Autowired(required=false)` -> 의존관계 주입 대상 없어도 동작 가능 / **_메소드 호출 X_**
  - `org.springframework.lang.@Nullable` -> 의존관계 주입 대상 없으면 null 입력 / **_메소드 호출 O_**
  - `Optional<>` -> 의존관계 주입 대상 없으면 `Optional.empty` 입력 / **_메소드 호출 O_**

<br/>

### ✅ 의존관계 주입 - 생성자 주입 중요성 
- 불변
  - 보통 애플리케이션 종료시점까지 의존관계 변경 X


- 누락 방지(NullPointerException)
  - 스프링 컨테이너가 작동해야 되기 때문에, 테스트 할때 `new` 로 테스트 하기에 어렵
    - 해결) **_의존관계 주입 - 생성자 주입_**
  - **_final 키워드_**
    - 생성자에서 혹시라도 값이 설정되지 않는 오류 -> 컴파일 시점에서 방지


- 정리(의존관계 주입 - 생성자 주입 중요성 )
  - 프레임워크에 의존하지 않고, 순수한 자바 언어 특징 
  - 필수 값이 아닌 경우: **_의존관계 주입 - 수정자 주입(setter 주입)_**

<br/>

### ✅ 의존관계 주입 - Lombok
- Lombok
  - final 키워드를 통한 필드 생성, 생성자(값 대입 코드) 생성 -> 귀찮다!
    - 해결) `@RequireArgsConstructor`: final 키워드를 통한 필드를 인식해서, 생성자(값 대입 코드) 생성
  - `Preferences` -> `Annotation Processors` -> `Enable annotation processing` 설정
    - **_Annotation Processors 라는 기능_** 을 통해 **_컴파일 시점에 생성자 코드 생성_**

<br/>

### ✅ 의존관계 주입 - 타입으로 빈 조회 시 중복 문제 해결 방법
- 타입으로 빈 조회 시 중복 문제 해결 방법 - `@Autowired` 필드 명 매칭
  - (1) 타입 조회<br>
    (2) 타입 조회 결과 2개 이상 일때 **_필드명, 매개변수 명_** 으로 빈 이름 조회 
  - ```java
    @Component
    public class OrderServiceImpl implements OrderService {
        private final MemberRepository memberRepository;
        private final DiscountPolicy discountPolicy;
    
        @Autowired
        public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy fixDiscountPolicy){
            this.memberRepository = memberRepository;    
            this.discountPolicy = fixDiscountPolicy;
        }
    } 
    ``` 


- 타입으로 빈 조회 시 중복 문제 해결 방법 - `@Qualifier` 사용
  - (1) `@Qualifier` 끼리 매칭<br>
    (2) 빈 이름 조회<br>
    (3) `NoSuchBeanDefinitionException` 발생
  - ```java
    @Component
    @Qualifier("mainDiscountPolicy")
    public class RateDiscountPolicy implements DiscountPolicy {
    
    }
    
    @Component
    @Qualifier("fixDiscountPolicy")
    public class FixDiscountPolicy implements DiscountPolicy {
    
    }
    
    @Component
    public class OrderServiceImpl implements OrderService {
        private final MemberRepository memberRepository;
        private final DiscountPolicy discountPolicy;
    
        @Autowired
        public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy){
            this.memberRepository = memberRepository;    
            this.discountPolicy = discountPolicy;
        }
    } 
    ``` 


- 타입으로 빈 조회 시 중복 문제 해결 방법 - `@Primary` 사용
  - ```java
    @Component
    @Primary
    public class RateDiscountPolicy implements DiscountPolicy {
    
    }
    
    @Component
    public class FixDiscountPolicy implements DiscountPolicy {
    
    }
    
    @Component
    public class OrderServiceImpl implements OrderService {
        private final MemberRepository memberRepository;
        private final DiscountPolicy discountPolicy;
    
        @Autowired
        public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy){
            this.memberRepository = memberRepository;    
            this.discountPolicy = discountPolicy;
        }
    } 
    ``` 
    

- 정리(타입으로 빈 조회 시 중복 문제 해결 방법)
  - 메인 데이터베이스의 커넥션을 획득하는 스프링 빈 -> `@Primary`<br>
    서브 데이터베이스의 커넥션을 획득하는 스프링 빈 -> `@Qualifier`
  - 우선순위<br/>
    `@Primary` -> 기본값 동작<br>
    `@Qualifier` -> 매우 상세하게 동작<br>
    따라서 자동 < 수동, 넓은 범위 < 좁은 범위 이기 때문에 `@Qualifier` > `@Primary`

<br/>

### ✅ 의존관계 주입 - 타입으로 빈 조회 시 중복 문제 해결 방법(`@Qualifier` 사용)시 `Annotation` 생성
- **_스프링 지원: 여러 애노테이션 모아서 사용_**
  - 애노테이션은 상속X

<br/>

### ✅ 의존관계 주입 - 타입으로 조회한 빈이 모두 필요
```java
public class AllBeanTest {

    @Test
    void findAllBean() {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);

        //when
        int fixDiscountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");

        //then
        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
        Assertions.assertThat(fixDiscountPrice).isEqualTo(1000);
        Assertions.assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService {

        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        //DiscountPolicy -> DiscountService 의존관계 주입
        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies){
            this.policyMap = policyMap;
            this.policies = policies;
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}
```
- DiscountSerivce
  - Map 으로 DiscountPolicy 의존관계 주입
    - fixDiscountPolicy, rateDiscountPolicy
  - List 로 DiscountPolicy 의존관계 주입

<br/>

### ✅ 컴포넌트 스캔과 의존관계 주입 - 자동, 수동의 실무 운영 기준
- **_업무 로직 빈_**: 자동
  - 웹을 지원하는 컨트롤러
  - 핵심 비즈니스 로직이 있는 서비스
  - 데이터 계층의 로직을 처리하는 레포지토리
  

- **_기술 지원 빈_**: 수동
  - 기술적인 문제
  - 공통 관심사(AOP) ex) 데이터베이스 연결, 공통 로그 처리


- **_비즈니스 로직 중에서 다형성 적극 활용_**: 수동
  ```java
    @Configuration
      public class DiscountPolicyConfig {
    
          @Bean
          public DiscountPolicy rateDiscountPolicy() {
              return new RateDiscountPolicy();
          }
     
          @Bean 
          public DiscountPolicy fixDiscountPolicy() {
              return new FixDiscountPolicy();
          }
      }
  ```
  - 빈의 이름, 빈의 의존관계 주입 파악 가능
  - 따라서 `DiscountPolicy` 의 구현 빈들만 따로 모아서 특정 패키지 만들기