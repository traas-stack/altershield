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
/**
 * Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.change.exe.service.execute.statemachine.impl;

import com.alipay.altershield.change.meta.model.MetaChangeSceneEntity;
import com.alipay.altershield.framework.core.change.facade.request.ChangeFinishNotifyRequest;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeCheckInfo;
import com.alipay.altershield.shared.change.exe.node.entity.ExeNodeEntity;
import com.alipay.altershield.shared.change.exe.node.enums.ExeNodeStateEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 后置完成。可以重新提交后置检查
 *
 * @author yuanji
 * @version : PostAopFinishExeNodeStateMachine.java, v 0.1 2022年10月21日 11:22 yuanji Exp $
 */
@Component
public class PostAopFinishExeNodeStateMachine extends CheckFinishNodeStateMachine{

    /**
     * Instantiates a new Post aop finish exe node state machine.
     */
    public PostAopFinishExeNodeStateMachine() {
        super.exeStateNode = ExeNodeStateEnum.POST_AOP_FINISH;
    }


    @Override
    public void submitNodePostStartCheck(ExeNodeEntity exeNodeEntity, ChangeFinishNotifyRequest request, MetaChangeSceneEntity metaChangeSceneEntity) {
        exeNodeEntity.setFinishTime(request.getFinishTime());
        exeNodeEntity.setMsg(request.getMsg());
        if (StringUtils.isNotBlank(request.getServiceResult())) {
            exeNodeEntity.getRstRef().write(request.getServiceResult());
        }
        exeNodeEntity.tagSpiSuccess(request.isSuccess());
        exeNodeEntity.setStatus(ExeNodeStateEnum.POST_AOP_SUBMIT);
        ExeNodeCheckInfo exeNodeCheckInfo = exeNodeEntity.getExeNodeCheckInfo();
        exeNodeCheckInfo.setPostCheckStartTime(System.currentTimeMillis());
        exeNodeEntity.setExeNodeCheckInfo(exeNodeCheckInfo);
        exeChangeNodeRepository.update(exeNodeEntity);
    }

    @Override
    protected boolean checkFinishSuccess() {
        return true;
    }
}