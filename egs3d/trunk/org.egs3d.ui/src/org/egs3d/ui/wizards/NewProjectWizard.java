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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
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
    private final Log log = LogFactory.getLog(getClass());
    private String projectName;
    private String projectPath;


    public NewProjectWizard() {
        super();
        setWindowTitle(Messages.NewProjectWizard_windowTitle);
    }


    public String getProjectName() {
        return projectName;
    }


    public String getProjectPath() {
        return projectPath;
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
            nameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));

            final Group pathGroup = new Group(comp, SWT.NONE);
            pathGroup.setText(Messages.NewProjectWizard_contents);
            pathGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    true, 2, 1));
            pathGroup.setLayout(new GridLayout(3, false));

            final Button createInWorkspaceOption = new Button(pathGroup,
                    SWT.RADIO);
            createInWorkspaceOption
                    .setText(Messages.NewProjectWizard_createProjectInWorkspace);
            // par défaut, le projet est créé dans l'espace de travail
            createInWorkspaceOption.setSelection(true);
            createInWorkspaceOption.setLayoutData(new GridData(SWT.FILL,
                    SWT.CENTER, true, false, 3, 1));

            final Button createElsewhereOption = new Button(pathGroup,
                    SWT.RADIO);
            createElsewhereOption
                    .setText(Messages.NewProjectWizard_createProjectElsewhere);
            createElsewhereOption.setLayoutData(new GridData(SWT.FILL,
                    SWT.CENTER, true, false, 3, 1));

            final Label pathLabel = new Label(pathGroup, SWT.NONE);
            pathLabel.setText(Messages.NewProjectWizard_path);
            pathLabel.setLayoutData(new GridData());

            final Text pathField = new Text(pathGroup, SWT.BORDER);
            pathField.setText(getWorkspacePath());
            pathField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
            // le champ n'est pas éditable : il faut utiliser le sélecteur de
            // dossier à l'aide du bouton "Parcourir..."
            pathField.setEditable(false);
            pathField.setEnabled(false);

            final Button browseDirButton = new Button(pathGroup, SWT.NONE);
            browseDirButton.setText(Messages.NewProjectWizard_browse);
            // bouton désactivé par défaut
            browseDirButton.setEnabled(false);
            browseDirButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                    false, false));

            nameField.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    checkNameField(nameField);
                }
            });
            createInWorkspaceOption
                    .addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            pathField.setEnabled(false);
                            browseDirButton.setEnabled(false);

                            // le dossier est réinitialisé
                            pathField.setText(getWorkspacePath());
                        }
                    });
            createElsewhereOption.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    pathField.setEnabled(true);
                    browseDirButton.setEnabled(true);

                    browseDirButton.setFocus();
                }
            });
            browseDirButton.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    final DirectoryDialog dialog = new DirectoryDialog(
                            getShell());
                    final String path = StringUtils.trimToNull(dialog.open());
                    if (path != null) {
                        // mise à jour du champ "Dossier"
                        pathField.setText(path);
                    }
                }
            });
            setPageComplete(false);

            // affection du composant graphique à la page de l'assistant
            // (indispensable sinon une exception est levée !)
            setControl(comp);
        }


        /**
         * Vérifie que le champ "Nom du projet" est renseigné et qu'il n'est pas
         * utilisé par un autre projet existant. Si la vérification échoue,
         * l'assistant est bloqué.
         * 
         * @param nameField champ "Nom du projet" à tester
         */
        private void checkNameField(Text nameField) {
            // on efface le message d'erreur précédent éventuel
            setErrorMessage(null);

            projectName = nameField.getText();

            // on ne peut pas quitter la page si le nom du projet n'est
            // pas renseigné
            setPageComplete(!StringUtils.isBlank(projectName));

            // on vérifie qu'il n'y a aucun autre projet avec un nom
            // similaire
            final IProject[] currentProjects = ResourcesPlugin.getWorkspace()
                    .getRoot().getProjects();
            for (final IProject project : currentProjects) {
                if (projectName.equals(project.getName())) {
                    final String msg = "Un projet existe avec le même nom : "
                            + projectName;
                    log.info(msg);
                    setErrorMessage(msg);

                    setPageComplete(false);
                }
            }
        }
    }


    private String getWorkspacePath() {
        return ResourcesPlugin.getWorkspace().getRoot().getLocation()
                .toOSString();
    }
}
