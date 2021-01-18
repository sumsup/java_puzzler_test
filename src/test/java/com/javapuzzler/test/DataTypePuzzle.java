package com.javapuzzler.test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DataTypePuzzle {

    @Test
    public void isOdd() {
        /** 홀수 판별에는 i % 2 != 0 을 사용할 것 */
        // i % 2 == 1 로 홀수를 판별하는 것은 양수의 경우엔 잘 작동하지만,
        // 음수의 경우에는 홀,짝 상관없이 항상 false를 리턴한다.
        // 자바는 i % 2 를 계산 할때 i의 부호를 따르기 때문임.
        assertThat(isOddW(5)).isTrue();
        assertThat(isOddW(-1)).isTrue(); // fail
        assertThat(isOddW(-2)).isTrue();

        // i % 2 != 0 으로 홀수를 판별하면 음수들도 걸러낼 수 있다.
        assertThat(isOddR(5)).isTrue();
        assertThat(isOddR(-1)).isTrue();
        assertThat(isOddR(-2)).isTrue();
    }

    public static boolean isOddW(int num) {
        return num % 2 == 1;
    }

    public static boolean isOddR(int num) {
        return num % 2 != 0;
    }

    @Test
    public void bigDecimal() {
        /** 금융 계산을 할 때는 십진 연산을 하는 Big Decimal을 사용 할 것. */
        // float이나 double형은 이진 부동소수점 연산을 하므로 오차가 발생한다.
        assertThat(2.00 - 1.10).isEqualTo(0.9); // 0.89999999999999

        // BigDecimal로 연산하면 십진수 연산을 하므로 정확한 숫자가 나온다.
        // BigDecimal을 생성할 때. new BigDecimal("2.0") 처럼 문자열 형태로 생성해야 함.
        // new BigDecimal(2.0) 이런식으로 생성하면 또 2.0000000000000000029389182 같은 숫자로 생성이 됨.
        // 혹은 이렇게 생성해야 함. BigDecimal.valueOf(2.0).

        // 따라서 아래 테스트는 틀렸음.
        assertThat(new BigDecimal(2.00).subtract(new BigDecimal(1.10))).isEqualTo(0.9);

        // BigDecimal.valueOf(2.0) 혹은 new BigDecimal("2.0") 과 같은 형식으로.
        assertThat(BigDecimal.valueOf(2.0).subtract(BigDecimal.valueOf(1.1))).isEqualTo(0.9); // 성공.
    }

    @Test
    public void rangeOfLong() {
        /** int형 간의 곱하기는 overflow를 발생시킬 수 있으니 맨 첫번째 숫자를 Long형으로 지정 할 것.
         *  큰수를 다룰 때는 overflow를 주의할 것. */

        // 곱샘연산이 int형들끼리 이루어지는 동안 overflow 발생.
        // java는 타깃 타이핑을 지원하지 않는다.
        // 타깃 타이핑 : long = int * int 에서 계산결과가 long 이니까 long = long * long으로 변환해서 계산하는 기능.
        final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

        System.out.println(MICROS_PER_DAY);
        System.out.println(MILLIS_PER_DAY);
        assertThat(MICROS_PER_DAY / MILLIS_PER_DAY).isEqualTo(1000); // 틀렸다. 5L 이 반환된다.

        // 타깃 타이핑을 지원하지 않으니 해결책은?
        // 맨 첫번째 수를 Long 형으로 지정해주면 된다.
        final long MICROS_PER_DAY_LONG = 24L * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY_LONG = 24L * 60 * 60 * 1000;
        System.out.println(MICROS_PER_DAY_LONG);
        System.out.println(MILLIS_PER_DAY_LONG);

        assertThat(MICROS_PER_DAY / MILLIS_PER_DAY).isEqualTo(1000); // 성공. 1000을 반환함.

        // 참고로 python이나 ruby는 연산 결과가 overflow가 발생하면 자동으로 자료형이 변환된다.
    }



}
