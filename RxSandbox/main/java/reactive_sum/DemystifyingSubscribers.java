package reactive_sum;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.SafeSubscriber;

public class DemystifyingSubscribers {

    public static void main(String[] args) {
        iteration3();
    }

    public String doSomething() {
        String foo = "bar";
        //do stuff with foo
        return foo;
    }

    public static void iteration1() {
        Observable<String> observable = Observable.just("Hi!");
        Subscription subscription = observable.subscribe();
        System.out.println(subscription);

        observable.subscribe();
        observable.subscribe();
        observable.subscribe();
        observable.subscribe();
    }

    public static void iteration2() {
        Observable<String> observable = Observable.just("Hi!");

        Subscription subscription = observable.subscribe();
        System.out.println(subscription);

        SafeSubscriber<?> safeSubscriber = (SafeSubscriber<?>) subscription;
        System.out.println(safeSubscriber.getActual());
    }

    public static void iteration3() {
        Observable<String> observable = Observable.just("Hi!");

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override public void onCompleted() {}

            @Override public void onError(Throwable e) {}

            @Override
            public void onNext(String item) {
                System.out.println("Item: " + item);
            }
        };

        System.out.println(subscriber);
        Subscription subscription = observable.subscribe(subscriber);

        SafeSubscriber<?> safeSubscriber = (SafeSubscriber<?>) subscription;
        System.out.println(safeSubscriber.getActual());
    }

    public static void iteration4() {
        Observable<String> greeter = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SafeSubscriber<?> safeSubscriber = (SafeSubscriber<?>) subscriber;
                System.out.println(safeSubscriber.getActual());                             // (3) Printing the subscriber that is given
                subscriber.onNext("Hi");                                                    // to our function that creates the Observable
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Item: " + item);
            }

            @Override public void onCompleted() {}
            @Override public void onError(Throwable e) {}
        };

        System.out.println(subscriber);                                                      // (1) Creating the subscriber, printing out
        Subscription subscription = greeter.subscribe(subscriber);                           // what it is

        SafeSubscriber<?> safeSubscriber = (SafeSubscriber<?>) subscription;                 // (2) Subscribing to the stream, printing
        System.out.println(safeSubscriber.getActual());                                      // what subscription we get back
    }

    public static void iteration5() {
        Observable<Integer> observable = Observable.just("Hi!")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }
                });


        Subscription s = observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Item: " + integer);
            }
        });
    }
}
