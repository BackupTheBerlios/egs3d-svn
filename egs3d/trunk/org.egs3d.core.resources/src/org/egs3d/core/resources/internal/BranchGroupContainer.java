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

package org.egs3d.core.resources.internal;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.media.j3d.BranchGroup;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.egs3d.core.resources.IBranchGroupContainer;
import org.egs3d.core.resources.IScene;


/**
 * Implémentation de l'interface {@link IBranchGroupContainer}.
 * 
 * @author romale
 */
public class BranchGroupContainer extends AbstractSceneObject implements
        IBranchGroupContainer {
    private final List<BranchGroup> branchGroups = new ArrayList<BranchGroup>(1);


    public BranchGroupContainer(final IScene scene) {
        super(scene);
    }


    public Iterator<BranchGroup> iterator() {
        return Collections.unmodifiableList(branchGroups).iterator();
    }


    public void add(BranchGroup branchGroup) {
        branchGroups.add(branchGroup);
        refreshProject();
    }


    public void remove(BranchGroup branchGroup) {
        branchGroups.remove(branchGroup);
        refreshProject();
    }


    public int getSize() {
        return branchGroups.size();
    }


    public BranchGroup getBranchGroup(int i) {
        return branchGroups.get(i);
    }


    public BranchGroup[] toArray() {
        return branchGroups.toArray(new BranchGroup[branchGroups.size()]);
    }


    public void refresh() {
        final IProject project = getScene().getProject();
        if (project == null) {
            return;
        }
        try {
            getScene().getFile().touch(null);
        } catch (CoreException e) {
        }
    }
}
