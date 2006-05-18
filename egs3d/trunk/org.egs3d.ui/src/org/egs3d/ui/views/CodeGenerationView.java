/**
 * $Id: DiagnosticView.java 86 2006-04-01 20:56:48Z romale $
 *
 * EGS3D
 * http://egs3d.berlios.de
 * Copyright (c) 2006 EGS3D team
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.egs3d.ui.views;


import javax.media.j3d.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.codegen.CodeGenPlugin;
import org.egs3d.codegen.ICodeGenerator;
import org.egs3d.core.StringUtils;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ISceneObject;
import org.egs3d.ui.internal.UIPlugin;


/**
 * Affichage du code Java généré par EGS3D.
 * 
 * @author brachet
 * @author romale
 */
public class CodeGenerationView extends ViewPart implements ISelectionListener {
    public static final String VIEW_ID = "org.egs3d.ui.views.codeGeneration";
    private final Log log = LogFactory.getLog(getClass());
    private TextViewer textViewer;
    private Font font;


    @Override
    public void createPartControl(Composite parent) {
        textViewer = new TextViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        parent.setLayout(new FillLayout());

        font = new Font(Display.getCurrent(), "Courier New", 10, 0);
        textViewer.getTextWidget().setFont(font);

        textViewer.getTextWidget().setTabs(4);
        textViewer.setEditable(false);
        textViewer.setDocument(new Document(StringUtils.EMPTY_STRING));

        getSite().getWorkbenchWindow().getSelectionService().addPostSelectionListener(
                SceneExplorerView.VIEW_ID, this);
    }


    @Override
    public void setFocus() {
        textViewer.getControl().setFocus();
    }


    @Override
    public void dispose() {
        if (textViewer != null) {
            getSite().getWorkbenchWindow().getSelectionService()
                    .removePostSelectionListener(SceneExplorerView.VIEW_ID, this);

            textViewer.getControl().dispose();
            textViewer = null;
        }
        if (font != null) {
            font.dispose();
            font = null;
        }
        super.dispose();
    }


    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        final IStructuredSelection sel = (IStructuredSelection) selection;
        if (sel.isEmpty() || sel.getFirstElement() == null) {
            return;
        }

        final Object selectedObj = sel.getFirstElement();
        if (selectedObj instanceof Node) {
            generateCode(((Node) selectedObj).cloneTree(true));
        } else if (selectedObj instanceof IFile) {
            final IScene scene = UIPlugin.getDefault().getSelectedScene();
            if (scene != null) {
                generateCode(scene);
            }
        } else if (selectedObj instanceof ISceneObject) {
            final IScene scene = ((ISceneObject) selectedObj).getScene();
            generateCode(scene);
        }
    }


    private void generateCode(Object object) {
        log.info("Génération du code pour l'élément : " + object);

        final ICodeGenerator codeGen = CodeGenPlugin.createCodeGenerator();
        final CharSequence code;
        if (codeGen.isSupported(object)) {
            code = codeGen.generate(object);
        } else {
            code = null;
        }
        textViewer.getDocument().set(
                code == null ? StringUtils.EMPTY_STRING : code.toString());
    }
}
