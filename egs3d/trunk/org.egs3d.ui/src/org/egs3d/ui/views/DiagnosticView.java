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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.ui.actions.ShowJava3DTestDialogAction;
import org.egs3d.ui.internal.Messages;


/**
 * Diagnostique sur le système et les bibliothèques installées.
 * 
 * @author romale
 */
public class DiagnosticView extends ViewPart {
    public static final String VIEW_ID = "org.egs3d.ui.views.diagnostic"; //$NON-NLS-1$
    private final Log log = LogFactory.getLog(getClass());
    private TableViewer tableViewer;


    @Override
    public void createPartControl(Composite parent) {
        tableViewer = new TableViewer(parent);

        final Table table = tableViewer.getTable();
        final TableColumn titleCol = new TableColumn(table, SWT.LEFT);
        titleCol.setText(Messages.DiagnosticView_name);
        titleCol.setWidth(200);
        final TableColumn valueCol = new TableColumn(table, SWT.LEFT);
        valueCol.setText(Messages.DiagnosticView_value);
        valueCol.setWidth(50);
        table.setHeaderVisible(true);

        tableViewer.setLabelProvider(new PackageInfoLabelProvider());

        final Object[] data = new Object[] { new PackageInfo("java.lang.String"), //$NON-NLS-1$
                new PackageInfo("javax.media.j3d.BranchGroup"), }; //$NON-NLS-1$
        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.setInput(data);

        createActions();
    }


    /**
     * Crée les actions associées à la vue.
     */
    private void createActions() {
        final IActionBars actionBars = getViewSite().getActionBars();
        actionBars.getMenuManager().add(
                new ShowJava3DTestDialogAction(getSite().getWorkbenchWindow()
                        .getWorkbench()));
        actionBars.updateActionBars();
    }


    @Override
    public void setFocus() {
        tableViewer.getControl().setFocus();
    }


    @Override
    public void dispose() {
        if (tableViewer != null) {
            tableViewer.getControl().dispose();
            tableViewer = null;
        }
        super.dispose();
    }


    /**
     * Informations sur un paquetage.
     */
    private class PackageInfo {
        private String name;
        private String version;
        private String title;


        public PackageInfo(final String className) {
            try {
                final Package pkg = Class.forName(className).getPackage();
                name = pkg.getName();
                version = pkg.getSpecificationVersion();
                title = pkg.getSpecificationTitle();
            } catch (Exception e) {
                final String unknownValue = "?"; //$NON-NLS-1$
                name = version = title = unknownValue;

                log.error("Impossible de récupérer les informations sur la classe " //$NON-NLS-1$
                        + className, e);
            }
        }


        public String getTitle() {
            return title;
        }


        public String getVersion() {
            return version;
        }


        public String getName() {
            return name;
        }
    }


    /**
     * Définition des informations à afficher en fonction de la colonne de la
     * table.
     */
    private class PackageInfoLabelProvider extends LabelProvider implements
            ITableLabelProvider {
        public Image getColumnImage(Object element, int columnIndex) {
            // aucune image
            return null;
        }


        public String getColumnText(Object element, int columnIndex) {
            final PackageInfo info = (PackageInfo) element;

            switch (columnIndex) {
                case 0:
                    return info.getTitle();
                case 1:
                    return info.getVersion();
                default:
                    throw new IndexOutOfBoundsException();
            }
        }
    }
}
