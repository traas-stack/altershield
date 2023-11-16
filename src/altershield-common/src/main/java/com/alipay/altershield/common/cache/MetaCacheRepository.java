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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.common.cache;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shuo.qius
 * @version Nov 11, 2018
 */
@Component("metaRepositoryCache")
public class MetaCacheRepository implements InitializingBean {
    protected static final Logger logger = Loggers.META_CACHE;

    /**
     * @see InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            try {
                for (; ; ) {
                    cacheGc();
                    Thread.sleep(AlterShieldConstant.META_CACHE_INTERVAL_MILLI);
                }
            } catch (InterruptedException e) {
                AlterShieldLoggerManager.log("error", logger, e, "cache.gc.interrupted");
            }
        }).start();
    }

    private volatile ConcurrentHashMap<CacheKey, CacheRst> cacheMap = new ConcurrentHashMap<>();

    private synchronized void cacheGc() {
        try {
            int originSize = 0;
            int newSize = 0;
            ConcurrentHashMap<CacheKey, CacheRst> cacheMap = new ConcurrentHashMap<>();
            for (Entry<CacheKey, CacheRst> en : this.cacheMap.entrySet()) {
                ++originSize;
                if (en == null || en.getValue() == null) {
                    continue;
                }
                if (en.getValue().getGmt() > System.currentTimeMillis() - AlterShieldConstant.META_CACHE_TIMEOUT_MILLI) {
                    ++newSize;
                    cacheMap.put(en.getKey(), en.getValue());
                }
            }
            this.cacheMap = cacheMap;
            AlterShieldLoggerManager.log("info", logger, "cache.gc.finished", "from " + originSize + " to " + newSize);
        } catch (Throwable e) {
            AlterShieldLoggerManager.log("error", logger, e, "cache.gc.error");
        }
    }

    /**
     * @param method
     * @param args
     * @return
     */
    public CacheRst getCache(Method method, Object[] args) {
        if (!cachable(method)) {
            return null;
        }
        CacheRst r = cacheMap.get(new CacheKey(method, args));
        return r;
    }

    /**
     * @param method
     * @param args
     * @param rst
     */
    public void putCache(Method method, Object[] args, Object rst) {
        if (!cachable(method)) {
            return;
        }
        cacheMap.put(new CacheKey(method, args), new CacheRst(rst));
    }

    private static boolean cachable(Method method) {
        MetaCache[] ann = method.getAnnotationsByType(
                MetaCache.class);
        if (ann == null || ann.length == 0) {
            return false;
        }
        return true;
    }

    private static class CacheKey {
        private final Method   mtd;
        private final Object[] args;

        public CacheKey(Method mtd, Object[] args) {
            super();
            this.mtd = mtd;
            this.args = args;
        }

        /**
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(args);
            result = prime * result + ((mtd == null) ? 0 : mtd.hashCode());
            return result;
        }

        /**
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) { return true; }
            if (obj == null) { return false; }
            if (getClass() != obj.getClass()) { return false; }
            CacheKey other = (CacheKey) obj;
            if (!Arrays.equals(args, other.args)) { return false; }
            if (mtd == null) {
                if (other.mtd != null) { return false; }
            } else if (!mtd.equals(other.mtd)) { return false; }
            return true;
        }

    }

    /**
     * @author shuo.qius
     * @version Jul 30, 2019
     */
    public static class CacheRst {
        private final Object obj;
        private final long   gmt;

        /**
         * @param obj
         */
        public CacheRst(Object obj) {
            super();
            this.obj = obj;
            this.gmt = System.currentTimeMillis();
        }

        /**
         * @return property value of {@link #gmt}
         */
        public long getGmt() {
            return gmt;
        }

        /**
         * @return property value of {@link #obj}
         */
        public Object getObj() {
            return obj;
        }

    }

}
