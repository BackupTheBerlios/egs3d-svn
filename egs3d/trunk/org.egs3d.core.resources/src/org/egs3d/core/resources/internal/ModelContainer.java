/**
 * $Id: ISceneGraphWriter.java 52 2006-03-25 01:10:30Z romale $
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


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.IModelContainer;
import org.egs3d.core.resources.IScene;

import com.sun.j3d.loaders.Loader;


/**
 * Implémentation de l'interface {@link IModelContainer}.
 * 
 * @author romale
 */
public class ModelContainer extends AbstractSceneObject implements IModelContainer {
    private final List<IModel> models = new ArrayList<IModel>(1);


    public ModelContainer(final IScene scene) {
        super(scene);
    }


    public void add(IModel model) {
        models.add(model);
        refreshProject();
    }


    public void remove(IModel model) {
        models.remove(model);
        refreshProject();
    }


    public int getSize() {
        return models.size();
    }


    public IModel getModel(int i) {
        return models.get(i);
    }


    public Iterator<IModel> iterator() {
        return models.iterator();
    }


    public IModel[] toArray() {
        return models.toArray(new IModel[models.size()]);
    }


    public IModel create(String name, Class<? extends Loader> loaderClass,
            InputStream input) {
        return new Model(name, loaderClass, input);
    }
}
