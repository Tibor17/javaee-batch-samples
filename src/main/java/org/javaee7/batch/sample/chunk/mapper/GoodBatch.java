/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 * Portions Copyright 2018 Tibor Digana
 */
package org.javaee7.batch.sample.chunk.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;

import java.io.Serializable;
import java.util.Arrays;

import static javax.batch.runtime.BatchStatus.COMPLETED;

@Named
public class GoodBatch extends AbstractBatchlet implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(GoodBatch.class);

    @Inject
    private JobContext jobContext;

    @Inject
    private StepContext stepContext;

    @Inject
    UserTransaction ut;

    @Override
    public String process() throws Exception {
        System.out.println("getPersistentUserData: " + stepContext.getPersistentUserData());
        stepContext.setPersistentUserData("persistent user data [good]");
        System.out.println("good batch. Transaction status " + ut.getStatus());
        System.out.println("transient object " + jobContext.getTransientUserData());
        LOG.info("good batch ({}:{}), ({}:{}), ({}:{}), {}",
                jobContext.getJobName(),
                jobContext.getInstanceId(),
                jobContext.getBatchStatus(),
                jobContext.getExitStatus(),
                stepContext.getBatchStatus(),
                stepContext.getExitStatus(),
                Arrays.toString(stepContext.getMetrics()));

        return COMPLETED.name();
    }
}
