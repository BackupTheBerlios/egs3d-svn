/*
 * Cr�� le 6 mai 2006
 *
 * TODO Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre - Pr�f�rences - Java - Style de code - Mod�les de code
 */
package org.egs3d.ui.actions;

import javax.media.j3d.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.egs3d.ui.wizards.InsertGeometryWizard;
import org.eclipse.jface.window.Window;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.ColorCube;


/**
 * Ouvre une fen�tre de dialogue pour l'insertion d'une nouvelle g�om�trie.
 * 
 * @author brachet
 */
public class InsertGeometryAction extends AbstractInsertAction {
    private final Log log = LogFactory.getLog(getClass());
    
	@Override
	protected Node createNode(IWorkbenchWindow window) {
        log.info("Ouverture de la fen�tre d'insertion de g�om�trie"); //$NON-NLS-1$
        
        final InsertGeometryWizard wizard = new InsertGeometryWizard();
        final WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
        dialog.setBlockOnOpen(true);

        if (Window.OK != dialog.open()) {
            return null;
        }

        final InsertGeometryWizard.GeometryType type = wizard.getGeometryType();
        final String name = wizard.getGeometryName();
        
        if (type.equals(InsertGeometryWizard.GeometryType.CUBE)) {
        	final ColorCube colorCube = new ColorCube(10f);
            colorCube.setName(name);
        	return colorCube;
        } else {
        	final Sphere sphere = new Sphere(10f);
            sphere.setName(name);
        	return sphere;
        }
	}
}
