package randolph;

import org.hamcrest.Matchers;
import org.junit.Test;
import rx.observers.TestSubscriber;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

import static org.junit.Assert.assertThat;

public class BehaviorSubjectTest {

    private Subject<String, String> subject = BehaviorSubject.create();
    private TestSubscriber<String> subscriber = new TestSubscriber<>();

    @Test
    public void testIGetMostRecentNotification() throws Exception {
        subject.onNext("hi");

        subject.subscribe(subscriber);

        assertThat(subscriber.getOnNextEvents(), Matchers.contains("hi"));
    }

    @Test
    public void testSubscriberOnlyGetsMostRecentNotification() throws Exception {
        subject.onNext("hi");
        subject.onNext("bye");

        subject.subscribe(subscriber);

        assertThat(subscriber.getOnNextEvents(), Matchers.contains("bye"));
    }

    @Test
    public void testTwoSubscribers() throws Exception {
        TestSubscriber<String> sub2 = new TestSubscriber();
        subject.onNext("hi");

        subject.subscribe(subscriber);

        subject.onNext("there");

        subject.subscribe(sub2);

        subject.onNext("foo");

        assertThat(subscriber.getOnNextEvents(), Matchers.contains("hi", "there", "foo"));
        assertThat(sub2.getOnNextEvents(), Matchers.contains("there", "foo"));
    }

    @Test
    public void testStoreLatestErrorNotificationAndNotLatestOnNextNotification() throws Exception {
        RuntimeException omg = new RuntimeException("omg");
        subject.onNext("hi");
        subject.onError(omg);

        subject.subscribe(subscriber);

        assertThat(subscriber.getOnNextEvents(), Matchers.empty());
        assertThat(subscriber.getOnErrorEvents(), Matchers.contains(omg));
    }
}
