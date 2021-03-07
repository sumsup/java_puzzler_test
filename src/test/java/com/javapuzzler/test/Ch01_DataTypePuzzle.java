package com.javapuzzler.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class Ch01_DataTypePuzzle {

    @Test
    public void isOdd() {
        /** 홀수 판별에는 i % 2 != 0 을 사용할 것 */
        /** 홀수 판별에는 0 을 사용할 것 */
        // i % 2 == 1 로 홀수를 판별하는 것은 양수의 경우엔 잘 작동하지만,
        // 음수의 경우에는 홀,짝 상관없이 항상 false를 리턴한다.
        // 자바는 i % 2 를 계산 할때 i의 부호를 따르기 때문임.
        assertTrue(isOddW(5));
        assertFalse(isOddW(-2)); // fail
        assertFalse(isOddW(-1)); // fail
        assertFalse(isOddW(-3)); // fail


        // i % 2 != 0 으로 홀수를 판별하면 음수들도 걸러낼 수 있다.
        assertTrue(isOddR(5));
        assertTrue(isOddR(-1));
        assertFalse(isOddR(-2));
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
        assertNotEquals(2.00 - 1.10, 0.9); // 0.89999999999999

        // BigDecimal로 연산하면 십진수 연산을 하므로 정확한 숫자가 나온다.
        // BigDecimal을 생성할 때. new BigDecimal("2.0") 처럼 문자열 형태로 생성해야 함.
        // new BigDecimal(2.0) 이런식으로 생성하면 또 2.0000000000000000029389182 같은 숫자로 생성이 됨.
        // 혹은 이렇게 생성해야 함. BigDecimal.valueOf(2.0).

        // 따라서 아래 테스트는 틀렸음.
        assertNotEquals(new BigDecimal(2.00).subtract(new BigDecimal(1.10)).compareTo(new BigDecimal(0.9)), 0);
        System.out.println(new BigDecimal(2.00).subtract(new BigDecimal(1.10)) +  " is " + new BigDecimal(0.9) + " ?");

        // BigDecimal.valueOf(2.0) 혹은 new BigDecimal("2.0") 과 같은 형식으로.
        assertEquals(BigDecimal.valueOf(2.0).subtract(BigDecimal.valueOf(1.1)), BigDecimal.valueOf(0.9)); // 성공.
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
        assertNotEquals(MICROS_PER_DAY / MILLIS_PER_DAY, 1000); // 틀렸다. 5L 이 반환된다.
        System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);

        // 타깃 타이핑을 지원하지 않으니 해결책은?
        // 맨 첫번째 수를 Long 형으로 지정해주면 된다.
        final long MICROS_PER_DAY_LONG = 24L * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY_LONG = 24L * 60 * 60 * 1000;
        System.out.println(MICROS_PER_DAY_LONG);
        System.out.println(MILLIS_PER_DAY_LONG);

        assertEquals(MICROS_PER_DAY_LONG / MILLIS_PER_DAY_LONG, 1000); // 성공. 1000을 반환함.
        // 참고로 python이나 ruby는 연산 결과가 overflow가 발생하면 자동으로 자료형이 변환된다.
    }

    @Test
    public void plusLong() {
        /** Long 형 자료형을 사용할 때는 소문자 l을 쓰지마라. 숫자 1과 혼동된다. 대문자 L 을 사용할 것.
         * 변수에도 마찬가지. 소문자 l보다는 대문자 L을 사용할 것.
         */
        assertNotEquals(12345 + 5432l, 66666);
        assertEquals(12345 + 5432L, 17777);
    }

    @Test
    public void dosEquis() {
        /** byte, short, char 자료형을 int형과 섞어 쓰면 int형으로 형변환 시킨다.
         * 조건연산자를 사용할 때는 두번째, 세번째 변수의 자료형을 일치시켜야 한다.
         */
        char x = 'X';
        int i = 0;
        System.out.println(true ? x : i); // 88
        System.out.println(false ? i : x); // 88

        char y = 'Y';
        final int j = 0;
        System.out.println(true ? y : 0);
        System.out.println(false ? j : y);

        char z = 'Z';
        System.out.println(true ? z : 0);
        System.out.println(false ? 0 : z);
    }

    @Test
    public void twins() {
        /** byte, short, char 자료형에는 x += i; 와 같은 복합할당 연산자를 사용하지 않기 */
        short x = 0;
        int i = 123456;

        assertNotEquals(x += i , 123456); // 123456이 나올거 같지만 -7616이 반환된다.
        // x += i 와 같이 복합할당연산자는 자동형변환이 일어난다.
        // int가 short로 강제형변환 overflow가 발생한다.

        // 대신 x = x + i 는 아예 컴파일 에러가 나버린다. incompatible types: possible lossy conversion from int to short.
        // 작은 자료형에 큰 자료형을 할당하지 않도록 한다.
        // 특히 복합연산자를 사용할 때는 '강제 축소 형변환'이 일어나기 때문에 overflow가 발생해 결과 값이 달라진다.
        // 컴파일 에러에서도 잡히지 않으니까 꼭 자료형을 맞춰줄 것.
    }

    @Test
    public void reverseTwins() {
        /** 둘다 작동잘됨. 자바 1.5 이상의 버전에서는 버전업되면서 개선된 것으로 추정. */
        Object x = "Buy";
        String i = "Effective Java!";

        x = x + i;
        System.out.println(x);

        Object y = "Buy";
        String j = "Effective Java!";
        System.out.println(y += j);
    }

}