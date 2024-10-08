/*
 * TEALsim - MIT TEAL Project
 * Copyright (c) 2004 The Massachusetts Institute of Technology. All rights reserved.
 * Please see license.txt in top level directory for full license.
 * 
 * http://icampus.mit.edu/teal/TEALsim
 * 
 * $Id: RectangularBoxBeanInfo.java,v 1.8 2007/07/17 15:46:57 pbailey Exp $ 
 * 
 */

package teal.physics.physical;

import java.beans.*;
import java.util.*;

import teal.core.AbstractElementBeanInfo;
import teal.util.TDebug;

public class RectangularBoxBeanInfo extends AbstractElementBeanInfo
{

	protected static ArrayList sProperties =null;
	protected static Class baseClass = RectangularBox.class;
	
	static
	{
       try
       {
            PropertyDescriptor pd = null;
			sProperties = new ArrayList(AbstractElementBeanInfo.getPropertyList());
			
			pd = new PropertyDescriptor("height",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("length",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("normal",baseClass);
			sProperties.add(pd);
			pd = new PropertyDescriptor("open",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("orientation",baseClass);
			sProperties.add(pd);
			pd = new PropertyDescriptor("width",baseClass);
			sProperties.add(pd);

			TDebug.println(baseClass.getName()+"BeanInfo: array complete");    
		}
        catch(IntrospectionException ie)
        {
           TDebug.println(ie.getMessage());
		}
           
	} 

	public static Collection getPropertyList()
	{
		return sProperties;
	}

	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return (PropertyDescriptor[]) sProperties.toArray(sPropertyTemplate);
	}

}