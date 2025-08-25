package sample.cafekiosk.learning.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GuavaCollectTest {

    @DisplayName("partition 함수를 호출하면 리스트를 서로 다른 리스트로 분할한다.")
    @Test
    public void should_hasSize_2_when_partition_called_with_half_numberOfElement() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // when
        List<List<Integer>> partitioned = Lists.partition(integers, 5);

        // then
        assertThat(partitioned).hasSize(2)
                .isEqualTo(List.of(List.of(1, 2, 3, 4, 5), List.of(6, 7, 8, 9, 10)))
        ;
    }
    @DisplayName("원소의 개수를 절반보다 많이 설정하고 partition 함수를 호출하면 뒤에 리스트는 나머지가 된다.")
    @Test
    public void should_hasSize_2_when_partition_called_with_more_half_numberOfElement() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // when
        List<List<Integer>> partitioned = Lists.partition(integers, 6);

        // then
        assertThat(partitioned).hasSize(2)
                .isEqualTo(List.of(List.of(1, 2, 3, 4, 5, 6), List.of(7, 8, 9, 10)))
        ;
    }
    @DisplayName("인자로 전달된 원소의 개수만큼 리스트를 자르고 나머지만큼의 리스트를 생성한다.")
    @Test
    public void should_hasSize3_when_partition_called_with_one_third() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // when
        List<List<Integer>> partitioned = Lists.partition(integers, 3);

        // then
        assertThat(partitioned).hasSize(4)
                .isEqualTo(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9), List.of(10)))
        ;
    }

    @DisplayName("")
    @Test
    public void test_multiMap() {
        // given
        Multimap<String, Integer> nameMap = ArrayListMultimap.create();
        nameMap.put("gno", 25);
        nameMap.put("gno", 26);
        nameMap.put("gno", 27);
        nameMap.put("cmo", 30);
        nameMap.put("olaf", 20);

        // when
        Collection<Integer> results = nameMap.get("gno");

        // then
        assertThat(results).hasSize(3)
                .isEqualTo(List.of(25, 26, 27));
    }

    @DisplayName("")
    @TestFactory
    public Collection<DynamicTest> test_multiMap_remove() {
        // given
        Multimap<String, Integer> nameMap = ArrayListMultimap.create();
        nameMap.put("gno", 25);
        nameMap.put("gno", 26);
        nameMap.put("gno", 27);
        nameMap.put("cmo", 30);
        nameMap.put("olaf", 20);

        // when

        // then
        return List.of(
                DynamicTest.dynamicTest("value를 한개 삭제한다.", () -> {
                    nameMap.remove("gno", 25);
                    assertThat(nameMap.get("gno")).hasSize(2)
                            .isEqualTo(List.of(26, 27));
                }),
                DynamicTest.dynamicTest("key에 속한 모든 value를 삭제한다", () -> {
                    nameMap.removeAll("gno");
                    assertThat(nameMap.get("gno")).isEmpty();
                }),
                DynamicTest.dynamicTest("없는 키를 조회하면 empty List가 나온다", () -> {
                    assertThat(nameMap.get("notExists")).isNotNull();
                    assertThat(nameMap.get("notExists")).isEmpty();
                })
        );
    }

}
