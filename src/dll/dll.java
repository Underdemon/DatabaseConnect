/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dll;

/**
 *
 * @author rayan
 */
public class dll<E>
{
    private int len;
    private dl_node<E> head;
    private dl_node<E> tail;
    
    public dll()
    {      
        head = null;
        tail = null;
        len  = 0;
    }
    
    public boolean isEmpty()
    {
        return this.head == null;
    }
    
    public void addFirst(E data)
    {
        head = new dl_node<E>(data, head, null);
        
        if(tail == null)
        {
            tail = head;
            head.getNext().setPrev(head);
            ++this.len;
        }
    }
    
    public void append(E data)
    {
        tail = new dl_node<E>(data, null, tail);
        if(this.isEmpty())
        {
            head = tail;
            ++this.len;
        }
    }
    
    public void insert(E data, int pos)
    {
        dl_node<E> temp = head;
        if(pos == 0)
        {
            addFirst(data);
            return;
        }
        else if(pos == this.len)
        {
            append(data);
            return;
        }
        while(pos != 1)
        {
            temp.setNext(temp.getNext());
            --pos;
        }
        dl_node node = new dl_node<E>(data, temp.getNext(), temp.getPrev());
        node.setNext(temp.getNext());
        temp.setNext(node);
        node.setPrev(temp);
        temp = node.getNext();
        temp.setPrev(node);
        ++this.len;
    }
    
    public E peek()
    {
        return this.head.getData();
    }
    
    public E pop()
    {
        E data = this.head.getData();
        this.head.setNext(head);
        this.head.setPrev(null);
        --len;
        return data;
    }
    
    public E removeLast()
    {
        E data = this.tail.getData();
        this.tail = this.tail.getPrev();
        if(this.tail == null)
            this.head = null;
        else
            this.tail.setNext(null);
        --len;
        return data;
    }
    
    public void remove(E d)
    {
        dl_node temp = this.head;
        if(this.head.getData().equals(d))
        {
            pop();
            --len;
        }
        else if(this.tail.getData().equals(d))
        {
            removeLast();
            --len;
        }
        else
        {
            while(temp != null && temp.getData().equals(d))
            {
                temp = temp.getNext();
            }
            if(temp != null)
            {
                temp.getPrev().setNext(temp.getNext());
                temp.getNext().setPrev(temp.getPrev());
                --len;
                System.out.println("Successfully removed " + d);
            }
            else
            {
                System.out.println("Data not found in DLL");
            }
        }
    }
    
    public void deletePos(int pos) throws IndexOutOfBoundsException
    {
        dl_node temp = this.head;
        if(pos == 0)
        {
            pop();
        }
        else if(pos == len)
        {
            removeLast();
        }
        else
        {
            for(int i = 0; i < pos; ++i)
            {
                temp = temp.getNext();
            }
            temp.getPrev().setNext(temp.getNext());
            temp.getNext().setPrev(temp.getPrev());
            --len;
            System.out.println("Successfully removed " + temp.getData() + " at position " + pos);
        }
    }
    
    public boolean contains(E d)
    {
        dl_node temp = this.head;
        boolean isContained = false;
        while(temp != null)
        {
            if(temp.getData().equals(d))
            {
                isContained = true;
            }
        }
        return isContained;
    }
    
    public int count(E item)
    {
        dl_node temp = this.head;
        int occurence = 0;
        do
        {
            if(temp.getData().equals(item))
                ++occurence;
            temp = temp.getNext();
        }
        while(temp != null);
        
        return occurence;
    }
    
    public void index(E item)
    {
        dl_node temp = this.head;
        int index = 0;
        do
        {
            if(temp.getData().equals(item))
                System.out.println("\tItem " + item + " found at index " + index);
            temp = temp.getNext();
            ++index;
        }
        while(temp != null);
    }
    
    public void printList()
    {
        dl_node currNode = this.head;
        System.out.println("\n======== PRINTING LIST ========\n");
        if(isEmpty())
        {
            System.out.println("\tLIST IS EMPTY");
        }
        else
        {
            while(currNode != null)
            {
                System.out.println("\t" + currNode.getData() + ", ");
                currNode = currNode.getNext();
            }
        }
    }
    
    
    
    public int getCount()
    {
        return len;
    }

    public void setCount(int count)
    {
        this.len = count;
    }

    public dl_node<E> getHead()
    {
        return head;
    }

    public void setHead(dl_node<E> head)
    {
        this.head = head;
    }

    public dl_node<E> getTail()
    {
        return tail;
    }

    public void setTail(dl_node<E> tail)
    {
        this.tail = tail;
    }
}
