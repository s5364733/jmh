/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jmh.generators.annotations;

import org.openjdk.jmh.generators.source.GeneratorDestination;
import org.openjdk.jmh.generators.source.MetadataInfo;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;

public class APGeneratorDestinaton implements GeneratorDestination {

    private final RoundEnvironment roundEnv;
    private final ProcessingEnvironment processingEnv;

    public APGeneratorDestinaton(RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        this.roundEnv = roundEnv;
        this.processingEnv = processingEnv;
    }

    @Override
    public Writer newResource(String resourcePath) throws IOException {
        return processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", resourcePath).openWriter();
    }

    @Override
    public Writer newClass(String className) throws IOException {
        return processingEnv.getFiler().createSourceFile(className).openWriter();
    }

    @Override
    public void printError(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    @Override
    public void printError(String message, MetadataInfo element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, ((APMetadataInfo)element).getElement());
    }

    @Override
    public void printError(String message, Throwable throwable) {
        printError(message + " " + throwable);
    }

}
