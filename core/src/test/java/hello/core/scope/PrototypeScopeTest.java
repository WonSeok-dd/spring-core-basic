package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeScopeTest {

    @Test
    void prototypeBeanFind(){
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        //when
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        //then
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // 스프링 컨테이너가 빈 등록, 의존관계 주입, 초기화 후 관리 X
        // 소멸전 콜백 -> destroy() 실행 X
        prototypeBean1.destroy();
        prototypeBean2.destroy();
        ac.close();
    }

    //AppConfig 도 Bean 이다.
    @Scope("prototype")
    static class PrototypeBean{

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
