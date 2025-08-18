package sample.cafekiosk.spring.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/// 이거 config 왜 굳이 분리?
/// main에 붙여도 되긴함. but
/// 1. 관심사의 분리
/// 2. 관심사를 분리해야 test할 때 @WebMvcTest를 사용해도 jpaAuditing때문에 실행이 불가능한 문제가 없어짐
@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {
}
