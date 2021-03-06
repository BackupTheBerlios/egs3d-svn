/**
 * $Id$
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


import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.ui.util.SceneContentProvider;
import org.egs3d.ui.util.SceneLabelProvider;


/**
 * Explorateur de sc�ne 3D. L'impl�mentation de cette vue et des objets
 * {@link SceneContentProvider} et {@link SceneLabelProvider} est inspir�e de
 * l'article : <a
 * href="http://www.eclipse.org/articles/treeviewer-cg/TreeViewerArticle.htm">http://www.eclipse.org/articles/treeviewer-cg/TreeViewerArticle.htm</a>.
 * 
 * @author romale
 */
public class SceneExplorerView extends ViewPart {
    public static final String VIEW_ID = "org.egs3d.ui.views.sceneExplorer"; //$NON-NLS-1$
    private TreeViewer treeViewer;


    @Override
    public void createPartControl(Composite parent) {
        treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.BORDER);
        parent.setLayout(new FillLayout());

        // permet d'acc�l�rer l'organisation interne de l'arbre
        treeViewer.setUseHashlookup(true);

        // configuration
        treeViewer.setLabelProvider(new SceneLabelProvider());
        treeViewer.setContentProvider(new SceneContentProvider());

        // connexion avec l'espace de travail
        // (doit �tre fait APRES avoir affect� un ILabelProvider et un
        // IContentProvider au TreeViewer)
        treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());

        // enregistrement aupr�s de l'espace de travail de l'�l�ment s�lectionn�
        getSite().setSelectionProvider(treeViewer);

        // navigation simplifi�e dans l'arbre avec la gestion du double-clic
        treeViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                final IStructuredSelection sel = (IStructuredSelection) event
                        .getSelection();
                if (sel.isEmpty()) {
                    return;
                }

                final Object obj = sel.getFirstElement();
                treeViewer.setExpandedState(obj, !treeViewer
                        .getExpandedState(obj));
            }
        });
    }


    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }


    @Override
    public void dispose() {
        if (treeViewer != null) {
            getSite().setSelectionProvider(null);

            treeViewer.getControl().dispose();
            treeViewer = null;
        }
        super.dispose();
    }
}
