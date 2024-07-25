package org.squirrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.squirrel.po.Product;

/**
 * @author luobaosong
 * @date 2024-07-25 19:18
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
