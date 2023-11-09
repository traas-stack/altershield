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

import com.alipay.opscloud.api.change.exe.node.enums.ExeNodeStateEnum;
import com.alipay.opscloud.change.exe.service.execute.statemachine.ExeNodeStateMachine;
import com.alipay.opscloud.change.exe.service.execute.statemachine.ExeNodeStateMachineManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * The type Exe node state machine manager.
 *
 * @author yuanji
 * @version : ExeNodeStateMachineManagerImpl.java, v 0.1 2022年10月21日 11:06 yuanji Exp $
 */
@Component
public class ExeNodeStateMachineManagerImpl implements ExeNodeStateMachineManager, ApplicationContextAware {

    /**
     * hold application context
     */
    private  ApplicationContext applicationContext;

    @Override
    public ExeNodeStateMachine getExeNodeStateMachine(ExeNodeStateEnum exeNodeState) {
        Assert.notNull(exeNodeState, "exNodeState is null");
        Collection<ExeNodeStateMachine> exeNodeStateMachineList = applicationContext.getBeansOfType(ExeNodeStateMachine.class).values();
        ExeNodeStateMachine result = null;
        for(ExeNodeStateMachine exeNodeStateMachine : exeNodeStateMachineList)
        {
            if(exeNodeStateMachine.getExeState() == exeNodeState)
            {
                result = exeNodeStateMachine;
                break;
            }
        }
        Assert.notNull(result, "exeNodeStateMachine is null by state:" + exeNodeState);
        return result;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext =  applicationContext;
    }



}