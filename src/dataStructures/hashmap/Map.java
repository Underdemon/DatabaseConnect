/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dataStructures.hashmap;

/**
 *
 * @author rayan
 */
public interface Map<K, V>
{
    public Object add(K key, V value);
    
    public Object remove(K key);
    
    public V get(K key);
    
    public boolean containsKey(K key);
    
    public int size();
}
