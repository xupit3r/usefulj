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

import java.io.File;
import java.io.IOException;

import uj.log.UJ;
import uj.string.StringUtils;

public class FileHelper
{
	public static synchronized String getFileText(File f)
	{
		UJ.log.out("Loading "+f.getAbsolutePath());
		String ft = " ";
		try
		{
			ft = StringUtils.makeString(f);
		}catch(IOException fnf){System.err.println("[FileHelper.getFileText]: could not load file and IOException occurred.");}
		UJ.log.out("File Loaded.");
		return ft;
	}

}
