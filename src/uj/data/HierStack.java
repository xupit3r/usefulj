/**
 * Copyright 2010 Joseph D'Alessandro
 * This program is distributed under the GNU Public License.
 * 
 *  This file is part of the UsefulJ library.
 *
 *   UsefulJ is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   UsefulJ is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with UsefulJ.  If not, see <http://www.gnu.org/licenses/>
 */
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
