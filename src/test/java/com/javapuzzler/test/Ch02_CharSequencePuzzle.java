package com.javapuzzler.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class Ch02_CharSequencePuzzle {

    @Test
    public void plusChar() {
        /**
         * 결론 : char 형의 문자열 합치기는 "" 를 더해준다. StringBuilder를 쓴다.
         * char + char 는 int형으로 계산됨.
         * char형을 문자로 결합 하려면 StringBuilder나 StringBuffer를 쓰거나
         * 맨 앞에다 빈 문자열을 추가해줘야 함.
         * "" + 'H' + 'a' => "Ha"
         */
        char h = 'H';
        char a = 'a';
        assertNotEquals(h + a , "Ha");
        System.out.println(h + a); // char 간의 사칙연산은 정말 사칙연산으로 취급되어 Ha가 아닌 169가 출력된다.

        // Ha 를 출력하게 하기 위해서는?
        StringBuilder sb = new StringBuilder(h + a); // 이러면 안됨. 공백이 출력됨.
        // char형 h + a 는 169가 되는데, StringBuilder 생성자에 숫자를 넣으면 capacity로 인식함. 169개의 공간이 생성되는 것.
        // 그래서 초기값은 "" 가 되고 아무것도 출력하지 않게 되는 것.
        System.out.println(sb.toString());

        sb.append(h);
        sb.append(a);
        System.out.println(sb.toString());

        // 혹은 맨앞에다가 "" 를 붙여줘서 자동형변환이 일어나게 해주기.
        System.out.println("" + h + a);

        // 다음은 어떤것이 출력될까?
        System.out.println("2 + 2 = " +  2 + 2); // 예상으론 2 + 2 = 22가 출력될 것.
    }

}