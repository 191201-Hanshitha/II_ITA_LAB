class Queue
{
    int n;
    boolean value = false;
    synchronized int getItem()
    {
         while(!value)
	{
	    try
	    {
		wait();
	    }
	    catch(InterruptedException e)
	    {
		System.out.println("InterruptedException caught");
	    }
	}
	System.out.println("Consumed:" +n);
        value = false;
	try 
	{
	    Thread.sleep(1000);
	} 
	catch (InterruptedException e)
	{
	    System.out.println("InterruptedException caught");
	}
	notify();
	return n;
    }
    synchronized void putItem(int n) 
    {
	while (value)
	    try 
	    {
		wait();
	    }
	    catch (InterruptedException e)
	    {
		System.out.println("InterruptedException caught");
	    }
	this.n = n;
	value = true;
	System.out.println("Produced: " +n);
	try 
	{
	    Thread.sleep(1000);
	}
	catch (InterruptedException e)
	{
	    System.out.println("InterruptedException caught");
	}
	notify();
    }
}
class Producer implements Runnable
{
    Queue q;
    Producer(Queue q)
    {
	this.q = q;
	new Thread(this, "Producer").start();
    }
    public void run() 
    {
	int i = 0;
	while(true) 
	{
	    q.putItem(i++);
	}
    }
}
class Consumer implements Runnable
{
    Queue q;
    Consumer(Queue q)
    {
        this.q = q;
	new Thread(this, "Consumer").start();
    }
    public void run() 
    {
	while(true) 
	{
	    q.getItem();
	}
    }
}
class ProducerConsumerInterThread
{
    public static void main(String args[])
    {
	Queue q = new Queue();
	new Producer(q);
	new Consumer(q);
    }
}


