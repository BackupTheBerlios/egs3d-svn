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

package org.egs3d.ui.internal;


import org.eclipse.osgi.util.NLS;


public class Messages extends NLS {
    private static final String BUNDLE_NAME = "org.egs3d.ui.internal.messages"; //$NON-NLS-1$


    private Messages() {
    }

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }
    public static String Java3DTest_title;
    public static String Java3DTest_text;
    public static String DiagnosticView_name;
    public static String DiagnosticView_value;
    public static String NewProjectWizard_windowTitle;
    public static String NewProjectWizard_title;
    public static String NewProjectWizard_description;
    public static String NewProjectWizard_projectName;
    public static String Error_createProject;
    public static String NewProjectWizard_contents;
    public static String NewProjectWizard_createProjectInWorkspace;
    public static String NewProjectWizard_createProjectElsewhere;
    public static String NewProjectWizard_path;
    public static String NewProjectWizard_browse;
    public static String Error_openProject;
    public static String NewProjectWizard_chooseDirectory;
    public static String Error_projectNameExists;
    public static String PreviewView_noPreview;
}
