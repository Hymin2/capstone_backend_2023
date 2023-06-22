package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.config.Exception.CategoryNotFoundException;
import ac.kr.tukorea.capstone.config.util.ImageComponent;
import ac.kr.tukorea.capstone.config.util.ProductFilter;
import ac.kr.tukorea.capstone.config.util.ProductFilterDetail;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.dto.UsedProductPriceDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.entity.UsedProductPrice;
import ac.kr.tukorea.capstone.product.repository.CategoryRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepositoryCustom;
import ac.kr.tukorea.capstone.product.repository.UsedProductPriceRepository;
import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepositoryCustom productRepositoryCustom;
    private final ProductRepository productRepository;
    private final ImageComponent imageComponent;
    private final UsedProductPriceRepository usedProductPriceRepository;

    @Transactional
    public ProductListDto getTopProductList(long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        List<ProductVo> products = productRepositoryCustom.getTopProductList(categoryId);

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName(), products);
        return productListDto;
    }

    @Transactional
    public ProductListDto getProductList(long categoryId, String filter, String name){
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        List<ProductVo> products = productRepositoryCustom.getProductList(categoryId, getFilterList(filter), name);

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName() , products);

        return productListDto;
    }

    @Transactional
    public ProductDetailsDto getProductDetails(long productId){
        List<ProductDetailVo> ProductDetails = productRepositoryCustom.getProductDetailList(productId);

        ProductDetailsDto productDetailsDto = new ProductDetailsDto(productId, ProductDetails);

        return productDetailsDto;
    }

    public Map<String, List<BooleanExpression>> getFilterList(String filter){
        if(filter == null || filter.equals("")) return new HashMap<>();

        String[] filters = filter.split("(?<=\\G.{" + 5 + "})");

        Map<String, List<BooleanExpression>> filterMap = new HashMap<>();

        filterMap.put("company", new ArrayList<>());
        filterMap.put("price", new ArrayList<>());
        filterMap.put("size", new ArrayList<>());
        filterMap.put("processor", new ArrayList<>());
        filterMap.put("ram", new ArrayList<>());
        filterMap.put("memory", new ArrayList<>());

        for(String str : filters){
            String filterName = ProductFilterDetail.getValue(str);
            System.out.println(str + " " + filterName);

            if(filterName.startsWith("COMPANY")) filterMap.get("company").add(ProductFilterDetail.getFilter(str));
            else if(filterName.startsWith("PRICE")) filterMap.get("price").add(ProductFilterDetail.getFilter(str));
            else if(filterName.startsWith("SIZE")) filterMap.get("size").add(ProductFilterDetail.getFilter(str));
            else if(filterName.startsWith("PROCESSOR")) filterMap.get("processor").add(ProductFilterDetail.getFilter(str));
            else if(filterName.startsWith("RAM")) filterMap.get("ram").add(ProductFilterDetail.getFilter(str));
            else if(filterName.startsWith("MEMORY")) filterMap.get("memory").add(ProductFilterDetail.getFilter(str));
        }

        return filterMap;
    }

    public Resource getProductImage(String name){
        String imgPath = "";
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win"))
            imgPath = "c:/capstone/resource/product/img/" + name;
        else if(os.contains("linux"))
            imgPath = "/capstone/resource/product/img/" + name;

        return imageComponent.getImage(imgPath);
    }

    public UsedProductPriceDto getUsedPriceList(long product, int month) {
        List<UsedProductPriceVo> usedProductPrices = productRepositoryCustom.getUsedProductPriceList(product);
        List<UsedProductPriceVo> usedProductPricesFromMonth = new ArrayList<>();

        Date endDate = new Date();
        Date startDate = getBeforeMonthDate(endDate, month);

        Date current = startDate;

        while (current.compareTo(endDate) <= 0) {
            usedProductPricesFromMonth.add(new UsedProductPriceVo(new java.sql.Date(current.getTime())));
            current = getAfterOneDayDate(current);
        }

        int i, k = 0;

        for(i = 0; i < usedProductPricesFromMonth.size(); i++){
            if(i != 0 && k == usedProductPrices.size()){
                usedProductPricesFromMonth.get(i).setPrice(usedProductPricesFromMonth.get(i - 1).getPrice());
            }

            for (; k < usedProductPrices.size(); k++) {
                if(usedProductPricesFromMonth.get(i).getTime().compareTo(usedProductPrices.get(k).getTime()) >= 0)
                    usedProductPricesFromMonth.get(i).setPrice(usedProductPrices.get(k).getPrice());
                else break;
            }

            if(i != 0 && usedProductPricesFromMonth.get(i).getPrice() == 0)
                usedProductPricesFromMonth.get(i).setPrice(usedProductPricesFromMonth.get(i - 1).getPrice());
        }

        return new UsedProductPriceDto(product, usedProductPricesFromMonth);
    }

    public Date getBeforeMonthDate(Date date, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month * -1);

        return calendar.getTime();
    }

    public Date getAfterOneDayDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);

        return calendar.getTime();
    }
}
