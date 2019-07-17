package com.wechat_order.sell.controller;

import com.wechat_order.sell.VO.ProductInfoVo;
import com.wechat_order.sell.VO.ProductVO;
import com.wechat_order.sell.VO.ResultVO;
import com.wechat_order.sell.dataobject.ProductCategory;
import com.wechat_order.sell.dataobject.ProductInfo;
import com.wechat_order.sell.service.CategoryService;
import com.wechat_order.sell.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import com.wechat_order.sell.utils.ResultVOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 卖家商品
 */
//@CrossOrigin(origins = "*",maxAge = 3600)
//@CrossOrigin(
//        origins = "*",
//        allowedHeaders = "*",
//        allowCredentials = "true",
//        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD}
//)
@RestController
//返回的是json格式——使用@Controller 注解，在对应的方法上，视图解析器可以解析return 的jsp,html页面，并且跳转到相应页面
//若返回json等内容到页面，则需要加@ResponseBody注解
@RequestMapping("/buyer/product")   //url前缀
//RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;    //查类目需要调用这个服务

    @GetMapping("/list")
    //@GetMapping是一个组合注解，等价于@RequestMapping(method = RequestMethod.GET)，它将HTTP Get请求映射到特定的处理方法上。
    // value：String 类型，请求参数名
//    @Cacheable(cacheNames = "product",key = "123")
    @Cacheable(cacheNames = "product",key = "#sellerId",condition = "#sellerId.length()>3",unless = "#result.getCode() != 0")
    //使用spel表达式进行key动态命名
    //使用condition进行条件判别
    //unless如果不"..."则进行缓存
    //@Cacheable    触发缓存填充，初次调用执行方法，此后再redis中查找
    //cacheNames相当于前缀，key就是平时redis中的key
    public ResultVO list(@RequestParam("sellerId") String sellerId){
        //参数是为了测试缓存用的

        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2.查询类目（一次性查询）
        List<Integer> categoryTypeList = new ArrayList<>();
        //传统方法
        /*for (ProductInfo productInfo : productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }*/
        //精简做法（Java8 lambda表达式)
        categoryTypeList = productInfoList.stream()
                .map(e->e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装，不要把数据库的查询放到for循环中
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVo> productInfoVos = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);    //使用BeanUtils进行对象间同名同类型成员的复制
                    productInfoVos.add(productInfoVo);
                }
            }
            productVO.setProductInfoVoList(productInfoVos);
            productVOList.add(productVO);
        }

/*         ResultVO<Object> resultVO = new ResultVO<>();

        ProductVO productVO = new ProductVO();
        ProductInfoVo productInfoVo = new ProductInfoVo();

        productVO.setProductInfoVoList(Arrays.asList(productInfoVo));

        resultVO.setData(Arrays.asList(productVO));
        resultVO.setData(productVOList);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO; 初次试验*/

        return ResultVOUtil.success(productVOList);
    }
}

