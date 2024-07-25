package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.squirrel.dto.ProductParamDto;
import org.squirrel.dto.SquirrelPageDto;
import org.squirrel.po.Product;
import org.squirrel.service.ProductService;

import javax.annotation.Resource;

/**
 * @author luobaosong
 * @date 2024-07-25 19:38
 */
@RestController
@RequestMapping("/product")
@Tag(name = "商品管理")
public class ProductController {

    @Resource
    private ProductService productService;


    @Operation(summary = "获取所有商品信息", description = "获取商品信息")
    @PostMapping("/getAllProduct")
    public SquirrelPageDto<Product> getAllProduct(@RequestBody ProductParamDto productParamDto) {
        return productService.findAllProduct(productParamDto);
    }

}
