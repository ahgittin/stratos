/*
 *  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.stratos.tenant.activity.beans;

import org.wso2.carbon.utils.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Bean for paginated tenant information
 */
public class PaginatedTenantDataBean implements Pageable, Serializable {
    private TenantDataBean[] tenantInfoBeans;
    private int numberOfPages;

    public TenantDataBean[] getTenantInfoBeans() {
        return tenantInfoBeans;
    }

    public void setTenantInfoBeans(TenantDataBean[] tenantInfoBeans) {
        this.tenantInfoBeans = tenantInfoBeans;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public <T> void set(List<T> items) {
        this.tenantInfoBeans = items.toArray(new TenantDataBean[items.size()]);
    }
}
