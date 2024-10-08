/*
 * TEALsim - MIT TEAL Project
 * Copyright (c) 2004 The Massachusetts Institute of Technology. All rights reserved.
 * Please see license.txt in top level directory for full license.
 * 
 * http://icampus.mit.edu/teal/TEALsim
 * 
 * $Id: RenderedBeanInfo.java,v 1.7 2007/07/16 22:04:51 pbailey Exp $ 
 * 
 */

package teal.render;

import java.beans.*;
import java.util.*;

import teal.core.AbstractElementBeanInfo;
import teal.util.TDebug;

public class RenderedBeanInfo extends AbstractElementBeanInfo
{

    protected static ArrayList sEvents = null;
	protected static ArrayList sProperties =null;
	protected static Class baseClass = Rendered.class;
	
	static
	{
       try
       {
            PropertyDescriptor pd = null;
			sProperties = new ArrayList(AbstractElementBeanInfo.getPropertyList());

			pd = new PropertyDescriptor("boundingArea",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("color",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("drawn",baseClass);
			sProperties.add(pd);
			pd = new PropertyDescriptor("moveable",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("pickable",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("picked",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("recievingFog",baseClass);
			sProperties.add(pd);
			pd = new PropertyDescriptor("rotation",baseClass);
			sProperties.add(pd);
			pd = new PropertyDescriptor("rotation",baseClass);
			sProperties.add(pd);
			pd = new PropertyDescriptor("rotating",baseClass);
			sProperties.add(pd);
			pd = new PropertyDescriptor("selectable",baseClass);
			sProperties.add(pd);
			pd = new PropertyDescriptor("selected",baseClass);
			pd.setBound(true);
			sProperties.add(pd);
			pd = new PropertyDescriptor("uRL",baseClass);
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
