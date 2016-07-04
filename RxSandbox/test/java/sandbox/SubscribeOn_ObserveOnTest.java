package sandbox;

import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SubscribeOn_ObserveOnTest {

    private Observable<String> numberStringsObservable = Observable.from(Arrays.asList("One", "Two", "Three",
                                                                                       "Four", "Five", "Six"));
    private TestSubscriber<String> stringTestSubscriber = TestSubscriber.create();

    @Test
    public void testWhenSubscribeOnNotUsedThenMainThreadIsUsed() throws Exception {
        numberStringsObservable.subscribe(stringTestSubscriber);

        assertThat(stringTestSubscriber.getOnNextEvents().size(), is(equalTo(6)));
//        assertThat(stringTestSubscriber.getOnCompletedEvents().size(), is(equalTo(1)));
    }

    @Test
    public void testWhenSubscribeOnWithSchedulerUsedThenTestFinishesBeforeObservableEmitsAnything() throws Exception {
        numberStringsObservable.subscribeOn(Schedulers.newThread())
                               .subscribe(stringTestSubscriber);
        assertThat(stringTestSubscriber.getOnNextEvents().size(), is(equalTo(0)));
    }

    @Test
    public void testSubscribeOnComputationThread() throws Exception {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma");

        source.subscribeOn(Schedulers.computation())
              .map(String::length)
              .subscribe(sum -> {
                  System.out.println("Received " + sum + " on thread " + Thread.currentThread().getName());
              });

        Thread.sleep(1000);
    }

    // Take this example. We emit the numbers 1 through 10 and do some simple multiplication to them.
    // By default the emissions happen on the main thread since we do not specify a subscribeOn().
    // But before the map(i -> i * 10) operation we switch the emissions over to a computation thread.
    @Test
    public void testObserveOn() throws Exception {
        Observable<Integer> source = Observable.range(1, 10);

        source.map(i -> i * 100)
              .doOnNext(i -> System.out.println("Emitting " + i
                                                        + " on thread " + Thread.currentThread().getName()))
              .observeOn(Schedulers.computation())
              .map(i -> i * 10)
              .subscribe(i -> System.out.println("Received " + i + " on thread "
                                                         + Thread.currentThread().getName()));

        Thread.sleep(1000);
    }
}
