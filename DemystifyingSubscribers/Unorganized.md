### Key `subscribe()` Method()
#### Inside `Observable` class 
```java
/**
 * Subscribes to an Observable and provides a Subscriber that implements functions 
 * to handle the items the Observable emits and any error or completion notification 
 * it issues.
 */
public final Subscription subscribe(Subscriber<? super T> subscriber) {
    if(!(subscriber instanceof SafeSubscriber)){
        subscriber=new SafeSubscriber<T>(subscriber);
    }
        
    onSubscribe.call(subscriber);
    return subscriber; 
}
```

### `Observable` Constructor

Observable is just a fancy wrapper around this `OnSubscribe` function
```java
public class Observable<T> {

    final OnSubscribe<T> onSubscribe;

    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * 
     * Note Use OnSubscribe to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * OnSubscribe to be executed when subscribe(Subscriber) is called
     */
    protected Observable(OnSubscribe<T> f) {
        this.onSubscribe = f;
    }
}
```



### `OnSubscribe`
`OnSubscribe<T>` itself is just a fancy type wrapper around an `Action<T>`

It's just a callback and that callback takes a `Subscriber`
```java
    /**
     * Invoked when Observable.subscribe is called.
     * @param <T> the output value type
     */
    public interface OnSubscribe<T> extends Action1<Subscriber<? super T>> {
    
    }
```

`Observable<T>` is just a helper for passing `Subscriber<T>` to `OnSubscribe<T>` (aka `Action1<Subscriber<T>>`)

So how does passing in `Action1<Subscriber<T>>` end up creating the stream that allows data to flow into the`Subscriber`?

<!--????-->
<!--From the [Main Example](DemystifyingRxJavaSubscribers_Notes.md#main-example-part-1--)-->

```java
static <T> Observable<T> just(final T value) {
    return new Observable<T>(new OnSubscribe<T>() {
        @Override
        public void call(Subscriber<? super T> subscriber) {
            subscriber.onNext(value); 
            subscriber.onCompleted(); 
        }
    });
}   
```

We know `Subscribers` happen at the bottom of the stream and at the top of the stream, so the different operators (`map()` in this example) we call have to somehow hook those two together.

If we take a look at how `map()` is implemented, we will _eventually_ find out how this connection happens.

> "How these `subscribers` get hooked together is probably the most challenging thing to grasp 
> in RxJava"
>  - Jake Wharton

```java
public final <R> Observable<R> map(Func1<? super T, ? extends R> func) {
    return lift(new OperatorMap<T, R>(func));
}
```

`map()` takes data from the upstream type (String, in our example), and converts it to the downstream type (Integer, in our example)

We're going to wrap that function in an `Operator` and passed to a method called `lift()`

`lift()` is where a large majority of all the RxJava transformations eventually hook into. It's the foundation of a lot of operations that happen on a stream. 

```java
public final <R> Observable<R> lift(final Operator<? extends R, ? super T> operator) {
    return new Observable<R>(new OnSubscribeLift<T, R>(onSubscribe, operator));
}
```

Inside an `Observable` of type `<T>`, the `lift()` method takes an `Operator`, which goes from our new type to our old type, and it returns a new `Observable` of that new type.
 
So we're inside our `Observable<String>`, we take in an `Operator<Integer, String>` (**_seems backwards?_**) and we return an `Observable<Integer>`

Unlike the `map()` function, the `Operator` has its types seemingly backwards, but they are in the other direction: The type that we want **out** comes first, and the  type that is going **in** comes second.
  
```java
/**
 * Operator function for lifting into an Observable.
 * @param <T> the upstream's value type (input)
 * @param <R> the downstream's value type (output)
 */
public interface Operator<R, T> extends Func1<Subscriber<? super R>, Subscriber<? super T>> {
    
}
```
`Operator` is just a type-safe sugar over a `Func1`, except this function transforms a `Subscriber` to another `Subscriber` of those two types.
 
This is where we see how the `Subscribers` that are above us in the stream and the `Subscribers` that are  coming in below us are going to get hooked together.
 
An `Operator` is the function that takes a `Subscriber` of one type and converts it to a `Subscriber` of another type so that the stream can be connected together. 

```java
Observable.just("Hi!")                            // <--- Subscriber<String> 
          .map(s -< s.length())                   // <--- Func1<Subscriber<Integer>, Subscriber<String>>
          .subscribe(new Action1<Integer> {       // <--- Subscriber<Integer> 
            ...
          });  
```

# Pause at ~27:00 
