package com.djyz.web;

import com.alibaba.fastjson.JSONObject;
import com.djyz.util.*;
import com.djyz.domain.RentClothes;
import com.djyz.service.RentClothesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
@Api(value = "/RentClothes", tags = "RentClothes接口")
public class RentClothesController {
    @Autowired
    private RentClothesService rentClothesService;
    @Autowired
    private FileUpload fileUpload;

    /*根据服装分类的id获取服装*/
    @GetMapping("/getClothesWithTypeId/{cloType}")
    @ResponseBody
    public List<RentClothes> getClothesWithTypeId(@PathVariable Long cloType){
        List<RentClothes> clothes = rentClothesService.getClothesWithTypeId(cloType);
        return clothes;
    }

    /*添加租赁服装*/
    @ApiOperation("租赁服装增加--")
    @PostMapping(value = "/addRentClothes",
            consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ResponseBody
    public AjaxRes addRentClothes(String cloName, Double cloPrice, MultipartFile file,  Long cloAmount, Long cloType, HttpSession session) throws IOException {
        RentClothes rentClothes = new RentClothes();
        /*上传图片*/
        if(file != null){
            String filename = fileUpload.upload(file, session);
            //添加图片字段
            rentClothes.setCloPicture(filename);
        }
        /*增加租赁服装的字段*/
        rentClothes.setCloName(cloName);
        rentClothes.setCloPrice(cloPrice);
        rentClothes.setCloAmount(cloAmount);
        rentClothes.setCloType(cloType);

        return rentClothesService.addRentClothes(rentClothes);
    }


    /*根据id删除租赁服装*/
    @DeleteMapping("/deleteRentClothesWithId/{cloId}")
    @ResponseBody
    public AjaxRes deleteRentClothesWithId(@PathVariable Long cloId,HttpSession session){
        AjaxRes ajaxRes = rentClothesService.deleteRentClothesWithId(cloId,session);
        return ajaxRes;
    }

    /*根据id获取租赁服装*/
    @GetMapping("/getClothesWithId/{cloId}")
    @ResponseBody
    public RentClothes getClothesWithId(@PathVariable Long cloId){
        return rentClothesService.getClothesWithId(cloId);
    }

    /*修改租赁服装------------put过不来方法上--先使用post代替-----返回值错误，但是可以正确修改内容*/
   @ApiOperation("租赁服装修改--")
   @PostMapping(value = "/editRentClothes",
               consumes = "multipart/*", headers = "content-type=multipart/form-data")
   @ResponseBody
//    public AjaxRes editRentClothes(@PathVariable Long cloId,@PathVariable String cloName,@PathVariable Double cloPrice,
//                                @PathVariable MultipartFile file,@PathVariable Long cloAmount, @PathVariable Long cloType,HttpSession session) throws IOException {
   public AjaxRes editRentClothes(Long cloId, String cloName, Double cloPrice,
                                   MultipartFile file, Long cloAmount, Long cloType,HttpSession session) throws IOException {
       AjaxRes ajaxRes = new AjaxRes();
       RentClothes rentClothes = new RentClothes();
       RentClothes clothesWithId = rentClothesService.getClothesWithId(cloId);
       try{
           if(cloName != null || !"".equals(cloName))
               rentClothes.setCloName(cloName);
           if(cloPrice != null)
               rentClothes.setCloPrice(cloPrice);
           if(cloAmount != null)
               rentClothes.setCloAmount(cloAmount);
           if(cloType != null)
               rentClothes.setCloType(cloType);
           //如果file不为空，删除之前上传到服务器的图片，然后再上传新的图片
           if(file != null || !"".equals(file)){
               String cloPicture = clothesWithId.getCloPicture();
               //删除
               fileUpload.deleteFile(cloPicture,session);
               //上传新的图片
               String filename = fileUpload.upload(file, session);
               rentClothes.setCloPicture(filename);
           }
           //更新
           rentClothesService.updateRentClothes(rentClothes);

           ajaxRes.setMsg("更新租赁服装成功");
           ajaxRes.setSuccess(true);
       }catch (Exception e){
           ajaxRes.setMsg("更新租赁服装失败");
           ajaxRes.setSuccess(false);
       }
       return ajaxRes;
   }


   /*查询全部租赁服装-分页*/
    @GetMapping("/getAllRentClothes")
    @ResponseBody
    public PageList getAllRentClothes(QueryVo vo){
        return rentClothesService.getAllRentClothes(vo);

    }

    /*查询租赁服装-不分页-高级查询*/
    @GetMapping("/getAllClothes")
    @ResponseBody
    public List<RentClothes> getAllClothes(QueryVo vo){
        return rentClothesService.getAllClothes(vo);
    }




    }
