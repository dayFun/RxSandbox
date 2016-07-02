package reactive_sum.imperative;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ReactiveSumModelTest {


    private ReactiveSumModel reactiveSumModel;

    @Before
    public void setUp() throws Exception {
        reactiveSumModel = new ReactiveSumModel();
    }

    @Test
    public void testBothValuesInitializedToTrue() throws Exception {
        assertTrue(reactiveSumModel.isSumUpdated());
    }

    @Test
    public void testSettingValueToExistingValueDoesNotUpdateA() throws Exception {
        reactiveSumModel.setA(5);
        reactiveSumModel.setA(5);

        assertFalse(reactiveSumModel.isSumUpdated());
    }

    @Test
    public void testSettingValueToExistingValueDoesNotUpdateB() throws Exception {
        reactiveSumModel.setB(7);
        reactiveSumModel.setB(7);

        assertFalse(reactiveSumModel.isSumUpdated());
    }

    @Test
    public void testGetSumReturnsTotalOfAPlusB() throws Exception {
        reactiveSumModel.setA(5);
        reactiveSumModel.setB(7);

        assertThat(reactiveSumModel.getSum(), equalTo(12));
    }
}