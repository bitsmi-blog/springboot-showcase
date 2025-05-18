package com.bitsmi.springbootshowcase.testing.testsupport.instancio;

import static org.instancio.Select.all;

import com.bitsmi.springbootshowcase.testing.Category;
import org.instancio.Instancio;

public class CategoryObjectMother
{
    private static final String CATEGORY_VALUES = """
        externalId,name
        category-1,Category 1
        category-2,Category 2
        """;

    public static Category aCategoryWithExternalId(String externalId)
    {
        return Instancio.of(Category.class)
                .applyFeed(all(Category.class), feed -> feed.ofString(CATEGORY_VALUES)
                    .withTagKey("externalId")
                    .withTagValue(externalId)
                )
                .create();
    }
}
