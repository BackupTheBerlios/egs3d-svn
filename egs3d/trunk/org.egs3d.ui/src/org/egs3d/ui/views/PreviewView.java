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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.ui.internal.Messages;


/**
 * Vue d'aperçu.
 * 
 * @author romale
 */
public class PreviewView extends ViewPart implements ISelectionListener {
    private final Log log = LogFactory.getLog(getClass());
    private Label noPreviewLabel;
    private PageBook pageBook;


    @Override
    public void createPartControl(Composite parent) {
        pageBook = new PageBook(parent, SWT.NONE);
        pageBook.setLayout(new FillLayout());
        parent.setLayout(new FillLayout());

        noPreviewLabel = new Label(pageBook, SWT.NONE);
        noPreviewLabel.setText(Messages.PreviewView_noPreview);

        // l'élément sélectionné dans l'explorateur de scène est récupéré à
        // travers l'interface ISelectionListener
        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(
                SceneExplorerView.VIEW_ID, this);

        showNoPreview();
    }


    @Override
    public void setFocus() {
        pageBook.setFocus();
    }


    @Override
    public void dispose() {
        if (pageBook != null) {
            getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(
                    SceneExplorerView.VIEW_ID, this);

            pageBook.dispose();
            pageBook = null;
        }
        super.dispose();
    }


    private void showNoPreview() {
        pageBook.showPage(noPreviewLabel);
    }


    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        if (this == part || selection.isEmpty()
                || !(selection instanceof IStructuredSelection)) {
            showNoPreview();
            return;
        }

        final IStructuredSelection isl = (IStructuredSelection) selection;
        final Object selectedObj = isl.getFirstElement();
        // TODO implémentation de l'aperçu
        log.debug("Elément sélectionné : " + selectedObj); //$NON-NLS-1$

        // pas d'aperçu pour l'élément sélectionné
        showNoPreview();
    }
}
