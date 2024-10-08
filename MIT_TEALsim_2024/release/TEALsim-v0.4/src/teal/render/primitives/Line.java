/*
 * TEALsim - MIT TEAL Project
 * Copyright (c) 2004 The Massachusetts Institute of Technology. All rights reserved.
 * Please see license.txt in top level directory for full license.
 * 
 * http://icampus.mit.edu/teal/TEALsim
 * 
 * $Id: Line.java,v 1.3 2007/07/16 22:04:56 pbailey Exp $ 
 * 
 */

package teal.render.primitives;

import java.beans.PropertyChangeEvent;
import javax.vecmath.*;

import teal.core.TElement;
import teal.render.*;
import teal.render.geometry.HasFromTo;
import teal.render.j3d.*;
import teal.render.scene.*;


/**
 * Line provides a base class for a group of spatial classes that represent connections
 * between two objects that have a position. This should be re- implemented as an interface, with 
 * specific classes that have zero -> two HasPosition mobjects.
 *
 *
 * @author Phil Bailey - Center for Educational Computing Initiatives / MIT
 */

public class Line extends Rendered {

    private static final long serialVersionUID = 4050196440661832752L;
    protected Vector3d drawTo;
    protected HasPosition posObj1 = null;
    protected HasPosition posObj2 = null;

    public Line() {
        super();
    }

    public Line(Vector3d position, Vector3d drawTo) {
        super();
        setDrawTo(drawTo);
        setPosition(position);
    }

    public Line(Vector3d pos, HasPosition obj) {
        super();
        setPosition(pos);
        posObj1 = null;
        posObj2 = obj;
        setDrawTo(posObj2.getPosition());
        ((TElement)obj).addPropertyChangeListener("position",this);
    }

    public Line(HasPosition obj1, HasPosition obj2) {
        super();
        posObj1 = obj1;
        posObj2 = obj2;
        setDrawTo(posObj2.getPosition());
        setPosition(posObj1.getPosition());
        ((TElement)obj1).addPropertyChangeListener("position",this);
        ((TElement)obj2).addPropertyChangeListener("position",this);
    }

    public Vector3d getDrawTo() {
        return drawTo;
    }

    public void setDrawTo(Vector3d pt) {
        if (drawTo != pt) {
            drawTo = pt;
        }
        renderFlags |= GEOMETRY_CHANGE;
    }


    protected TNode3D makeNode() {
        LineNode node = new LineNode();
        ((HasFromTo) node).setFromTo(position, drawTo);
        node.setColor(mColor);
        return node;
    }

    public void render() {
       
        if (mNode != null) {
            if (((renderFlags & POSITION_CHANGE) == POSITION_CHANGE) 
                || ((renderFlags & GEOMETRY_CHANGE) == GEOMETRY_CHANGE)){
   
                ((HasFromTo) mNode).setFromTo(position, drawTo);
                renderFlags ^= (GEOMETRY_CHANGE | POSITION_CHANGE);
            }
        }
        super.render();
    }
    
    public void propertyChange(PropertyChangeEvent pce){
        if((posObj1 != null) &&( pce.getSource() == posObj1)){
            setPosition(posObj1.getPosition());
        }
        else if((posObj2 != null) &&( pce.getSource() == posObj2)){
            setDrawTo(posObj2.getPosition());
        }
        else{
            super.propertyChange(pce);
        }
    }
        

}
