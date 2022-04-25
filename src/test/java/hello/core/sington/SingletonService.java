package hello.core.sington;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }

    private SingletonService(){
        // new 사용을 막자, 생성자를 private로 선언
    }

    public void logic(){
        System.out.println("싱글통 객체 로직 호출");
    }
}
