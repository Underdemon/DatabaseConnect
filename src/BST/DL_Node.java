/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BST;

/**
 *
 * @author rayan
 */
public class DL_Node<E>
{
    protected E data;
    protected DL_Node<E> next;
    protected DL_Node<E> prev;
    
    public DL_Node(E d, DL_Node<E> nextNode, DL_Node<E> prevNode)
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

    public DL_Node<E> getNext()
    {
        return next;
    }

    public void setNext(DL_Node<E> next)
    {
        this.next = next;
    }

    public DL_Node<E> getPrev()
    {
        return prev;
    }

    public void setPrev(DL_Node<E> prev)
    {
        this.prev = prev;
    }
    
    public DL_Node(E d)
    {
        this(d, null, null);
    }
}