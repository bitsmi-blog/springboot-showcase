package com.bitsmi.springbootshowcase.testing.testsupport;

import static org.instancio.Select.all;
import static org.instancio.Select.field;

import com.bitsmi.springbootshowcase.testing.Category;
import com.bitsmi.springbootshowcase.testing.Product;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.instancio.Assign;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.feed.Feed;
import org.instancio.feed.FeedSpec;
import org.instancio.feed.FunctionProvider;

public class ProductObjectMother
{
    private static final Model<Product> MODEL = buildModel();

    private static final String PRODUCT_VALUES = """
        id,externalId,name,categoryExtId,creationDate,lastUpdated,tag
        1001,product-1,Product 1,category-1,2024-01-01T00:00:00,2024-01-10T00:00:00,
        1002,product-2,Product 2,category-1,2024-02-01T00:00:00,2024-02-10T00:00:00,TEST_PRODUCT
        1003,product-3,Product 3,category-1,2024-03-01T00:00:00,2024-03-10T00:00:00,
        1004,product-4,Product 4,category-1,2024-04-01T00:00:00,2024-04-10T00:00:00,
        1005,product-5,Product 5,category-1,2024-05-01T00:00:00,2024-05-10T00:00:00,
        """;

    public static Product aRandomProduct()
    {
        return Instancio.of(Product.class)
            .generate(field(Product::id), gen -> gen.longs().min(1L))
            .generate(field(Product::externalId), gen -> gen.string().alphaNumeric())
            .generate(field(Product::name), gen -> gen.string().alphaNumeric())
            .generate(field(Product::creationDate), gen -> gen.temporal()
                    .localDateTime()
                    .range(
                        LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN),
                        LocalDateTime.now()
                    )
            )
            .assign(Assign.valueOf(field(Product::creationDate))
                    .to(field(Product::lastUpdated))
                    .as((LocalDateTime creationDate) -> Instancio.gen()
                        .temporal()
                        .localDateTime()
                        .range(
                            creationDate,
                            LocalDateTime.now()
                        )
                        .get()
                    )
            )
            // Enable it for troubleshooting
//            .verbose()
            .create();
    }

    public static Product aRandomProductFromModel()
    {
        return Instancio.of(MODEL)
            // Override values
            .set(field(Product::name), "Modified Name")
            .create();
    }

    public static List<Product> aListOfRandomProducts(int size)
    {
        return Instancio.ofList(MODEL)
            .size(size)
            .create();
    }

    public static Product aRandomProductFromTestValues()
    {
        return Instancio.of(Product.class)
            .applyFeed(all(Product.class), feed -> feed.ofString(PRODUCT_VALUES))
            .create();
    }

    public static Product aTaggedProductFromTestValues(String tag)
    {
        return Instancio.of(Product.class)
            .applyFeed(all(Product.class), feed -> feed.of(ProductFeed.class)
                .withTagKey("tag")
                .withTagValue(tag)
            )
            .create();
    }

    private static Model<Product> buildModel()
    {
        return Instancio.of(Product.class)
            .generate(field(Product::id), gen -> gen.longs().min(1L))
            .generate(field(Product::externalId), gen -> gen.string().alphaNumeric())
            .generate(field(Product::name), gen -> gen.string().alphaNumeric())
            .generate(field(Product::creationDate), gen -> gen.temporal()
                .localDateTime()
                .range(
                    LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN),
                    LocalDateTime.now()
                )
            )
            .assign(Assign.valueOf(field(Product::creationDate))
                .to(field(Product::lastUpdated))
                .as((LocalDateTime creationDate) -> Instancio.gen()
                    .temporal()
                    .localDateTime()
                    .range(
                        creationDate,
                        LocalDateTime.now()
                    )
                    .get()
                )
            )
            .toModel();
    }

    @Feed.Source(string = PRODUCT_VALUES)
    interface ProductFeed extends Feed
    {
        @FunctionSpec(params = { "categoryExtId" }, provider = CategoryProvider.class)
        FeedSpec<Category> category();

        class CategoryProvider implements FunctionProvider
        {
            Category get(String categoryExtId)
            {
                return CategoryObjectMother.aCategoryWithExternalId(categoryExtId);
            }
        }
    }
}
