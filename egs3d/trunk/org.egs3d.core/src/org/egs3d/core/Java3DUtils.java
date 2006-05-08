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

package org.egs3d.core;


import javax.media.j3d.Bounds;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;


/**
 * Méthodes utilitaires pour Java3D.
 * 
 * @author romale
 */
public final class Java3DUtils {
    private static final Logger log = LoggerFactory
            .getLogger(Java3DUtils.class);


    private Java3DUtils() {
    }


    /**
     * Retourne <code>true</code> si Java3D est installé sur le système.
     */
    public static boolean isJava3DInstalled() {
        final String[] classNames = { "javax.media.j3d.Bounds",
                "javax.vecmath.Vector3f",
                "com.sun.j3d.utils.universe.SimpleUniverse", };
        try {
            for (final String className : classNames) {
                // tentative de chargement de la classe
                Class.forName(className);
            }
        } catch (Throwable e) {
            // si une erreur est levée, Java3D n'est pas installé
            log.error("Java3D n'est pas installé", e);
            return false;
        }
        return true;
    }


    /**
     * Transforme un noeud en changeant ses proportions.
     */
    public static TransformGroup scale(Node node, double scaleFactor) {
        final Transform3D scale3D = new Transform3D();
        scale3D.setScale(scaleFactor);
        final TransformGroup scaleTG = new TransformGroup(scale3D);
        scaleTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        scaleTG.addChild(node);

        return scaleTG;
    }


    /**
     * Ajoute la possibilité à un noeud de modifier la vue à l'aide de la souris
     * (rotation, translation, zoom).
     */
    public static TransformGroup addMouseBehavior(Node node, Bounds bounds) {
        final TransformGroup rotateTG = new TransformGroup();
        rotateTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        final MouseRotate rotator = new MouseRotate(rotateTG);
        rotator.setSchedulingBounds(bounds);
        rotateTG.addChild(rotator);
        rotateTG.addChild(node);

        final TransformGroup translateTG = new TransformGroup();
        translateTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        final MouseTranslate translator = new MouseTranslate(translateTG);
        translator.setSchedulingBounds(bounds);
        translateTG.addChild(translator);
        translateTG.addChild(rotateTG);

        final TransformGroup zoomTG = new TransformGroup();
        zoomTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        final MouseWheelZoom zoomer = new MouseWheelZoom(zoomTG);
        zoomer.setSchedulingBounds(bounds);
        zoomTG.addChild(zoomer);
        zoomTG.addChild(translateTG);

        return zoomTG;
    }


    /**
     * Retourne le noeud racine.
     */
    public static Node getRootNode(Node node) {
        if (node == null) {
            return null;
        }
        final Node parent = node.getParent();
        if (parent == null) {
            // nous venons de trouver le noeud racine
            // (celui qui n'a pas de père)
            return node;
        }
        return getRootNode(parent);
    }
}
