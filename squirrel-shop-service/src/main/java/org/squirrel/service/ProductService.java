package org.squirrel.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.f4b6a3.uuid.UuidCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.squirrel.dto.PrizeDto;
import org.squirrel.dto.PrizeParamDto;
import org.squirrel.dto.ProductParamDto;
import org.squirrel.dto.SquirrelPageDto;
import org.squirrel.enums.ProductStatusEnum;
import org.squirrel.mapper.ProductMapper;
import org.squirrel.po.Prize;
import org.squirrel.po.Product;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author luobaosong
 * @date 2024-07-25 19:26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;


    /**
     * 返回map,map key为Name,value为商品ID
     */
    public Map<String, String> saveProductByWithOutId(List<Product> productList) {
        Map<String, String> nameIdMap = new HashMap<>();
        productList.forEach(product -> {
            // 如果名称和价格相同的情况下,不进行插入
            Product productByNameAndPrice = findProductByNameAndPrice(product.getProductName(), product.getProductMoney());
            // 存在的情况下不插入商品了
            if (productByNameAndPrice != null) {
                nameIdMap.put(product.getProductName(), productByNameAndPrice.getProductId());
                return;
            }
            String productId = UuidCreator.getTimeOrdered().toString();
            product.setProductId(productId);
            product.setProductStatus(ProductStatusEnum.VALID_STATUS.getCode());
            product.setCreateDate(new Date());
            productMapper.insert(product);
            nameIdMap.put(product.getProductName(), productId);
        });
        return nameIdMap;
    }


    public void saveProduct(Product product) {
        log.info("saveProduct product : {}", JSONObject.toJSONString(product));
        product.setProductId(UuidCreator.getTimeOrdered().toString());
        product.setProductStatus(ProductStatusEnum.VALID_STATUS.getCode());
        product.setCreateDate(new Date());
        productMapper.insert(product);
    }

    public void updateProduct(Product product) {
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("product_id", product.getProductId());
        log.info("updateProduct product : {}", JSONObject.toJSONString(product));
        productMapper.update(product, updateWrapper);
    }


    public SquirrelPageDto<Product> findAllProduct(ProductParamDto productParamDto) {
        int page = productParamDto.getPage();
        int pageSize = productParamDto.getPageSize();
        Page<Product> productPage = new Page<>(page, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(productParamDto.getProductName()), "product_name", productParamDto.getProductName());
        queryWrapper.eq(StringUtils.isNotBlank(productParamDto.getProductId()), "product_id", productParamDto.getProductId());
        Page<Product> productDtoPage = productMapper.selectPage(productPage, queryWrapper);
        long total = productDtoPage.getTotal();
        if (total == 0) {
            return new SquirrelPageDto<>(page, pageSize, total);
        }
        List<Product> records = productDtoPage.getRecords();
        SquirrelPageDto<Product> productSquirrelPageDto = new SquirrelPageDto<>(page, pageSize, total, records);
        log.info("findAllProduct prize : {}", JSONObject.toJSONString(productSquirrelPageDto));
        return productSquirrelPageDto;
    }

    public List<Product> findProductByIdList(List<String> productIdList) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIdList);
        queryWrapper.eq("product_status", ProductStatusEnum.VALID_STATUS.getCode());
        return productMapper.selectList(queryWrapper);
    }

    public Product findProductByNameAndPrice(String productName, BigDecimal price) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_name", productName);
        queryWrapper.eq("product_money", price);
        return productMapper.selectOne(queryWrapper);
    }
}

