package uj.data;

import java.util.Stack;

public class HierStack
{
	private Stack<Class<?>> stack = null;
	private int depth = 0;
	
	
	public HierStack()
	{
		depth = 0;
		stack = new Stack<Class<?>>();
	}
	
	public void push(Class<?> c)
	{
		depth++;
		stack.push(c);
	}
	
	public Class<?> pop()
	{
		depth--;
		return stack.pop();
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public Class<?> peek()
	{
		return stack.peek();
	}

}
