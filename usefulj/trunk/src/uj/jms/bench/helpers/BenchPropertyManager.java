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
package uj.jms.bench.helpers;

import java.util.Properties;
import javax.naming.Context;

//Possible Additions to ActiveMQ uri ?wireFormat.maxInactivityDuration=0&connectionTimeout=3000
public class BenchPropertyManager
{
	public static final int ACTIVEMQ=0;
	
	
	/*
	 * required properties for JNDI context: 
	 * 	 - INITIAL_CONTEXT_FACTORY (location of initial context factory)
	 *   - PROVIDER_URL (URL for the service)
	 *   Example:
	 *   ACTIVEMQ, org.apache.activemq.jndi.ActiveMQInitialContextFactory, tcp://127.0.0.1:61616
	 */
	public static Properties getJNDIProps(int chc)
	{
		Properties props = new Properties();
		switch(chc)
		{
			case ACTIVEMQ:
					props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
					props.setProperty(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
					break;
			default: System.err.println("Broker properties not found.");
		}
		return props;
	}

}
