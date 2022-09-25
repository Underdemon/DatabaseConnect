/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataStructures.dll;

/**
 *
 * @author rayan
 */
public class dl_node<E>
{
    private E data;
    private dl_node<E> next;
    private dl_node<E> prev;
    
    public dl_node(E d, dl_node<E> nextNode, dl_node<E> prevNode)
    {
        this.data = d;
        this.next = nextNode;
        this.prev = prevNode;
        
        if(next != null)
            next.prev = this;
        
        if(prev != null)
            prev.next = this;
    }

    public E getData()
    {
        return data;
    }

    public void setData(E data)
    {
        this.data = data;
    }

    public dl_node<E> getNext()
    {
        return next;
    }

    public void setNext(dl_node<E> next)
    {
        this.next = next;
    }

    public dl_node<E> getPrev()
    {
        return prev;
    }

    public void setPrev(dl_node<E> prev)
    {
        this.prev = prev;
    }
}
