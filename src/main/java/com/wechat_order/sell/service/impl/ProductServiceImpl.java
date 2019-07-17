package com.wechat_order.sell.service.impl;

import com.wechat_order.sell.dataobject.ProductInfo;
import com.wechat_order.sell.dto.CartDTO;
import com.wechat_order.sell.enums.ProductStatusEnum;
import com.wechat_order.sell.enums.ResultEnum;
import com.wechat_order.sell.exception.SellException;
import com.wechat_order.sell.repository.ProductInfoRepository;
import com.wechat_order.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    @Cacheable(key = "124")
    public ProductInfo findOne(String productId) {

        Optional<ProductInfo> byId = repository.findById(productId);
        return byId.isPresent()?byId.get():null;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.Up.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @CachePut(key = "124")
    //更新缓存。key默认是方法的参数的名字
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
//            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            Optional<ProductInfo> byId = repository.findById(cartDTO.getProductId());
            ProductInfo productInfo =  byId.isPresent()?byId.get():null;
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST );
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO: cartDTOList){
            Optional<ProductInfo> byId = repository.findById(cartDTO.getProductId());
//            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            ProductInfo productInfo =  byId.isPresent()?byId.get():null;
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST );
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                throw new SellException(ResultEnum.LACK_OF_PRODUCT);
            }

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        Optional<ProductInfo> byId = repository.findById(productId);
        ProductInfo productInfo =  byId.isPresent()?byId.get():null;
        if(productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST );
        }
        if (productInfo.getProductStatusEnum().equals(ProductStatusEnum.Up)){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //上架
        productInfo.setProductStatus(ProductStatusEnum.Up.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        Optional<ProductInfo> byId = repository.findById(productId);
        ProductInfo productInfo =  byId.isPresent()?byId.get():null;
        if(productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST );
        }
        if (productInfo.getProductStatusEnum().equals(ProductStatusEnum.Down)){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //下架
        productInfo.setProductStatus(ProductStatusEnum.Down.getCode());
        return repository.save(productInfo);
    }
}
