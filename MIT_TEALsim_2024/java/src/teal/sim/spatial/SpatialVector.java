/*
 * TEALsim - MIT TEAL Project
 * Copyright (c) 2004 The Massachusetts Institute of Technology. All rights reserved.
 * Please see license.txt in top level directory for full license.
 * 
 * http://icampus.mit.edu/teal/TEALsim
 * 
 * $Id: SpatialVector.java,v 1.15 2007/07/17 21:00:54 pbailey Exp $ 
 * 
 */

package teal.sim.spatial;

import java.awt.Color;

import javax.media.j3d.*;
import javax.vecmath.*;

import teal.render.j3d.*;
import teal.render.scene.*;
import teal.physics.physical.PhysicalObject;

public abstract class SpatialVector extends SpatialPhysicalObject {

    double arrowScale = 1.;
    Vector3d value = null;

    public SpatialVector() {
        value = new Vector3d();
    }

    public SpatialVector(PhysicalObject x) {
        this();
        setPhysicalObject(x);
    }

    protected TNode3D makeNode() {
        SolidArrowNode node = new SolidArrowNode();
        node.setPickable(false);
        node.setVisible(true);
        node.setColor(getColor());
        updateNode3D(node);
        return node;
    }

    public double getArrowScale() {
        return arrowScale;
    }

    public void setArrowScale(double scale) {
        arrowScale = scale;
        renderFlags |= GEOMETRY_CHANGE;
        //nextSpatial();
    }

    public void setColor(Color c) {
        super.setColor(c);
    }

    public void render() {
        if (mNeedsSpatial) {
            nextSpatial();
            mNeedsSpatial = false;
        }
        if (mNode != null) {
        	
            if ((renderFlags & GEOMETRY_CHANGE) == GEOMETRY_CHANGE) {
                if (this instanceof FieldVector ) {
                    updateNode3D((TShapeNode) mNode);
                } else {
                    updateNode3D((LineNode) mNode);
                }
                renderFlags ^= (GEOMETRY_CHANGE); // | POSITION_CHANGE);
            }
            super.render();
        }
        //updateNode3D((LineNode) mNode);
        //super.render();
        
    }

    public void updateNode3D(LineNode node) {
        if ((node == null) || (theEngine == null)) {
            return;
        }
        Vector3d vector = new Vector3d(value);
        vector.scale(arrowScale);

        Vector3d from = object.getPosition();
        Vector3d to = new Vector3d(from);
        to.add(vector);
        node.setFromTo(from, to);
        //System.out.println("SpatialVector From: " + from + " To: " + to);
        node.setScale(arrowScale);

    }

    // this method is used by FieldVector, I need to finish implementing this properly - Mike
    public void updateNode3D(TShapeNode node) {
        if ((node == null) || (theEngine == null)) {
            return;
        }
        boolean scaleByMagnitude = true;
        if (scaleByMagnitude) {
            node.setScale(value.length());
        } else {
            node.setScale(arrowScale);
        }
        node.setPosition(position);

    }

    public Bounds getBoundingArea() {
        return new BoundingSphere(new Point3d(position), arrowScale);
    }

	/**
	 * @return Returns the value.
	 */
	public Vector3d getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(Vector3d value) {
		this.value = value;
	}
}