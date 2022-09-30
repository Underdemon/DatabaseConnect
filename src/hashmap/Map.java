/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hashmap;

/**
 *
 * @author rayan
 */
public interface Map<K, V>
{
    public void add(K key, V value);
    
    public void remove(K key);
    
    public V get(K key);
    
    public boolean containsKey(K key);
    
    public int size();
}
