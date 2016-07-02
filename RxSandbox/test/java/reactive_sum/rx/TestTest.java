package reactive_sum.rx;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TestTest {

    public static String deFront(String str) {
//        return str.replaceFirst("\\w{2}(.*)", "$1");
        Matcher matcher = Pattern.compile("(?i)(?<firstTwoChars>[^a][^b])(.*)", Pattern.CASE_INSENSITIVE).matcher(str);
        String result = "";

        if (matcher.find()) {
            String firstTwoChars = matcher.group("firstTwoChars");
            System.out.println("firstTwoChars = " + firstTwoChars);
            System.out.println("group 1 = " + matcher.group(1));
            System.out.println("group 2 = " + matcher.group(2));
            if (firstTwoChars.charAt(0) == 'a') {
                result = "a" + matcher.group(2);
            } else {
                result = matcher.group(2);
            }

        }
        return result;
    }

    @Test
    public void testRemoveFirstTwoChars() throws Exception {
        assertThat(deFront("Hello"), equalTo("llo"));
    }

    @Test
    public void testRemoveFirstTwoCharsWhenStringIsLengthTwo() throws Exception {
        assertThat(deFront("He"), equalTo(""));
    }

    @Test
    public void testDoNotReplaceFirstCharacterIfFirstCharacterIsA() throws Exception {
        assertThat(deFront("ahxa!"), equalTo("axa!"));
    }
}