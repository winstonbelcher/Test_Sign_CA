/*
 * TEALsim - MIT TEAL Project
 * Copyright (c) 2004 The Massachusetts Institute of Technology. All rights reserved.
 * Please see license.txt in top level directory for full license.
 * 
 * http://icampus.mit.edu/teal/TEALsim
 * 
 * $Id: HasRadius.java,v 1.3 2007/07/16 22:04:52 pbailey Exp $
 * 
 */

package teal.render.geometry;

/**
 * 
 * @author Andrew McKinney
 * @author Phil Bailey
 * @author Michael Danziger
 * @version $Revision: 1.3 $
 */

/**
 * Interface for rendered objects that have a radius.
 */
public interface HasRadius {

    /**
     * Sets the radius of this object. 
     * 
     * @param radius new radius.
     */
    public void setRadius(double radius);

    /**
     * Gets the radius of this object. 
     * 
     * @return radius.
     */
    public double getRadius();
}
