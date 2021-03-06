/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at

 *  http://www.apache.org/licenses/LICENSE-2.0

 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.stratos.autoscaler.service.jcloud;

import java.util.Map;
import java.util.Properties;

import org.apache.stratos.autoscaler.service.util.IaasProvider;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.enterprise.config.EnterpriseConfigurationModule;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.sshj.config.SshjSshClientModule;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

/**
 * This class is responsible for creating a JClouds specific ComputeService object instances.
 */
public class ComputeServiceBuilder {
    
    public static ComputeService buildComputeService(IaasProvider iaas) {

        Properties properties = new Properties();

        // load properties
        for (Map.Entry<String, String> entry : iaas.getProperties().entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        // set modules
        Iterable<Module> modules =
            ImmutableSet.<Module> of(new SshjSshClientModule(), new SLF4JLoggingModule(),
                                     new EnterpriseConfigurationModule());

        // build context
        ContextBuilder builder =
            ContextBuilder.newBuilder(iaas.getProvider())
                          .credentials(iaas.getIdentity(), iaas.getCredential()).modules(modules)
                          .overrides(properties);

        // return the compute service object
        return builder.buildView(ComputeServiceContext.class).getComputeService();
    }
    
    
}
