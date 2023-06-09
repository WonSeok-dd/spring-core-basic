package hello.core.singleton;

import hello.core.beanFind.ApplicationContextSameBeanFindTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    ApplicationContext ac;

    @BeforeEach
    public void beforeEach(){
        this.ac = new AnnotationConfigApplicationContext(TestConfig.class);
    }

    @Test
    void statefulServiceSingleton() {
        //given
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        //when(ThreadA: 사용자 A 10000원 주문 / ThreadB: 사용자 B 20000원 주문)
        statefulService1.order("userA", 10000);
        statefulService2.order("userB", 20000);

        //then
        Assertions.assertThat(statefulService1.getPrice()).isNotEqualTo(10000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}