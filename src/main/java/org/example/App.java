package org.example;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class App 
{
    public static void main( String[] args )
    {
//        testSubscriber();
//        testTransform();
        testControllingSubscriber();
    }

    private static void testControllingSubscriber() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        ControllingEndSubscriber<String> subscriber = new ControllingEndSubscriber<>(3);
        publisher.subscribe(subscriber);
        List<String> items = List.of("1", "x", "2", "x", "3", "x");
//        List<String> expected = List.of("1");

        // when

        items.forEach(publisher::submit);
        publisher.close();
    }

    private static void testSubscriber() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();
        publisher.subscribe(subscriber);
        List<String> items = List.of("1", "x", "2", "x", "3", "x");

        // when
        items.forEach(publisher::submit);
        publisher.close();
    }

    private static void testTransform() {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        TransformProcessor<String, Integer> transformProcessor
                = new TransformProcessor<>(Integer::parseInt);
        EndSubscriber<Integer> subscriber = new EndSubscriber<>();
        List<String> items = List.of("1", "2", "3");
        List<Integer> expectedResult = List.of(1, 2, 3);

        // when
        publisher.subscribe(transformProcessor);
        transformProcessor.subscribe(subscriber);
        items.forEach(publisher::submit);
        publisher.close();
    }
}
