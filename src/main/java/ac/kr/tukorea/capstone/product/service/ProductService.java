package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.repository.CategoryRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepositoryImpl productRepository;


    public ProductListDto getProductList(long categoryId, String filter, Pageable pageable){
        Category category = categoryRepository.findById(categoryId).get();
        Slice<ProductDto> products = productRepository.findByCategoryAndFilter(category, getFilterList(filter), pageable);

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName() , products.getContent(), products.hasNext());

        return productListDto;
    }

    public String[][] getFilterList(String filter){
        String[] filters_code = filter.split("|");
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
