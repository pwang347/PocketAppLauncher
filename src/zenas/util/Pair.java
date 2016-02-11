package zenas.util;

/**
 * Created by Paul on 2/10/2016.
 */

//multipurpose helper class for associating two objects
public class Pair<T,U> {
    private T a;
    private U b;

    //constructs a new pair object
    public Pair(T a, U b){
        this.a = a;
        this.b = b;
    }

    public T first(){
        return a;
    }

    public U second(){
        return b;
    }
}
