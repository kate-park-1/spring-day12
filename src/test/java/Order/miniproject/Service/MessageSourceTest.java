package Order.miniproject.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {
  @Autowired
  MessageSource ms;

  @Test
  void helloMessage(){
    String hello = ms.getMessage("hello", null, null);
    Assertions.assertThat(hello).isEqualTo("안녕");

    String hello1 = ms.getMessage("hello.name", new Object[]{"world!"},null);
    Assertions.assertThat(hello1).isEqualTo("안녕 world!");
  }

  @Test
  void helloMessage_kr(){
    String hello = ms.getMessage("hello", null, Locale.KOREA);
    Assertions.assertThat(hello).isEqualTo("안녕");

    String hello1 = ms.getMessage("hello.name", new Object[]{"world!"},Locale.KOREA);
    Assertions.assertThat(hello1).isEqualTo("안녕 world!");
  }

  @Test
  void helloMessage_en(){
    String hello = ms.getMessage("hello", null, Locale.ENGLISH);
    Assertions.assertThat(hello).isEqualTo("hello");

    String hello1 = ms.getMessage("hello.name", new Object[]{"world!"},Locale.ENGLISH);
    Assertions.assertThat(hello1).isEqualTo("hello world!");
  }
}
