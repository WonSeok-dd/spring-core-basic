# 🟢 스프링 핵심 원리 - 기본편

## 📄 Section07 의존관계 자동 주입
### ✅ 다양한 의존관계 주입 방법
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