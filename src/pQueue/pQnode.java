/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pQueue;

/**
 *
 * @author rayan
 */
public class pQnode<T extends Comparable<T>>
{
    private T data;
    private pQnode<T> next;
    private pQnode<T> prev;
    
    public pQnode(T data, pQnode next, pQnode prev)
    {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public pQnode<T> getNext()
    {
        return next;
    }

    public void setNext(pQnode<T> next)
    {
        this.next = next;
    }

    public pQnode<T> getPrev()
    {
        return prev;
    }

    public void setPrev(pQnode<T> prev)
    {
        this.prev = prev;
    }
    
}
