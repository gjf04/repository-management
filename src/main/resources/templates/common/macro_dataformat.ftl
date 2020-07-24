<#-- 商品价格格式化 -->
<#macro display_product_price product>
<#if (product.saleGuidePrice)?? && product.saleGuidePrice != 0>
￥<strong class="strong-price">${(product.saleGuidePrice)?string("0")}</strong>
<#else>
<strong class="strong-price"></strong>
</#if>
</#macro>

<#-- 商品价格格式化 使用em-price样式-->
<#macro display_product_price1 product>
<#if (product.saleGuidePrice)?? && product.saleGuidePrice != 0>
<em class="em-price">￥${(product.saleGuidePrice)?string("0")}</em>
<#else>
<em class="em-price"></em>
</#if>
</#macro>

<#-- 产品图片链接 -->
<#macro get_product_page_url recommendProduct>
<#if (recommendProduct.productLinkUrl)??>
${recommendProduct.productLinkUrl}
<#else>
${domainUrlUtil.PHP_BASEURL_DOMAIN+'product/'+recommendProduct.productId+'.html'}
</#if>	
</#macro>

<#-- 产品图片src -->
<#macro get_product_img_url recommendProduct width height>
<#if recommendProduct.customTailorImageKey?? && recommendProduct.customTailorImageKey!=''>
${recommendProduct.customTailorImageKey}
<#elseif recommendProduct.defaultImageFileId?? && recommendProduct.defaultImageFileId!=''>
${domainUrlUtil.PRODUCT_IMG_SERVER_BASEURL_DOMAIN+recommendProduct.defaultImageFileId+'_'+width+'_'+height+'.jpg'}
<#--${domainUrlUtil.PRODUCT_IMG_SERVER_BASEURL_DOMAIN+recommendProduct.defaultImageFileId} -->
<#else>
</#if>
</#macro>

<#-- 首页面产品链接title -->
<#macro get_product_link_title_for_index recommendProduct>
${recommendProduct.productTitle!''} ${recommendProduct.productName!''}
</#macro>
<#-- 首页面产品图片alt -->
<#macro get_product_img_alt_for_index recommendProduct>
${recommendProduct.productTitle!''} ${recommendProduct.productName!''}
</#macro>

<#-- 首页面抢购链接title -->
<#macro get_flash_sales_link_title_for_index flashSale>
${flashSale.product.brand.brandName+flashSale.product.productType.typeName+' '+flashSale.product.productName}
</#macro>
<#-- 首页面抢购图片alt -->
<#macro get_flash_sales_img_alt_for_index flashSale>
${flashSale.product.brand.brandName+flashSale.product.productType.typeName+' '+flashSale.product.productName}
</#macro>
<#-- 首页面抢购产品图片链接 -->
<#macro get_flash_sales_product_page_url product>
${domainUrlUtil.PHP_BASEURL_DOMAIN+'product/'+product.id+'.html'}
</#macro>

<#-- 海尔公告链接 -->
<#macro get_article_page_url article>
${domainUrlUtil.PHP_BASEURL_DOMAIN+'article/'+article.id+'.html'}
</#macro>

<#-- 海尔静态广告图片url -->
<#macro get_static_advertise_img_url staticAdvertise>
${staticAdvertise.advertiseImageKey}
</#macro>


<#-- 获取字典代码对应的中文名称 -->
<#macro codeText codeDiv codeCd>
${codeManager.getCodeText(codeDiv, codeCd?string)}
</#macro>

<#macro display_920product_price price>
<span>嗨拼价<strong>
    <small>&yen;</small>
    <#if price??>
        ${(price?string)}</strong></span>
    <#else>
    </strong></span>
    </#if>
</#macro>

<#--超值闪拍-闪拍价格-->
<#macro display_920_kill_price dto>
<del>原价&yen;<#if (dto.saleGuidePrice)?? && dto.saleGuidePrice != 0>${(dto.saleGuidePrice)?string("0")}</#if></del>
<span>闪拍价
    <strong><small>&yen;</small><#if (dto.secKillPrice)?? && dto.secKillPrice != 0>${(dto.secKillPrice)?string("0")}</#if></strong>
</span>
</#macro>

<#macro display_920_flash_price dto>
<del>原价&yen;<#if (dto.saleGuidePrice)?? && dto.saleGuidePrice != 0>${(dto.saleGuidePrice)?string("0")}</#if></del>
<span>闪拍价
    <strong><small>&yen;</small><#if (dto.flashSalePrice)?? && dto.flashSalePrice != 0>${(dto.flashSalePrice)?string("0")}</#if></strong>
</span>
</#macro>
<#macro display_920_hot_price dto>
<del>原价&yen;<#if (dto.saleGuidePrice)?? && dto.saleGuidePrice != 0>${(dto.saleGuidePrice)?string("0")}</#if></del>
<span>闪拍价
    <strong><small>&yen;</small><#if (dto.saleGuidePrice)?? && dto.saleGuidePrice != 0>${(dto.saleGuidePrice)?string}</#if></strong>
</span>
</#macro>

<#macro display_1111product_price pdt>
<div class="fl">
<span>嗨拼价<strong>
    <small>&yen;</small>
    <#if pdt.prePrice??>
    <#assign pre=1 />
    ${(pdt.prePrice?string)}</strong></span>
    <#elseif pdt.saleGuidePrice??>
    <#assign pre=0 />
    ${(pdt.saleGuidePrice?string)}</strong></span>
    <#else>
    </strong></span>
    </#if>
</div>
<a class="<#if pdt.prePrice??>kttxzc-buy-btn<#else>crazy-buy-btn</#if> fr" data-pre="${pre}" data-sku="${(pdt.sku)!''}" href="#" <#if (pdt.productId)??>data-href="${domainUrlUtil.EHAIER_BASE_DOMAIN}product/${pdt.productId}.html" target="_blank"</#if>>立即秒杀</a>
</#macro>

<#macro display_1111_hot_price dto>
<div class="fl">
<del>原价&yen;<#if (dto.saleGuidePrice)?? && dto.saleGuidePrice != 0>${(dto.saleGuidePrice)?string("0")}</#if></del>
<span>闪拍价
    <strong>
        <small>&yen;</small>
    <#if dto.prePrice??>
    <#assign pre=1 />
    ${(dto.prePrice?string)}
    <#elseif (dto.saleGuidePrice)?? && dto.saleGuidePrice != 0>
    <#assign pre=0 />
    ${(dto.saleGuidePrice)?string}
    </#if>
    </strong>
</span>
</div>
<a class="<#if dto.prePrice??>kttx-buy-btn<#else>buy-btn</#if> fr" data-pre="${pre}" data-sku="${(dto.sku)!''}" href="#" <#if (dto.productId)??>data-href="${domainUrlUtil.EHAIER_BASE_DOMAIN}product/${dto.productId}.html" target="_blank"</#if>>立即闪拍</a>
</#macro>
