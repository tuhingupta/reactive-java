
package io.tuhin.reactive.rxjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author Tuhin Gupta
 * @since 2016 
 * 
 * 
 * <p>Refer to examples in {@linkplain RXJavaBasics} and {@linkplain RXJavaOperators} before looking at
 * examples here.
 * 
 * <p> For APIs and reference about Reactive RXJava read https://github.com/ReactiveX/RxJava/wiki
 */
public class RXJavaComplex {
	

	public static void main(String[] args) {
		
		observableSubscriberComplex();
		observableSubscriberComplexUsingFlatMap();
		processListUsingFlatMapLambdas();
		moreTransformationsUsingFlatMap();
	}
	
	
	
	/**
	 * This is the query method that returns a List of strings based on a text search
	 * 
	 * @param text
	 * @return
	 */
	static Observable<List<String>> query(String text){
		
		List<String> stringList = new ArrayList<String>(Arrays.asList("java","javascript","swift","kotlin","go","java8"));
		
		List<String> result = new ArrayList<>();
		
		for(String str: stringList){
			
			if(str.contains(text)){
				result.add(str);
			}
		}//for
		
		return Observable.just(result);
	}
	
	/**
	 * This method takes a little more complex look at {@code Observable} and {@code Subscriber}.
	 * Here we demonstrate use of {@code Observable} and {@code Subscriber}  to search a list based on an input.
	 * 
	 * {@code from} operator takes the list and convert an Iterable, a Future, or an Array into an Observable.
	 * Then {@code subscribe} method iterates over the list of values processing each one by one.
	 *  
	 */
	public static void observableSubscriberComplex(){
		
		List<String> strList = new ArrayList<String>(Arrays.asList("java","javascript","swift","kotlin","go","java8"));
		
		List<String> result = new ArrayList<>();
		
		String filterString = "java";
		
		Observable.from(strList).
			subscribe(value -> {

					if(value.contains(filterString)){
						result.add(value);
						System.out.println(value);
						
					}
				
			});//subscribe
	
	}
	
	/**
	 * This method takes a List of strings from {@code query} method and converts them to {@code Observable}
	 * Then elements of this list are emitted that can be subscribed to.
	 * <p>Uses operator {@code flatMap}
	 */
	public static void observableSubscriberComplexUsingFlatMap(){
		
		System.out.println("observableSubscriberComplex");
		
		query("java").
			flatMap(new Func1<List<String>, Observable<String>>() {
		        
				@Override
		        public Observable<String> call(List<String> strs) {
		            return Observable.from(strs);
		        }
		    })
		    .subscribe(str -> System.out.println(str));
		
	}
	
	/**
	 * This methods is same as {@code observableSubscriberComplexUsingFlatMap}.
	 * Instead of defining functions for {@code flatMap} it uses Lamdas.
	 */
	public static void processListUsingFlatMapLambdas(){
		
		System.out.println("processListUsingFlatMapLambdas");
		
		query("java")
	    	.flatMap(strs -> Observable.from(strs))
	    	.subscribe(str -> System.out.println(str));
		
	}
	
	/**
	 * This methods shows how to use {@code flatMap} to do more transformations on data.
	 * Commented code shows same implementation using Lambdas 
	 * 
	 * This code could also be written using Lambdas as:
	 * {@code 
	    query("java")
	    	.flatMap(strs -> Observable.from(strs))
	    	.flatMap(str -> {
	    			return Observable.just("transformed data "+str);
	    			})
			    .subscribe(str -> System.out.println(str));
		}	    
	 */
	public static void moreTransformationsUsingFlatMap(){
		
		System.out.println("processListUsingFlatMapLambdas");
		
		query("java")
	    	.flatMap(strs -> Observable.from(strs))
	    	.flatMap(new Func1<String, Observable<String>>() {
	            @Override
	            public Observable<String> call(String str) {
	            	
	                return Observable.just("transformed data "+str);
	            }
	        })
	    	.subscribe(str -> System.out.println(str));
		
	}
	
	

}
