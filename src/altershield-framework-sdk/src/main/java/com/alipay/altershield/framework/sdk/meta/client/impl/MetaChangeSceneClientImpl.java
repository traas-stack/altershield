/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alipay.altershield.framework.sdk.meta.client.impl;

import com.alipay.altershield.framework.core.change.facade.MetaChangeSceneFacade;
import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import com.alipay.altershield.framework.sdk.client.AbstractAlterShieldClient;
import com.alipay.altershield.framework.sdk.meta.client.MetaChangeSceneClient;
import com.alipay.altershield.framework.core.change.facade.request.CreateMetaChangeSceneRequest;
import com.alipay.altershield.framework.core.change.facade.result.CreateMetaChangeSceneResult;
import lombok.Setter;

/**
 * The type Ops cloud meta change scene client.
 *
 * @author yuanji
 * @version : OpsCloudMetaChangeSceneClientImpl.java, v 0.1 2022年06月06日 2:09 下午 yuanji Exp $
 */
@Setter
public class MetaChangeSceneClientImpl extends AbstractAlterShieldClient
        implements MetaChangeSceneClient {

    private MetaChangeSceneFacade metaChangeSceneFacade;

    @Override
    public AlterShieldResult<CreateMetaChangeSceneResult> create(
            CreateMetaChangeSceneRequest request) {

        return doProcess(request, () -> metaChangeSceneFacade.create(request));

    }

}
