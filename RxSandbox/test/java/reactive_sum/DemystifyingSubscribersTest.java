package reactive_sum;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;

public class DemystifyingSubscribersTest {

    @Test
    public void testVariousWaysToSubscribeToObservable() throws Exception {
        Observable<String> observable = Observable.just("Hi!");

        // Various ways to subscribe:

        // 1a) Pass in an action via AIC:
        Subscription actionSubscriptionAIC = observable.subscribe(new Action1<String>() {
            @Override
            public void call(String item) {
                System.out.println("Item: " + item);
            }
        });

        // 1b) Pass in an action via Lambda:
        Subscription actionSubscriptionLambda = observable.subscribe(item -> System.out.println("Item: " + item));

        // 2a) Full Observer via AIC
        Subscription observerSubscriptionAIC = observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Completed...");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error...");
                e.printStackTrace();
            }

            @Override
            public void onNext(String item) {
                System.out.println("Item: " + item);
            }
        });

        // 2b) Full Observer via Lambda
        Subscription observerSubscriptionLambda = observable.subscribe(
                item -> System.out.println("Item: " + item),
                error -> {
                    System.out.println("Error...");
                    error.printStackTrace();
                },
                (() -> System.out.println("Completed...")));
    }


}