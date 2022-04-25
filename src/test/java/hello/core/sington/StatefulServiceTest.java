package hello.core.sington;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);
        
        // ThreadA: A사용자가 10000원 주문
        statefulService1.order("userA", 10000);

        // ThreadB: B사용자가 10000원 주문
        statefulService1.order("userB", 20000);
        
        // ThreadA: 사용자A 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price of userA = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    @Test
    void statelessServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatelessService statelessService1 = ac.getBean(StatelessService.class);
        StatelessService statelessService2 = ac.getBean(StatelessService.class);

        // ThreadA: A사용자가 10000원 주문
        int userAPrice = statelessService1.order("userA", 10000);

        // ThreadB: B사용자가 10000원 주문
        int userBPrice = statelessService2.order("userB", 20000);

        // ThreadA: 사용자A 주문 금액 조회
        System.out.println("price of userA = " + userAPrice);

        Assertions.assertThat(userAPrice).isEqualTo(10000);
    }


    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }

        @Bean
        public StatelessService statelessService(){
            return new StatelessService();
        }
    }

}