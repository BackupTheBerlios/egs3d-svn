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

package org.egs3d.ui.wizards;


import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.egs3d.core.StringUtils;
import org.egs3d.ui.internal.Messages;


/**
 * Assistant de création de projet.
 * 
 * @author romale
 */
public class NewProjectWizard extends Wizard {
    private String projectName;


    public NewProjectWizard() {
        super();
        setWindowTitle(Messages.NewProjectWizard_windowTitle);
    }


    public String getProjectName() {
        return projectName;
    }


    @Override
    public void addPages() {
        addPage(new FirstPage());
    }


    @Override
    public boolean performFinish() {
        return true;
    }


    private class FirstPage extends WizardPage {
        public FirstPage() {
            super("firstPage"); //$NON-NLS-1$
            setTitle(Messages.NewProjectWizard_title);
            setDescription(Messages.NewProjectWizard_description);
        }


        public void createControl(Composite parent) {
            final Composite comp = new Composite(parent, SWT.NONE);
            comp.setLayout(new GridLayout(2, false));
            parent.setLayout(new FillLayout());

            final Label nameLabel = new Label(comp, SWT.NONE);
            nameLabel.setText(Messages.NewProjectWizard_projectName);

            final Text nameField = new Text(comp, SWT.BORDER);
            GridData gd = new GridData(GridData.FILL_HORIZONTAL);
            nameField.setLayoutData(gd);

            // TODO ajouter le champ pour le chemin vers le dossier du projet

            nameField.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    projectName = nameField.getText();

                    // on ne peut pas quitter la page si le nom du projet n'est
                    // pas renseigné
                    setPageComplete(!StringUtils.isBlank(projectName));

                    // TODO vérifier qu'il n'y a aucun autre projet avec un nom
                    // similaire
                }
            });
            setPageComplete(false);

            // affection du composant graphique à la page de l'assistant
            // (indispensable sinon une exception est levée !)
            setControl(comp);
        }
    }
}
