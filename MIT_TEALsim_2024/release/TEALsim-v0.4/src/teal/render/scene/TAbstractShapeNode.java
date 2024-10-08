/*
 * TEALsim - MIT TEAL Project
 * Copyright (c) 2004 The Massachusetts Institute of Technology. All rights reserved.
 * Please see license.txt in top level directory for full license.
 * 
 * http://icampus.mit.edu/teal/TEALsim
 * 
 * $Id: TAbstractShapeNode.java,v 1.2 2007/07/16 22:04:57 pbailey Exp $ 
 * 
 */

package teal.render.scene;

import java.util.Enumeration;

import com.sun.j3d.utils.geometry.GeometryInfo;

/** Interface for the
 */

public interface TAbstractShapeNode{

	public void setGeometry(GeometryInfo geo);
	public void setGeometry(GeometryInfo geo, int idx);
	public void addGeometry(GeometryInfo geo);
	public GeometryInfo getGeometry();
	public Enumeration getAllGeometries();
	public void removeAllGeometry();

    public void setShininess(float shine);
    public void setTransparency(float x);

}