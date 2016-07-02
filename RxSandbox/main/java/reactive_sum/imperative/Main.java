package reactive_sum.imperative;

public class Main {



    public static void main(String[] args) {
        System.out.println("Imperative Sum");
        System.out.println("Type 'a: <number>' and 'b: <number>' to try it");

        ReactiveSum reactiveSum = new ReactiveSum();
        IoWrapper ioWrapper = new IoWrapper(System.in, System.out);

        while(reactiveSum.parseInput(ioWrapper)) {
            System.out.println();
        }
    }


}
