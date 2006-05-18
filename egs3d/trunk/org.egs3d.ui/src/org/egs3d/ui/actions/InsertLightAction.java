/*
 * Cr�� le 6 mai 2006
 *
 * TODO Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre - Pr�f�rences - Java - Style de code - Mod�les de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Node;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import org.eclipse.ui.IWorkbenchWindow;


/**
 * Ajoute une lumi�re
 * 
 * @author brachet
 */
public class InsertLightAction extends AbstractInsertAction {
	@Override
	protected Node createNode(IWorkbenchWindow window) {
        // TODO choisir le type de lumi�re
        final Vector3f lightDirection = new Vector3f(1.0f, 1.0f, 1.0f);
        final DirectionalLight light = new DirectionalLight(new Color3f(1.0f, 1.0f,
                0.9f), lightDirection);
        light.setInfluencingBounds(new BoundingSphere(new Point3d(), Double.POSITIVE_INFINITY));

        return light;
	}
}
