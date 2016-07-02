package sandbox;

import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CommonRxMistakes {

    TestSubscriber<String> stringSubscriber = new TestSubscriber<>();
    TestSubscriber<Integer> intSubscriber = new TestSubscriber<>();

    @Test
    public void testOneCallPerLineBreaksTheChain() throws Exception {
        Observable<String> observable = Observable.just("Hello");
        observable.map(String::length);         // <--- This will not be part of the stream
        observable.subscribe(System.out::println);

        assertThat(stringSubscriber.getOnNextEvents().get(0), equalTo("Hello"));
    }

    @Test
    public void testOneCallPerLineWithoutBreakingTheChain() throws Exception {
        Observable<String> step1 = Observable.just("Hello");
        Observable<Integer> step2 = step1.map(String::length);
        step2.subscribe(intSubscriber);

        assertThat(intSubscriber.getOnNextEvents().get(0), equalTo(5));
    }
}
