
package io.tuhin.reactive.rxjava;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author Tuhin Gupta
 * @since 2016
 * <p>This class extends the basic methods that were defined in {@linkplain RXJavaBasics}
 */
public class RXJavaOperators {
	
	public static void main(String[] args) {
		
		transformDataUsingMap();
		transformDataByMapUsingLambdas();
		transformDataByMapImproved();
		transformDataThreads();
	}
	
	/**
	 * This method is extends {@code RXJavaBasics.observableSubscriberSimpler} by using {@code map} transformation
	 * to transform data that is emitted by observable object.
	 * 
	 * Here {@code myObservable} prints the same string emitted by Observable.
	 * And {@code intObservable} transforms using {@code map} api the input string into an integer.
	 * 
	 * So we can have different subscribers that can subscribe to original data emitted and the transformed one.
	 */ 
	public static void transformDataUsingMap(){
		
		Observable<String> myObservable = Observable.just("Hello, world! transform by map");
		
		Observable<Integer> intObservable = myObservable.map(new Func1<String, Integer>() {
	        @Override
	        public Integer call(String s) {
	            return s.hashCode();
	        }
	    });
	    
		myObservable.subscribe(s -> System.out.println(s));
		intObservable.subscribe(i2 -> System.out.println(Integer.toString(i2)));
		
	}
	
	/**
	 * This method extends {@code RXJavaBasics.observableSubscriberSimpler} by using {@code map} operator
	 * to transform data that is emitted by observable object.
	 * 
	 * Same as {@code transformDataUsingMap} but {@code intObservable} uses Lambdas.
	 * 
	 */ 
	public static void transformDataByMapUsingLambdas(){
	
		Observable.just("Hello, world! transform by map using Lambda")
				.map(s -> s.hashCode())
				.subscribe(i -> System.out.println(Integer.toString(i)));
	
	}
	
	
	/**
	 * This method extends {@code RXJavaBasics.observableSubscriberSimpler} by using {@code map} operator
	 * to transform data that is emitted by observable object. 
	 * <p>However {@code Subscriber} should do minimal work as it might be running on the main nonblocking thread.</p> 
	 * 
	 * So we can use {@code map} operator to do transformation for us before {@code Subscriber} uses it.
	 * 
	 */ 
	public static void transformDataByMapImproved(){
	
		Observable.just("Hello, world! heavylifting done by map operators")
				.map(s -> s.hashCode())
				.map(i -> Integer.toString(i))
				.subscribe(s -> System.out.println(s));
	
	}
	
	
	/**
	 * This code demonstrates use of {@code Observable} and {@code Subscriber} with {@code map} operator.
	 * It shows that if there is an apparent delay from {@code Observable} or {@code map} such as a DB query 
	 * taking long, then  {@code Subscriber} does not block main thread and keeps waiting.
	 * But only reacts when an event is emitted.
	 * 
	 */ 
	public static void transformDataThreads(){
		
		Observable<String> myObservable = Observable.just("Hello emitted.");
		
		Observable<Integer> intObservable = myObservable.map(s -> {
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					return s.hashCode();
				});
		
		intObservable.map(i -> Integer.toString(i));
		
		myObservable.subscribe(s -> System.out.println(s));
		System.out.println("Reached intObservable");
		intObservable.subscribe(s -> System.out.println(s+" Hello emitted with delay."));
				
	
	}
	
	

}
