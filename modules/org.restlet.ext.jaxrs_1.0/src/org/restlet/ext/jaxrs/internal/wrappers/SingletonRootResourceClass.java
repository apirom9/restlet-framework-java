/*
 * Copyright 2005-2008 Noelios Consulting.
 * 
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the "License"). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.txt See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL HEADER in each file and
 * include the License file at http://www.opensource.org/licenses/cddl1.txt If
 * applicable, add the following below this CDDL HEADER, with the fields
 * enclosed by brackets "[]" replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 */
package org.restlet.ext.jaxrs.internal.wrappers;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;

import org.restlet.ext.jaxrs.InstantiateException;
import org.restlet.ext.jaxrs.ObjectFactory;
import org.restlet.ext.jaxrs.internal.core.ThreadLocalizedContext;
import org.restlet.ext.jaxrs.internal.exceptions.IllegalBeanSetterTypeException;
import org.restlet.ext.jaxrs.internal.exceptions.IllegalConstrParamTypeException;
import org.restlet.ext.jaxrs.internal.exceptions.IllegalFieldTypeException;
import org.restlet.ext.jaxrs.internal.exceptions.IllegalPathOnClassException;
import org.restlet.ext.jaxrs.internal.exceptions.IllegalPathParamTypeException;
import org.restlet.ext.jaxrs.internal.exceptions.InjectException;
import org.restlet.ext.jaxrs.internal.exceptions.MissingAnnotationException;
import org.restlet.ext.jaxrs.internal.exceptions.MissingConstructorException;
import org.restlet.ext.jaxrs.internal.wrappers.provider.ExtensionBackwardMapping;
import org.restlet.ext.jaxrs.internal.wrappers.provider.JaxRsProviders;

/**
 * @author Stephan Koops
 */
public class SingletonRootResourceClass extends RootResourceClass {

    private final ResourceObject rootResObject;

    /**
     * @param rootResourceObject
     * @param tlContext
     * @param jaxRsProviders
     * @param extensionBackwardMapping
     * @param logger
     * @throws IllegalArgumentException
     * @throws MissingAnnotationException
     * @throws IllegalPathOnClassException
     * @throws MissingConstructorException
     * @throws IllegalConstrParamTypeException
     * @throws IllegalFieldTypeException
     * @throws IllegalBeanSetterTypeException
     * @throws IllegalPathParamTypeException
     * @throws InvocationTargetException
     *                 if an exception occurs while the injecting of
     *                 dependencies.
     * @throws InjectException
     */
    SingletonRootResourceClass(Object rootResourceObject,
            ThreadLocalizedContext tlContext, JaxRsProviders jaxRsProviders,
            ExtensionBackwardMapping extensionBackwardMapping, Logger logger)
            throws IllegalArgumentException, MissingAnnotationException,
            IllegalPathOnClassException, MissingConstructorException,
            IllegalConstrParamTypeException, IllegalFieldTypeException,
            IllegalBeanSetterTypeException, IllegalPathParamTypeException,
            InvocationTargetException, InjectException {
        super(rootResourceObject.getClass(), tlContext, jaxRsProviders,
                extensionBackwardMapping, logger);
        this.injectHelper.injectInto(rootResourceObject, true);
        this.rootResObject = new ResourceObject(rootResourceObject, this);
    }

    /**
     * Creates an or gets the instance of this root resource class.
     * 
     * @param objectFactory
     *                object responsible for instantiating the root resource
     *                class. Optional, thus can be null.
     * @return the wrapped root resource class instance
     * @throws InvocationTargetException
     * @throws InstantiateException
     * @throws MissingAnnotationException
     * @throws WebApplicationException
     */
    @Override
    public ResourceObject getInstance(ObjectFactory objectFactory) {
        return this.rootResObject;
    }
}