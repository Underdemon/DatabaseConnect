/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hashmap;

import BST.BST;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author rayan
 * 
 * 
 * normally a hashmap handles collisions by chaining
 *      an array of buckets where each bucket is a linked list holding the hashEntry key-value pairs
 * 
 * this makes worse case time complexity for methods such as get() O(n) from (compared to to best case O(1))
 * in order to improve this java 8 handled collisions using a (balanced) binary tree (BST) instead of a linked list
 * this improves the time complexity of methods such as get() to O(log n)
 * 
 * (note to self: often implemented with red-black BST)
 */
public class hashMap<K extends Comparable<K>, V extends Comparable<V>> implements Map<K, V>
{
    /*
        below a bitwise shift operator is used (just to test knowledge)
            1 << 7
                a binary value of 00000001 (1 in decimal) is shifted left by 7
                the resultant value is 10000000 (128 in decimal)
    
    
        https://www.javatpoint.com/operator-shifting
        https://bit-calculator.com/bit-shift-calculator
    */
    private final int capacity = (1 << 7);
    
    /*  array stores the root node for the binary tree  */
    private final hashEntry<K, V> [] table = new hashEntry[capacity];
    
    
    
    
    /*
        ... = VARARGS
        normally unneccessary boilerplate for arbitrary no of parameters (passing arguments as an array or making multiple overloaded functions each of which
        has a number of arguments eg void put(String s1); void put (String s1, String s2...)
            the ellipses here is an example of what the ellipses is being used for
                that is, an arbitrary number of hashEntries
        varargs (which is the ellipses below) handles an arbitrary number of parameters automatically using an array
    
        https://www.baeldung.com/java-varargs
        https://www.geeksforgeeks.org/variable-arguments-varargs-in-java/
    */
    public hashMap(hashEntry<K, V> ... entries)
    {
        for(hashEntry<K, V> entry : entries)
        {
            if(entry != null)
            {
                
            }
        }
    }
    
    public int hash(K key)
    {
        return Math.abs(key.hashCode() % capacity);
    }
    
    @Override
    public void add(K key, V value)
    {
        
    }

    @Override
    public void remove(K key)  // renmoves hashEntry with key value matching key arg
    {
        
    }

    @Override
    public V get(K key)
    {
        
    }

    @Override
    public boolean containsKey(K key)
    {
        
    }

    @Override
    public int size()
    {
        
    }
    
    /*
        below is the binary tree implementation for the buckets
        the functions are recursive in order to traverse the branches
    */
    
    public hashEntry<K, V> searchTree(hashEntry<K, V> root, K key)
    {
        
    }
      
    
//    public mapIterator iterator()
//    {
//        return new mapIterator();
//    }
    
    /*
        to use iterator
        
        hashMap<type1, type2> map = new hashMap<>();
        Iterator itr = map.iterator();
        hashEntry<type1, type2> temp;
        while(itr.hasNext())
        {
            temp = new hashEntry<>((hashEntry) itr.next());
            sout("key " + temp.getKey() + "value " + temp.getValue());
        }
    */
    
//    public class mapIterator implements Iterator<hashEntry<K, V>>
//    {
//        int curr;
//        
//        public mapIterator()
//        {
//            curr = 0;
//        }
//        
//        public boolean hasNext()
//        {
//            return curr < kvEntries.size(); // if the current element the iterable is on is less than the arraylist's size, then there is a next element to iterate to
//        }
//        
//        /*
//            returns the next element in the hashmap
//            if there are no more elements in the hashmap, an error is thrown
//        */
//        public hashEntry<K, V> next()
//        {
//            if(curr >= kvEntries.size())
//            {
//                throw new IllegalArgumentException();
//            }
//            
//            ++curr;
//            return new hashEntry<>(kvEntries.get(curr - 1));
//        }
//    }
}


