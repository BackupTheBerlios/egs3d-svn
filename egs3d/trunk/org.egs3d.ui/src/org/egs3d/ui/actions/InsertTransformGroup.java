/*
 * Créé le 18 mai 2006
 *
 */

package org.egs3d.ui.actions;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
//import javax.media.j3d.Alpha;
//import javax.media.j3d.BoundingSphere;
//import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Node;

//import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.eclipse.ui.IWorkbenchWindow;


/**
 * Ajoute d'un TransformGroup
 * 
 * @author brachet
 */
public class InsertTransformGroup extends AbstractInsertAction {
	@Override
	protected Node createNode(IWorkbenchWindow window) {
		// TODO choisir le type de transformation
		Transform3D transform3D = new Transform3D();
		transform3D.setTranslation(new Vector3d(-40d,0d,0d));
		
		TransformGroup tgrp = new TransformGroup(transform3D);
		
//		Alpha spinAlpha = new Alpha(-1,4000);
//		RotationInterpolator spinner = new RotationInterpolator(spinAlpha,tgrp);
//		spinner.setSchedulingBounds(new BoundingSphere(new Point3d(),200.0));
//		tgrp.addChild(spinner);
		
		return tgrp;
	}
}
