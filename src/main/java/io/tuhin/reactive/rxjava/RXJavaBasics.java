
package io.tuhin.reactive.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * @author Tuhin Gupta
 * @since 2016 
 * <p> For APIs and reference about Reactive RXJava read https://github.com/ReactiveX/RxJava/wiki
 * 
 */

public class RXJavaBasics {
	
	public static void main(String[] args) {
		
		observableSubscriberHelloWorld();
		observableSubscriberSimpler();
		observableSubscriberFluent();
		observableSubscriberLambda();
		
		
	}
	
	/**
	 * Hello world to demo Observable Subscriber setup in RXJava
	 */
	public static void observableSubscriberHelloWorld(){
		
		Observable<String> myObservable = Observable.create(
			    new Observable.OnSubscribe<String>() {
			        @Override
			        public void call(Subscriber<? super String> sub) {
			            sub.onNext("Hello, world!");
			            sub.onCompleted();
			        }
			    }
			);
		
		Subscriber<String> mySubscriber = new Subscriber<String>() {
		    @Override
		    public void onNext(String s) { System.out.println(s); }

		    @Override
		    public void onCompleted() { }

		    @Override
		    public void onError(Throwable e) { }
		};
		
		myObservable.subscribe(mySubscriber);
		
	}
	
	/**
	 * A simpler version of Observable Subscriber setup in RXJava
	 * Since we do not require onCompleted and onError implementations in this simple example,
	 * hence there is a simple way to do this using {@code just()} API
	 */ 
	public static void observableSubscriberSimpler(){
		
		Observable<String> myObservable =
			    Observable.just("Hello, world! using just() api");
		
		Action1<String> onNextAction = new Action1<String>() {
		    @Override
		    public void call(String s) {
		        System.out.println(s);
		    }
		};
		
		myObservable.subscribe(onNextAction);
		
	}
	
	/**
	 * Another version of Observable Subscriber usage in RXJava.
	 * This method is same as {@code observableSubscriberSimpler} but uses fluent APIs (method chaining)
	 * 
	 */ 
	public static void observableSubscriberFluent(){
		
		Observable.just("Hello, world! using fluent apis.")
	    .subscribe(new Action1<String>() {
	        @Override
	        public void call(String s) {
	              System.out.println(s);
	        }
	    });
	}
	
	/**
	 * Another version of Observable Subscriber usage in RXJava.
	 * This method is same as {@code observableSubscriberSimpler} but uses Java 8 Lambda
	 * You can add multiple subscribers
	 */ 
	public static void observableSubscriberLambda(){
		
		Observable<String> myObservable = Observable.just("Hello, world! using Lambda");
	    
		myObservable.subscribe(s -> System.out.println(s));
		myObservable.subscribe(s2 -> System.out.println(s2));
		
	}
		

}
