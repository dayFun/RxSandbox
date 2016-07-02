package reactive_sum.imperative;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ReactiveSumTest {

    @Mock
    IoWrapper ioWrapper;

    private ReactiveSum reactiveSum;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        reactiveSum = new ReactiveSum();
    }

    @Test
    public void testParseSingleInput() throws Exception {
        enterInput("a: 13");
        assertEquals(13, reactiveSum.getReactiveSumModel().getA());
    }

    @Test
    public void testShowErrorMessageWhenUserEntersInvalidInput() throws Exception {
        enterInput("junkkkk");

        verify(ioWrapper).printMessage("Error: Invalid input");
    }

    @Test
    public void testParseBothInputs() throws Exception {
        enterInput("a: 5");
        enterInput("b: 3");

        assertThat(reactiveSum.getReactiveSumModel().getA(), equalTo(5));
        assertThat(reactiveSum.getReactiveSumModel().getB(), equalTo(3));
    }

    @Test
    public void testGetSumReturnsSumOfBothInputsWhenBothInputsSet() throws Exception {
        enterInput("a: 5");

        enterInput("b: 3");

        assertThat(reactiveSum.getReactiveSumModel().getSum(), equalTo(8));
    }

    @Test
    public void testWhenUserEntersQuitThenParseInputReturnsFalse() throws Exception {
        when(ioWrapper.getInput()).thenReturn("quit");

        assertFalse(reactiveSum.parseInput(ioWrapper));
    }

    @Test
    public void testWhenQuitIsCaseInsensitive() throws Exception {
        when(ioWrapper.getInput()).thenReturn("qUiT");

        assertFalse(reactiveSum.parseInput(ioWrapper));
    }

    @Test
    public void testSumPrintedOnFirstValueUpdated() throws Exception {
        enterInput("a: 7");

        verify(ioWrapper).printMessage("Sum: 7");
    }

    @Test
    public void testSumPrintedWhenBothValuesUpdated() throws Exception {
        enterInput("a: 3");
        verify(ioWrapper).printMessage("Sum: 3");

        enterInput("b: 3");
        verify(ioWrapper).printMessage("Sum: 6");

    }

    @Test
    public void testSumNotPrintedWhenValueUpdatedToSameNumber() throws Exception {
        enterInput("a: 33");
        enterInput("b: 1");
        enterInput("a: 33");

        verify(ioWrapper, times(2)).printMessage(anyString());
    }

    private void enterInput(String input) {
        when(ioWrapper.getInput()).thenReturn(input);
        reactiveSum.parseInput(ioWrapper);
    }
}