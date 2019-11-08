package com.github.framework.evo.autoconfigure.data.mybatisplus;

import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: Kyll
 * Date: 2019-03-29 18:35
 */
@ConditionalOnClass(MybatisMapWrapperFactory.class)
@Configuration
public class MybatisPlusConfiguration {
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setLimit(Integer.MAX_VALUE);// 默认每页限制500条记录，这里使用整型最大值，相当于没有限制了
		return paginationInterceptor;
	}
}
