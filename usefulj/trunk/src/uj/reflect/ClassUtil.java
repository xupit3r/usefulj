package uj.reflect;

public class ClassUtil
{
	public static Class<?>[] getParamClasses(Object[] objs)
	{
		Class<?>[] types = null;
		if(objs != null)
		{
			types = new Class<?>[objs.length];
			for(int i = 0; i < objs.length; i++)
				types[i] = objs[i].getClass();
		}
		return types;
	}
}
