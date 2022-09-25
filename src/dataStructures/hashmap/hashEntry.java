/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures.hashmap;

/**
 *
 * @author rayan
 */

public class hashEntry<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<hashEntry<K, V>>
{
    private K key;
    private V value;
    private hashEntry<K, V> left;
    private hashEntry<K, V> right;
    
    public hashEntry()
    {
        this.key   = null;
        this.value = null;
        this.left  = null;
        this.right = null;
    }
    
    public hashEntry(K key, V value)
    {
        this.key   = key;
        this.value = value;
        this.left  = null;
        this.right = null;
    }
    
    public hashEntry(hashEntry<K,V> entry)
    {
        this.key   = entry.getKey();
        this.value = entry.getValue();
        this.left  = entry.getLeft();
        this.right = entry.getRight();
    }
    
    @Override
    public int compareTo(hashEntry<K, V> obj)
    {
        return this.getKey().compareTo(obj.getKey());
        // hashEntries are compared by key values
    }

    public K getKey()
    {
        return key;
    }

    public void setKey(K key)
    {
        this.key = key;
    }

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    public hashEntry<K, V> getLeft()
    {
        return left;
    }

    public void setLeft(hashEntry<K, V> left)
    {
        this.left = left;
    }

    public hashEntry<K, V> getRight()
    {
        return right;
    }

    public void setRight(hashEntry<K, V> right)
    {
        this.right = right;
    }

}
