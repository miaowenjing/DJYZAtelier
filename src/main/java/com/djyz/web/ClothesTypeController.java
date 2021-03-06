package com.djyz.web;

import com.alibaba.fastjson.JSONObject;
import com.djyz.util.AjaxRes;
import com.djyz.domain.ClothesType;
import com.djyz.service.ClothesTypeService;
import com.djyz.util.CommonUtil;
import com.djyz.util.StatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * combotype的增改查写了，删除没写✔✔
 * Combo（摄影套餐）写了增加（增加可拍摄地点和相应的价格）和查询 (增加还有点问题，地点---还有价格还没上传)
 * ClothesType增删查改都写了,获取全部租赁衣服种类-包括租赁服装✔✔✔
 * RentClothes增删查改都写了，但是修改有问题（1.put方法用不了 用post暂时代替）✔✔
 * GuestPhoto写了增加和查询、删除✔✔
 * ArticleController的增加文章、踩文章、点赞, 查询全部文章，根据用户id查询文章,根据id删除文章✔✔
 * customer注册--添加客户，登录（电话号码，密码）,  根据id查询,查询所有客户✔✔
 * comment写了增加评论(更新文章表中的评论数量) 根据文章id查询评论  根据文章id查询评论数量✔✔
 * ComboOrder 查询,根据id查询订单，增加（增加一个订单还得在shooting_days的表里增加次数 ok）,修改订单状态✔✔
 * employee（实体类建好了，只有controller，没有service）
 * role
 * ShootingLocation---增加摄影地点，根据地点获取可拍摄的天数（添加摄影套餐预定之前）✔✔
 * RentClothesOrder
 *（剩下员工权限和衣服订单！）
 * 问题：跨域上传图片，权限管理cookie跨域
 * */

@Controller
@Api(value = "/ClothesType", tags = "ClothesType接口")
public class ClothesTypeController {
    @Autowired
    private ClothesTypeService clothesTypeService;

    /*获取全部租赁衣服种类*/
    @GetMapping("/getAllClothesTypes")
    @ResponseBody
    @ApiOperation(value = "根据id获取用户信息", notes = "获取信息", httpMethod = "GET")
    public List<ClothesType> getAllClothesType(){
        List<ClothesType> allClothesType = clothesTypeService.getAllClothesType();
        return allClothesType;
    }

    /*获取全部租赁衣服种类-包括租赁服装*/
    @GetMapping("/getTypeAndClothes")
    @ResponseBody
    public List<ClothesType> getTypeAndClothes(){
        List<ClothesType> allClothesType = clothesTypeService.getTypeAndClothes();
        return allClothesType;
    }



    /*增加衣服种类*/
    @PostMapping("/addClothesType")
    @ResponseBody
    public AjaxRes addClothesType(ClothesType clothesType){
        return clothesTypeService.addClothesType(clothesType);
    }

    /*删除衣服分类*/
    @DeleteMapping("/deleteClothesType/{id}")
    @ResponseBody
    public AjaxRes deleteClothesType(@PathVariable("id") Long id){
        return clothesTypeService.deleteClothesType(id);
    }

    /*根据id获取---修改衣服种类信息*/
    @GetMapping("/getClothesTypeWith/{id}")
    @ResponseBody
    public ClothesType editClothesType(@PathVariable("id") Long id){
        return clothesTypeService.getClothesTypeWithId(id);
    }

    /*更新*/
    @PutMapping("/editClothesType")
    @ResponseBody
    public AjaxRes editClothesType(ClothesType clothesType){
        return clothesTypeService.updateClothesType(clothesType);
    }



}
