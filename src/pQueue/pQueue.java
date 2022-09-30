/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pQueue;

import java.util.regex.Pattern;

/**
 *
 * @author rayan
 */
public class pQueue<T extends Comparable<T>>
{
    private pQnode<T> head;
    private pQnode<T> tail;
    private int size;
    
    public pQueue()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public boolean isNumeric(String str)
    {
        // used as compareTo() compares values lexicographically and only strings should be compared that way
        // https://chortle.ccsu.edu/java5/Notes/chap92/ch92_2.html
        if(str.equals(null))
            return false;
        
        final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        
        /*
        -?          determines if number starts with minus
        \d+         matches one or more digits
        (\.\d+)?    matches for decimal point and digits following
        */
        
        return pattern.matcher(str).matches();
    }
    
    public void enqueue(T data)
    {
        pQnode temp = new pQnode(data, null, null);
        if(isNumeric(data.toString()))  // SORTS NUMBERS NUMERICALLY
        {
            if(head == null)
            {
                // adds first node to queue
                this.head = temp;
                this.tail = temp;
            }
            else if(Integer.parseInt(this.head.getData().toString()) >= Integer.parseInt(data.toString()))
            {
                temp.setNext(this.head);
                this.head.setPrev(temp.getNext());
                this.head = temp;
            }
            else if(Integer.parseInt(this.tail.getData().toString()) <= Integer.parseInt(data.toString()))
            {
                temp.setPrev(this.tail);
                this.tail.setNext(temp);
                this.tail = temp;
            }
            else
            {
                pQnode node = this.head;
                // find the point at which the inserted data has a higher priority
                //if(!isNumeric(data.toString()))
                //{
                while(Integer.parseInt(node.getData().toString()) < Integer.parseInt(data.toString()))
                {
                    node = node.getNext();
                }
                //}
                // code to actually add the node
                temp.setNext(node);
                temp.setPrev(node.getPrev());
                node.getPrev().setNext(temp);
                node.setPrev(temp);
            }
        }
        else    // SORTS STRING DATA LEXICOGRAPHICALLY
        {
            if(head == null)
            {
                // adds first node to queue
                this.head = temp;
                this.tail = temp;
            }
            else if(this.head.getData().compareTo(data) >= 0)
            {
                // add node at first pos
                temp.setNext(this.head);
                this.head.setPrev(temp.getNext());
                this.head = temp;
            }
            else if(this.tail.getData().compareTo(data) <= 0)
            {
                // add node to last pos
                temp.setPrev(this.tail);
                this.tail.setNext(temp);
                this.tail = temp;
            }
            else
            {
                pQnode node = this.head;

                // find the point at which the inserted data has a higher priority
                //if(!isNumeric(data.toString()))
                //{
                    while(node.getData().compareTo(data) < 0)
                    {
                        node = node.getNext();
                    }
                //}
                // code to actually add the node
                temp.setNext(node);
                temp.setPrev(node.getPrev());
                node.getPrev().setNext(temp);
                node.setPrev(temp);
            }
        }
        
        this.size++;
    }
    
    public T peek() throws IllegalStateException    // peeks first element
    {
        if(!isEmpty())
        {
            return this.head.getData();
        }
        throw new IllegalStateException();
    }
    
    public T pop() throws IllegalStateException // peeks and removes first element
    {
        if(!isEmpty())
        {
            T data = this.peek();
            pQnode temp = this.head;
            if(this.head.equals(this.tail))
            {
                this.head = null;
                this.tail = null;
            }
            else
            {
                this.head = this.head.getNext();
                this.head.setPrev(null);
            }
            this.size--;
            return data;
        }
        throw new IllegalStateException();
    }
    
    public void remove(T d) throws IllegalStateException
    {
        if(!isEmpty())
        {
            pQnode temp = this.head;
            if(this.head.equals(this.tail))
            {
                this.head = null;
                this.tail = null;
            }
            else
            {
                while(!temp.getData().equals(d))
                {
                    temp = temp.getNext();
                }
                if(temp.getData().equals(d))
                {
                    if(temp == this.head)
                    {
                        head = temp.getNext();
                        temp.getNext().setPrev(null);
                    }
                    else if(temp == this.tail)
                    {
                        tail = temp.getPrev();
                        temp.getPrev().setNext(null);
                    }
                    else
                    {                        
                        temp.getPrev().setNext(temp.getNext());
                        temp.getNext().setPrev(temp.getPrev());
                    }
                }
                else
                {
                    System.out.println("remove() failed. No such value exists");
                    return;
                }
            }
            this.size--;
            return;
        }
        throw new IllegalStateException();
    }
    
    public void printQueue()
    {
        pQnode temp = this.head;
        System.out.println("\n\t======== PRINTING QUEUE ========");
        while(temp != null)
        {
            System.out.println("\t - " + temp.getData());
            temp = temp.getNext();
        }
        System.out.println("\n\t======== FINISHED PRINTING ========");
    }
    
    public int size()
    {
        return this.size;
    }
}
