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

package org.egs3d.codegen.internal;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.media.j3d.Geometry;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.egs3d.codegen.ICodeGenerator;
import org.egs3d.core.StringUtils;
import org.egs3d.core.resources.IScene;


/**
 * Implémentation de l'interface {@link ICodeGenerator}.
 * 
 * @author romale
 */
public class CodeGenerator implements ICodeGenerator {
    private static final Set<Class<?>> UNSUPPORTED_CLASSES;
    static {
        UNSUPPORTED_CLASSES = new HashSet<Class<?>>();
        UNSUPPORTED_CLASSES.add(Transform3D.class);
    }
    private final Log log = LogFactory.getLog(getClass());


    public CharSequence generate(Object obj) {
        try {
            final CodeGeneratorHandler handler = new CodeGeneratorHandler();
            handler.generate(obj);
            return handler.getCode();
        } catch (Exception e) {
            log.error("Erreur durant la génération de code", e);
            return null;
        }
    }


    public boolean isSupported(Object obj) {
        if (obj == null) {
            return false;
        }

        final Class<?> objClass = obj.getClass();
        for (final Class<?> clazz : UNSUPPORTED_CLASSES) {
            if (!clazz.isAssignableFrom(objClass)) {
                return true;
            }
        }
        return false;
    }


    private class CodeGeneratorHandler {
        private final TextBuffer textBuffer = new TextBuffer();
        private final Map<Class<?>, Integer> nameMap = new HashMap<Class<?>, Integer>();


        public CharSequence getCode() {
            return textBuffer.getBuffer();
        }


        public String generate(Object obj) throws Exception {
            if (obj instanceof IScene) {
                return handleScene((IScene) obj);
            } else if (obj instanceof Group) {
                return handleGroup((Group) obj);
            } else if (obj instanceof Shape3D) {
                return handleShape3D((Shape3D) obj);
            }

            return newJavaBean(obj.getClass(), obj);
        }


        private String createNewName(Class<?> clazz) {
            Integer index = nameMap.get(clazz);
            final String suffix;
            if (index == null) {
                suffix = StringUtils.EMPTY_STRING;
                index = 0;
            } else {
                suffix = String.valueOf(index + 1);
            }
            nameMap.put(clazz, index + 1);

            final StringBuilder buf = new StringBuilder(clazz.getSimpleName());
            buf.setCharAt(0, Character.toLowerCase(buf.charAt(0)));
            return buf.append(suffix).toString();
        }


        private String handleScene(IScene scene) throws Exception {
            final String name = "MyScene";

            final DateFormat dateFormat = DateFormat.getDateInstance();
            textBuffer.newLine("// Généré par EGS3D le " + dateFormat.format(new Date()));
            textBuffer.newLine();
            textBuffer.newLine("import javax.media.j3d.*;");
            textBuffer.newLine("import javax.vecmath.*;");
            textBuffer.newLine("import com.sun.j3d.utils.geometry.*;");
            textBuffer.newLine("import com.sun.j3d.utils.universe.*;");
            textBuffer.newLine();
            textBuffer.newLine("public class " + name + " {");
            textBuffer.addIndentLevel();
            textBuffer.newLine("public BranchGroup createBranchGraph() {");
            textBuffer.addIndentLevel();

            final String rootName = handleGroup(scene.getBranchGroupContainer()
                    .getBranchGraph());
            textBuffer.newLine("return " + rootName + ";");

            textBuffer.removeIndentLevel();
            textBuffer.newLine("}");
            textBuffer.removeIndentLevel();
            textBuffer.newLine("}");

            return name;
        }


        private String handleGroup(Group group) throws Exception {
            final String name = newJavaBean(group.getClass());
            textBuffer.newLine();

            final int numChildren = group.numChildren();
            if (numChildren == 0) {
                textBuffer.newLine();
            } else {
                for (int i = 0; i < numChildren; ++i) {
                    final Object child = group.getChild(i);
                    final String childName = generate(child);
                    if (childName == null) {
                        continue;
                    }
                    textBuffer.newLine(name + ".addChild(" + childName + ");");
                    textBuffer.newLine();
                }
            }
            return name;
        }


        private String handleShape3D(Shape3D shape) throws Exception {
            final String name = newJavaBean(shape.getClass(), shape);
            textBuffer.newLine("// TODO gérer la géométrie de l'objet");
            return name;
        }


        private String newJavaBean(Class clazz) throws Exception {
            final String beanClassName = clazz.getSimpleName();
            final String beanName = createNewName(clazz);
            textBuffer.newLine(beanClassName + ' ' + beanName + " = new " + beanClassName
                    + "();");
            return beanName;
        }


        private String newJavaBean(Class clazz, Object obj) throws Exception {
            final String name = newJavaBean(clazz);

            for (final Method method : clazz.getMethods()) {
                method.setAccessible(true);

                final int methodMod = method.getModifiers();
                if (!method.getReturnType().equals(Void.TYPE)
                        || !Modifier.isPublic(methodMod) || Modifier.isStatic(methodMod)
                        || method.getParameterTypes().length != 1) {
                    continue;
                }

                final Class<?> propClass = method.getParameterTypes()[0];

                // types d'objet non gérés pour le moment
                if (Geometry.class.isAssignableFrom(propClass)) {
                    continue;
                }

                final String methodName = method.getName();
                if (methodName.length() > 3 && methodName.startsWith("set")) {
                    final String prop = methodName.substring(3);

                    final Method getter;
                    try {
                        getter = clazz.getMethod("get" + prop);
                    } catch (NoSuchMethodException e) {
                        continue;
                    }

                    getter.setAccessible(true);
                    final int getterMod = getter.getModifiers();
                    if (getter.getParameterTypes().length != 0
                            || !Modifier.isPublic(getterMod)
                            || Modifier.isStatic(getterMod)
                            || !propClass.equals(getter.getReturnType())) {
                        continue;
                    }

                    Object propValue = getter.invoke(obj);
                    if (propValue != null) {
                        if (propClass.isPrimitive() || String.class.equals(propClass)) {
                            propValue = formatPrimitiveValue(propValue);
                            textBuffer.newLine(name + ".set" + prop + "(" + propValue
                                    + ");");
                        } else {
                            final String propName = generate(propValue);
                            textBuffer.newLine(name + ".set" + prop + "(" + propName
                                    + ");");
                        }
                    }
                }
            }

            return name;
        }
    }


    private String formatPrimitiveValue(Object value) {
        final Class<?> clazz = value.getClass();

        log.debug("Formatage de la primitive de type " + clazz.getName() + " : " + value);

        if (Float.class.isAssignableFrom(clazz)) {
            final float fValue = ((Float) value).floatValue();
            if (Float.NEGATIVE_INFINITY == fValue) {
                return "Float.NEGATIVE_INFINITY";
            }
            if (Float.POSITIVE_INFINITY == fValue) {
                return "Float.POSITIVE_INFINITY";
            }

            // cas particulier : un float a la même
            // représentation String qu'un double, mais il faut
            // faire la distinction entre les deux
            return value + "F";
        }
        if (Double.class.isAssignableFrom(clazz)) {
            final double dValue = ((Double) value).doubleValue();
            if (Double.NEGATIVE_INFINITY == dValue) {
                return "Double.NEGATIVE_INFINITY";
            }
            if (Double.POSITIVE_INFINITY == dValue) {
                return "Double.POSITIVE_INFINITY";
            }
        }
        if (String.class.equals(clazz)) {
            return '"' + value.toString() + '"';
        }
        if (Character.class.equals(clazz)) {
            return "'" + value.toString() + "'";
        }

        return value.toString();
    }
}
