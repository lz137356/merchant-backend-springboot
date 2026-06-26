package com.qlzf.commons.handler;

import com.lxtx.pay.handler.BaseHandler;
import org.springframework.stereotype.Component;

/**
 * 兼容层：将 com.qlzf.commons.handler.SimpleIbatisEntityHandler 映射到 BaseHandler
 * 用于支持遗留代码，新代码应直接使用 BaseHandler
 */
@Component
public abstract class SimpleIbatisEntityHandler<T> extends BaseHandler<T> {
    // 所有方法继承自 BaseHandler
}
