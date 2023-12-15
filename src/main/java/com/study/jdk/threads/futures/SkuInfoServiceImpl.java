//package com.study.jdk.threads.futures;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//
//import java.util.concurrent.CompletableFuture;
//
//public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService{
//
//    SkuItemVo skuItemVo = new SkuItemVo();
//
//    CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
//        //1、sku基本信息的获取  pms_sku_info
//        SkuInfoEntity info = this.getById(skuId);
//        skuItemVo.setInfo(info);
//        return info;
//    }, executor);
//
//
//    CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
//        //3、获取spu的销售属性组合
//        List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrBySpuId(res.getSpuId());
//        skuItemVo.setSaleAttr(saleAttrVos);
//    }, executor);
//
//
//    CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
//        //4、获取spu的介绍    pms_spu_info_desc
//        SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(res.getSpuId());
//        skuItemVo.setDesc(spuInfoDescEntity);
//    }, executor);
//
//
//    CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync((res) -> {
//        //5、获取spu的规格参数信息
//        List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(), res.getCatalogId());
//        skuItemVo.setGroupAttrs(attrGroupVos);
//    }, executor);
//
//
//    // Long spuId = info.getSpuId();
//    // Long catalogId = info.getCatalogId();
//
//    //2、sku的图片信息    pms_sku_images
//    CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
//        List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
//        skuItemVo.setImages(imagesEntities);
//    }, executor);
//
//    CompletableFuture<Void> seckillFuture = CompletableFuture.runAsync(() -> {
//        //3、远程调用查询当前sku是否参与秒杀优惠活动
//        R skuSeckilInfo = seckillFeignService.getSkuSeckilInfo(skuId);
//        if (skuSeckilInfo.getCode() == 0) {
//            //查询成功
//            SeckillSkuVo seckilInfoData = skuSeckilInfo.getData("data", new TypeReference<SeckillSkuVo>() {
//            });
//            skuItemVo.setSeckillSkuVo(seckilInfoData);
//
//            if (seckilInfoData != null) {
//                long currentTime = System.currentTimeMillis();
//                if (currentTime > seckilInfoData.getEndTime()) {
//                    skuItemVo.setSeckillSkuVo(null);
//                }
//            }
//        }
//    }, executor);
//
//
//    //等到所有任务都完成
//        CompletableFuture.allOf(saleAttrFuture,descFuture,baseAttrFuture,imageFuture,seckillFuture).get();
//
//        return skuItemVo;
//}
