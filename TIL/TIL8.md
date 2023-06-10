# 🟢 스프링 핵심 원리 - 기본편

## 📄 Section08 스프링 빈 생명주기 콜백
### ✅ 스프링 빈 생명주기 콜백 
- ex) **데이터베이스 커넥션 풀, 네트워크 소켓**
  - **_애플리케이션 시작 지점_** 에 미리 연결
  - **_애플리케이션 종료 시점_** 에 연결을 모두 종료
    - 객체의 초기화 종료 작업 필요

- ```java
  public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
  
            //1. 객체 생성
            NetworkClient networkClient = new NetworkClient();
            
            //2. 수정자(setter) 주입
            networkClient.setUrl("http://hello-spring.dev");
  
            return networkClient;
        }
    }
  }
  
  public class NetworkClient {

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = " + this.url);
        connect();
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + this.url);
    }

    public void call(String message) {
        System.out.println("call: " + this.url + ", message = " + message);
    }

    //서비스 종료 시 호출
    public void disconnect(){
        System.out.println("close:" + this.url);
    }
  }
  ```
  - `url`정보 없이 `connect`메소드가 호출
  - **_객체를 생성하는 단계_** 에 `url` 존재 X, **_수정자(setter) 주입_** 을 통해 `url` 존재 O 


- `스프링`
  - `스프링 빈`에게 `초기화 콜백`을 통해서 **_초기화 시점을 알려줌_**
  - **_`스프링 컨테이너`과 `스프링 빈`이 종료되기 직전_** 에 `소멸 콜백`을 통해서 **_종료 작업 진행_**
    - `초기화 콜백`: 스프링 빈이 생성되고, 스프링 빈의 의존관계 주입 완료 후 추출
    - `소멸전 콜백`: 스프링 빈이 소멸되기 직전에 호출


- `스프링 빈`의 `이벤트 라이프 사이클`
  - 스프링 컨테이너 생성
  - 스프링 빈 생성
  - 의존관계 주입
  - 초기화 콜백
  - 사용
  - 소멸전 콜백
  - 스프링 종료


- 객체의 `생성자`와 `초기화` 분리
  - `생성자`: 매개변수로 필수 정보 받고, 메모리 할당해 객체 생성
  - `초기화`: 생성된 값을 활용해 무거운 동작 수행  **_ex) 외부 커넥션 연결_**
  - 따라서 `객체를 생성하는 부분`과 `초기화 하는 부분`을 명확하게 나누기<br>
    단순한 경우에는 `생성자`에서 한번에 처리