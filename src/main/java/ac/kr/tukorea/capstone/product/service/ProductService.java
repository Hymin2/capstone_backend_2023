package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.CategoryRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepositoryImpl productRepositoryImpl;
    private final ProductRepository productRepository;

    public ProductListDto getProductList(long categoryId, String filter, String name){
        Category category = categoryRepository.findById(categoryId).get();
        List<ProductDto> products = productRepositoryImpl.findByCategoryAndFilter(category, getFilterList(filter), name);

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName() , products);

        return productListDto;
    }

    public ProductDetailsDto getProductDetails(long productId){
        Product product = productRepository.findById(productId).get();

        ProductDetailsDto productDetailsDto = productRepositoryImpl.findDetailsByProduct(product);

        return productDetailsDto;
    }

    public String[][] getFilterList(String filter){
        if(filter == null) return null;

        String[] filters_code = filter.split(",");
        String[][] filters_value = new String[filters_code.length][2];

        for(int i = 0; i < filters_code.length; i++){
            switch (filters_code[i]){
                case "S1":
                case "s1":
                    filters_value[i][0] = "RAM";
                    filters_value[i][1] = "4GB";
                    break;
                case "S2":
                case "s2":
                    filters_value[i][0] = "RAM";
                    filters_value[i][1] = "6GB";
                    break;
                case "S3":
                case "s3":
                    filters_value[i][0] = "RAM";
                    filters_value[i][1] = "8GB";
                    break;
                case "S4":
                case "s4":
                    filters_value[i][0] = "RAM";
                    filters_value[i][1] = "10GB";
                    break;
                case "S5":
                case "s5":
                    filters_value[i][0] = "RAM";
                    filters_value[i][1] = "12GB";
                    break;
                case "S6":
                case "s6":
                    filters_value[i][0] = "RAM";
                    filters_value[i][1] = "14GB";
                    break;
            }
        }
        return filters_value;
    }
}
