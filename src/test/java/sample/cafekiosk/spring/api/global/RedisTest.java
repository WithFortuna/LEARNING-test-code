package sample.cafekiosk.spring.api.global;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sample.cafekiosk.spring.api.IntegrationTestSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestContainer
 * 1. port 충돌 없음
 * 로컬이 6379 점유하고 있어도 문제없음
 * 컨테이너는 랜덤포트로 뜨고 그 값을 Spring에 주입한다
 *
 * 2. test 격리
 * 매번 새 redis 컨테이너가 뜨고 내려가니까 실행환경의 Redis와는 독립적이다
 *
 * 3. CICD 호환성
 * Git actions, GitLab CI에서 테스트 돌릴 때 별도의 Redis 설치가 필요없음
 * TestContainer가 알아서 Redis 이미지 띄움.
 */

@Testcontainers
public class RedisTest extends IntegrationTestSupport {
    @Container
    static GenericContainer<?> redis =
            new GenericContainer<>("redis:7.2")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void overrideRedisProps(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", () -> redis.getHost());
        registry.add("spring.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testRedisWork() {
        // given
        redisTemplate.opsForValue().set("hello", "world");
        // when
        String value = redisTemplate.opsForValue().get("hello");
        // then
        assertThat(value).isEqualTo("world");
    }
}
