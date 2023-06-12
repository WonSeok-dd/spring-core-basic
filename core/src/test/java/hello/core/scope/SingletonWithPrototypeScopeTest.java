package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

public class SingletonWithPrototypeScopeTest {

    @Test
    void prototypeFind() {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        //when
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();

        //then
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);

        // 스프링 컨테이너가 빈 등록, 의존관계 주입, 초기화 후 관리 X
        // 소멸전 콜백 -> destroy() 실행 X
        prototypeBean1.destroy();
        prototypeBean2.destroy();
        ac.close();
    }

    @Test
    void singletonClientUsePrototype(){
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        //when
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();

        //then
        Assertions.assertThat(count1).isEqualTo(1);
        Assertions.assertThat(count2).isEqualTo(1);

        // 스프링 컨테이너가 빈 등록, 의존관계 주입, 초기화 후 관리 X
        // 소멸전 콜백 -> destroy() 실행 X
        // prototypeBean1.destroy(); -> 하지만 언급x
        // prototypeBean2.destroy(); -> 하지만 언급x
        ac.close();
    }

    @Scope("singleton")
    static class ClientBean {

        private final Provider<PrototypeBean> prototypeBeanObjectProvider;

        @Autowired
        public ClientBean(Provider<PrototypeBean> prototypeBeanObjectProvider){
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = this.prototypeBeanObjectProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            this.count++;
        }

        public int getCount() {
            return this.count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init = " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy = " + this);
        }
    }
}
