package com.javapuzzler.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class Ch02_CharSequencePuzzle {

    @Test
    public void plusChar() {
        /**
         * 결론 : char 형의 문자열 합치기는 "" 를 더해준다. StringBuilder를 쓴다. StringBuffer를 쓴다.
         * char + char 는 int형으로 계산됨.
         * char형을 문자로 결합 하려면.
         * 1. StringBuilder나 StringBuffer를 쓰거나
         * 2. 맨 앞에다 빈 문자열을 추가해줘야 함.
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

    @Test
    public void abc() {
        /** char 배열은 String.valueOf 를 통해서 문자열 형태로 변경 가능 하다. */
        String letters = "ABC";
        char[] numbers = {'1','2','3'}; // char 문자열.
        System.out.println(letters + " easy as " + numbers); // 'ABC easy as [C@543c6f6d'를 반환.

        System.out.println(letters + " easy as " + String.valueOf(numbers)); // 'ABC easy as 123' 을 정상적으로 출력.

        System.out.print(numbers); // 이것도 123 출력.
    }

    @Test
    public void animalFarm() {
        final String pig = "length: 10";
        final String dog = "length: " + pig.length();
        System.out.println("Animals are equal : " + pig == dog); // false 만 출력됨.
        // + 연산자가 == 연산자보다 우선순위가 높다.
        // 따라서 위의 식은 ("Animals are equal : " + pig) == dog 로 계산된다.

        // 자바는 문자열을 intern 해서 사용한다.
        // intern이란 무엇인가?
        String a = "animal";
        String b = "animal";
        assertEquals(a, b); // pass.
        System.out.println(a == b);
        // 통과가 되기는 하는데 "animal" == "animal" 로 비교해서 통과가 된게 아님.
        // String을 == 로 비교하면 메모리 주소값으로 비교함. 두 메모리 주소가 같아서 true.
        // "animal" 을 하나의 메모리공간에 넣어두고 두 변수가 같이 참조함. 메모리를 절약하기 위해서.
        // 이게 바로 intern 임.

        // 만약 별도의 메모리 공간에 저장하고 싶으면? new String() 으로 생성한다.
        String c = new String("animal");
        String d = new String("animal");

        System.out.println(b == c); // 메모리 주소값 다름. false.
        System.out.println(c == d); // 메모리 주소값 다름. false.

        // 따라서 아래와 같이 고쳐서 봐야함. 그러나...
        System.out.println("Animals are equal : " + (pig == dog)); // false.
        // 문자열이 다르기 때문에 intern이 일어나지 않음. 서로 다른 메모리 주소값을 참조하므로 false.

        System.out.println("Animals are equal : " + pig.equals(dog));
    }

    @Test
    public void strangeWorldHelloWorld() {
        // 주석에서도 이스케이프 시퀀스는 작동한다.
        /**
         * Generated by the IBM IDL-to-Java compiler, version 1.0 from
         * F:\TestRoot\apps\a1\.units\include\PolicyHome.idl Wednesday, June 17, 1998 6:44:40 o' clock.
         */
        System.out.print("Hell");
        System.out.println("o world");

        // 위 코드는 실행조차 안됨. 주석에서도 \.u 는 유니코드 시작으로 인식됨. 앞의 \.u에서 .을 제거해야 함.
        // 실행이 안되길래 dot을 추가했음.
        // \u0052 이런식으로.
    }

    @Test
    public void classyFire() {
        // indexOf는 특정문자의 index를 반환한다.
        System.out.println("abcabc".indexOf('a')); // 0
        System.out.println("abcabc".indexOf('c')); // 2
        System.out.println("abcabc".indexOf('f')); // -1

        // lastIndexOf는 뒤에서 부터 순서를 확인. 뒤에서부터 처음 등장하는 index 값을 반환.
        System.out.println("abcabc".lastIndexOf('a')); // 뒤에서부터 처음등장하는 a의 index는 3.
        System.out.println("abcabc".lastIndexOf('b')); // 뒤에서부터 4번째 index에 등장.
        System.out.println("abcabc".lastIndexOf('c')); // 뒤에서부터 5번째 index에 등장.
        System.out.println("abcabc".lastIndexOf('d')); // -1

        String typeResult = null;
        if ("0123456789".indexOf('n') >= 0) {
            typeResult = "NUMERAL ";
        }
        if ("abcdefghijklmnopqrstuvwxyz".indexOf('n') >= 0) {
            typeResult = "LETTER ";
        }
        System.out.println(typeResult);
    }

    @Test
    public void replaceEscaped() {
        /** replaceAll이나 split 메서드에서 .을 바꿀 때는 \\. 으로 입력할 것. 정규표현식으로 인식함.
         * replaceAll의 첫번째 파라미터는 정규표현식으로 인식함. 정규표현식을 쓰려면 replaceAll 을 사용하고.
         * 일반 문자열로 쓰려면 replace를 쓰면됨.
         */

        String className = Ch02_CharSequencePuzzle.class.getName();

        System.out.println("replaceAll no Escaped : " + className.replaceAll(".","/") + ".class"); // replace 관련 메서드는 첫 매개변수가 정규표현식이다.
        // 정규표현식에서 . 은 '전부다'를 의미. 그래서 모든 문자열을 / 로 변경. /////////////////////.class가 출력되는 것.
        // 정규표현식에서 .을 문자로 인식하게 하려면 앞에 escape 문자를 붙여줘야함. \. 이렇게.
        // 그런데 문자열 안에서 \ 만 쓰면 또 이스케이프로 인식하기 때문에 한번더 써줘야함. \\. 이렇게 됨.
        // 그러면 정상 출력.
        System.out.println("replaceAll escaped : " + className.replaceAll("\\.","/") + ".class");

        // 그냥 replace는 첫 매개변수가 문자이므로 . 을 입력해도 됨.
        System.out.println("replace() : " + className.replace(".", "/") + ".class");

        // 이런저런 번거로움을 피하기 위해서 자바5에서는 Pattern.quote() 가 등장.
        // 문자열을 유효한 정규표현식으로 바뀌줌.
        System.out.println(Pattern.quote("."));
        System.out.println("Pattern.quote() : " + className.replaceAll(Pattern.quote("."), "/"));

        // 이런걸 보면.
        // 결국 쓸만한 라이브러리나 메서드 사용법이 프로그래밍 실력이다.
    }

    @Test
    public void fileSeparator() {
        String className = Ch02_CharSequencePuzzle.class.getName();
        // 주석처리 하겠음.
        // System.out.println("file seperator : " + className.replaceAll("\\.", File.separator));
        // 위 코드는 에러를 내뱉는다. IllegalArgumentException : character to be escaped is missing.
        // 유닉스 계열은 파일경로 구분자가 / 라서 괜찮은데.
        // 윈도우 계열은 구분자가 \ 라서 이스케이프 문자가 되버리기 때문.

        // 그래서 나온 자바5에서 나온 치환문자열용 메서드. Matcher.quoteReplacement();
        System.out.println("Matcher.quoteReplacement() : " + className.replaceAll("\\.",
                                                                                  Matcher.quoteReplacement(File.separator)));

        // 이 문제도 역시 replace를 쓰면 해결. 정규표현식을 쓰지 않을 거라면.
        // => 안됨.. 결국 File.separator는 이스케이프 문자이기 때문. => \
        System.out.println("File.separator : " + className.replace("\\.",File.separator));

        // 그러면 'char 매개변수 replace' 를 쓰면 됨. 거기에 File.separatorChar.
        System.out.println("File.separatorChar : " + className.replace('.' , File.separatorChar));

    }

    @Test
    public void label() {
        System.out.println("chrome:");
        http://www.google.com;
        System.out.println(":security");

        // 위처럼 http: 가 있어도 에러가 나지 않는 이유.
        // label 이기 때문. 그 뒤의 // 는 주석이 되버림.
    }

    @Test
    public void noPainNoGain() {
        /** 익숙하지 않은 api를 사용할 경우에는 문서를 꼼꼼히 보고 주의하도록 할 것. */
        Random random = new Random();
        StringBuffer word;
        switch (random.nextInt(2)) {
            case 1 :
                word = new StringBuffer('P');
            case 2 :
                word = new StringBuffer('G');
            default :
                word = new StringBuffer('M');
        }

        word.append('a');
        word.append('i');
        word.append('n');

        System.out.println("word1 : " + word); // ain이 출력됨.
        // Random클래스의 nextInt(2)는 0에서 시작해 2개의 정수를 랜덤으로 출력함.
        // 따라서 0, 1 이 반환됨. 그러면 case 2 는 영영 안타게 되는 것.
        // 이를 울타리 에러 라고 부른다. int형 인수가 생성범위에 포함이 되느냐 안되느냐 때문에 발생한 에러.
        // substring 때문에 매번 햇갈림.

        // StringBuffer의 생성자는 인수로 capacity를 보냄.
        // 'P'를 인수로 쓰면 INT형인 77로 형변환되고 77개의 공간을 가진 StringBuffer가 생성됨.
        // char형은 int형으로 형변환 된다. String 형이 아니라.
        // 참고 : 인수(argument) 는 함수로 보내는 입장. 인자(parameter)는 받는 입장.

        // 그리고 case문에 break도 빠져있음.

        // 제대로 고쳐 보면 아래와 같다.
        StringBuffer word2;
        switch (random.nextInt(3)) {
            case 1 :
                word2 = new StringBuffer("P");
                break;
            case 2 :
                word2 = new StringBuffer("G");
                break;
            default :
                word2 = new StringBuffer("M");
                break;
        }

        word2.append('a');
        word2.append('i');
        word2.append('n');
        System.out.println("word2 : " + word2);

        // 위와 같이 쓰는 경우는 드물고 차라리 아래와 같이 구현도 가능.
        String msg[] = {"Main", "Pain", "Gain"};
        System.out.println("String[] : " + msg[random.nextInt(msg.length)]);
    }



}