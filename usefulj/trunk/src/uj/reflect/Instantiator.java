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
package uj.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Instantiator <E>
{
	
	public E getActorInstance(Class<?> cl, Object ...objects) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException
	{
		
		return (E) getObjectInstance(cl,objects);
	}
	
	public Object getObjectInstance(Class<?> cl, Object ...objects) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException
	{
		Class<?>[] params = ClassUtil.getParamClasses(objects);
		Object[] actuals = objects;
		Constructor<?> con = null;
		try
		{
			con = (Constructor<?>) cl.getConstructor(params);
		}
		catch(NoSuchMethodException nm)
		{
			actuals = endingArray(cl,objects);
			con = (Constructor<?>)cl.getConstructor(ClassUtil.getParamClasses(actuals));
		}
		return con.newInstance(actuals);
	}
	
	//by the time we get here, we already know that no constructor matches base classes of the parameters
	//so let's try to relax the constraints...
	private Object[] endingArray(Class<?> cl, Object ...arr) throws ClassNotFoundException, SecurityException, NoSuchMethodException
	{
		Class<?>[] carr = ClassUtil.getParamClasses(arr);
		Object[] mod = null;
		Class<?> end = carr[carr.length - 1];
		Class<?>[] inf = end.getInterfaces();
		int indx = carr.length-1;
		boolean found = false;
		while(compareClasses(carr[indx],end)){indx--;}
		indx++;
		for(int i = 0; i < inf.length && !found; i++)
		{
			Object[] oOM = createArray(inf[i], indx, arr);
			mod = modifyArray(indx,arr,oOM);
			try
			{
				cl.getConstructor(ClassUtil.getParamClasses(mod));
				found = true;
			}
			catch(NoSuchMethodException nm){}
		}
		return mod;
	}
	
	//either they are of the same parent class or they have an interface in common
	private static boolean compareClasses(Class<?> a, Class<?> b)
	{
		boolean flag = false;
		Class<?>[] ainfs = a.getInterfaces();
		Class<?>[] binfs = b.getInterfaces();		
		flag = a == b || interfaceIntersection(ainfs,binfs);		
		return flag;
	}
	
	// do they have an interface in common?
	private static boolean interfaceIntersection(Class<?>[] as, Class<?>[] bs)
	{
		boolean yay = false;
		int stop = as.length > bs.length ? bs.length : as.length;
		for(int i = 0; i < stop && !yay; i++)
			yay = (as[i] == bs[i]);		
		return yay;
	}
	
	private static Object[] modifyArray(int idx, Object[] original, Object[] add)
	{
		Object[] tmp = new Object[idx+1];
		for(int i=0; i < tmp.length-1; i++)
			tmp[i] = original[i];
		tmp[tmp.length-1] = add;
		return tmp;
	}
	
	private static Object[] createArray(Class<?> cl, int begin, Object[] objs)
	{
		Object o = Array.newInstance(cl, objs.length - begin);
		for(int i = 0; i < objs.length - begin; i++)
			Array.set(o, i, objs[begin+i]); 
		return (Object[])o;
	}
	
	/*************************/
	/******** Testing ********/
	/*************************/
	private static void printArray(Object[] arr)
	{
		for(int i = 0; i < arr.length; i++)
			System.out.print(arr[i]+" ");
		System.out.println();
	}

}
