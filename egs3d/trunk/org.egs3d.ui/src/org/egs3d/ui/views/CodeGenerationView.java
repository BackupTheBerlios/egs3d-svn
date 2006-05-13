/*
 * Créé le 8 mai 2006
 *
 * TODO Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
 */
package org.egs3d.ui.views;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


/**
 * Affichage du code java généré par EGS3D
 * 
 * @author brachet
 */
public class CodeGenerationView extends ViewPart {
	private TextViewer textViewer;
    
	@Override
	public void createPartControl(Composite parent) {
		textViewer = new TextViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		parent.setLayout(new FillLayout());

		textViewer.setEditable(false);
		textViewer.setDocument(new Document("Hello world!"));
	}

	@Override
	public void setFocus() {
		textViewer.getControl().setFocus();
	}

	@Override
	public void dispose() {
		if(textViewer != null) {
			textViewer.getControl().dispose();
			textViewer = null;
		}
		super.dispose();
	}
}
